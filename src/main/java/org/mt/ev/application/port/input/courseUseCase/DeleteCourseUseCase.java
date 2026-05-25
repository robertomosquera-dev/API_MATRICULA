package org.mt.ev.application.port.input.courseUseCase;

import java.util.UUID;

public interface DeleteCourseUseCase {
    void deleteCourse(UUID courseId);
}
