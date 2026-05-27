package org.mt.ev.infrastructure.controller;

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
public class AuthController {

    private final LoginUseCase loginUseCase;

    @PreAuthorize( "permitAll()")
    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request
    ) {
        return loginUseCase.login(request);
    }

}
