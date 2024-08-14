package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public Pet getPetById(Long id) {
        Optional<Pet> petById = petRepository.findById(id);
        if (petById.isEmpty()) {
            throw new PetNotFoundException(String.format("Pet with id %s is not found", id));
        }
        return petById.get();
    }

    public List<Pet> getPetByOwnerId(long ownerId) {
        Optional<List<Pet>> petsByOwnerId = petRepository.findPetsByOwnerId(ownerId);
        if (petsByOwnerId.isEmpty()) {
            throw new PetNotFoundException(String.format("Customer with id %s does not own any pet.", ownerId));
        }
        return petsByOwnerId.get();
    }

    public Pet savePet(Pet pet) {
        Long ownerId = pet.getOwner().getId();
        Optional<Customer> customerById = customerRepository.findById(ownerId);
        if (customerById.isEmpty()) {
            throw new CustomerNotFoundException(String.format("Customer with id %s is not found.", ownerId));
        }
        Pet persistedPet = petRepository.save(pet);
        Customer customer = customerById.get();
        List<Pet> pets = customer.getPets();
        pets.add(persistedPet);
        customer.setPets(pets);
        customerRepository.save(customer);
        return persistedPet;
    }

    public Customer getCustomerByPetId(long petId) {
        Optional<Pet> petById = petRepository.findById(petId);
        if (petById.isEmpty()) {
            throw new PetNotFoundException(String.format("Pet with id %s is not found", petId));
        }
        return petById.get().getOwner();
    }
}
