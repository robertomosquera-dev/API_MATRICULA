package org.mt.ev.infrastructure.repository;

import org.mt.ev.infrastructure.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataStudentRepository extends JpaRepository<StudentEntity, UUID> {
}
