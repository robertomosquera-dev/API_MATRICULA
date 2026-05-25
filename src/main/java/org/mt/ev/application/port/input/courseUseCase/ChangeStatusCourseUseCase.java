package org.mt.ev.application.port.input.courseUseCase;

import org.mt.ev.application.dto.Response.CourseResponse;

import java.util.UUID;

public interface ChangeStatusCourseUseCase {
    CourseResponse enable(UUID courseId);
    CourseResponse disable(UUID courseId);
}
