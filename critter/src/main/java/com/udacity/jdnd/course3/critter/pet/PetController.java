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
        petService.savePet(pets,petDTO.getOwnerId());
        return petDTO;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pets pet=petService.getPetById(petId);

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


    private Pets convertsPetsDTOToPets(PetDTO petDTO) {
//        Customer customer = new Customer();
        Pets pets= new Pets();
//        Pets pets = new Pets(petDTO.getType(), petDTO.getName(),petDTO.getBirthDate(),petDTO.getNotes());
        BeanUtils.copyProperties(petDTO,pets);
        LOGGER.info(pets.toString());
        return pets;
    }

    private PetDTO covertPetsToPetsDTO(Pets pets){
        LOGGER.info(pets.getId()+" "+pets.getNotes());
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pets,petDTO);
        /*petDTO.setType(pets.getPetType());
        petDTO.setName(pets.getName());
        petDTO.setBirthDate(pets.getBirthDate());
        petDTO.setNotes(pets.getNotes());
        petDTO.setOwnerId((pets.getCustomer()).getId());*/
        LOGGER.info(petDTO.toString());
        return petDTO;
    }


}
