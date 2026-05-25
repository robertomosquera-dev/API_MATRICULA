package org.mt.ev.application.port.input.courseUseCase;

import org.mt.ev.application.dto.Response.CourseResponse;

import java.util.List;
import java.util.UUID;

public interface FindCourseUseCase {
    CourseResponse findCourseByName(String name);
    CourseResponse findCourseById(UUID id);
    List<CourseResponse> findAllCourses(int page, int size,String sort);
}
