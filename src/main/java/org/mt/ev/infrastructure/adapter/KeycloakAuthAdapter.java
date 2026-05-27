package org.mt.ev.infrastructure.adapter;

import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.mt.ev.application.dto.Request.LoginRequest;
import org.mt.ev.application.dto.Response.LoginResponse;
import org.mt.ev.application.port.out.AuthRepositoryPort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KeycloakAuthAdapter implements AuthRepositoryPort {

    @Override
    public LoginResponse login(LoginRequest request) {

        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl("http://localhost:9090/auth")
                .realm("spring-boot-realm-dev")
                .clientId("tu-client-id")
                .clientSecret("tu-client-secret")
                .grantType(OAuth2Constants.PASSWORD)
                .username(request.username())
                .password(request.password())
                .build();

        AccessTokenResponse token =
                keycloak.tokenManager().getAccessToken();

        return new LoginResponse(
                token.getToken(),
                token.getRefreshToken(),
                token.getExpiresIn()
        );
    }

}