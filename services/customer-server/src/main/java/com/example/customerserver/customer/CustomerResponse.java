package com.example.customerserver.customer;

public record CustomerResponse(
        String id,
        String firstname,
        String lastname,
        String email,
        Customer.Address address
) {
}
