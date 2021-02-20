package com.udacity.jdnd.course3.critter.service;



import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pets;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.exceptions.CustomerNotFound;
import com.udacity.jdnd.course3.critter.exceptions.EmployeeNotFound;
import com.udacity.jdnd.course3.critter.exceptions.PetNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
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
    EmployeeRepository employeeRepository;


    public ScheduleService(ScheduleRepository scheduleRepository, PetRepository petRepository,CustomerRepository customerRepository,EmployeeRepository employeeRepository){
        this.scheduleRepository=scheduleRepository;
        this.petRepository=petRepository;
        this.customerRepository=customerRepository;
        this.employeeRepository=employeeRepository;
    }

    public Schedule saveSchedule(Schedule schedule){
        return this.scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleByPet(Pets pet){
        return scheduleRepository.getSchedulesByPets(pet);
    }

    public List<Schedule> getScheduleByEmployeeId(long employeeId){
        return scheduleRepository.getSchedulesByEmployees(employeeId);
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
            scheduleList.addAll(scheduleRepository.getSchedulesByPets(p));
        });
        return scheduleList;
    }

    public List<Employee> getEmployees(List<Long> employeeIds){
        List<Employee> employeeList = new ArrayList<Employee>();
        employeeIds.forEach(id->{
            Optional<Employee> employeeOptional= employeeRepository.findById(id);
            if(employeeOptional.isPresent()) {
                Employee employee = employeeOptional.get();
                employeeList.add(employee);
            }else{
                throw new EmployeeNotFound();
            }
        });
        return employeeList;
    }

    public List<Pets> getPetsById(List<Long> petsIds){
        List<Pets> petsList = new ArrayList<Pets>();
        petsIds.forEach(id->{
            Optional<Pets> optionalPets=petRepository.findById(id);
            if(optionalPets.isPresent()) {
                Pets pet = optionalPets.get();
                petsList.add(pet);
            }else{
                throw new PetNotFoundException();
            }
        });
        return petsList;
    }

    public List<Long> getPetIds(List<Pets> petsList){
        List<Long> petIdList = new ArrayList<Long>();
        petsList.forEach(p->{
            petIdList.add(p.getId());
        });
        return petIdList;
    }

}
