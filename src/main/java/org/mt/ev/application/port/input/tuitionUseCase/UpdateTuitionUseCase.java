package org.mt.ev.application.port.input.tuitionUseCase;

import org.mt.ev.application.dto.Request.TuitionRequest;
import org.mt.ev.application.dto.Response.TuitionResponse;

import java.util.UUID;

public interface UpdateTuitionUseCase {
    TuitionResponse updateTuition(UUID id, TuitionRequest request);
}
