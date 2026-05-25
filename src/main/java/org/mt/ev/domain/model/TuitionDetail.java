package org.mt.ev.domain.model;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
            throw new IllegalArgumentException("El curso es obligatorio");

        if (!course.isActive())
            throw new IllegalStateException("No se puede matricular en un curso inactivo");

        return TuitionDetail.builder()
                .course(course)
                .classroom(classroom)
                .build();
    }

}
