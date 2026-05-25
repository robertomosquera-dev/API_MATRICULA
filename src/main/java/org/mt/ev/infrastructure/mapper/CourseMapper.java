package org.mt.ev.infrastructure.mapper;

import org.mapstruct.*;
import org.mt.ev.application.dto.Request.CourseRequest;
import org.mt.ev.application.dto.Request.CourseUpdateRequest;
import org.mt.ev.application.dto.Response.CourseResponse;
import org.mt.ev.domain.model.Course;
import org.mt.ev.infrastructure.entity.CourseEntity;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CourseMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    Course toDomainFromRequest(CourseRequest courseRequest);

    @Mapping(target = "id", source = "courseId")
    Course toDomainFromUpdateRequest(CourseUpdateRequest request, UUID courseId);

    Course toDomain(CourseEntity entity);

    CourseEntity toEntity(Course course);

    CourseResponse toResponse(Course course);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    void updateEntity(Course course, @MappingTarget CourseEntity entity);

    List<Course> toDomainList(List<CourseEntity> entityList);

    List<CourseResponse> toResponseList(List<Course> domainList);
}
