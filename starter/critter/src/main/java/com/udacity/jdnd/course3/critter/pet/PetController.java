package com.udacity.jdnd.course3.critter.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;
    private final PetMapper petMapper;

    public PetController(PetService petService, PetMapper petMapper) {
        this.petService = petService;
        this.petMapper = petMapper;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet petEntity = this.petService.savePet(this.petMapper.convertPetDTOToEntity(petDTO));
        return this.petMapper.convertPetEntityToDTO(petEntity);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return this.petMapper.convertPetEntityToDTO(this.petService.getPetById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return this.petService.getAllPets()
                .stream()
                .map(this.petMapper::convertPetEntityToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return this.petService.getPetByOwnerId(ownerId)
                .stream()
                .map(this.petMapper::convertPetEntityToDTO)
                .collect(Collectors.toList());
    }
}
