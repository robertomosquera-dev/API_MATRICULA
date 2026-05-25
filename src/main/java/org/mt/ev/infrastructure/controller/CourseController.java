package org.mt.ev.infrastructure.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mt.ev.application.dto.Request.CourseRequest;
import org.mt.ev.application.dto.Request.CourseUpdateRequest;
import org.mt.ev.application.dto.Response.CourseResponse;
import org.mt.ev.application.port.input.courseUseCase.*;
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
    public ResponseEntity<CourseResponse> create(@Valid @RequestBody CourseRequest request) {
        return ResponseEntity.ok(createCourseUseCase.createCourse(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(findCourseUseCase.findCourseById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CourseResponse> findByName(@PathVariable String name) {
        return ResponseEntity.ok(findCourseUseCase.findCourseByName(name));
    }

    @GetMapping
    public ResponseEntity<List<CourseResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sort
    ) {
        return ResponseEntity.ok(
                findCourseUseCase.findAllCourses(page, size, sort)
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CourseResponse> update(@Valid @RequestBody CourseUpdateRequest request, @PathVariable UUID id) {
        return ResponseEntity.ok(updateCourseUseCase.updateCourse(request,id));
    }

    @PatchMapping("/{id}/enable")
    public ResponseEntity<CourseResponse> enable(@PathVariable UUID id) {
        return ResponseEntity.ok(
                changeStatusCourseUseCase.enable(id)
        );
    }

    @PatchMapping("/{id}/disable")
    public ResponseEntity<CourseResponse> disable(@PathVariable UUID id) {
        return ResponseEntity.ok(
                changeStatusCourseUseCase.disable(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteCourseUseCase.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}