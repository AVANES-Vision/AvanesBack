package com.avanes.backend.repository;

import com.avanes.backend.models.PaymentExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentExamRepository extends JpaRepository<PaymentExam, Long> {
    List<PaymentExam> findByPaymentId(Long paymentId);
}