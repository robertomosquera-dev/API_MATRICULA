package org.mt.ev.domain.model;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.mt.ev.domain.exceptions.TuitionInvalidStateException;
import org.mt.ev.domain.exceptions.TuitionValidationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Tuition {

    private UUID id;
    private LocalDateTime tuitionDate;
    private Boolean status;
    private Student student;
    private List<TuitionDetail> details;

    public void enable() {
        if (Boolean.TRUE.equals(this.status))
            throw TuitionInvalidStateException.alreadyActive();
        this.status = true;
    }

    public void disable() {
        if (Boolean.FALSE.equals(this.status))
            throw TuitionInvalidStateException.alreadyInactive();
        this.status = false;
    }

    public boolean isActive() {
        return Boolean.TRUE.equals(this.status);
    }

    public void update(Student student, List<TuitionDetail> details) {
        if (student == null)
            throw TuitionValidationException.studentRequired();
        if (!student.isAdult())
            throw TuitionInvalidStateException.studentIsMinor();
        if (details == null || details.isEmpty())
            throw TuitionValidationException.detailsRequired();
        this.student = student;
        this.details = details;
    }

    public static Tuition create(Student student, List<TuitionDetail> details) {

        if (student == null)
            throw TuitionValidationException.studentRequired();

        if (!student.isAdult())
            throw TuitionInvalidStateException.studentIsMinor();

        if (details == null || details.isEmpty())
            throw TuitionValidationException.detailsRequired();

        return Tuition.builder()
                .student(student)
                .details(details)
                .build();
    }

}
