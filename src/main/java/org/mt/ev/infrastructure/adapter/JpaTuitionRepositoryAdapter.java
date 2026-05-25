package org.mt.ev.infrastructure.adapter;

import lombok.RequiredArgsConstructor;
import org.mt.ev.application.port.out.TuitionRepositoryPort;
import org.mt.ev.domain.model.Tuition;
import org.mt.ev.domain.model.TuitionDetail;
import org.mt.ev.infrastructure.entity.TuitionDetailEntity;
import org.mt.ev.infrastructure.entity.TuitionEntity;
import org.mt.ev.infrastructure.mapper.TuitionMapper;
import org.mt.ev.infrastructure.repository.SpringDataTuitionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaTuitionRepositoryAdapter implements TuitionRepositoryPort {

    private final SpringDataTuitionRepository springDataTuitionRepository;
    private final TuitionMapper tuitionMapper;

    @Override
    public Tuition create(Tuition tuition) {

        TuitionEntity entity = tuitionMapper.toEntity(tuition);

        List<TuitionDetailEntity> details = entity.getTuitionDetails();

        if (details != null) {
            for (TuitionDetailEntity detail : details) {
                detail.setTuition(entity);
            }
        }

        entity = springDataTuitionRepository.save(entity);

        return tuitionMapper.toDomain(entity);
    }

    @Override
    public Tuition findById(UUID id) {

        TuitionEntity entity = springDataTuitionRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Tuition not found"));

        return tuitionMapper.toDomain(entity);
    }

    @Override
    public List<Tuition> findAll() {
        return tuitionMapper.toDomainList(springDataTuitionRepository.findAll());
    }
}