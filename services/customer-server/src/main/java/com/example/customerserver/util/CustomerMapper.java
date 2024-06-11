package com.example.customerserver.util;

import com.example.customerserver.dto.CustomerRequestDTO;
import com.example.customerserver.dto.CustomerResponseDTO;
import com.example.customerserver.entity.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {

    public Customer toCustomer(CustomerRequestDTO request) {
        if (request == null) {
            return null;
        }
        return Customer.builder()
                .id(request.id())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .address(request.address())
                .build();
    }

    public CustomerResponseDTO fromCustomer(Customer customer) {
        if (customer == null) {
            return null;
        }
        return new CustomerResponseDTO(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getAddress()
        );
    }
}
