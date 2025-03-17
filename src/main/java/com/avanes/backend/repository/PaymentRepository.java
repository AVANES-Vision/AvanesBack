package com.avanes.backend.repository;

import com.avanes.backend.models.Payment;
import com.avanes.backend.types.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Page<Payment> findByStatus(PaymentStatus status, Pageable pageable);
    Page<Payment> findByUserId(Long userId, Pageable pageable);
    Page<Payment> findByUserIdAndStatus(Long userId, PaymentStatus status, Pageable pageable);
    Optional<Payment> findByIdAndUserId(Long id, Long userId);
}