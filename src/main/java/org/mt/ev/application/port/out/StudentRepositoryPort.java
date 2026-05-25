package org.mt.ev.application.port.out;

import org.mt.ev.domain.model.Student;

import java.util.UUID;

public interface StudentRepositoryPort {
    Student create(Student student);
    void delete(UUID id);
    Student findById(UUID id);
    Student findByDni(String dni);
    Student update(Student student);
}
