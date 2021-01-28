package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.entity.Pets;
import com.udacity.jdnd.course3.critter.exceptions.PetExistsException;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private PetRepository petRepository;

    public PetService(){

    }

    public PetService(PetRepository petRepository){
        this.petRepository=petRepository;
    }

    public Pets savePet(Pets pet){
        if(isPetInDB(pet)){
            throw new PetExistsException();
        }
        return petRepository.save(pet);
    }

    private Boolean isPetInDB(Pets pet){

        if(pet.getName().equals(petRepository.findByName(pet.getName()))&&pet.getBirthDate().equals(pet.getBirthDate())&&pet.getPetType().equals(pet.getPetType())){
            return true;
        }else{
            return false;
        }
    }

}
