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
    @Column(nullable = false , length = 80)
    private String names;
    @Column(nullable = false , length = 80)
    private String surnames;
    @Column(nullable = false,unique = true)
    private String dni;
    @Column(nullable = false)
    private Integer age;

    @PrePersist
    private void init() {
        if (this.id == null) {
            this.id = UuidCreator.getTimeOrderedEpoch();
        }
    }
}
