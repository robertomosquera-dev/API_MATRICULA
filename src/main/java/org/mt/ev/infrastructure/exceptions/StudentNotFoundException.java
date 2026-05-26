package org.mt.ev.infrastructure.exceptions;

import java.util.UUID;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(UUID id) {
        super("El estudiante con id " + id + " no existe");
    }
    public StudentNotFoundException(String dni) {
        super("El estudiante con DNI " + dni + " no existe");
    }
}