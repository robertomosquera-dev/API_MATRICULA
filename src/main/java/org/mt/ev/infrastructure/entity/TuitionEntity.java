package org.mt.ev.infrastructure.entity;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table( name = "tuition")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TuitionEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime tuitionDate;

    private Boolean status;

    @OneToMany(
            mappedBy = "tuition",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TuitionDetailEntity> tuitionDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private StudentEntity student;

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
