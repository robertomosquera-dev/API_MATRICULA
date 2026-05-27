package org.mt.ev.infrastructure.controller;

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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CreateCourseUseCase createCourseUseCase;
    private final FindCourseUseCase findCourseUseCase;
    private final UpdateCourseUseCase updateCourseUseCase;
    private final DeleteCourseUseCase deleteCourseUseCase;
    private final ChangeStatusCourseUseCase changeStatusCourseUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<CourseResponse>> create(@Valid @RequestBody CourseRequest request) {
        return ResponseEntity.status(201)
                .body(ApiResponseImpl.created(createCourseUseCase.createCourse(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponseImpl.success(findCourseUseCase.findCourseById(id)));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse<CourseResponse>> findByName(@PathVariable String name) {
        return ResponseEntity.ok(ApiResponseImpl.success(findCourseUseCase.findCourseByName(name)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseResponse>>> findAll(
            @RequestParam(defaultValue = "0")   int page,
            @RequestParam(defaultValue = "10")  int size,
            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(defaultValue = "ALL") CourseStatus status
    ) {
        return ResponseEntity.ok(
                ApiResponseImpl.success(findCourseUseCase.findAllCourses(page, size, sort, status))
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> update(@Valid @RequestBody CourseUpdateRequest request, @PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponseImpl.success(updateCourseUseCase.updateCourse(request, id)));
    }

    @PatchMapping("/{id}/enable")
    public ResponseEntity<ApiResponse<CourseResponse>> enable(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponseImpl.success(changeStatusCourseUseCase.enable(id)));
    }

    @PatchMapping("/{id}/disable")
    public ResponseEntity<ApiResponse<CourseResponse>> disable(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponseImpl.success(changeStatusCourseUseCase.disable(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        deleteCourseUseCase.deleteCourse(id);
        return ResponseEntity.ok(ApiResponseImpl.success(null, "Curso eliminado correctamente"));
    }
}