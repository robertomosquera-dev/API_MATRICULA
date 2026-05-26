package org.mt.ev.application.port.out;

import org.mt.ev.domain.model.Course;
import org.mt.ev.domain.model.CourseStatus;

import java.util.List;
import java.util.UUID;

public interface CourseRepositoryPort {
    Course create(Course course);
    void delete(Course course);
    Course findById(UUID id);
    Course findByName(String name);
    Course update(Course course);
    Course merge(Course course);
    List<Course> findAll(int page, int size, String sort);
    List<Course> findAll(int page, int size, String sort, CourseStatus status);
}
