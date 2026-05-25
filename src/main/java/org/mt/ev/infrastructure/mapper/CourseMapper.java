package org.mt.ev.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mt.ev.application.dto.Request.CourseRequest;
import org.mt.ev.application.dto.Response.CourseResponse;
import org.mt.ev.domain.model.Course;
import org.mt.ev.infrastructure.entity.CourseEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    Course toDomainFromRequest(CourseRequest courseRequest);

    Course toDomain(CourseEntity entity);

    CourseEntity toEntity(Course course);

    CourseResponse toResponse(Course course);

    void updateEntity(Course course, @MappingTarget CourseEntity entity);

    List<Course> toDomainList(List<CourseEntity> entityList);

    List<CourseResponse> toResponseList(List<Course> domainList);
}
