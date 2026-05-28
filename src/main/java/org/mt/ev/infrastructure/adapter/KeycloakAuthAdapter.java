package org.mt.ev.infrastructure.adapter;

import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.mt.ev.application.dto.Request.LoginRequest;
import org.mt.ev.application.dto.Response.LoginResponse;
import org.mt.ev.application.port.out.AuthRepositoryPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KeycloakAuthAdapter implements AuthRepositoryPort {

    @Value("${jwt.keycloak-url}")
    private String KEYCLOAK_URL;
    @Value("${jwt.realm-name}")
    private String REALM_NAME;
    @Value("${jwt.client-id}")
    private String CLIENT_ID;
    @Value("${jwt.client-secret}")
    private String CLIENT_SECRET;

    @Override
    public LoginResponse login(LoginRequest request) {

        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(KEYCLOAK_URL)
                .realm(REALM_NAME)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
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