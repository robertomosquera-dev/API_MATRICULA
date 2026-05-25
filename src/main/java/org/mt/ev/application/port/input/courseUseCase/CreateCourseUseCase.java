package org.mt.ev.application.port.input.courseUseCase;

import org.mt.ev.application.dto.Request.CourseRequest;
import org.mt.ev.application.dto.Response.CourseResponse;

public interface CreateCourseUseCase {

    CourseResponse createCourse(CourseRequest courseRequest);

}
