package org.mt.ev.application.port.input.tuitionUseCase;

import org.mt.ev.application.dto.Response.TuitionResponse;

import java.util.UUID;

public interface ChangeStatusTuitionUseCase {
    TuitionResponse enable(UUID tuitionId);
    TuitionResponse disable(UUID tuitionId);
}
