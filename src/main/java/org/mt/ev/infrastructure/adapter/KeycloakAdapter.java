package org.mt.ev.infrastructure.adapter;

import jakarta.ws.rs.core.Response;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mt.ev.application.dto.Request.UserRequest;
import org.mt.ev.application.port.out.KeycloakRepositoryPort;
import org.mt.ev.infrastructure.utils.KeyCloakProvider;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
@Slf4j
public class KeycloakAdapter implements KeycloakRepositoryPort {

    /**
     * Retrieves a list of all users from the Keycloak server.
     *
     * @return a list of {@code UserRepresentation} objects representing all users in the realm.
     */
    @Override
    public List<UserRepresentation> findAllUsers() {
        return KeyCloakProvider
                .getRealmResource()
                .users()
                .list();
    }

    /**
     * Searches for users in Keycloak by their username.
     * This method performs a case-insensitive search for users
     * that match the provided username.
     *
     * @param username the username to search for; cannot be null or empty
     * @return a list of {@code UserRepresentation} objects matching the given username;
     *         an empty list if no matching users are found
     */
    @Override
    public List<UserRepresentation> findUsersByUsername(String username) {
        return KeyCloakProvider
                .getRealmResource()
                .users()
                .searchByUsername(username, true);
    }

    /**
     * Creates a new user in the Keycloak system based on the provided user request details.
     * The method configures the user's basic details, assigns roles, and sets the user's password.
     *
     * @param userRequest an instance of {@code UserRequest} containing the details of the user
     *                    to be created, such as username, email, firstname, lastname, password,
     *                    and roles.
     * @return a string indicating the result of the user creation process. The possible return
     *         values include:
     *         - "User created successfully" if the user is created without issues.
     *         - "User already exists" if a user with the same details already exists.
     *         - "Error creating user" if an error occurs during the user creation process.
     */
    @Override
    public String createUser(@NonNull UserRequest userRequest) {

        int status = 0;

        UsersResource usersResource = KeyCloakProvider.getUserResource();

        UserRepresentation userRepresentation = createRepresentation(userRequest);

        Response response= usersResource.create(userRepresentation);

        status = response.getStatus();

        if (status == 201) {
            String path = response.getLocation().getPath();
            String userId = path.substring(path.lastIndexOf("/") + 1);

            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setTemporary(false);
            credentialRepresentation.setType(OAuth2Constants.PASSWORD);
            credentialRepresentation.setValue(userRequest.password());

            usersResource
                    .get(userId)
                    .resetPassword(credentialRepresentation);

            RealmResource realmResource = KeyCloakProvider.getRealmResource();

            List<RoleRepresentation> roleRepresentations = null;

            if(userRequest.roles() == null || userRequest.roles().isEmpty() ){
                roleRepresentations = List.of(realmResource.roles().get("user").toRepresentation());
            }else{
                roleRepresentations = realmResource.roles()
                        .list()
                        .stream()
                        .filter(role -> userRequest
                                .roles()
                                .stream()
                                .anyMatch(roleName -> roleName.equalsIgnoreCase(role.getName()))
                        )
                        .toList();
            }

            realmResource
                    .users()
                    .get(userId)
                    .roles()
                    .realmLevel()
                    .add(roleRepresentations);

            return "User created successfully";
        } else if(status == 409) {
            log.error("User already exists");
            return "User already exists";
        } else {
            log.error("Error creating user");
            return "Error creating user";
        }
    }

    /**
     * Deletes a user from the Keycloak server based on the provided user ID.
     *
     * @param userId the unique identifier of the user to be deleted
     */
    @Override
    public void deleteUser(String userId) {
        KeyCloakProvider
                .getUserResource()
                .get(userId)
                .remove();
    }

    /**
     * Updates the details of an existing user in the Keycloak system using the provided user ID
     * and user request details. The update includes modifying user representation fields
     * and setting a new password if provided in the user request.
     *
     * @param userId      the unique identifier of the user to be updated; must not be null or empty
     * @param userRequest an instance of {@code UserRequest} containing the updated user details,
     *                    such as username, email, firstname, lastname, password, and roles;
     *                    must not be null
     */
    @Override
    public void updateUser(String userId, UserRequest userRequest) {

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();

        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(OAuth2Constants.PASSWORD);
        credentialRepresentation.setValue(userRequest.password());

        UserRepresentation representation = createRepresentation(userRequest);

        representation.setCredentials(Collections.singletonList(credentialRepresentation));

        UserResource usersResource = KeyCloakProvider.getUserResource().get(userId);
        usersResource.update(representation);
    }

    /**
     * Creates a {@code UserRepresentation} object from the given {@code UserRequest}.
     * This method configures the user's basic representation details such as
     * first name, last name, email, username, and default settings
     * (e.g., email verification and enabled status).
     *
     * @param userRequest the {@code UserRequest} containing the details required
     *                    to create a {@code UserRepresentation}.
     *                    It includes fields such as username, email, firstname, and lastname.
     * @return a new instance of {@code UserRepresentation} populated with the details
     *         provided in the {@code UserRequest}.
     */
    private UserRepresentation createRepresentation(UserRequest userRequest) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(userRequest.firstname());
        userRepresentation.setLastName(userRequest.lastname());
        userRepresentation.setEmail(userRequest.email());
        userRepresentation.setUsername(userRequest.username());
        userRepresentation.setEmailVerified(true);
        userRepresentation.setEnabled(true);
        return userRepresentation;
    }

}
