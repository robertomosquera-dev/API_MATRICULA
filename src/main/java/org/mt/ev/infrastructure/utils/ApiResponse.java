package org.mt.ev.infrastructure.utils;

public interface ApiResponse<T> {
    Boolean isError();
    Integer code();
    String message();
    T data();
}