package com.udacity.jdnd.course3.critter.dto.mappers;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {
    private final CustomerService customerService;

    public PetMapper(CustomerService customerService) {
        this.customerService = customerService;
    }

    public PetDTO convertPetEntityToDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setName(pet.getName());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setNotes(pet.getNotes());
        petDTO.setType(pet.getType());

        Customer owner = pet.getOwner();
        petDTO.setOwnerId(owner != null ? owner.getId() : null);
        return petDTO;
    }

    public Pet convertPetDTOToEntity(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setId(petDTO.getId());
        pet.setName(petDTO.getName());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());
        pet.setType(petDTO.getType());

        Customer owner = this.customerService.getCustomerById(petDTO.getOwnerId());
        pet.setOwner(owner);
        return pet;
    }
}
