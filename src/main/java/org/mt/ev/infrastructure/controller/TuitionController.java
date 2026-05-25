package org.mt.ev.infrastructure.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mt.ev.application.dto.Request.TuitionRequest;
import org.mt.ev.application.dto.Response.TuitionResponse;
import org.mt.ev.application.port.input.tuitionUseCase.CreateTuitionUseCase;
import org.mt.ev.application.port.input.tuitionUseCase.FindTuitionUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/tuitions")
@RequiredArgsConstructor
public class TuitionController {

    private final CreateTuitionUseCase createTuitionUseCase;
    private final FindTuitionUseCase findTuitionUseCase;

    @PostMapping
    public ResponseEntity<TuitionResponse> create(@Valid @RequestBody TuitionRequest request) {
        return ResponseEntity.ok(createTuitionUseCase.createTuition(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TuitionResponse> findById(@PathVariable String id) {
        return ResponseEntity.ok(findTuitionUseCase.findTuitionById(id));
    }

    @GetMapping("/{id}/map")
    public ResponseEntity<Map<String, Set<String>>> findMapById(@PathVariable String id) {
        return ResponseEntity.ok(findTuitionUseCase.findTuitionMapById(id));
    }
}