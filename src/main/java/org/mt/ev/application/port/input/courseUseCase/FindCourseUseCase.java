package org.mt.ev.application.port.input.courseUseCase;

import org.mt.ev.application.dto.Response.CourseResponse;

public interface FindCourseUseCase {
    CourseResponse findCourseByName(String name);
    CourseResponse findCourseById(String id);
}
