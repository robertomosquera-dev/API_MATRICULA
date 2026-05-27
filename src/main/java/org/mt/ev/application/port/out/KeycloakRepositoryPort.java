package org.mt.ev.application.port.out;

import org.keycloak.representations.idm.UserRepresentation;
import org.mt.ev.application.dto.Request.UserRequest;

import java.util.List;
import java.util.UUID;

public interface KeycloakRepositoryPort {

    List<UserRepresentation> findAllUsers();
    List<UserRepresentation> findUsersByUsername(String username);
    String createUser(UserRequest userRequest);
    void deleteUser(String userId);
    void updateUser(String userId, UserRequest userRequest);

}
