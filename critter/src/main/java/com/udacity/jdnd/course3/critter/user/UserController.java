package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pets;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);
    CustomerService customerService;
    PetService petService;
    EmployeeService employeeService;

    public UserController(CustomerService customerService, PetService petService, EmployeeService employeeService){
        this.customerService=customerService;
        this.petService=petService;
        this.employeeService=employeeService;

    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer savedCustomer=customerService.addCustomer(convertsCustomerDTOToCustomer(customerDTO)   );
        return convertsCustomerToCustomerDTO(savedCustomer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customerList=customerService.getAllCustomers();
        return convertsCustomerDTOToCustomer(customerList);
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = customerService.getCustomerByPetId(petId);
        LOGGER.info(customer.getName()+" "+customer.getNotes());
        return convertsCustomerToCustomerDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee savedEmployee = employeeService.saveEmployee(convertEmployeeDTOToEmployee(employeeDTO));
        EmployeeDTO savedEmployeeDTO = convertEmployeeToEmployeeDTO(savedEmployee);
        return savedEmployeeDTO;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        EmployeeDTO employeeDTO = convertEmployeeToEmployeeDTO(employee);
        return employeeDTO;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setEmployeeAvailability(daysAvailable,employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employeeList = employeeService.getEmployeesServices(employeeDTO);
        List<EmployeeDTO> employeeDTOList = convertEmployeeToEmployeeDTO(employeeList);
        return employeeDTOList;
    }

    private Customer convertsCustomerDTOToCustomer(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return customer;
    }

    private CustomerDTO convertsCustomerToCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO);
        List<Pets> petsList = customer.getPets();
        List<Long> petIdList = new ArrayList<Long>();
        LOGGER.info(customerDTO.getPetIds()+" "+customerDTO.getName()+" "+customerDTO.getPetIds()+" "+customerDTO.getPhoneNumber()+" "+customerDTO.getNotes());
        if(petsList!=null){
            for (Pets pet : petsList) {
                petIdList.add(pet.getId());
            }
        }
        customerDTO.setPetIds(petIdList);
        return customerDTO;
    }

    private List<CustomerDTO> convertsCustomerDTOToCustomer(List<Customer> customerList){
        List<CustomerDTO> customerDTOList = new ArrayList<CustomerDTO>();
        for(Customer customer:customerList){
            CustomerDTO customerDTO = convertsCustomerToCustomerDTO(customer);
            customerDTOList.add(customerDTO);
        }
        return customerDTOList;
    }

    private Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO){
        Employee employee= new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        return employee;
    }

    private EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee,employeeDTO);
        return employeeDTO;
    }

    private List<EmployeeDTO> convertEmployeeToEmployeeDTO(List<Employee> employeeList){
        List<EmployeeDTO> employeeDTOList = new ArrayList<EmployeeDTO>();
        for(Employee employee:employeeList){
            EmployeeDTO employeeDTO = convertEmployeeToEmployeeDTO(employee);
            employeeDTOList.add(employeeDTO);
        }
        return employeeDTOList;
    }


}
