package org.mt.ev.application.port.input.studentUseCase;

import org.mt.ev.application.dto.Request.StudentRequest;
import org.mt.ev.application.dto.Request.StudentUpdateRequest;
import org.mt.ev.application.dto.Response.StudentResponse;

import java.util.UUID;

public interface UpdateStudentUseCase {
    StudentResponse updateStudent(StudentUpdateRequest studentRequest, UUID studentId);
}
