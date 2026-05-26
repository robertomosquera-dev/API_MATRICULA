package org.mt.ev.application.dto.Response;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TuitionResponse(
        UUID id,
        Boolean status,
        LocalDateTime tuitionDate,
        String studentName,
        List<TuitionDetailResponse> tuitionDetails
) implements Serializable {
}
