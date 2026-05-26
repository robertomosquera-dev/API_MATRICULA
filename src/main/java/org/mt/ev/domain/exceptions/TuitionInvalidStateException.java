package org.mt.ev.domain.exceptions;

public class TuitionInvalidStateException extends DomainException {

    public TuitionInvalidStateException(String message) {
        super(message);
    }

    public static TuitionInvalidStateException alreadyActive() {
        return new TuitionInvalidStateException("La matrícula ya está activa");
    }

    public static TuitionInvalidStateException alreadyInactive() {
        return new TuitionInvalidStateException("La matrícula ya está inactiva");
    }

    public static TuitionInvalidStateException studentIsMinor() {
        return new TuitionInvalidStateException("El estudiante debe ser mayor de edad para matricularse");
    }
}
