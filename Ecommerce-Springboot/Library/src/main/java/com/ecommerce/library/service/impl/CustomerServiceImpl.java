package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.repository.CustomerRepository;
import com.ecommerce.library.repository.RoleRepository;
import com.ecommerce.library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerDto save(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setUsername(customerDto.getUsername());
        customer.setPassword(customerDto.getPassword());
        customer.setRoles(Arrays.asList(roleRepository.findByName("CUSTOMER")));

        Customer customerSave = customerRepository.save(customer);
        return mapToDto(customerSave);
    }

    @Override
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public Customer saveInfo(Customer customer) {
        Customer customerSave = customerRepository.findByUsername(customer.getUsername());
        customerSave.setAddress(customer.getAddress());
        customerSave.setCity(customer.getCity());
        customerSave.setCountry(customer.getCountry());
        customerSave.setPhoneNumber(customer.getPhoneNumber());
        return customerRepository.save(customerSave);
    }

    private CustomerDto mapToDto(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setUsername(customerDto.getUsername());
        customerDto.setPassword(customer.getPassword());
        return customerDto;
    }
}
