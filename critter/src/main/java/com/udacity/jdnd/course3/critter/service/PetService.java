package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pets;
import com.udacity.jdnd.course3.critter.exceptions.OwnerNotFoundException;
import com.udacity.jdnd.course3.critter.exceptions.PetExistsException;
import com.udacity.jdnd.course3.critter.exceptions.PetNotFoundException;
import com.udacity.jdnd.course3.critter.pet.PetController;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    private static final Logger LOGGER = LogManager.getLogger(PetService.class);

    private PetRepository petRepository;
    private CustomerRepository customerRepository;


    public PetService(PetRepository petRepository, CustomerRepository customerRepository){
        this.petRepository=petRepository;
        this.customerRepository=customerRepository;
    }

    public Pets savePet(Pets pet,long ownerId){
        Customer customer = new Customer();
        Optional<Customer> optionalCustomer = customerRepository.findById(ownerId);
        if(optionalCustomer.isPresent()){
            customer=optionalCustomer.get();
        }else{
            throw new OwnerNotFoundException();
        }
        if(isPetInDB(pet)){
            throw new PetExistsException();
        }
        pet.setCustomer(customer);
        LOGGER.info(pet.getId()+" "+pet.getName()+" "+pet.getNotes()+" "+pet.getBirthDate()+" "+pet.getPetType());
        return petRepository.save(pet);
    }

    public Pets getPetById(long Id){
        Optional<Pets> optionalPets= petRepository.findById(Id);
        if(optionalPets.isPresent()){
            return optionalPets.get();
        }else{
            throw new PetNotFoundException();
        }
    }

    private Boolean isPetInDB(Pets pet){
        if(pet.getName().equals(petRepository.findByName(pet.getName()))&&pet.getBirthDate().equals(petRepository.findByBirthDate(pet.getBirthDate().toString()))&&pet.getPetType().equals(petRepository.findByPetType(pet.getPetType().toString()))){
            return true;
        }else{
            return false;
        }
    }

}
