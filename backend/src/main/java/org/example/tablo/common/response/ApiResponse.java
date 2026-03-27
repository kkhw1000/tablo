package org.example.tablo.common.response;

import java.time.LocalDateTime;

public record ApiResponse<T>(
        boolean success,
        String code,
        String message,
        T data,
        LocalDateTime timestamp
) {
    public static <T> ApiResponse<T> of(SuccessCode successCode, T data) {
        return new ApiResponse<>(
                true,
                successCode.code(),
                successCode.message(),
                data,
                LocalDateTime.now()
        );
    }

    public static <T> ApiResponse<T> error(String code, String message, T data) {
        return new ApiResponse<>(
                false,
                code,
                message,
                data,
                LocalDateTime.now()
        );
    }
}
