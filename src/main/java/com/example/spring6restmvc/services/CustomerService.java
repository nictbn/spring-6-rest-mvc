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
    Boolean deleteById(UUID customerId);
    Optional<CustomerDto> patch(UUID customerId, CustomerDto customerDto);
}
