package org.mt.ev.application.dto.Request;

import java.util.UUID;

public record TuitionDetailRequest(
        UUID courseId,
        String classroom
) {
}
