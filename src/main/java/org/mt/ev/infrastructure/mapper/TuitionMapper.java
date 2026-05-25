package org.mt.ev.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mt.ev.application.dto.Response.TuitionResponse;
import org.mt.ev.domain.model.Tuition;
import org.mt.ev.infrastructure.entity.TuitionEntity;

@Mapper(
        componentModel = "spring",
        uses = {
                StudentMapper.class,
                TuitionDetailMapper.class
        }
)
public interface TuitionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "tuitionDate", ignore = true)
    TuitionEntity toEntity(Tuition tuition);


    @Mapping(target = "studentName", source = "student.names")
    @Mapping(target = "tuitionDetails", source = "details")
    @Mapping(target = "tuitionDate", source = "tuitionDate")
    TuitionResponse toResponse(Tuition tuition);


}
