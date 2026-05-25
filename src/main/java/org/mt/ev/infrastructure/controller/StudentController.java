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
    public ResponseEntity<StudentResponse> create(@Valid @RequestBody StudentRequest request) {
        return ResponseEntity.ok(createStudentUseCase.createStudent(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(findStudentUseCase.findStudentById(id));
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<StudentResponse> findByDni(@PathVariable String dni) {
        return ResponseEntity.ok(findStudentUseCase.findStudentByDni(dni));
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sort) {
        return ResponseEntity.ok(findStudentUseCase.findAllStudent(page, size, sort));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> update(@Valid @RequestBody StudentUpdateRequest request, @PathVariable UUID id) {
        return ResponseEntity.ok(updateStudentUseCase.updateStudent(request,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteStudentUseCase.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}