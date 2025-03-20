package com.avanes.backend.dtos;


import com.avanes.backend.types.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Long id;
    private Long userId;
    private String fullName;
    private BigDecimal amount;
    private String slipFilePath;
    private String bankName;
    private String branchName;
    private PaymentStatus status;
    private Long approvedBy;
    private LocalDateTime approvedDate;
    private LocalDateTime paymentDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ExamDTO> exams;
}