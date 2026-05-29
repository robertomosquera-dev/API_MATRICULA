package org.mt.ev.application.service;

import lombok.RequiredArgsConstructor;
import org.mt.ev.application.dto.Request.TuitionDetailRequest;
import org.mt.ev.application.dto.Request.TuitionRequest;
import org.mt.ev.application.dto.Response.TuitionResponse;
import org.mt.ev.application.port.input.tuitionUseCase.*;
import org.mt.ev.application.port.out.CourseRepositoryPort;
import org.mt.ev.application.port.out.StudentRepositoryPort;
import org.mt.ev.application.port.out.TuitionRepositoryPort;
import org.mt.ev.domain.exceptions.TuitionInvalidStateException;
import org.mt.ev.domain.model.Course;
import org.mt.ev.domain.model.Student;
import org.mt.ev.domain.model.Tuition;
import org.mt.ev.domain.model.TuitionDetail;
import org.mt.ev.infrastructure.exceptions.TuitionNotFoundException;
import org.mt.ev.infrastructure.mapper.TuitionMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TuitionService
        implements CreateTuitionUseCase, FindTuitionUseCase,
        ChangeStatusTuitionUseCase, DeleteTuitionUseCase, UpdateTuitionUseCase {

    private final TuitionRepositoryPort tuitionRepositoryPort;
    private final CourseRepositoryPort courseRepositoryPort;
    private final StudentRepositoryPort studentRepositoryPort;
    private final TuitionMapper tuitionMapper;

    private TuitionDetail createDetail(TuitionDetailRequest tuitionDetailRequest){

        Course course = courseRepositoryPort
                .findById(tuitionDetailRequest.courseId());

        return TuitionDetail
                .create(
                        course,
                        tuitionDetailRequest.classroom()
                );
    }

    @CacheEvict(value = {"tuition","tuition-map"}, allEntries = true)
    @Transactional
    @Override
    public TuitionResponse createTuition(TuitionRequest tuitionRequest) {

        Student student = studentRepositoryPort.findById(tuitionRequest.studentId());

        List<TuitionDetail>tuitionDetails = tuitionRequest
                .tuitionDetails()
                .stream()
                .map(this::createDetail).toList();

        Tuition tuition = Tuition.create(student, tuitionDetails);

        tuition = tuitionRepositoryPort.create(tuition);

        return tuitionMapper.toResponse(tuition);
    }

    @Cacheable(
            value = "tuition",
            key = "#tuitionId"
    )
    @Override
    public TuitionResponse findTuitionById(UUID tuitionId) {

        Tuition tuition = tuitionRepositoryPort.findById(tuitionId);

        return tuitionMapper.toResponse(tuition);
    }

    @Cacheable(
            value = "tuition-map"
    )
    @Override
    public Map<String, Set<String>> findTuitionMap() {

        List<Tuition> tuitions = tuitionRepositoryPort.findAll();

        return tuitions.stream()
                .flatMap(tuition ->
                        tuition.getDetails().stream()
                                .map(detail -> Map.entry(
                                        detail.getCourse().getName(),
                                        tuition.getStudent().getFullName()
                                ))
                )
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(
                                Map.Entry::getValue,
                                Collectors.toSet()
                        )
                ));
    }

    @CachePut(
            value = "tuition",
            key = "#tuitionId"
    )
    @Override
    public TuitionResponse enable(UUID tuitionId) {
        Tuition tuition = tuitionRepositoryPort.findById(tuitionId);
        tuition.enable();
        tuition = tuitionRepositoryPort.merge(tuition);
        return tuitionMapper.toResponse(tuition);
    }

    @CachePut(
            value = "tuition",
            key = "#tuitionId"
    )
    @Override
    public TuitionResponse disable(UUID tuitionId) {
        Tuition tuition = tuitionRepositoryPort.findById(tuitionId);
        tuition.disable();
        tuition = tuitionRepositoryPort.merge(tuition);
        return tuitionMapper.toResponse(tuition);
    }

    @CacheEvict(
            value = {"tuition","tuition-map"},
            allEntries = true
    )
    @Override
    public void deleteTuition(UUID tuitionId) {
        Tuition tuition = tuitionRepositoryPort.findById(tuitionId);
        if(!tuition.isActive()){
            throw TuitionInvalidStateException.cannotDeleteActive();
        }
        tuitionRepositoryPort.deleteTuition(tuition);
    }

    @CachePut(value = "tuition", key = "#id")
    @CacheEvict(value = "tuition-map", allEntries = true)
    @Transactional
    @Override
    public TuitionResponse updateTuition(UUID id, TuitionRequest request) {

        Tuition tuition = tuitionRepositoryPort.findById(id);

        Student student = studentRepositoryPort.findById(request.studentId());

        List<TuitionDetail> details = request.tuitionDetails()
                .stream()
                .map(this::createDetail)
                .toList();

        tuition.update(student, details);

        tuition = tuitionRepositoryPort.update(tuition);

        return tuitionMapper.toResponse(tuition);
    }
}
