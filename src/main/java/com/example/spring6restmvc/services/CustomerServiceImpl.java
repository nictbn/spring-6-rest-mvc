package com.example.spring6restmvc.services;

import com.example.spring6restmvc.model.CustomerDto;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, CustomerDto> customerMap;

    public CustomerServiceImpl() {
        CustomerDto customerDto1 = CustomerDto.builder()
                .id(UUID.randomUUID())
                .name("Customer 1")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        CustomerDto customerDto2 = CustomerDto.builder()
                .id(UUID.randomUUID())
                .name("Customer 2")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        CustomerDto customerDto3 = CustomerDto.builder()
                .id(UUID.randomUUID())
                .name("Customer 3")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        customerMap = new HashMap<>();
        customerMap.put(customerDto1.getId(), customerDto1);
        customerMap.put(customerDto2.getId(), customerDto2);
        customerMap.put(customerDto3.getId(), customerDto3);
    }
    @Override
    public Optional<CustomerDto> getCustomerById(UUID id) {
        return Optional.of(customerMap.get(id));
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public CustomerDto save(CustomerDto customerDto) {
        CustomerDto savedCustomerDto = CustomerDto.builder()
                .id(UUID.randomUUID())
                .updateDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .version(customerDto.getVersion())
                .name(customerDto.getName())
                .build();
        customerMap.put(savedCustomerDto.getId(), savedCustomerDto);
        return savedCustomerDto;
    }

    @Override
    public Optional<CustomerDto> update(UUID customerId, CustomerDto customerDto) {
        CustomerDto existing = customerMap.get(customerId);
        existing.setName(customerDto.getName());
        existing.setVersion(customerDto.getVersion());
        customerMap.put(existing.getId(), existing);
        return Optional.of(existing);
    }

    @Override
    public Boolean deleteById(UUID customerId) {
        customerMap.remove(customerId);
        return true;
    }

    @Override
    public Optional<CustomerDto> patch(UUID customerId, CustomerDto customerDto) {
        CustomerDto existing = customerMap.get(customerId);
        if (StringUtils.hasText(customerDto.getName())) {
            existing.setName(customerDto.getName());
        }
        if(customerDto.getVersion() != null) {
            existing.setVersion(customerDto.getVersion());
        }
        customerMap.put(existing.getId(), existing);
        return Optional.of(existing);
    }
}
