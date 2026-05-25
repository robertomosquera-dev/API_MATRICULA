package org.mt.ev.application.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record TuitionDetailRequest(
        @NotNull
        UUID courseId,
        @NotBlank
        String classroom
) {
}
