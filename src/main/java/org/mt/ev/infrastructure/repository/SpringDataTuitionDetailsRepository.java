package org.mt.ev.infrastructure.repository;

import org.mt.ev.infrastructure.entity.TuitionDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataTuitionDetailsRepository extends JpaRepository<TuitionDetailEntity, UUID> {
}
