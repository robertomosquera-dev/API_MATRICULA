package org.mt.ev.infrastructure.mapper;

import org.mapstruct.*;
import org.mt.ev.application.dto.Request.StudentRequest;
import org.mt.ev.application.dto.Request.StudentUpdateRequest;
import org.mt.ev.application.dto.Response.StudentResponse;
import org.mt.ev.domain.model.Student;
import org.mt.ev.infrastructure.entity.StudentEntity;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "id", ignore = true)
    Student toDomainFromRequest(StudentRequest studentRequest);

    @Mapping(target = "id", source = "studentId")
    Student toDomainFromUpdateRequest(StudentUpdateRequest studentUpdateRequest, UUID studentId);

    Student toDomain(StudentEntity studentEntity);

    StudentEntity toEntity(Student student);

    StudentResponse toResponse(Student student);

    List<Student> toDomainList(List<StudentEntity> entityList);

    List<StudentResponse> toResponseList(List<Student> domainList);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    void updateEntity(Student student, @MappingTarget StudentEntity entity);
}
