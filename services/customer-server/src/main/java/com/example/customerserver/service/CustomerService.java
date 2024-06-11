package com.example.customerserver.service;

import com.example.customerserver.util.CustomerMapper;
import com.example.customerserver.repository.CustomerRepository;
import com.example.customerserver.dto.CustomerRequestDTO;
import com.example.customerserver.entity.Customer;
import com.example.customerserver.exception.CustomerNotFoundException;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    public Customer createCustomer(CustomerRequestDTO customerRequestDTO) {
        log.info("create customer");
        Customer customer = customerRepository.save(customerMapper.toCustomer(customerRequestDTO));
        return customer;
    }

    public Customer updateCustomer(CustomerRequestDTO customerRequestDTO) {
        log.info("update customer");
        Customer customer = this.customerRepository.findById(customerRequestDTO.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Cannot update customer:: No customer found with the provided ID: %s", customerRequestDTO.id())
                ));
        if (StringUtils.isNotBlank(customerRequestDTO.firstName())) {
            customer.setFirstName(customerRequestDTO.firstName());
        }
        if (StringUtils.isNotBlank(customerRequestDTO.lastName())) {
            customer.setLastName(customerRequestDTO.lastName());
        }
        if (StringUtils.isNotBlank(customerRequestDTO.email())) {
            customer.setEmail(customerRequestDTO.email());
        }
        if (customerRequestDTO.address() != null) {
            customer.setAddress(customerRequestDTO.address());
        }
        return this.customerRepository.save(customer);
    }

    public List<Customer> findAllCustomers() {
        log.info("get all customer");
        List<Customer> customerList = this.customerRepository.findAll();
        return customerList;
    }

    public Customer findById(String id) {
        log.info("find customer by id = {}", id);
        Optional<Customer> customer = this.customerRepository.findById(id);
        if (!customer.isPresent()) {
            throw new CustomerNotFoundException(String.format("No customer found with the provided ID: %s", id));
        }
        return customer.get();
    }

    public Customer deleteCustomer(String id) {
        log.info("delete customer by using id = {}", id);
        Optional<Customer> customer = this.customerRepository.findById(id);
        if (!customer.isPresent()) {
            throw new CustomerNotFoundException(String.format("No customer found with the provided ID: %s", id));
        }
        this.customerRepository.delete(customer.get());
        return customer.get();
    }
}
