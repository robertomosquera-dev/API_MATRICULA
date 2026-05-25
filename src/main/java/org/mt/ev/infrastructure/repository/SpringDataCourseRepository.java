package org.mt.ev.infrastructure.repository;

import org.mt.ev.infrastructure.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataCourseRepository extends JpaRepository <CourseEntity, UUID>{
}
