package org.mt.ev.application.port.input.studentUseCase;

import org.mt.ev.application.dto.Response.StudentResponse;

import java.util.UUID;

public interface FindStudentUseCase {
    StudentResponse findStudentById(UUID studentId);
    StudentResponse findStudentByDni(String dni);
}
