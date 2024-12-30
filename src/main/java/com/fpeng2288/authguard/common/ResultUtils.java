package com.fpeng2288.authguard.common;

import java.io.Serializable;

/**
 * ClassName: ResultUtils
 * Package: com.fpeng2288.authguard.common
 * Description:
 *
 * @author Fan Peng
 * Create 2024/12/24 1:50
 * @version 1.0
 */
public class ResultUtils {

    // Private constructor to prevent instantiation
    private ResultUtils() {
        throw new AssertionError("ResultUtils class cannot be instantiated");
    }

    /**
     * Generate a successful response
     *
     * @param data The data to be returned
     * @param <T>  The type of the data
     * @return A BaseResponse object containing the successful result
     */
    public static <T extends Serializable> BaseResponse<T> success(T data) {
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), data, "ok");
    }

    /**
     * Generate an error response
     *
     * @param errorCode The error code
     * @return A BaseResponse object containing the error result
     */
    public static BaseResponse<?> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * Generate an error response with custom message and description
     *
     * @param code        The error code
     * @param message     The error message
     * @param description The error description
     * @return A BaseResponse object containing the error result
     */
    public static BaseResponse<?> error(int code, String message, String description) {
        return new BaseResponse<>(code, null, message, description);
    }

    /**
     * Generate an error response with custom message
     *
     * @param errorCode   The error code
     * @param message     The error message
     * @param description The error description
     * @return A BaseResponse object containing the error result
     */
    public static BaseResponse<?> error(ErrorCode errorCode, String message, String description) {
        return new BaseResponse<>(errorCode.getCode(), null, message, description);
    }

    /**
     * Generate an error response with custom description
     *
     * @param errorCode   The error code
     * @param description The error description
     * @return A BaseResponse object containing the error result
     */
    public static BaseResponse<?> error(ErrorCode errorCode, String description) {
        return new BaseResponse<>(errorCode.getCode(), null, errorCode.getMessage(), description);
    }
}
