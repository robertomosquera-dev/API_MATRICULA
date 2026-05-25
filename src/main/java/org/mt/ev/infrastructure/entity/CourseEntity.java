package org.mt.ev.infrastructure.entity;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "course")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, unique = true,length = 80)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, unique = true,length = 7)
    private String abbreviation;

    private Boolean status;

    @PrePersist
    private void init() {
        if (this.id == null) {
            this.id = UuidCreator.getTimeOrderedEpoch();
        }
        if (this.status == null) {
            this.status = true;
        }
    }

}
