package com.example.customerserver.dto;

import com.example.customerserver.entity.Customer;

public record CustomerResponseDTO(
        String id,
        String firstName,
        String lastName,
        String email,
        Customer.Address address
) {
}
