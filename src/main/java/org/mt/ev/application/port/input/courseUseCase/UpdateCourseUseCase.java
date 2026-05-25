package org.mt.ev.application.port.input.courseUseCase;

import org.mt.ev.application.dto.Request.CourseRequest;
import org.mt.ev.application.dto.Request.CourseUpdateRequest;
import org.mt.ev.application.dto.Response.CourseResponse;

import java.util.UUID;

public interface UpdateCourseUseCase {
    CourseResponse updateCourse(CourseUpdateRequest courseRequest, UUID courseId);
}
