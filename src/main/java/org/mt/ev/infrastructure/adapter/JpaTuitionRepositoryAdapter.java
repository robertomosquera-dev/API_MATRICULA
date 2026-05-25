package org.mt.ev.infrastructure.adapter;

import org.mt.ev.application.port.out.TuitionRepositoryPort;
import org.mt.ev.domain.model.Tuition;
import org.springframework.stereotype.Repository;

@Repository
public class JpaTuitionRepositoryAdapter implements TuitionRepositoryPort {

    @Override
    public Tuition create(Tuition tuition) {
        return null;
    }

    @Override
    public Tuition findById(String id) {
        return null;
    }

}
