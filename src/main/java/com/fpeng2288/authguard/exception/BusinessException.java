package com.fpeng2288.authguard.exception;

import com.fpeng2288.authguard.common.ErrorCode;
import lombok.Getter;

/**
 * ClassName: BusinessException
 * Package: com.fpeng2288.authguard.exception
 * Description:
 *
 * @author Fan Peng
 * Create 2024/12/24 2:00
 * @version 1.0
 */
@Getter
public class BusinessException extends RuntimeException {

    private final int code;
    private final String description;

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }
}
