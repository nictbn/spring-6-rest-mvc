package com.example.spring6restmvc.controllers;

import com.example.spring6restmvc.model.Customer;
import com.example.spring6restmvc.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/customer")
@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> listAllCustomers() {
        return customerService.getAllCustomers();
    }

    @RequestMapping(value = "{customerId}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable("customerId") UUID customerId) {
        return customerService.getCustomerById(customerId);
    }
}
