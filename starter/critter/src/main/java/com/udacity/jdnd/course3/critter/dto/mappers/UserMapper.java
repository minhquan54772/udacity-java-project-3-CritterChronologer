package com.udacity.jdnd.course3.critter.dto.mappers;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.services.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    private final PetService petService;

    public UserMapper(PetService petService) {
        this.petService = petService;
    }

    public CustomerDTO convertCustomerEntityToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setNotes(customer.getNotes());
        List<Pet> pets = customer.getPets();
        if (pets != null && !pets.isEmpty()) {
            List<Long> petIds = pets.stream().map(Pet::getId).collect(Collectors.toList());
            customerDTO.setPetIds(petIds);
        } else {
            customerDTO.setPetIds(new ArrayList<>());
        }
        return customerDTO;
    }

    public Customer convertCustomerDTOToEntity(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            return null;
        }

        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setName(customerDTO.getName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setNotes(customerDTO.getNotes());

        List<Long> petIds = customerDTO.getPetIds();
        if (petIds != null && !petIds.isEmpty()) {
            List<Pet> pets = petIds.stream().map(this.petService::getPetById).collect(Collectors.toList());
            customer.setPets(pets);
        } else {
            customer.setPets(new ArrayList<>());
        }
        return customer;
    }

    // Employee Entity and DTO have the same properties -> can use BeanUtils to copy properties
    public EmployeeDTO convertEmployeeEntityToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    // Employee Entity and DTO have the same properties -> can use BeanUtils to copy properties
    public Employee convertEmployeeDTOToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }
}
