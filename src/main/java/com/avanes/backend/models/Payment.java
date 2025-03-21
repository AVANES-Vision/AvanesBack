package com.avanes.backend.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String approvedBy;

    private LocalDateTime approvedDate;

    private String fullName;

    private String bankName;

    private String branchName;

    private BigDecimal amount;

    private LocalDateTime paymentDate;

    private String slipFile;

    private String status;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Constructor with required fields
    public Payment(String userId, String fullName, String bankName, String branchName,
                   BigDecimal amount, LocalDateTime paymentDate, String status) {
        this.userId = userId;
        this.fullName = fullName;
        this.bankName = bankName;
        this.branchName = branchName;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.status = status;
    }
}