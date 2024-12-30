package com.fpeng2288.authguard.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * ClassName: JwtResponse
 * Package: com.fpeng2288.authguard.model.dto
 * Description:
 *
 * @author Fan Peng
 * Create 2024/12/23 23:52
 * @version 1.0
 */
@Data
@Builder
public class JwtResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String TOKEN_TYPE = "Bearer";

    private String token;
    private String type = TOKEN_TYPE;
    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    public static JwtResponse of(String token, Long id, String username, String email, List<String> roles) {
        return JwtResponse.builder()
                .token(token)
                .id(id)
                .username(username)
                .email(email)
                .roles(roles)
                .build();
    }
}
