package org.mt.ev.application.port.input.studentUseCase;

import org.mt.ev.application.dto.Request.StudentRequest;
import org.mt.ev.application.dto.Response.StudentResponse;

public interface UpdateStudentUseCase {
    StudentResponse updateStudent(StudentRequest studentRequest);
}
