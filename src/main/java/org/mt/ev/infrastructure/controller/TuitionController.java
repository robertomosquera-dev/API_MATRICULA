package org.mt.ev.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mt.ev.application.dto.Request.TuitionRequest;
import org.mt.ev.application.dto.Response.TuitionResponse;
import org.mt.ev.application.port.input.tuitionUseCase.ChangeStatusTuitionUseCase;
import org.mt.ev.application.port.input.tuitionUseCase.CreateTuitionUseCase;
import org.mt.ev.application.port.input.tuitionUseCase.FindTuitionUseCase;
import org.mt.ev.infrastructure.utils.ApiResponse;
import org.mt.ev.infrastructure.utils.ApiResponseImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tuitions")
@RequiredArgsConstructor
@Tag(
        name = "Tuitions",
        description = "API para la gestión de matrículas. Algunos endpoints son accesibles por estudiantes y administradores, y otros solo por administradores."
)
@SecurityRequirement(name = "bearerAuth")
public class TuitionController {

    private final CreateTuitionUseCase createTuitionUseCase;
    private final FindTuitionUseCase findTuitionUseCase;
    private final ChangeStatusTuitionUseCase changeStatusTuitionUseCase;

    @Operation(
            summary = "Crear matrícula",
            description = "Permite crear una nueva matrícula. Accesible para usuarios con rol user_client_role o admin_client_role."
    )
    @PreAuthorize("hasAnyRole('user_client_role','admin_client_role')")
    @PostMapping
    public ResponseEntity<ApiResponse<TuitionResponse>> create(
            @Valid @RequestBody TuitionRequest request
    ) {
        return ResponseEntity.status(201)
                .body(ApiResponseImpl.created(createTuitionUseCase.createTuition(request)));
    }

    @Operation(
            summary = "Consultar matrícula por ID",
            description = "Obtiene la información de una matrícula mediante su identificador UUID. Accesible para usuarios con rol user_client_role o admin_client_role."
    )
    @PreAuthorize("hasAnyRole('user_client_role','admin_client_role')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TuitionResponse>> findById(
            @Parameter(description = "Identificador UUID de la matrícula", required = true)
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(ApiResponseImpl.success(findTuitionUseCase.findTuitionById(id)));
    }

    @Operation(
            summary = "Obtener mapa de matrículas",
            description = "Obtiene un mapa de matrículas agrupadas según la lógica definida por el caso de uso. Requiere el rol admin_client_role."
    )
    @PreAuthorize("hasRole('admin_client_role')")
    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Set<String>>>> findMapById() {
        return ResponseEntity.ok(ApiResponseImpl.success(findTuitionUseCase.findTuitionMap()));
    }

    @Operation(
            summary = "Habilitar matrícula",
            description = "Cambia el estado de una matrícula a habilitada/activa. Requiere el rol admin_client_role."
    )
    @PreAuthorize("hasRole('admin_client_role')")
    @PatchMapping("/{id}/enable")
    public ResponseEntity<ApiResponse<TuitionResponse>> enable(
            @Parameter(description = "Identificador UUID de la matrícula a habilitar", required = true)
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(ApiResponseImpl.success(changeStatusTuitionUseCase.enable(id)));
    }

    @Operation(
            summary = "Deshabilitar matrícula",
            description = "Cambia el estado de una matrícula a deshabilitada/inactiva. Requiere el rol admin_client_role."
    )
    @PreAuthorize("hasRole('admin_client_role')")
    @PatchMapping("/{id}/disable")
    public ResponseEntity<ApiResponse<TuitionResponse>> disable(
            @Parameter(description = "Identificador UUID de la matrícula a deshabilitar", required = true)
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(ApiResponseImpl.success(changeStatusTuitionUseCase.disable(id)));
    }
}