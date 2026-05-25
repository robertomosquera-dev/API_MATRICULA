package org.mt.ev.domain.model;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
            throw new IllegalStateException("La matrícula ya está activa");
        this.status = true;
    }

    public void disable() {
        if (Boolean.FALSE.equals(this.status))
            throw new IllegalStateException("La matrícula ya está inactiva");
        this.status = false;
    }

    //Intetar probar
    public Student getStudentIsCourse(UUID courseId) {
        if(details.stream().anyMatch(detail -> detail.getCourse().getId().equals(courseId))){
            return student;
        }
        return null;
    }

    public static Tuition create(
            LocalDateTime tuitionDate,
            Student student,
            List<TuitionDetail> details) {

        if (student == null)
            throw new IllegalArgumentException("El estudiante es obligatorio");

        if (!student.isAdult())
            throw new IllegalStateException("El estudiante debe ser mayor de edad para matricularse");

        if (details == null || details.isEmpty())
            throw new IllegalArgumentException("Debe incluir al menos un curso");

        return Tuition.builder()
                .student(student)
                .details(details)
                .build();
    }

}
