package com.fpeng2288.authguard.service.impl;

import com.fpeng2288.authguard.common.ErrorCode;
import com.fpeng2288.authguard.exception.BusinessException;
import com.fpeng2288.authguard.model.dto.JwtResponse;
import com.fpeng2288.authguard.model.dto.LoginRequest;
import com.fpeng2288.authguard.model.dto.RegisterRequest;
import com.fpeng2288.authguard.model.entity.Role;
import com.fpeng2288.authguard.model.entity.User;
import com.fpeng2288.authguard.model.entity.UserRole;
import com.fpeng2288.authguard.model.enums.ERole;
import com.fpeng2288.authguard.model.vo.UserVO;
import com.fpeng2288.authguard.repository.RoleRepository;
import com.fpeng2288.authguard.repository.UserRepository;
import com.fpeng2288.authguard.repository.UserRoleRepository;
import com.fpeng2288.authguard.security.JwtUtils;
import com.fpeng2288.authguard.security.UserDetailsImpl;
import com.fpeng2288.authguard.service.AuthService;
import com.fpeng2288.authguard.util.MaskUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * ClassName: AuthServiceImpl
 * Package: com.fpeng2288.authguard.service.impl
 * Description:
 *
 * @author Fan Peng
 * Create 2024/12/24 15:36
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String jwt = jwtUtils.generateJwtToken(authentication);

            return JwtResponse.of(
                    jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    MaskUtil.maskEmail(userDetails.getEmail()),  // Mask email
                    userDetails.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .toList()
            );
        } catch (AuthenticationException e) {
            log.warn("Authentication failed for user: {}", loginRequest.getUsername());
            throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
        }
    }

    @Override
    @Transactional
    public UserVO registerUser(RegisterRequest request) {
        // Validate username uniqueness
        if (userRepository.existsByUsername(request.getUsername())) {
            log.warn("Registration failed: username {} already exists", request.getUsername());
            throw new BusinessException(ErrorCode.USERNAME_EXISTS);
        }

        // Validate email uniqueness
        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Registration failed: email {} already exists", request.getEmail());
            throw new BusinessException(ErrorCode.EMAIL_EXISTS);
        }

        try {
            // Create new user
            User user = new User();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            // Save user first to get ID
            user = userRepository.save(user);

            // Assign default user role
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new BusinessException(ErrorCode.SYSTEM_ERROR, "Default role not found"));

            // Create user role relationship
            UserRole userRoleEntity = new UserRole(user, userRole, "SYSTEM");
            userRoleRepository.save(userRoleEntity);

            return UserVO.builder()
                    .username(user.getUsername())
                    .maskedEmail(MaskUtil.maskEmail(user.getEmail()))
                    .roles(Set.of(userRole.getName().name()))
                    .createdAt(user.getCreatedAt())
                    .build();

        } catch (Exception e) {
            log.error("Error during user registration", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "Registration failed");
        }
    }

    @Override
    public UserVO getUserInfo(String username) {
        User user = userRepository.findByUsernameWithRoles(username)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ERROR));

        return UserVO.builder()
                .username(username)
                .maskedEmail(MaskUtil.maskEmail(user.getEmail()))
                .roles(user.getUserRoles().stream()
                        .map(ur -> ur.getRole().getName().name())
                        .collect(Collectors.toSet()))
                .createdAt(user.getCreatedAt())
                .build();
    }
}
