package com.example.customerserver.controller;

import com.example.customerserver.dto.CustomerRequestDTO;
import com.example.customerserver.dto.CustomerResponseDTO;
import com.example.customerserver.entity.Customer;
import com.example.customerserver.service.CustomerService;
import com.example.customerserver.util.CustomerMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerMapper customerMapper;

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody @Valid CustomerRequestDTO customerRequestDTO) {
        Customer customer = customerService.createCustomer(customerRequestDTO);
        return ResponseEntity.ok(customerMapper.fromCustomer(customer));
    }

    @PutMapping
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@RequestBody @Valid CustomerRequestDTO customerRequestDTO) {
        Customer customer = this.customerService.updateCustomer(customerRequestDTO);
        return ResponseEntity.ok(customerMapper.fromCustomer(customer));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> findAll() {
        List<Customer> allCustomers = this.customerService.findAllCustomers();
        List<CustomerResponseDTO> customerResponsDTOS = allCustomers.stream()
                .map(customerMapper::fromCustomer)
                .collect(Collectors.toList());
        return ResponseEntity.ok(customerResponsDTOS);
    }

    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponseDTO> findById(@PathVariable("customer-id") String customerId) {
        Customer customer = this.customerService.findById(customerId);
        return ResponseEntity.ok(customerMapper.fromCustomer(customer));
    }

    @DeleteMapping("/{customer-id}")
    public ResponseEntity<CustomerResponseDTO> delete(@PathVariable("customer-id") String customerId) {
        Customer customer = this.customerService.deleteCustomer(customerId);
        return ResponseEntity.ok(customerMapper.fromCustomer(customer));
    }
}
