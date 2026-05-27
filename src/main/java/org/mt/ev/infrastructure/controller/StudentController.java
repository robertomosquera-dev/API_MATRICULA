package org.mt.ev.infrastructure.controller;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final CreateStudentUseCase createStudentUseCase;
    private final FindStudentUseCase findStudentUseCase;
    private final UpdateStudentUseCase updateStudentUseCase;
    private final DeleteStudentUseCase deleteStudentUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponse>> create(@Valid @RequestBody StudentRequest request) {
        return ResponseEntity.status(201)
                .body(ApiResponseImpl.created(createStudentUseCase.createStudent(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponseImpl.success(findStudentUseCase.findStudentById(id)));
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<ApiResponse<StudentResponse>> findByDni(@PathVariable String dni) {
        return ResponseEntity.ok(ApiResponseImpl.success(findStudentUseCase.findStudentByDni(dni)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentResponse>>> findAll(
            @RequestParam(defaultValue = "0")   int page,
            @RequestParam(defaultValue = "10")  int size,
            @RequestParam(defaultValue = "asc") String sort
    ) {
        return ResponseEntity.ok(ApiResponseImpl.success(findStudentUseCase.findAllStudent(page, size, sort)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> update(@Valid @RequestBody StudentUpdateRequest request, @PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponseImpl.success(updateStudentUseCase.updateStudent(request, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        deleteStudentUseCase.deleteStudent(id);
        return ResponseEntity.ok(ApiResponseImpl.success(null, "Estudiante eliminado correctamente"));
    }
}