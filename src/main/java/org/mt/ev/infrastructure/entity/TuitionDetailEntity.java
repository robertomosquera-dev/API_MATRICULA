package org.mt.ev.infrastructure.entity;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table( name = "tuition_details")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TuitionDetailEntity {


    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id",nullable = false)
    private CourseEntity course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tuition_id",nullable = false)
    private TuitionEntity tuition;

    @Column(nullable = false)
    private String classroom;

    @PrePersist
    private void init() {
        if (this.id == null) {
            this.id = UuidCreator.getTimeOrderedEpoch();
        }
    }

}
