package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pets;
import com.udacity.jdnd.course3.critter.exceptions.PetNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.UserController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {
    private static final Logger LOGGER = LogManager.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;

    public CustomerService(CustomerRepository customerRepository, PetRepository petRepository){
        this.customerRepository=customerRepository;
        this.petRepository=petRepository;
    }


    public Customer addCustomer(Customer customer){
        Customer savedCustomer=customerRepository.save(customer);
        return savedCustomer;
    }

    public List<Customer> getAllCustomers(){
        List<Customer> customerList = customerRepository.findAll();
        List<Customer> customerResponseList = new ArrayList<Customer>();
        for(Customer customer:customerList){
            List<Pets> pets=petRepository.findPetsByCustomerEquals(customer);
            customer.setPets(pets);
            customerResponseList.add(customer);
            LOGGER.info(customer.toString());
        }
        return customerResponseList;
    }

    public Customer getCustomerByPetId(long petId){
        Optional<Pets> optionalPet=petRepository.findById(petId);
        if(optionalPet.isPresent()){
            Pets pet = optionalPet.get();
            Customer customer=pet.getCustomer();
            return customer;
        }else{
            throw new PetNotFoundException();
        }
    }

    public Customer getCustomerById(Long customerId){
        return customerRepository.getOne(customerId);
    }



}
