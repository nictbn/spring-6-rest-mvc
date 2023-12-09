package com.example.spring6restmvc.mappers;

import com.example.spring6restmvc.entities.Customer;
import com.example.spring6restmvc.model.CustomerDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDto customerDto);
    CustomerDto customerToCustomerDto(Customer customer);
}
