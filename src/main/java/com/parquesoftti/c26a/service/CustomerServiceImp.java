package com.parquesoftti.c26a.service;

import com.parquesoftti.c26a.model.Customer;
import com.parquesoftti.c26a.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImp implements CustomerService {

    final CustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer findByName(String name) {
        return customerRepository.findByCustomerName(name)
                .orElseThrow(()->new RuntimeException("Customer not found"));
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public Customer update(Long id, Customer customer) {
        Customer customerTmp = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customerTmp.setCustomerName(customer.getCustomerName());
        customerTmp.setEmail(customer.getEmail());
        customerTmp.setPhoneNumber(customer.getPhoneNumber());

        return customerRepository.save(customerTmp);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }
}
