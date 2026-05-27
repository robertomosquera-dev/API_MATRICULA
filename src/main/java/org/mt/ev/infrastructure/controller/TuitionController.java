package org.mt.ev.infrastructure.controller;

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
import org.springframework.web.bind.annotation.*;


import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tuitions")
@RequiredArgsConstructor
public class TuitionController {

    private final CreateTuitionUseCase createTuitionUseCase;
    private final FindTuitionUseCase findTuitionUseCase;
    private final ChangeStatusTuitionUseCase changeStatusTuitionUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<TuitionResponse>> create(@Valid @RequestBody TuitionRequest request) {
        return ResponseEntity.status(201)
                .body(ApiResponseImpl.created(createTuitionUseCase.createTuition(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TuitionResponse>> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponseImpl.success(findTuitionUseCase.findTuitionById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Set<String>>>> findMapById() {
        return ResponseEntity.ok(ApiResponseImpl.success(findTuitionUseCase.findTuitionMap()));
    }

    @PatchMapping("/{id}/enable")
    public ResponseEntity<ApiResponse<TuitionResponse>> enable(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponseImpl.success(changeStatusTuitionUseCase.enable(id)));
    }

    @PatchMapping("/{id}/disable")
    public ResponseEntity<ApiResponse<TuitionResponse>> disable(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponseImpl.success(changeStatusTuitionUseCase.disable(id)));
    }
}