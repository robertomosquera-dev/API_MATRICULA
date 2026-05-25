package org.mt.ev.application.dto.Request;

public record StudentRequest(
    String names,
    String surnames,
    String dni,
    Integer age
) {
}
