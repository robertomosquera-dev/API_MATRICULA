package org.mt.ev.infrastructure.adapter;

import lombok.RequiredArgsConstructor;
import org.mt.ev.application.port.out.CourseRepositoryPort;
import org.mt.ev.domain.model.Course;
import org.mt.ev.infrastructure.entity.CourseEntity;
import org.mt.ev.infrastructure.mapper.CourseMapper;
import org.mt.ev.infrastructure.repository.SpringDataCourseRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaCourseRepositoryAdapter implements CourseRepositoryPort {

    private final SpringDataCourseRepository springDataCourseRepository;
    private final CourseMapper courseMapper;

    @Override
    public Course create(Course course) {
        CourseEntity entity = courseMapper.toEntity(course);
        entity = springDataCourseRepository.save(entity);
        return courseMapper.toDomain(entity);
    }

    @Override
    public void delete(Course course) {
        if (!springDataCourseRepository.existsById(course.getId())) {
            throw new RuntimeException("Course not found");
        }
        springDataCourseRepository.deleteById(course.getId());
    }

    @Override
    public Course findById(UUID id) {
        return springDataCourseRepository
                .findById(id)
                .map(courseMapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Override
    public Course findByName(String name) {
        return springDataCourseRepository
                .findByName(name)
                .map(courseMapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Override
    public Course merge(Course course){
        CourseEntity entity = springDataCourseRepository.getReferenceById(course.getId());
        courseMapper.updateEntity(course, entity);
        return courseMapper.toDomain(springDataCourseRepository.save(entity));
    }

    @Override
    public Course update(Course course) {
        CourseEntity entity = springDataCourseRepository
                .findById(course.getId())
                .orElseThrow(() -> new RuntimeException("Course not found"));
        courseMapper.updateEntity(course, entity);
        entity = springDataCourseRepository.save(entity);
        return courseMapper.toDomain(entity);
    }

    @Override
    public List<Course> findAll(int page, int size, String sort) {
        List<CourseEntity> courses = springDataCourseRepository.findAll(
                PageRequest.of(
                        page,
                        size,
                        Sort.by(Sort.Direction.fromString(sort), "name")
                )
        ).getContent();
        return courses.stream()
                .map(courseMapper::toDomain)
                .toList();
    }

}
