package org.mt.ev.infrastructure.repository;

import aj.org.objectweb.asm.commons.Remapper;
import org.mt.ev.infrastructure.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataStudentRepository extends JpaRepository<StudentEntity, UUID> {
    Optional<StudentEntity> findByDni(String dni);

    Boolean existsByDni(String dni);
}
