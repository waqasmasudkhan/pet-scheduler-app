package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pets;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    PetService petService;
    private ModelMapper modelMapper;
    private static final Logger LOGGER = LogManager.getLogger(PetController.class);



    public PetController(PetService petService){
        this.petService=petService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO){

        Pets pets =convertsPetsDTOToPets(petDTO);
        LOGGER.info(pets.getId()+" "+pets.getName()+" "+pets.getNotes()+" "+pets.getBirthDate());
        Pets savedPet=petService.savePet(pets,petDTO.getOwnerId());
        PetDTO responsePetDTO= covertsPetsToPetsDTO(pets);
        responsePetDTO.setOwnerId(pets.getCustomer().getId());
        LOGGER.info(savedPet.getId()+" "+savedPet.getName()+" "+savedPet.getNotes()+" "+savedPet.getBirthDate());
        responsePetDTO.setId(savedPet.getId());
        return responsePetDTO;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pets pet=petService.getPetById(petId);
        PetDTO responsePetDTO= covertsPetsToPetsDTO(pet);
        responsePetDTO.setOwnerId(pet.getCustomer().getId());
        return responsePetDTO;
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pets> petList = petService.getAllPets();
        return convertsListPetToListPetDTO(petList);
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pets> petList = petService.getPetsByOwner(ownerId);
        return convertsListPetToListPetDTO(petList);
    }


    private Pets convertsPetsDTOToPets(PetDTO petDTO) {
        Pets pets= new Pets();
        BeanUtils.copyProperties(petDTO,pets);
        LOGGER.info(pets.toString());
        return pets;
    }

    private PetDTO covertsPetsToPetsDTO(Pets pets){
        LOGGER.info(pets.getId()+" "+pets.getNotes());
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pets,petDTO);
        LOGGER.info(petDTO.toString());
        return petDTO;
    }

    private List<PetDTO> convertsListPetToListPetDTO(List<Pets> petsList){
        List<PetDTO> petDTOList = new ArrayList<PetDTO>();
        for(Pets pet: petsList){
            PetDTO petDTO = covertsPetsToPetsDTO(pet);
            petDTO.setOwnerId(pet.getCustomer().getId());
            petDTOList.add(petDTO);
        }
        return petDTOList;
    }


}
