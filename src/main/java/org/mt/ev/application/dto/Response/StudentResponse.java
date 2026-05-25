package org.mt.ev.application.dto.Response;

import java.util.UUID;

public record StudentResponse(
    UUID id,
    String names,
    String surnames,
    String dni,
    Integer age
) {
}
