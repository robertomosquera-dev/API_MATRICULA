package org.mt.ev.infrastructure.adapter;

import lombok.RequiredArgsConstructor;
import org.mt.ev.application.port.out.StudentRepositoryPort;
import org.mt.ev.domain.model.Student;
import org.mt.ev.infrastructure.entity.StudentEntity;
import org.mt.ev.infrastructure.mapper.StudentMapper;
import org.mt.ev.infrastructure.repository.SpringDataStudentRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
@RequiredArgsConstructor
public class JpaStudentRepositoryAdapter implements StudentRepositoryPort {

    private final SpringDataStudentRepository springDataStudentRepository;
    private final StudentMapper studentMapper;

    @Override
    public Student create(Student student) {
        StudentEntity entity = studentMapper.toEntity(student);
        entity = springDataStudentRepository.save(entity);
        return studentMapper.toDomain(entity);
    }

    @Override
    public void delete(UUID id) {

        if (!springDataStudentRepository.existsById(id)) {
            throw new RuntimeException("Student not found");
        }

        springDataStudentRepository.deleteById(id);
    }

    @Override
    public Student findById(UUID id) {
        return springDataStudentRepository
                .findById(id)
                .map(studentMapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @Override
    public Student findByDni(String dni) {
        return springDataStudentRepository
                .findByDni((dni))
                .map(studentMapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @Override
    public Student update(Student student) {

        StudentEntity entity = springDataStudentRepository
                .findById(student.getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        studentMapper.updateEntity(student, entity);

        entity = springDataStudentRepository.save(entity);

        return studentMapper.toDomain(entity);
    }

    @Override
    public List<Student> findAll(int page, int size, String sort) {

        List<StudentEntity> students = springDataStudentRepository.findAll(
                PageRequest.of(
                        page,
                        size,
                        Sort.by(Sort.Direction.fromString(sort), "names ")
                )
        ).getContent();

        return students.stream()
                .map(studentMapper::toDomain)
                .toList();
    }
}