package org.mt.ev.infrastructure.entity;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "student")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentEntity {
    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;
    private String names;
    private String surnames;
    private String dni;
    private Integer age;

    @PrePersist
    private void init() {
        if (this.id == null) {
            this.id = UuidCreator.getTimeOrderedEpoch();
        }
    }
}
