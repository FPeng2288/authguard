package com.fpeng2288.authguard.common;

import lombok.Getter;

/**
 * ClassName: ErrorCode
 * Package: com.fpeng2288.authguard.common
 * Description:
 *
 * @author Fan Peng
 * Create 2024/12/24 0:53
 * @version 1.0
 */
@Getter
public enum ErrorCode {
    SUCCESS(0, "Success", ""),
    PARAMS_ERROR(40000, "Request parameter error", ""),
    NOT_LOGIN_ERROR(40100, "Not logged in", ""),
    NO_AUTH_ERROR(40101, "No authorization", ""),
    TOKEN_ERROR(40102, "Invalid token", ""),
    FORBIDDEN_ERROR(40300, "Access denied", ""),
    NOT_FOUND_ERROR(40400, "Request data does not exist", ""),
    SYSTEM_ERROR(50000, "System internal exception", ""),


    USERNAME_EXISTS(40001, "Username already exists", ""),
    EMAIL_EXISTS(40002, "Email already exists", ""),
    INVALID_CREDENTIALS(40003, "Invalid username or password", "");

    private final int code;
    private final String message;
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }
}
