package org.mt.ev.application.port.out;

import org.mt.ev.domain.model.Student;

import java.util.List;
import java.util.UUID;

public interface StudentRepositoryPort {
    Student create(Student student);
    void delete(UUID id);
    Student findById(UUID id);
    Student findByDni(String dni);
    Student update(Student student);
    List<Student> findAll(int page, int size, String sort);
}
