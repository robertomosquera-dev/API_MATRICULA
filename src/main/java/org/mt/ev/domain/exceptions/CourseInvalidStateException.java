package org.mt.ev.domain.exceptions;

public class CourseInvalidStateException extends DomainException {

    public CourseInvalidStateException(String message) {
        super(message);
    }

    public static CourseInvalidStateException alreadyActive() {
        return new CourseInvalidStateException("El curso ya está activo");
    }

    public static CourseInvalidStateException alreadyInactive() {
        return new CourseInvalidStateException("El curso ya está inactivo");
    }

    public static CourseInvalidStateException cannotDeleteActive() {
        return new CourseInvalidStateException("Un curso activo no puede ser eliminado");
    }
}
