package org.mt.ev.application.port.input.tuitionUseCase;

import org.mt.ev.application.dto.Request.TuitionRequest;
import org.mt.ev.application.dto.Response.TuitionResponse;

public interface CreateTuitionUseCase {
    TuitionResponse createTuition(TuitionRequest tuitionRequest);
}
