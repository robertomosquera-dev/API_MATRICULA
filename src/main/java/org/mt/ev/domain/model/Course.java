package org.mt.ev.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.mt.ev.domain.exceptions.CourseInvalidStateException;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Course {

    private UUID id;
    private String name;
    private String description;
    private String abbreviation;
    private Boolean status;

    public void enable() {
        if (Boolean.TRUE.equals(this.status))
            throw CourseInvalidStateException.alreadyActive();
        this.status = true;
    }

    public void disable() {
        if (Boolean.FALSE.equals(this.status))
            throw CourseInvalidStateException.alreadyInactive();
        this.status = false;
    }

    public boolean isActive() {
        return Boolean.TRUE.equals(this.status);
    }

}
