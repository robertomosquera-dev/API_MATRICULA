package org.mt.ev.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Student {

    private UUID id;
    private String names;
    private String surnames;
    private String dni;
    private Integer age;

    public boolean isAdult() {
        return this.age >= 18;
    }

    public String getFullName() {
        return this.names + " " + this.surnames;
    }

}
