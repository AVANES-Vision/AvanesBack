package com.avanes.backend.service.interfaces;

import com.avanes.backend.models.Payment;

import java.util.List;

public interface PaymentService {

    /**
     * Retrieves all payments from the database
     * @return List of all Payment entities
     */
    List<Payment> getAllPayments();

    /**
     * Creates a new payment
     * @param payment The payment entity to create
     * @return The created Payment entity
     * @throws IllegalArgumentException if payment already has an ID
     */
    Payment createPayment(Payment payment);

    /**
     * Updates an existing payment
     * @param id The ID of the payment to update
     * @param paymentDetails The payment details to update
     * @return The updated Payment entity
     * @throws RuntimeException if payment with given ID is not found
     */
    Payment updatePayment(Long id, Payment paymentDetails);

    /**
     * Deletes a payment by ID
     * @param id The ID of the payment to delete
     * @throws RuntimeException if payment with given ID is not found
     */
    void deletePayment(Long id);

    /**
     * Deletes all payments
     */
    void deleteAllPayments();
}