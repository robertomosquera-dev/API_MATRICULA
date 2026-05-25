package org.mt.ev.infrastructure.repository;

import org.mt.ev.infrastructure.entity.TuitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataTuitionRepository extends JpaRepository<TuitionEntity, UUID> {
}
