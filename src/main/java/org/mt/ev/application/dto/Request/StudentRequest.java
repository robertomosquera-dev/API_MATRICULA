package org.mt.ev.application.dto.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record StudentRequest(

    @NotBlank
    @Size(max = 80)
    String names,

    @NotBlank
    @Size(max = 80)
    String surnames,

    @NotBlank
    String dni,

    @NotNull @Min(0)
    Integer age
) {
}
