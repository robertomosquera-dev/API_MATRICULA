package org.mt.ev.application.service;

import lombok.RequiredArgsConstructor;
import org.mt.ev.application.dto.Request.TuitionDetailRequest;
import org.mt.ev.application.dto.Request.TuitionRequest;
import org.mt.ev.application.dto.Response.TuitionResponse;
import org.mt.ev.application.port.input.tuitionUseCase.ChangeStatusTuitionUseCase;
import org.mt.ev.application.port.input.tuitionUseCase.CreateTuitionUseCase;
import org.mt.ev.application.port.input.tuitionUseCase.FindTuitionUseCase;
import org.mt.ev.application.port.out.CourseRepositoryPort;
import org.mt.ev.application.port.out.StudentRepositoryPort;
import org.mt.ev.application.port.out.TuitionRepositoryPort;
import org.mt.ev.domain.model.Course;
import org.mt.ev.domain.model.Student;
import org.mt.ev.domain.model.Tuition;
import org.mt.ev.domain.model.TuitionDetail;
import org.mt.ev.infrastructure.mapper.TuitionMapper;
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
        ChangeStatusTuitionUseCase {

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

    @Override
    public TuitionResponse findTuitionById(UUID id) {

        Tuition tuition = tuitionRepositoryPort.findById(id);

        return tuitionMapper.toResponse(tuition);
    }

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

    @Override
    public TuitionResponse enable(UUID tuitionId) {
        Tuition tuition = tuitionRepositoryPort.findById(tuitionId);
        tuition.enable();
        tuition = tuitionRepositoryPort.merge(tuition);
        return tuitionMapper.toResponse(tuition);
    }

    @Override
    public TuitionResponse disable(UUID tuitionId) {
        Tuition tuition = tuitionRepositoryPort.findById(tuitionId);
        tuition.disable();
        tuition = tuitionRepositoryPort.merge(tuition);
        return tuitionMapper.toResponse(tuition);
    }

}
