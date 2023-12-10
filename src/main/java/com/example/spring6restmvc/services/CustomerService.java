package com.example.spring6restmvc.services;

import com.example.spring6restmvc.model.CustomerDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    Optional<CustomerDto> getCustomerById(UUID id);
    List<CustomerDto> getAllCustomers();
    CustomerDto save(CustomerDto customerDto);
    Optional<CustomerDto> update(UUID customerId, CustomerDto customerDto);
    void deleteById(UUID customerId);
    void patch(UUID customerId, CustomerDto customerDto);
}
