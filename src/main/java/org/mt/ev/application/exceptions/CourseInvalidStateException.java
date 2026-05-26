package org.mt.ev.application.exceptions;

public class CourseInvalidStateException extends RuntimeException {
    public CourseInvalidStateException(String message) {
        super(message);
    }
    public static CourseInvalidStateException cannotDeleteActive() {
        return new CourseInvalidStateException("Un curso activo no puede ser eliminado");
    }
}
