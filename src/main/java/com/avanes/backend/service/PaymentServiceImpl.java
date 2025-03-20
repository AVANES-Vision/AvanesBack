package com.avanes.backend.service;

import com.avanes.backend.dtos.*;
import com.avanes.backend.exceptions.PaymentNotFoundException;
import com.avanes.backend.exceptions.ResourceNotFoundException;
import com.avanes.backend.mappers.ExamMapper;
import com.avanes.backend.mappers.PaymentMapper;
import com.avanes.backend.models.Exam;
import com.avanes.backend.models.Payment;
import com.avanes.backend.models.PaymentExam;
import com.avanes.backend.repository.ExamRepository;
import com.avanes.backend.repository.PaymentExamRepository;
import com.avanes.backend.repository.PaymentRepository;
import com.avanes.backend.service.interfaces.FileStorageService;
import com.avanes.backend.service.interfaces.PaymentService;
import com.avanes.backend.types.PaymentStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ExamRepository examRepository;
    private final PaymentExamRepository paymentExamRepository;
    private final FileStorageService fileStorageService;
    private final PaymentMapper paymentMapper;
    private final ExamMapper examMapper;

    private String uploadDir = "uploads";

    @Override
    @Transactional
    public void deletePayment(Long id) throws IOException {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + id));

        // Only allow deletion of pending payments
        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new IllegalStateException("Cannot delete payment with status: " + payment.getStatus());
        }

        // Delete slip file if exists
        if (payment.getSlipFilePath() != null) {
            fileStorageService.deleteFile(payment.getSlipFilePath());
        }

        paymentRepository.delete(payment);
    }




    @Override
    @Transactional
    public PaymentDTO approvePayment(PaymentApprovalRequest approvalRequest) {
        Payment payment = paymentRepository.findById(approvalRequest.getPaymentId())
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + approvalRequest.getPaymentId()));

        // Only pending payments can be approved
        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new IllegalStateException("Cannot approve payment with status: " + payment.getStatus());
        }

        payment.setStatus(PaymentStatus.APPROVED);
        payment.setApprovedBy(approvalRequest.getApproverId());
        payment.setApprovedDate(LocalDateTime.now());
        payment = paymentRepository.save(payment);

        List<PaymentExam> paymentExams = paymentExamRepository.findByPaymentId(payment.getId());
        List<ExamDTO> examDTOs = paymentExams.stream()
                .map(pe -> examMapper.mapToDTO(pe.getExam()))
                .collect(Collectors.toList());

        PaymentDTO paymentDTO = paymentMapper.mapToDTO(payment);
        paymentDTO.setExams(examDTOs);

        return paymentDTO;
    }

    @Override
    @Transactional
    public PaymentDTO rejectPayment(PaymentApprovalRequest approvalRequest) {
        Payment payment = paymentRepository.findById(approvalRequest.getPaymentId())
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + approvalRequest.getPaymentId()));

        // Only pending payments can be rejected
        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new IllegalStateException("Cannot reject payment with status: " + payment.getStatus());
        }

        payment.setStatus(PaymentStatus.REJECTED);
        payment.setApprovedBy(approvalRequest.getApproverId());
        payment.setApprovedDate(LocalDateTime.now());
        payment = paymentRepository.save(payment);

        List<PaymentExam> paymentExams = paymentExamRepository.findByPaymentId(payment.getId());
        List<ExamDTO> examDTOs = paymentExams.stream()
                .map(pe -> examMapper.mapToDTO(pe.getExam()))
                .collect(Collectors.toList());

        PaymentDTO paymentDTO = paymentMapper.mapToDTO(payment);
        paymentDTO.setExams(examDTOs);

        return paymentDTO;
    }


    @Override
    @Transactional
    public PaymentDTO createPayment(PaymentRequest paymentRequest, MultipartFile slipFile) throws IOException {
        Payment payment = new Payment();
        payment.setUserId(paymentRequest.getUserId());
        payment.setFullName(paymentRequest.getFullName());
        payment.setAmount(paymentRequest.getAmount());
        payment.setBankName(paymentRequest.getBankName());
        payment.setBranchName(paymentRequest.getBranchName());
        payment.setPaymentDate(paymentRequest.getPaymentDate());
        payment.setStatus(PaymentStatus.PENDING);

        // Store slip file
        if (slipFile != null && !slipFile.isEmpty()) {
            String fileName = fileStorageService.storeFile(slipFile, uploadDir);
            payment.setSlipFilePath(Paths.get(uploadDir, fileName).toString());
        }

        // Save payment first to generate ID
        payment = paymentRepository.save(payment);

        // Add exams to payment
        List<Exam> exams = examRepository.findAllById(paymentRequest.getExamIds());
        if (exams.size() != paymentRequest.getExamIds().size()) {
            throw new ResourceNotFoundException("One or more exams not found");
        }

        for (Exam exam : exams) {
            PaymentExam paymentExam = new PaymentExam();
            paymentExam.setPayment(payment);
            paymentExam.setExam(exam);
            paymentExamRepository.save(paymentExam);
        }

        // Fetch all exams for response
        List<PaymentExam> paymentExams = paymentExamRepository.findByPaymentId(payment.getId());
        List<ExamDTO> examDTOs = paymentExams.stream()
                .map(pe -> examMapper.mapToDTO(pe.getExam()))
                .collect(Collectors.toList());

        PaymentDTO paymentDTO = paymentMapper.mapToDTO(payment);
        paymentDTO.setExams(examDTOs);

        return paymentDTO;
    }

    @Override
    @Transactional
    public PaymentDTO updatePayment(Long id, PaymentUpdateRequest request, MultipartFile slipFile) throws IOException {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + id));

        // Only allow updates for pending payments
        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new IllegalStateException("Cannot update payment with status: " + payment.getStatus());
        }

        payment.setFullName(request.getFullName());
        payment.setAmount(request.getAmount());
        payment.setBankName(request.getBankName());
        payment.setBranchName(request.getBranchName());
        payment.setPaymentDate(request.getPaymentDate());

        // Update slip file if provided
        if (slipFile != null && !slipFile.isEmpty()) {
            // Delete old file if exists
            if (payment.getSlipFilePath() != null) {
                fileStorageService.deleteFile(payment.getSlipFilePath());
            }
            String fileName = fileStorageService.storeFile(slipFile, uploadDir);
            payment.setSlipFilePath(Paths.get(uploadDir, fileName).toString());
        }

        // Update exams if provided
        if (request.getExamIds() != null && !request.getExamIds().isEmpty()) {
            // Remove existing exam relationships
            paymentExamRepository.findByPaymentId(payment.getId())
                    .forEach(paymentExamRepository::delete);

            // Add new exam relationships
            List<Exam> exams = examRepository.findAllById(request.getExamIds());
            if (exams.size() != request.getExamIds().size()) {
                throw new ResourceNotFoundException("One or more exams not found");
            }

            for (Exam exam : exams) {
                PaymentExam paymentExam = new PaymentExam();
                paymentExam.setPayment(payment);
                paymentExam.setExam(exam);
                paymentExamRepository.save(paymentExam);
            }
        }

        payment = paymentRepository.save(payment);

        // Fetch all exams for response
        List<PaymentExam> paymentExams = paymentExamRepository.findByPaymentId(payment.getId());
        List<ExamDTO> examDTOs = paymentExams.stream()
                .map(pe -> examMapper.mapToDTO(pe.getExam()))
                .collect(Collectors.toList());

        PaymentDTO paymentDTO = paymentMapper.mapToDTO(payment);
        paymentDTO.setExams(examDTOs);

        return paymentDTO;
    }

    @Override
    public PaymentDTO getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + id));

        List<PaymentExam> paymentExams = paymentExamRepository.findByPaymentId(payment.getId());
        List<ExamDTO> examDTOs = paymentExams.stream()
                .map(pe -> examMapper.mapToDTO(pe.getExam()))
                .collect(Collectors.toList());

        PaymentDTO paymentDTO = paymentMapper.mapToDTO(payment);
        paymentDTO.setExams(examDTOs);

        return paymentDTO;
    }


    @Override
    public Page<PaymentDTO> findAll(Pageable pageable) {
        Page<Payment> paymentsPage = paymentRepository.findAll(pageable);
        return mapPaymentPageToPaymentDTOPage(paymentsPage, pageable);
    }

    @Override
    public Page<PaymentDTO> findByStatus(PaymentStatus status, Pageable pageable) {
        Page<Payment> paymentsPage = paymentRepository.findByStatus(status, pageable);
        return mapPaymentPageToPaymentDTOPage(paymentsPage, pageable);
    }

    @Override
    public Page<PaymentDTO> findByUserId(Long userId, Pageable pageable) {
        Page<Payment> paymentsPage = paymentRepository.findByUserId(userId, pageable);
        return mapPaymentPageToPaymentDTOPage(paymentsPage, pageable);
    }

    @Override
    public Page<PaymentDTO> findByUserIdAndStatus(Long userId, PaymentStatus status, Pageable pageable) {
        Page<Payment> paymentsPage = paymentRepository.findByUserIdAndStatus(userId, status, pageable);
        return mapPaymentPageToPaymentDTOPage(paymentsPage, pageable);
    }

    /**
     * Helper method to map a Page<Payment> to Page<PaymentDTO>
     */
    private Page<PaymentDTO> mapPaymentPageToPaymentDTOPage(Page<Payment> paymentsPage, Pageable pageable) {
        List<PaymentDTO> paymentDTOs = new ArrayList<>();

        for (Payment payment : paymentsPage.getContent()) {
            PaymentDTO dto = paymentMapper.mapToDTO(payment);

            List<PaymentExam> paymentExams = paymentExamRepository.findByPaymentId(payment.getId());
            List<ExamDTO> examDTOs = paymentExams.stream()
                    .map(pe -> examMapper.mapToDTO(pe.getExam()))
                    .collect(Collectors.toList());

            dto.setExams(examDTOs);
            paymentDTOs.add(dto);
        }

        return new PageImpl<>(paymentDTOs, pageable, paymentsPage.getTotalElements());
    }
}