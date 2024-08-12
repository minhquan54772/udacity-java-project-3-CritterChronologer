package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {
    private final PetService petService;
    private final CustomerService customerService;

    public PetMapper(PetService petService, CustomerService customerService) {
        this.petService = petService;
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
