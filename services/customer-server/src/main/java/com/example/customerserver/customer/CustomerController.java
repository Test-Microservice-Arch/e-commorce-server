package com.example.customerserver.customer;

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
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        Customer customer = customerService.createCustomer(customerRequest);
        return ResponseEntity.ok(customerMapper.fromCustomer(customer));
    }

    @PutMapping
    public ResponseEntity<CustomerResponse> updateCustomer(@RequestBody @Valid CustomerRequest request) {
        Customer customer = this.customerService.updateCustomer(request);
        return ResponseEntity.ok(customerMapper.fromCustomer(customer));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll() {
        List<Customer> allCustomers = this.customerService.findAllCustomers();
        List<CustomerResponse> customerResponses = allCustomers.stream()
                .map(customerMapper::fromCustomer)
                .collect(Collectors.toList());
        return ResponseEntity.ok(customerResponses);
    }

    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable("customer-id") String customerId) {
        Customer customer = this.customerService.findById(customerId);
        return ResponseEntity.ok(customerMapper.fromCustomer(customer));
    }

    @DeleteMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> delete(@PathVariable("customer-id") String customerId) {
        Customer customer = this.customerService.deleteCustomer(customerId);
        return ResponseEntity.ok(customerMapper.fromCustomer(customer));
    }
}
