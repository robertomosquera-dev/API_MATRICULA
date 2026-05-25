package org.mt.ev.application.dto.Request;

public record StudentUpdateRequest(
        String names,
        String surnames,
        String dni,
        Integer age
) {}
