package com.avanes.backend.dtos;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentStatusChangeDto {

    @NotNull(message = "Payment ID is required")
    private Long id;

    @NotBlank(message = "Status is required")
    private String status;
}