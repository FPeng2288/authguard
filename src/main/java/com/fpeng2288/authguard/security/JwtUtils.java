package com.fpeng2288.authguard.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ClassName: JwtUtils
 * Package: com.fpeng2288.authguard.security
 * Description: Utility class for handling JWT operations including token generation,
 * validation, parsing, and refresh functionality.
 *
 * @author Fan Peng
 * Create 2024/12/24 2:30
 * @version 1.0
 */
@Slf4j
@Component
public class JwtUtils implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final String ROLES_KEY = "roles";
    private static final String EMAIL_KEY = "email";
    private static final String USER_ID_KEY = "userId";
    private static final long REFRESH_WINDOW = 24 * 60 * 60 * 1000L; // 24 hours

    @Value("${auth.jwt.secret}")
    private String jwtSecret;

    @Value("${auth.jwt.expiration}")
    private int jwtExpirationMs;

    private SecretKey key;

    /**
     * Initialize the JWT key from the secret on bean creation
     */
    @PostConstruct
    protected void init() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generate JWT token from authentication object
     *
     * @param authentication the authentication object
     * @return the generated JWT token
     */
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        String roles = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(userPrincipal.getUsername())
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .claim(ROLES_KEY, roles)
                .claim(EMAIL_KEY, userPrincipal.getEmail())
                .claim(USER_ID_KEY, userPrincipal.getId())
                .signWith(key)
                .compact();
    }

    /**
     * Generate a new token based on the claims from an existing token
     *
     * @param oldToken the existing token to refresh
     * @return Optional containing the new token, or empty if refresh is not possible
     */
    public Optional<String> refreshToken(String oldToken) {
        if (!canTokenBeRefreshed(oldToken)) {
            return Optional.empty();
        }

        try {
            Claims claims = getAllClaimsFromToken(oldToken);
            return Optional.of(Jwts.builder()
                    .claims(claims)
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                    .signWith(key)
                    .compact());
        } catch (Exception e) {
            log.error("Failed to refresh token", e);
            return Optional.empty();
        }
    }

    /**
     * Check if a token can be refreshed
     *
     * @param token the token to check
     * @return true if the token can be refreshed
     */
    public boolean canTokenBeRefreshed(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            Date expirationDate = claims.getExpiration();
            // Allow refresh if token is expired but within refresh window
            return expirationDate != null &&
                    System.currentTimeMillis() - expirationDate.getTime() <= REFRESH_WINDOW;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get username from JWT token
     *
     * @param token the JWT token
     * @return the username
     */
    public String getUsernameFromJwtToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    /**
     * Get all claims from token
     *
     * @param token the JWT token
     * @return the claims
     * @throws JwtException if token is invalid
     */
    protected Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Get specific claim from token
     *
     * @param token the JWT token
     * @param claimName the name of the claim
     * @return Optional containing the claim value, or empty if not found
     */
    public Optional<Object> getClaimFromToken(String token, String claimName) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            return Optional.ofNullable(claims.get(claimName));
        } catch (Exception e) {
            log.error("Failed to get claim {} from token", claimName, e);
            return Optional.empty();
        }
    }

    /**
     * Validate JWT token
     *
     * @param authToken the JWT token
     * @return true if valid, false otherwise
     */
    public boolean validateJwtToken(String authToken) {
        try {
            getAllClaimsFromToken(authToken);
            return true;
        } catch (SecurityException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}