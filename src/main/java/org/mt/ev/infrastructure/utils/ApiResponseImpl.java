package org.mt.ev.infrastructure.utils;


public record ApiResponseImpl<T>(
        Boolean isError,
        Integer code,
        String message,
        T data
) implements ApiResponse<T> {

    public static <T> ApiResponseImpl<T> success(T data) {
        return new ApiResponseImpl<>(false, 200, "Operación exitosa", data);
    }

    public static <T> ApiResponseImpl<T> success(T data, String message) {
        return new ApiResponseImpl<>(false, 200, message, data);
    }

    public static <T> ApiResponseImpl<T> error(Integer code, String message) {
        return new ApiResponseImpl<>(true, code, message, null);
    }

    public static <T> ApiResponseImpl<T> error(Integer code, String message, T data) {
        return new ApiResponseImpl<>(true, code, message, data);
    }

    public static <T> ApiResponseImpl<T> created(T data) {
        return new ApiResponseImpl<>(false, 201, "Recurso creado", data);
    }
}