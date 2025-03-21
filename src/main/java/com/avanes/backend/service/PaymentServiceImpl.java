package com.avanes.backend.service;

import com.avanes.backend.models.Payment;
import com.avanes.backend.repository.PaymentRepository;
import com.avanes.backend.service.interfaces.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    // Get all payments
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // Create a new payment
    @Transactional
    public Payment createPayment(Payment payment) {
        if (payment.getId() != null) {
            throw new IllegalArgumentException("New payment cannot have an ID");
        }
        return paymentRepository.save(payment);
    }

    // Update an existing payment
    @Transactional
    public Payment updatePayment(Long id, Payment paymentDetails) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));

        // Update fields
        payment.setUserId(paymentDetails.getUserId());
        payment.setApprovedBy(paymentDetails.getApprovedBy());
        payment.setApprovedDate(paymentDetails.getApprovedDate());
        payment.setFullName(paymentDetails.getFullName());
        payment.setBankName(paymentDetails.getBankName());
        payment.setBranchName(paymentDetails.getBranchName());
        payment.setAmount(paymentDetails.getAmount());
        payment.setPaymentDate(paymentDetails.getPaymentDate());
        payment.setSlipFile(paymentDetails.getSlipFile());
        payment.setStatus(paymentDetails.getStatus());

        return paymentRepository.save(payment);
    }

    // Delete a payment
    @Transactional
    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
        paymentRepository.delete(payment);
    }

    // Delete all payments
    @Transactional
    public void deleteAllPayments() {
        paymentRepository.deleteAll();
    }
}