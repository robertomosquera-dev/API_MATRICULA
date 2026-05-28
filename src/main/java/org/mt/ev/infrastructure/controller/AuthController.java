package org.mt.ev.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.mt.ev.application.dto.Request.LoginRequest;
import org.mt.ev.application.dto.Response.LoginResponse;
import org.mt.ev.application.port.input.userUseCase.LoginUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(
        name = "Authentication",
        description = "API para autenticación de usuarios. Permite iniciar sesión y obtener las credenciales de acceso."
)
public class AuthController {

    private final LoginUseCase loginUseCase;

    @Operation(
            summary = "Iniciar sesión",
            description = "Autentica a un usuario mediante sus credenciales. Este endpoint es público y no requiere autenticación previa ni roles."
    )
    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request
    ) {
        return loginUseCase.login(request);
    }

}
