package org.mt.ev.application.port.input.userUseCase;

import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface FindUserUseCase {
    List<UserRepresentation> findAllUsers();
    List<UserRepresentation> findUsersByUsername(String username);
}
