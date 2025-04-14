package com.avanes.backend.controllers;


import com.avanes.backend.dtos.PaymentApprovalRequest;
import com.avanes.backend.dtos.PaymentDTO;
import com.avanes.backend.dtos.PaymentRequest;
import com.avanes.backend.dtos.PaymentUpdateRequest;
import com.avanes.backend.service.interfaces.PaymentService;
import com.avanes.backend.types.PaymentStatus;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * Create a new payment

     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> createPayment(
            @Valid @RequestPart("payment") PaymentRequest paymentRequest,
            @RequestPart(value = "slip", required = false) MultipartFile slipFile) throws IOException {

        PaymentDTO createdPayment = paymentService.createPayment(paymentRequest, slipFile);

        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("message", "Payment created successfully");
        response.put("data", createdPayment);
        response.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Update an existing payment

     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> updatePayment(
            @PathVariable Long id,
            @Valid @RequestPart("payment") PaymentUpdateRequest paymentRequest,
            @RequestPart(value = "slip", required = false) MultipartFile slipFile) throws IOException {

        PaymentDTO updatedPayment = paymentService.updatePayment(id, paymentRequest, slipFile);

        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("message", "Payment updated successfully");
        response.put("data", updatedPayment);
        response.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get payment by ID

     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getPaymentById(@PathVariable Long id) {
        PaymentDTO payment = paymentService.getPaymentById(id);

        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("message", "Payment retrieved successfully");
        response.put("data", payment);
        response.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Delete payment

     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletePayment(@PathVariable Long id) throws IOException {
        paymentService.deletePayment(id);

        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("message", "Payment deleted successfully");
        response.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get all payments with pagination
     *
     * @param page page number
     * @param size page size
     * @param sortField sort field
     * @param direction sort direction
     * @return paginated payments
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPayments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ?
                Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortField));
        Page<PaymentDTO> paymentsPage = paymentService.findAll(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("message", "Payments retrieved successfully");
        response.put("data", paymentsPage.getContent());
        response.put("currentPage", paymentsPage.getNumber());
        response.put("totalItems", paymentsPage.getTotalElements());
        response.put("totalPages", paymentsPage.getTotalPages());
        response.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get payments by status with pagination
     *
     * @param status payment status
     * @param page page number
     * @param size page size
     * @param sortField sort field
     * @param direction sort direction
     * @return paginated payments by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<Map<String, Object>> getPaymentsByStatus(
            @PathVariable PaymentStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ?
                Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortField));
        Page<PaymentDTO> paymentsPage = paymentService.findByStatus(status, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("message", "Payments retrieved successfully");
        response.put("data", paymentsPage.getContent());
        response.put("currentPage", paymentsPage.getNumber());
        response.put("totalItems", paymentsPage.getTotalElements());
        response.put("totalPages", paymentsPage.getTotalPages());
        response.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get payments by user ID and optionally filter by status
     *
     * @param userId user ID
     * @param status payment status (optional)
     * @param page page number
     * @param size page size
     * @param sortField sort field
     * @param direction sort direction
     * @return paginated payments by user ID and status
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getPaymentsByUserId(
            @PathVariable Long userId,
            @RequestParam(required = false) PaymentStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ?
                Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortField));
        Page<PaymentDTO> paymentsPage;

        if (status != null) {
            paymentsPage = paymentService.findByUserIdAndStatus(userId, status, pageable);
        } else {
            paymentsPage = paymentService.findByUserId(userId, pageable);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("message", "Payments retrieved successfully");
        response.put("data", paymentsPage.getContent());
        response.put("currentPage", paymentsPage.getNumber());
        response.put("totalItems", paymentsPage.getTotalElements());
        response.put("totalPages", paymentsPage.getTotalPages());
        response.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Approve a payment
     *
     * @param approvalRequest payment approval request
     * @return approved payment details
     */
    @PostMapping("/approve")
    public ResponseEntity<Map<String, Object>> approvePayment(
            @Valid @RequestBody PaymentApprovalRequest approvalRequest) {

        approvalRequest.setApprove(true);
        PaymentDTO approvedPayment = paymentService.approvePayment(approvalRequest);

        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("message", "Payment approved successfully");
        response.put("data", approvedPayment);
        response.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Reject a payment
     *
     * @param approvalRequest payment rejection request
     * @return rejected payment details
     */
    @PostMapping("/reject")
    public ResponseEntity<Map<String, Object>> rejectPayment(
            @Valid @RequestBody PaymentApprovalRequest approvalRequest) {

        approvalRequest.setApprove(false);
        PaymentDTO rejectedPayment = paymentService.rejectPayment(approvalRequest);

        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("message", "Payment rejected successfully");
        response.put("data", rejectedPayment);
        response.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}