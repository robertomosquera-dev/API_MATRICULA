package org.mt.ev.infrastructure.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mt.ev.application.dto.Request.StudentRequest;
import org.mt.ev.application.dto.Request.StudentUpdateRequest;
import org.mt.ev.application.dto.Response.StudentResponse;
import org.mt.ev.application.port.input.studentUseCase.CreateStudentUseCase;
import org.mt.ev.application.port.input.studentUseCase.DeleteStudentUseCase;
import org.mt.ev.application.port.input.studentUseCase.FindStudentUseCase;
import org.mt.ev.application.port.input.studentUseCase.UpdateStudentUseCase;
import org.mt.ev.infrastructure.utils.ApiResponse;
import org.mt.ev.infrastructure.utils.ApiResponseImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Tag(
        name = "Students",
        description = "API para la gestión de estudiantes. El registro es público y las operaciones de consulta, actualización y eliminación requieren el rol admin_client_role."
)
@SecurityRequirement(name = "bearerAuth")
public class StudentController {

    private final CreateStudentUseCase createStudentUseCase;
    private final FindStudentUseCase findStudentUseCase;
    private final UpdateStudentUseCase updateStudentUseCase;
    private final DeleteStudentUseCase deleteStudentUseCase;

    @Operation(
            summary = "Registrar estudiante",
            description = "Registra un nuevo estudiante en el sistema. Este endpoint es público y no requiere autenticación."
    )
    @PreAuthorize("permitAll()")
    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponse>> create(
            @Valid @RequestBody StudentRequest request
    ) {
        return ResponseEntity.status(201)
                .body(ApiResponseImpl.created(createStudentUseCase.createStudent(request)));
    }

    @Operation(
            summary = "Buscar estudiante por ID",
            description = "Obtiene la información de un estudiante mediante su identificador UUID. Requiere el rol admin_client_role."
    )
    @PreAuthorize("hasRole('admin_client_role')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> findById(
            @Parameter(description = "Identificador UUID del estudiante", required = true)
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(ApiResponseImpl.success(findStudentUseCase.findStudentById(id)));
    }

    @Operation(
            summary = "Buscar estudiante por DNI",
            description = "Obtiene la información de un estudiante mediante su DNI. Requiere el rol admin_client_role."
    )
    @PreAuthorize("hasRole('admin_client_role')")
    @GetMapping("/dni/{dni}")
    public ResponseEntity<ApiResponse<StudentResponse>> findByDni(
            @Parameter(description = "DNI del estudiante", required = true)
            @PathVariable String dni
    ) {
        return ResponseEntity.ok(ApiResponseImpl.success(findStudentUseCase.findStudentByDni(dni)));
    }

    @Operation(
            summary = "Listar estudiantes",
            description = "Lista los estudiantes registrados con soporte de paginación y ordenamiento. Requiere el rol admin_client_role."
    )
    @PreAuthorize("hasRole('admin_client_role')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentResponse>>> findAll(
            @Parameter(description = "Número de página, inicia en 0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Cantidad de elementos por página")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "Tipo de ordenamiento: asc o desc")
            @RequestParam(defaultValue = "asc") String sort
    ) {
        return ResponseEntity.ok(
                ApiResponseImpl.success(findStudentUseCase.findAllStudent(page, size, sort))
        );
    }

    @Operation(
            summary = "Actualizar estudiante",
            description = "Actualiza los datos de un estudiante existente mediante su identificador UUID. Requiere el rol admin_client_role."
    )
    @PreAuthorize("hasRole('admin_client_role')")
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> update(
            @Valid @RequestBody StudentUpdateRequest request,

            @Parameter(description = "Identificador UUID del estudiante a actualizar", required = true)
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                ApiResponseImpl.success(updateStudentUseCase.updateStudent(request, id))
        );
    }

    @Operation(
            summary = "Eliminar estudiante",
            description = "Elimina un estudiante existente mediante su identificador UUID. Requiere el rol admin_client_role."
    )
    @PreAuthorize("hasRole('admin_client_role')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "Identificador UUID del estudiante a eliminar", required = true)
            @PathVariable UUID id
    ) {
        deleteStudentUseCase.deleteStudent(id);
        return ResponseEntity.ok(
                ApiResponseImpl.success(null, "Estudiante eliminado correctamente")
        );
    }
}