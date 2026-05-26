package org.mt.ev.domain.exceptions;


public class TuitionValidationException extends DomainException {

    public TuitionValidationException(String message) {
        super(message);
    }

    public static TuitionValidationException studentRequired() {
        return new TuitionValidationException("El estudiante es obligatorio");
    }

    public static TuitionValidationException detailsRequired() {
        return new TuitionValidationException("Debe incluir al menos un curso");
    }
}
