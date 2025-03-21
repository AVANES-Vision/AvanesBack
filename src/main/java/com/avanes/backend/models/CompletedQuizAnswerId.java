package com.avanes.backend.models;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class CompletedQuizAnswerId implements Serializable {
    private Long completedId;
    private Long questionId;
}
