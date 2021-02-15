package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pets, Long> {

    String findByName(String name);
    String findByBirthDate(LocalDate birthDate);
    String findByType(String type);
    List<Pets> findPetsByCustomerEquals(Customer customer);
    long findCustomerById(Long petId);


}
