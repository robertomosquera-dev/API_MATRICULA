package org.mt.ev.application.port.out;

import org.mt.ev.domain.model.Tuition;

import java.util.List;
import java.util.UUID;

public interface TuitionRepositoryPort {
    Tuition create(Tuition tuition);
    Tuition findById(UUID id);
    List<Tuition> findAll();
    Tuition merge(Tuition tuition);
    void deleteTuition(Tuition tuition);
    Tuition update(Tuition tuition);
}
