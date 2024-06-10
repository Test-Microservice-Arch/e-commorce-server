package com.example.customerserver.customer.dto;

import com.example.customerserver.customer.entity.Customer;

public record CustomerResponseDTO(
        String id,
        String firstName,
        String lastName,
        String email,
        Customer.Address address
) {
}
