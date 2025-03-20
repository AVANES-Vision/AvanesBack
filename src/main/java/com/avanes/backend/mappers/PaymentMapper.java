package com.avanes.backend.mappers;


import com.avanes.backend.dtos.PaymentDTO;
import com.avanes.backend.models.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentDTO mapToDTO(Payment payment) {
        if (payment == null) {
            return null;
        }

        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setUserId(payment.getUserId());
        dto.setFullName(payment.getFullName());
        dto.setAmount(payment.getAmount());
        dto.setSlipFilePath(payment.getSlipFilePath());
        dto.setBankName(payment.getBankName());
        dto.setBranchName(payment.getBranchName());
        dto.setStatus(payment.getStatus());
        dto.setApprovedBy(payment.getApprovedBy());
        dto.setApprovedDate(payment.getApprovedDate());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setCreatedAt(payment.getCreatedAt());
        dto.setUpdatedAt(payment.getUpdatedAt());

        return dto;
    }

    public Payment mapToEntity(PaymentDTO dto) {
        if (dto == null) {
            return null;
        }

        Payment payment = new Payment();
        payment.setId(dto.getId());
        payment.setUserId(dto.getUserId());
        payment.setFullName(dto.getFullName());
        payment.setAmount(dto.getAmount());
        payment.setSlipFilePath(dto.getSlipFilePath());
        payment.setBankName(dto.getBankName());
        payment.setBranchName(dto.getBranchName());
        payment.setStatus(dto.getStatus());
        payment.setApprovedBy(dto.getApprovedBy());
        payment.setApprovedDate(dto.getApprovedDate());
        payment.setPaymentDate(dto.getPaymentDate());

        return payment;
    }
}