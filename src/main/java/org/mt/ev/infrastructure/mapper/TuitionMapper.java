package org.mt.ev.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mt.ev.application.dto.Response.TuitionResponse;
import org.mt.ev.domain.model.Tuition;
import org.mt.ev.infrastructure.entity.TuitionEntity;

import java.util.List;

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
    @Mapping(target = "tuitionDetails", source = "details")
    TuitionEntity toEntity(Tuition tuition);

    @Mapping(target = "details", source = "tuitionDetails")
    Tuition toDomain(TuitionEntity tuitionEntity);

    @Mapping(target = "studentName", source = "student.names")
    @Mapping(target = "tuitionDetails", source = "details")
    @Mapping(target = "tuitionDate", source = "tuitionDate")
    TuitionResponse toResponse(Tuition tuition);

    List<Tuition> toDomainList(List<TuitionEntity> entityList);
}
