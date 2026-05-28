package org.mt.ev.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mt.ev.application.dto.Request.CourseRequest;
import org.mt.ev.application.dto.Request.CourseUpdateRequest;
import org.mt.ev.application.dto.Response.CourseResponse;
import org.mt.ev.application.port.input.courseUseCase.*;
import org.mt.ev.domain.model.CourseStatus;
import org.mt.ev.infrastructure.utils.ApiResponse;
import org.mt.ev.infrastructure.utils.ApiResponseImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
@PreAuthorize("hasRole('admin_client_role')")
@Tag(
        name = "Courses",
        description = "API para la gestión de cursos. Todos los endpoints requieren el rol admin_client_role."
)
@SecurityRequirement(name = "bearerAuth")
public class CourseController {

    private final CreateCourseUseCase createCourseUseCase;
    private final FindCourseUseCase findCourseUseCase;
    private final UpdateCourseUseCase updateCourseUseCase;
    private final DeleteCourseUseCase deleteCourseUseCase;
    private final ChangeStatusCourseUseCase changeStatusCourseUseCase;

    @Operation(
            summary = "Crear un curso",
            description = "Crea un nuevo curso en el sistema. Requiere el rol admin_client_role."
    )
    @PostMapping
    public ResponseEntity<ApiResponse<CourseResponse>> create(@Valid @RequestBody CourseRequest request) {
        return ResponseEntity.status(201)
                .body(ApiResponseImpl.created(createCourseUseCase.createCourse(request)));
    }

    @Operation(
            summary = "Buscar un curso por ID",
            description = "Obtiene la información de un curso mediante su identificador UUID. Requiere el rol admin_client_role."
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> findById(
            @Parameter(description = "Identificador UUID del curso", required = true)
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(ApiResponseImpl.success(findCourseUseCase.findCourseById(id)));
    }

    @Operation(
            summary = "Buscar un curso por nombre",
            description = "Obtiene la información de un curso mediante su nombre. Requiere el rol admin_client_role."
    )
    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse<CourseResponse>> findByName(
            @Parameter(description = "Nombre del curso", required = true)
            @PathVariable String name
    ) {
        return ResponseEntity.ok(ApiResponseImpl.success(findCourseUseCase.findCourseByName(name)));
    }

    @Operation(
            summary = "Listar cursos",
            description = "Lista los cursos registrados con soporte de paginación, ordenamiento y filtro por estado. Requiere el rol admin_client_role."
    )
    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseResponse>>> findAll(
            @Parameter(description = "Número de página, inicia en 0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Cantidad de elementos por página")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "Tipo de ordenamiento: asc o desc")
            @RequestParam(defaultValue = "asc") String sort,

            @Parameter(description = "Estado del curso a filtrar. Por defecto: ALL")
            @RequestParam(defaultValue = "ALL") CourseStatus status
    ) {
        return ResponseEntity.ok(
                ApiResponseImpl.success(findCourseUseCase.findAllCourses(page, size, sort, status))
        );
    }

    @Operation(
            summary = "Actualizar un curso",
            description = "Actualiza los datos de un curso existente mediante su identificador UUID. Requiere el rol admin_client_role."
    )
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> update(
            @Valid @RequestBody CourseUpdateRequest request,

            @Parameter(description = "Identificador UUID del curso a actualizar", required = true)
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(ApiResponseImpl.success(updateCourseUseCase.updateCourse(request, id)));
    }

    @Operation(
            summary = "Habilitar un curso",
            description = "Cambia el estado de un curso a habilitado/activo. Requiere el rol admin_client_role."
    )
    @PatchMapping("/{id}/enable")
    public ResponseEntity<ApiResponse<CourseResponse>> enable(
            @Parameter(description = "Identificador UUID del curso a habilitar", required = true)
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(ApiResponseImpl.success(changeStatusCourseUseCase.enable(id)));
    }

    @Operation(
            summary = "Deshabilitar un curso",
            description = "Cambia el estado de un curso a deshabilitado/inactivo. Requiere el rol admin_client_role."
    )
    @PatchMapping("/{id}/disable")
    public ResponseEntity<ApiResponse<CourseResponse>> disable(
            @Parameter(description = "Identificador UUID del curso a deshabilitar", required = true)
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(ApiResponseImpl.success(changeStatusCourseUseCase.disable(id)));
    }

    @Operation(
            summary = "Eliminar un curso",
            description = "Elimina un curso existente mediante su identificador UUID. Requiere el rol admin_client_role."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "Identificador UUID del curso a eliminar", required = true)
            @PathVariable UUID id
    ) {
        deleteCourseUseCase.deleteCourse(id);
        return ResponseEntity.ok(ApiResponseImpl.success(null, "Curso eliminado correctamente"));
    }
}