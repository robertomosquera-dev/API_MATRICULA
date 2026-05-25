package org.mt.ev.application.dto.Response;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record TuitionResponse(
        UUID id,
        Boolean status,
        LocalDate tuitionDate,
        String studentName,
        List<TuitionDetailResponse> tuitionDetails
) {
}
