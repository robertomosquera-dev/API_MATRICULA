package org.mt.ev.application.port.input.userUseCase;

import org.mt.ev.application.dto.Request.LoginRequest;
import org.mt.ev.application.dto.Response.LoginResponse;

public interface LoginUseCase {
    LoginResponse login(LoginRequest request);
}
