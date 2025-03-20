package com.avanes.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamDTO {
    private Long id;
    private String name;
    private String code;
    private BigDecimal fee;
    private String description;
}