package com.avanes.backend.service.interfaces;



import com.avanes.backend.dtos.ExamDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExamService {

    ExamDTO createExam(ExamDTO examDTO);

    ExamDTO updateExam(Long id, ExamDTO examDTO);

    ExamDTO getExamById(Long id);

    void deleteExam(Long id);

    Page<ExamDTO> getAllExams(Pageable pageable);

    List<ExamDTO> getAllExams();
}