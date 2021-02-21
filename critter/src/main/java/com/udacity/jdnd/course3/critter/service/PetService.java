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

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {
    private static final Logger LOGGER = LogManager.getLogger(PetService.class);

    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;


    public PetService(PetRepository petRepository, CustomerRepository customerRepository){
        this.petRepository=petRepository;
        this.customerRepository=customerRepository;
    }

    public Pets savePet(Pets pet){
        Pets savedPet = petRepository.save(pet);
        Customer customer=savedPet.getCustomer();
        List<Pets> customerPets = customer.getPets();

        if(customerPets==null){
            customerPets = new ArrayList<>();
        }
        customerPets.add(savedPet);
        customer.setPets(customerPets);
        customerRepository.save(customer);
        return savedPet;
    }

    public Pets getPetById(long Id){
        Optional<Pets> optionalPets= petRepository.findById(Id);
        if(optionalPets.isPresent()){
            return optionalPets.get();
        }else{
            throw new PetNotFoundException();
        }
    }

    public List<Pets> getAllPets(){
        List<Pets> petsList = petRepository.findAll();
        return petsList;
    }

    public List<Pets> getPetsByOwner(long Id){
        Optional<Customer> optionalCustomer = customerRepository.findById(Id);
        Customer customer = new Customer();
        if(optionalCustomer.isPresent()){
            customer= optionalCustomer.get();
        }else{
            throw new OwnerNotFoundException();
        }
        List<Pets> petsList=petRepository.findPetsByCustomerEquals(customer);
        return petsList;
    }



}
