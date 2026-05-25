package org.mt.ev.application.dto.Request;

import java.util.List;
import java.util.UUID;

public record TuitionRequest(
    UUID studentId,
    List<TuitionDetailRequest> tuitionDetails
) {
}
