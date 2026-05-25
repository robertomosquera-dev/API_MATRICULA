package org.mt.ev.application.dto.Response;

import java.util.UUID;

public record CourseResponse(
        UUID id,
        String name,
        String description,
        String abbreviation,
        Boolean status
) {
}
