package org.mt.ev.application.port.input.studentUseCase;

import org.mt.ev.application.dto.Request.StudentRequest;
import org.mt.ev.application.dto.Response.StudentResponse;

public interface CreateStudentUseCase {
    StudentResponse createStudent(StudentRequest studentRequest);
}
