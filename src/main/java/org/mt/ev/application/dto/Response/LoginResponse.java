package org.mt.ev.application.dto.Response;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        Long expiresIn
) {
}