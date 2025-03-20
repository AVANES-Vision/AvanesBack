package com.avanes.backend.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentApprovalRequest {

    @NotNull(message = "Payment ID is required")
    private Long paymentId;

    @NotNull(message = "Approver ID is required")
    private Long approverId;

    @NotNull(message = "Decision is required")
    private Boolean approve;

    private String comment;
}