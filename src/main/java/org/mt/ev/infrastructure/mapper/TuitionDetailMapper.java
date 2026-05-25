package org.mt.ev.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mt.ev.application.dto.Response.TuitionDetailResponse;
import org.mt.ev.domain.model.TuitionDetail;
import org.mt.ev.infrastructure.entity.TuitionDetailEntity;

@Mapper(
        componentModel = "spring",
        uses = {CourseMapper.class}
)
public interface TuitionDetailMapper {

    @Mapping(target = "id", ignore = true)
    TuitionDetailEntity toEntity(TuitionDetail tuitionDetail);

    TuitionDetail toDomain(TuitionDetailEntity tuitionDetailEntity);

    @Mapping(target = "courseName", source = "course.name")
    TuitionDetailResponse toResponse(TuitionDetail tuitionDetail);
}
