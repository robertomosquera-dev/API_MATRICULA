package org.mt.ev.application.dto.Request;

public record CourseUpdateRequest(
        String name,
        String description,
        String abbreviation
) {
}
