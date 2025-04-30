package com.avanes.backend.mappers;

import com.avanes.backend.dtos.ExamDTO;
import com.avanes.backend.models.Exam;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ExamMapper {

    public ExamDTO mapToDTO(Exam exam) {
        if (exam == null) {
            return null;
        }

        ExamDTO dto = new ExamDTO();
        dto.setId(exam.getId());
        dto.setName(exam.getTitle());
        dto.setCode(String.valueOf(exam.getId()));
        dto.setFee(BigDecimal.valueOf(exam.getId()));
        dto.setDescription(exam.getTitle());

        return dto;
    }

    public Exam mapToEntity(ExamDTO dto) {
        if (dto == null) {
            return null;
        }

        Exam exam = new Exam();
        exam.setId(dto.getId());
//        exam.setName(dto.getName());
//        exam.setCode(dto.getCode());
//        exam.setFee(dto.getFee());
//        exam.setDescription(dto.getDescription());

        return exam;
    }
}
