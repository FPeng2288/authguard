package com.fpeng2288.authguard.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * ClassName: BaseResponse
 * Package: com.fpeng2288.authguard.common
 * Description:
 *
 * @author Fan Peng
 * Create 2024/12/24 0:57
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T extends Serializable> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int code;
    private T data;
    private String message;
    private String description;

    public BaseResponse(int code, T data, String message) {
        this(code, data, message, "");
    }

    public BaseResponse(int code, T data) {
        this(code, data, "", "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }
}
