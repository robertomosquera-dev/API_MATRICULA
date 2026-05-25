package org.mt.ev.application.service;

import lombok.RequiredArgsConstructor;
import org.mt.ev.application.dto.Request.TuitionDetailRequest;
import org.mt.ev.application.dto.Request.TuitionRequest;
import org.mt.ev.application.dto.Response.TuitionResponse;
import org.mt.ev.application.port.input.tuitionUseCase.CreateTuitionUseCase;
import org.mt.ev.application.port.input.tuitionUseCase.FindTuitionUseCase;
import org.mt.ev.application.port.out.CourseRepositoryPort;
import org.mt.ev.application.port.out.StudentRepositoryPort;
import org.mt.ev.application.port.out.TuitionRepositoryPort;
import org.mt.ev.domain.model.Course;
import org.mt.ev.domain.model.Student;
import org.mt.ev.domain.model.Tuition;
import org.mt.ev.domain.model.TuitionDetail;
import org.mt.ev.infrastructure.entity.TuitionDetailEntity;
import org.mt.ev.infrastructure.mapper.TuitionDetailMapper;
import org.mt.ev.infrastructure.mapper.TuitionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TuitionService
        implements CreateTuitionUseCase, FindTuitionUseCase {

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
    public TuitionResponse findTuitionById(String id) {

        Tuition tuition = tuitionRepositoryPort.findById(id);

        return tuitionMapper.toResponse(tuition);
    }

    @Override
    public Map<String, Set<String>> findTuitionMapById(String id) {
        return Map.of();
    }

}
