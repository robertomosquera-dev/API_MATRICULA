package org.mt.ev.application.exceptions;

public class StudentInvalidStateException extends RuntimeException {

    public StudentInvalidStateException(String message) {
        super(message);
    }

    public static StudentInvalidStateException isMinor() {
        return new StudentInvalidStateException("El estudiante debe ser mayor de edad");
    }

    public static StudentInvalidStateException dniAlreadyExists() {
        return new StudentInvalidStateException("El DNI ya está registrado en la base de datos");
    }

}
