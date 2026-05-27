package org.mt.ev.application.dto.Request;

import jakarta.validation.constraints.*;

public record StudentRequest(

    @NotBlank
    @Size(max = 80)
    String names,

    @NotBlank
    @Size(max = 80)
    String surnames,

    @NotBlank
    @Email
    @Size(max = 120)
    String email,

    @NotBlank
    @Size(min = 5, max = 100)
    String password,

    @NotBlank
    String dni,

    @NotNull
    @Min(18)
    Integer age

) {
}
