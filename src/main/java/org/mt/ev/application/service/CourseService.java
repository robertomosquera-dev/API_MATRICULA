package org.mt.ev.application.service;

import lombok.RequiredArgsConstructor;
import org.mt.ev.application.dto.Request.CourseRequest;
import org.mt.ev.application.dto.Request.CourseUpdateRequest;
import org.mt.ev.application.dto.Response.CourseResponse;
import org.mt.ev.application.port.input.courseUseCase.*;
import org.mt.ev.application.port.out.CourseRepositoryPort;
import org.mt.ev.domain.exceptions.CourseInvalidStateException;
import org.mt.ev.domain.model.Course;
import org.mt.ev.domain.model.CourseStatus;
import org.mt.ev.infrastructure.mapper.CourseMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService implements
        CreateCourseUseCase, DeleteCourseUseCase,
        FindCourseUseCase, UpdateCourseUseCase,
        ChangeStatusCourseUseCase {

    private final CourseRepositoryPort courseRepositoryPort;
    private final CourseMapper courseMapper;

    @Override
    public CourseResponse createCourse(CourseRequest courseRequest) {
        Course course = courseMapper.toDomainFromRequest(courseRequest);
        course = courseRepositoryPort.create(course);
        return courseMapper.toResponse(course);
    }

    @Override
    public void deleteCourse(UUID courseId) {
        Course course = courseRepositoryPort.findById(courseId);

        if (course.isActive()) {
            throw CourseInvalidStateException.cannotDeleteActive();
        }

        courseRepositoryPort.delete(course);
    }

    @Override
    public CourseResponse findCourseByName(String name) {
        return courseMapper
                .toResponse(courseRepositoryPort.findByName(name));
    }

    @Override
    public CourseResponse findCourseById(UUID id) {
        return courseMapper
                .toResponse(courseRepositoryPort.findById(id));
    }

    @Override
    public List<CourseResponse> findAllCourses(
            int page,
            int size,
            String sort,
            CourseStatus status
    ) {
        return courseMapper
                .toResponseList(courseRepositoryPort.findAll(page, size, sort, status));
    }

    @Override
    public CourseResponse updateCourse(CourseUpdateRequest courseRequest, UUID courseId) {
        Course course = courseMapper.toDomainFromUpdateRequest(courseRequest,courseId);
        course = courseRepositoryPort.update(course);
        return courseMapper.toResponse(course);
    }

    @Override
    public CourseResponse enable(UUID courseId) {
        Course course = courseRepositoryPort.findById(courseId);
        course.enable();
        course = courseRepositoryPort.merge(course);
        return courseMapper.toResponse(course);
    }

    @Override
    public CourseResponse disable(UUID courseId) {
        Course course = courseRepositoryPort.findById(courseId);
        course.disable();
        course = courseRepositoryPort.merge(course);
        return courseMapper.toResponse(course);
    }

}
