package org.mt.ev.application.port.out;

import org.mt.ev.domain.model.Course;

import java.util.UUID;

public interface CourseRepositoryPort {
    Course create(Course course);
    void delete(UUID id);
    Course findById(String id);
    Course findByName(String name);
    Course update(Course course);
}
