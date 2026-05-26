package org.mt.ev.domain.exceptions;


public class TuitionDetailValidationException extends DomainException {

    public TuitionDetailValidationException(String message) {
        super(message);
    }

    public static TuitionDetailValidationException courseRequired() {
        return new TuitionDetailValidationException("El curso es obligatorio");
    }

    public static TuitionDetailValidationException courseIsInactive() {
        return new TuitionDetailValidationException("No se puede matricular en un curso inactivo");
    }
}