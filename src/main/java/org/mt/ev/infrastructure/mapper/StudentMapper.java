package org.mt.ev.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mt.ev.application.dto.Request.StudentRequest;
import org.mt.ev.application.dto.Response.StudentResponse;
import org.mt.ev.domain.model.Student;
import org.mt.ev.infrastructure.entity.StudentEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "id", ignore = true)
    Student toDomainFromRequest(StudentRequest studentRequest);

    Student toDomain(StudentEntity studentEntity);

    StudentEntity toEntity(Student student);

    StudentResponse toResponse(Student student);

    List<Student> toDomainList(List<StudentEntity> entityList);

    List<StudentResponse> toResponseList(List<Student> domainList);

    void updateEntity(Student student, @MappingTarget StudentEntity entity);
}
