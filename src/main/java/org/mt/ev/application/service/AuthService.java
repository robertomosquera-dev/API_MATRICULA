package org.mt.ev.application.service;

import lombok.RequiredArgsConstructor;
import org.mt.ev.application.dto.Request.LoginRequest;
import org.mt.ev.application.dto.Response.LoginResponse;
import org.mt.ev.application.port.input.userUseCase.LoginUseCase;
import org.mt.ev.application.port.out.AuthRepositoryPort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements LoginUseCase {

    private final AuthRepositoryPort authRepositoryPort;

    @Override
    public LoginResponse login(LoginRequest request) {
        return authRepositoryPort.login(request);
    }
}