package org.mt.ev.application.dto.Response;

import java.io.Serializable;
import java.util.UUID;

public record TuitionDetailResponse(
        UUID id,
        String classroom,
        String courseName
) implements Serializable {
}
