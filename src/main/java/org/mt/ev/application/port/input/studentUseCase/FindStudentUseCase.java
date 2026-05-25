package org.mt.ev.application.port.input.studentUseCase;

import org.mt.ev.application.dto.Response.CourseResponse;
import org.mt.ev.application.dto.Response.StudentResponse;

import java.util.List;
import java.util.UUID;

public interface FindStudentUseCase {
    StudentResponse findStudentById(UUID studentId);
    StudentResponse findStudentByDni(String dni);
    List<StudentResponse> findAllStudent(int page, int size, String sort);

}
