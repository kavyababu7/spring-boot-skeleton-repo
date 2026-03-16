package com.nissan.template.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private ApiError error;
    private LocalDateTime timestamp;
    private String requestId;

    public static <T> ApiResponse<T> success(T data) {
        return success(data, null);
    }

    public static <T> ApiResponse<T> success(T data, String requestId) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .timestamp(LocalDateTime.now())
                .requestId(requestId)
                .build();
    }

    public static <T> ApiResponse<T> error(String code, String message) {
        return error(code, message, null);
    }

    public static <T> ApiResponse<T> error(String code, String message, String requestId) {
        return ApiResponse.<T>builder()
                .success(false)
                .error(new ApiError(code, message))
                .timestamp(LocalDateTime.now())
                .requestId(requestId)
                .build();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ApiError {
        private String code;
        private String message;
    }
}
