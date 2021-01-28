package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.Pets;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    PetService petService;
    private ModelMapper modelMapper;


    public PetController(PetService petService){
        this.petService=petService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO){
        Pets pets=covertPetsToPetsDTO(petDTO);
        petService.savePet(pets);
        return petDTO;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping
    public List<PetDTO> getPets(){
        throw new UnsupportedOperationException();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        throw new UnsupportedOperationException();
    }


    private Pets covertPetsToPetsDTO(PetDTO petDTO) {
        Pets pets = modelMapper.map(petDTO,Pets.class);
        return pets;
    }


}
