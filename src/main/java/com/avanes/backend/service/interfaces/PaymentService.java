package com.avanes.backend.service.interfaces;

import com.avanes.backend.dtos.PaymentApprovalRequest;
import com.avanes.backend.dtos.PaymentDTO;
import com.avanes.backend.dtos.PaymentRequest;
import com.avanes.backend.dtos.PaymentUpdateRequest;
import com.avanes.backend.types.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PaymentService {

    PaymentDTO createPayment(PaymentRequest paymentRequest, MultipartFile slipFile) throws IOException;

    PaymentDTO updatePayment(Long id, PaymentUpdateRequest paymentRequest, MultipartFile slipFile) throws IOException;

    PaymentDTO getPaymentById(Long id);

    void deletePayment(Long id) throws IOException;

    // New pagination methods using Spring Data's Page and Pageable
    Page<PaymentDTO> findAll(Pageable pageable);

    Page<PaymentDTO> findByStatus(PaymentStatus status, Pageable pageable);

    Page<PaymentDTO> findByUserId(Long userId, Pageable pageable);

    Page<PaymentDTO> findByUserIdAndStatus(Long userId, PaymentStatus status, Pageable pageable);

    PaymentDTO approvePayment(PaymentApprovalRequest approvalRequest);

    PaymentDTO rejectPayment(PaymentApprovalRequest approvalRequest);
}