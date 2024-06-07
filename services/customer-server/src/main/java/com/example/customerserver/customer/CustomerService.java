package com.example.customerserver.customer;

import com.example.customerserver.exception.CustomerNotFoundException;
import io.micrometer.common.util.StringUtils;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    public Customer createCustomer(CustomerRequest customerRequest) {
        Customer customer = customerRepository.save(customerMapper.toCustomer(customerRequest));
        return customer;
    }

    public Customer updateCustomer(CustomerRequest request) {
        Customer customer = this.customerRepository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Cannot update customer:: No customer found with the provided ID: %s", request.id())
                ));
        if (StringUtils.isNotBlank(request.firstName())) {
            customer.setFirstName(request.firstName());
        }
        if (StringUtils.isNotBlank(request.lastName())) {
            customer.setLastName(request.lastName());
        }
        if (StringUtils.isNotBlank(request.email())) {
            customer.setEmail(request.email());
        }
        if (request.address() != null) {
            customer.setAddress(request.address());
        }
        return this.customerRepository.save(customer);
    }

    public List<Customer> findAllCustomers() {
        List<Customer> customerList = this.customerRepository.findAll();
        return customerList;
    }

    public Customer findById(String id) {
        Optional<Customer> customer = this.customerRepository.findById(id);
        if (!customer.isPresent()) {
            throw new CustomerNotFoundException(String.format("No customer found with the provided ID: %s", id));
        }
        return customer.get();
    }

    public Customer deleteCustomer(String id) {
        Optional<Customer> customer = this.customerRepository.findById(id);
        if (!customer.isPresent()) {
            throw new CustomerNotFoundException(String.format("No customer found with the provided ID: %s", id));
        }
        this.customerRepository.delete(customer.get());
        return customer.get();
    }
}
