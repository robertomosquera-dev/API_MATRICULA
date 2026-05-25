package org.mt.ev.application.port.input.tuitionUseCase;

import org.mt.ev.application.dto.Response.TuitionResponse;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface FindTuitionUseCase {
    TuitionResponse findTuitionById(UUID id);
    Map<String, Set<String>> findTuitionMap();
}
