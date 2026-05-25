package org.mt.ev.application.port.out;

import org.mt.ev.domain.model.Tuition;

public interface TuitionRepositoryPort {
    Tuition create(Tuition tuition);
    Tuition findById(String id);
}
