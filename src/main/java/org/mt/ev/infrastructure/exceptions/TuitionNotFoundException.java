package org.mt.ev.infrastructure.exceptions;

import java.util.UUID;

public class TuitionNotFoundException extends RuntimeException {
    public TuitionNotFoundException(UUID id) {
        super("La matrícula con id " + id + " no existe");
    }
}