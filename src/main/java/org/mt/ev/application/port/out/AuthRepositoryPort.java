package org.mt.ev.application.port.out;

import org.mt.ev.application.dto.Request.LoginRequest;
import org.mt.ev.application.dto.Response.LoginResponse;

public interface AuthRepositoryPort {
    LoginResponse login(LoginRequest request);
}
