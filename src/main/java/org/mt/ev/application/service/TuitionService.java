package org.mt.ev.application.service;

import lombok.RequiredArgsConstructor;
import org.mt.ev.application.dto.Request.TuitionRequest;
import org.mt.ev.application.dto.Response.TuitionResponse;
import org.mt.ev.application.port.input.tuitionUseCase.CreateTuitionUseCase;
import org.mt.ev.application.port.input.tuitionUseCase.FindTuitionUseCase;
import org.mt.ev.application.port.out.TuitionRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TuitionService
        implements CreateTuitionUseCase, FindTuitionUseCase {

    private final TuitionRepositoryPort tuitionRepositoryPort;

    @Override
    public TuitionResponse createTuition(TuitionRequest tuitionRequest) {
        return null;
    }

    @Override
    public TuitionResponse findTuitionById(String id) {
        return null;
    }

    @Override
    public Map<String, Set<String>> findTuitionMapById(String id) {
        return Map.of();
    }

}
