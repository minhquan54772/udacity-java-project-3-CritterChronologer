package com.udacity.jdnd.course3.critter.user;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer save(Customer customer) {
        return this.customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    public Customer getCustomerById(long id) {
        return this.customerRepository.findById(id).orElse(null);
    }

    public Customer getCustomerByPetId(long petId) {
        Optional<Customer> customerByPetId = this.customerRepository.findCustomerByPetId(petId);
        if (customerByPetId.isEmpty()) {
            // May not occur this case because pet always belongs to 1 owner (customer)
            throw new CustomerNotFoundException("Customer with pet id " + petId + " is not found");
        }
        return customerByPetId.get();
    }
}