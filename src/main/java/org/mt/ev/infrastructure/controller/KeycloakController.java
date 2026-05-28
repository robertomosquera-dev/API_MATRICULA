package org.mt.ev.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "Keycloak",
        description = "API para la administración de usuarios en Keycloak. Todos los endpoints requieren el rol admin_client_role."
)
@SecurityRequirement(name = "bearerAuth")
public class KeycloakController {

    private final KeycloakService keycloakService;

    @Operation(
            summary = "Listar usuarios de Keycloak",
            description = "Obtiene la lista completa de usuarios registrados en Keycloak. Requiere el rol admin_client_role."
    )
    @GetMapping("/users")
    public List<UserRepresentation> findAllUsers() {
        return keycloakService.findAllUsers();
    }

    @Operation(
            summary = "Buscar usuarios por username",
            description = "Busca usuarios en Keycloak filtrando por nombre de usuario. Requiere el rol admin_client_role."
    )
    @GetMapping("/users/search")
    public List<UserRepresentation> findUsersByUsername(
            @Parameter(description = "Nombre de usuario o coincidencia parcial para realizar la búsqueda", required = true)
            @RequestParam String username
    ) {
        return keycloakService.findUsersByUsername(username);
    }

    @Operation(
            summary = "Eliminar usuario de Keycloak",
            description = "Elimina un usuario de Keycloak mediante su identificador. Requiere el rol admin_client_role."
    )
    @DeleteMapping("/users/{userId}")
    public void deleteUser(
            @Parameter(description = "Identificador del usuario en Keycloak", required = true)
            @PathVariable String userId
    ) {
        keycloakService.deleteUser(userId);
    }

}
