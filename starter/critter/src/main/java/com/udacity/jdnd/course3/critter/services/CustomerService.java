package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.exceptions.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
        Optional<Customer> customerById = this.customerRepository.findById(id);
        if (customerById.isEmpty()) {
            throw new CustomerNotFoundException(String.format("Customer with id %d not found", id));
        }
        return customerById.get();
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
