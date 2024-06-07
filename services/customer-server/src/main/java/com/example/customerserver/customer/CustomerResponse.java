package com.example.customerserver.customer;

public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        Customer.Address address
) {
}
