package com.example.customerserver.customer.dto;

import com.example.customerserver.customer.entity.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequestDTO(
        String id,
        @NotNull(message = "Customer firstname is required")
        String firstName,
        @NotNull(message = "Customer firstname is required")
        String lastName,
        @NotNull(message = "Customer Email is required")
        @Email(message = "Customer Email is not a valid email address")
        String email,
        Customer.Address address
) {
}
