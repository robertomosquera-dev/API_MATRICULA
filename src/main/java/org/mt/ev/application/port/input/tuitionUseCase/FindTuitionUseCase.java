package org.mt.ev.application.port.input.tuitionUseCase;

import org.mt.ev.application.dto.Response.TuitionResponse;

import java.util.Map;
import java.util.Set;

public interface FindTuitionUseCase {
    TuitionResponse findTuitionById(String id);
    Map<String, Set<String>> findTuitionMapById(String id);
}
