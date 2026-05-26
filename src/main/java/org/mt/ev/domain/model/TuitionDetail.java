package org.mt.ev.domain.model;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.mt.ev.domain.exceptions.TuitionDetailValidationException;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TuitionDetail {

    private UUID id;
    private Course course;
    private String classroom;

    public static TuitionDetail create(Course course, String classroom) {
        if (course == null)
            throw TuitionDetailValidationException.courseRequired();

        if (!course.isActive())
            throw TuitionDetailValidationException.courseIsInactive();

        return TuitionDetail.builder()
                .course(course)
                .classroom(classroom)
                .build();
    }

}
