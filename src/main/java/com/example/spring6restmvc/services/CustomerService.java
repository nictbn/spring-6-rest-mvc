package com.example.spring6restmvc.services;

import com.example.spring6restmvc.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    Customer getCustomerById(UUID id);
    List<Customer> getAllCustomers();
    Customer save(Customer customer);
    void update(UUID customerId, Customer customer);
    void deleteById(UUID customerId);
    void patch(UUID customerId, Customer customer);
}
