package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.exceptions.EmployeeNotFound;
import com.udacity.jdnd.course3.critter.exceptions.PetNotFoundException;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository=employeeRepository;
    }


    public List<Employee> getEmployeesServices(EmployeeRequestDTO employeeDTO){
        List<Employee> availableEmployees= employeeRepository.findEmployeesByDaysAvailableAndSkillsIn(employeeDTO.getDate().getDayOfWeek(),employeeDTO.getSkills());
        return availableEmployees;
    }

    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId){
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

        if(!optionalEmployee.isPresent()){
            throw new EmployeeNotFound();
        }else{
            Employee employee = optionalEmployee.get();
            employee.setDaysAvailable(daysAvailable);
            employeeRepository.save(employee);
        }
    }

    public Employee saveEmployee(Employee employee){
        Employee savedEmployee=employeeRepository.save(employee);
        return savedEmployee;
    }

    public Employee getEmployee(long employeeId){
        Optional<Employee> optionalEmployee=employeeRepository.findById(employeeId);
        if(optionalEmployee.isPresent()){
            return optionalEmployee.get();
        }else{
            throw new EmployeeNotFound();
        }
    }

    public List<Long> getEmployeeIds(List<Employee> employeeList){
        List<Long> employeeIdsList = new ArrayList<Long>();
        employeeList.forEach(e->{
            employeeIdsList.add(e.getId());
        });
        return employeeIdsList;
    }

}
