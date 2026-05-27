package org.mt.ev.infrastructure.controller;

import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.mt.ev.application.service.KeycloakService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/keycloak")
@RequiredArgsConstructor
@PreAuthorize("hasRole('admin_client_role')")
public class KeycloakController {

    private final KeycloakService keycloakService;

    @GetMapping("/users")
    public List<UserRepresentation> findAllUsers() {
        return keycloakService.findAllUsers();
    }

    @GetMapping("/users/search")
    public List<UserRepresentation> findUsersByUsername(
            @RequestParam String username
    ) {
        return keycloakService.findUsersByUsername(username);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(
            @PathVariable String userId
    ) {
        keycloakService.deleteUser(userId);
    }

}
