package org.mt.ev.infrastructure.exceptions;

import java.util.UUID;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(UUID id) {
        super("El curso con id " + id + " no existe");
    }
    public CourseNotFoundException(String name) {
        super("El curso '" + name + "' no existe");
    }
}