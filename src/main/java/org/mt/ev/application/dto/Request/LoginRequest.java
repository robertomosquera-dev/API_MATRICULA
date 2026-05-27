package org.mt.ev.application.dto.Request;

public record LoginRequest(
        String username,
        String password
) {
}