package org.mt.ev.application.service;

import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.mt.ev.application.port.input.userUseCase.DeleteUserUseCase;
import org.mt.ev.application.port.input.userUseCase.FindUserUseCase;
import org.mt.ev.application.port.out.KeycloakRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakService implements
        FindUserUseCase,
        DeleteUserUseCase {

    private final KeycloakRepositoryPort keycloakRepositoryPort;

    @Override
    public void deleteUser(String userId) {
        keycloakRepositoryPort.deleteUser(userId);
    }

    @Override
    public List<UserRepresentation> findAllUsers() {
        return keycloakRepositoryPort.findAllUsers();
    }

    @Override
    public List<UserRepresentation> findUsersByUsername(String username) {
        return keycloakRepositoryPort.findUsersByUsername(username);
    }

}