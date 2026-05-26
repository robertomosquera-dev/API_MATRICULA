package org.mt.ev.infrastructure.Utils;

public interface ApiResponse<T> {
    Boolean isError();
    Integer code();
    String message();
    T data();
}