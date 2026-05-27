package org.mt.ev.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mt.ev.application.dto.Request.StudentRequest;
import org.mt.ev.application.dto.Request.StudentUpdateRequest;
import org.mt.ev.application.dto.Request.UserRequest;
import org.mt.ev.application.dto.Response.StudentResponse;
import org.mt.ev.application.exceptions.StudentInvalidStateException;
import org.mt.ev.application.port.input.studentUseCase.CreateStudentUseCase;
import org.mt.ev.application.port.input.studentUseCase.DeleteStudentUseCase;
import org.mt.ev.application.port.input.studentUseCase.FindStudentUseCase;
import org.mt.ev.application.port.input.studentUseCase.UpdateStudentUseCase;
import org.mt.ev.application.port.out.KeycloakRepositoryPort;
import org.mt.ev.application.port.out.StudentRepositoryPort;
import org.mt.ev.domain.model.Student;
import org.mt.ev.infrastructure.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService implements
        CreateStudentUseCase,
        DeleteStudentUseCase,
        FindStudentUseCase,
        UpdateStudentUseCase {

    private final StudentRepositoryPort studentRepositoryPort;
    private final KeycloakRepositoryPort keycloakRepositoryPort;
    private final StudentMapper studentMapper;

    @Value("${jwt.role-user}")
    private String ROLE_STUDENT;

    @CacheEvict(
            value = "student",
            allEntries = true
    )
    @Override
    public StudentResponse createStudent(StudentRequest studentRequest) {

        Student student = studentMapper.toDomainFromRequest(studentRequest);

        if (!student.isAdult()) {
            throw StudentInvalidStateException.isMinor();
        }

        UserRequest userRequest = UserRequest.builder()
                .username(studentRequest.dni())
                .email(studentRequest.email())
                .firstname(studentRequest.names())
                .lastname(studentRequest.surnames())
                .password(studentRequest.password())
                .roles(Set.of(ROLE_STUDENT))
                .build();

        String res = keycloakRepositoryPort.createUser(userRequest);

        log.info("user creation response: {}", res);

        student = studentRepositoryPort.create(student);

        return studentMapper.toResponse(student);
    }

    @CacheEvict(
            value = "student",
            allEntries = true
    )
    @Override
    public void deleteStudent(UUID studentId) {
        studentRepositoryPort.delete(studentId);
    }

    @Cacheable(
            value = "student",
            key = "#studentId"
    )
    @Override
    public StudentResponse findStudentById(UUID studentId) {
        return studentMapper.toResponse(
                studentRepositoryPort.findById(studentId)
        );
    }

    @Cacheable(
            value = "student",
            key = "#dni"
    )
    @Override
    public StudentResponse findStudentByDni(String dni) {
        return studentMapper.toResponse(
                studentRepositoryPort.findByDni(dni)
        );
    }


    @Cacheable(
            value = "student",
            key = "{#page,#size,#sort}"
    )
    @Override
    public List<StudentResponse> findAllStudent(
            int page,
            int size,
            String sort
    ) {
        return studentMapper.toResponseList(
                studentRepositoryPort.findAll(page, size, sort)
        );
    }

    @CachePut(
            value = "student",
            key = "#studentId"
    )
    @Override
    public StudentResponse updateStudent(
            StudentUpdateRequest studentUpdateRequest,
            UUID studentId) {

        Student student =
                studentMapper.toDomainFromUpdateRequest(
                        studentUpdateRequest,
                        studentId
                );

        student = studentRepositoryPort.update(student);

        return studentMapper.toResponse(student);
    }
}