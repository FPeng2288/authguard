package com.fpeng2288.authguard.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * ClassName: LoginRequest
 * Package: com.fpeng2288.authguard.model.dto
 * Description:
 *
 * @author Fan Peng
 * Create 2024/12/23 23:52
 * @version 1.0
 */
@Data
public class LoginRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 40, message = "Password must be between 6 and 40 characters")
    private String password;
}
