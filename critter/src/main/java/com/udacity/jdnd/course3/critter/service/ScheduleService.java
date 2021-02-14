package com.udacity.jdnd.course3.critter.service;



import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pets;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.exceptions.CustomerNotFound;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    ScheduleRepository scheduleRepository;
    PetRepository petRepository;
    CustomerRepository customerRepository;

    public ScheduleService(){

    }

    public ScheduleService(ScheduleRepository scheduleRepository, PetRepository petRepository,CustomerRepository customerRepository){
        this.scheduleRepository=scheduleRepository;
        this.petRepository=petRepository;
        this.customerRepository=customerRepository;
    }

    public Schedule saveSchedule(Schedule schedule){
        return this.scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleByPet(long petId){
        return scheduleRepository.getSchedulesByPetId(petId);
    }

    public List<Schedule> getScheduleByEmployeeId(long employeeId){
        return scheduleRepository.getSchedulesByEmployeeIds(employeeId);
    }

    public List<Schedule> getScheduleByCustomerId(long customerId){
        List<Schedule> scheduleList = new ArrayList<Schedule>();
        Customer customer;
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if(!optionalCustomer.isPresent()){
            throw new CustomerNotFound();
        }else{
            customer= optionalCustomer.get();
        }
        List<Pets> petsList = petRepository.findPetsByCustomerEquals(customer);
        petsList.forEach(p->{
            scheduleList.addAll(scheduleRepository.getSchedulesByPetIds(p));
        });
        return scheduleList;
    }
}
