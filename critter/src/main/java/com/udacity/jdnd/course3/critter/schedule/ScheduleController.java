package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pets;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.UserController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private static final Logger LOGGER = LogManager.getLogger(ScheduleController.class);
    ScheduleService scheduleService;
    EmployeeService employeeService;
    PetService petService;


    public ScheduleController(ScheduleService scheduleService, EmployeeService employeeService, PetService petService){
        this.scheduleService=scheduleService;
        this.employeeService=employeeService;
        this.petService=petService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        LOGGER.info(scheduleDTO.getId()+" "+scheduleDTO.getPetIds()+" "+scheduleDTO.getEmployeeIds()+" "+scheduleDTO.getActivities()+" "+scheduleDTO.getDate());
        Schedule schedule = convertScheduleDTOToSchedule(scheduleDTO);
        LOGGER.info(schedule.getId()+" "+schedule.getPets()+" "+schedule.getEmployees()+" "+schedule.getActivities()+" "+schedule.getDate());
        Schedule savedSchedule = scheduleService.saveSchedule(schedule);
        ScheduleDTO savedScheduleDTO = convertScheduleToScheduleDTO(savedSchedule);
        return savedScheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> scheduleList = new ArrayList<Schedule>();
        scheduleList=scheduleService.getAllSchedules();
        return convertScheduleToScheduleDTO(scheduleList);
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        Pets pet = petService.getPetById(petId);
        LOGGER.info(pet.getId()+" "+pet.getName()+" "+pet.getBirthDate()+" "+pet.getCustomer());
        List<Schedule> scheduleList = scheduleService.getScheduleByPet(pet);
        List<ScheduleDTO> scheduleDTOList = convertScheduleToScheduleDTO(scheduleList);
        return scheduleDTOList;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return convertScheduleToScheduleDTO(scheduleService.getScheduleByEmployeeId(employeeId));
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> scheduleList = scheduleService.getScheduleByCustomerId(customerId);
        return convertScheduleToScheduleDTO(scheduleList);
    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO,schedule);
        List<Long> employeeIds= scheduleDTO.getEmployeeIds();
        employeeIds.forEach(id->{
            LOGGER.info(id);
        });
        List<Employee> employeeList = scheduleService.getEmployees(employeeIds);
        employeeList.forEach(e->{
            LOGGER.info("Employee is "+e.getName()+" "+e.getId());
        });
        List<Pets> petsList = scheduleService.getPetsById(scheduleDTO.getPetIds());
        schedule.setPets(petsList);
        schedule.setEmployees(employeeList);
        return schedule;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        List<Long> petIds = scheduleService.getPetIds(schedule.getPets());
        List<Long> employeeIds = employeeService.getEmployeeIds(schedule.getEmployees());
        scheduleDTO.setPetIds(petIds);
        scheduleDTO.setEmployeeIds(employeeIds);
        return scheduleDTO;
    }

    private List<ScheduleDTO> convertScheduleToScheduleDTO(List<Schedule> scheduleList){
        List<ScheduleDTO> scheduleDTOList = new ArrayList<ScheduleDTO>();
        for(Schedule schedule: scheduleList){
            scheduleDTOList.add(convertScheduleToScheduleDTO(schedule));
        }
        return scheduleDTOList;
    }


}
