package org.mt.ev.application.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CourseRequest(
        @NotBlank
        @Size(max = 80)
        String name,
        @NotBlank
        String description,
        @NotBlank
        @Size(max = 7)
        String abbreviation
) {
}
