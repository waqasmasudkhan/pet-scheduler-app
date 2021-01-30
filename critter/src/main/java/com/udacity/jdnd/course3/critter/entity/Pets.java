package com.udacity.jdnd.course3.critter.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.udacity.jdnd.course3.critter.pet.PetType;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
public class Pets {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private PetType petType;
    private String name;
    private LocalDate birthDate;
    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer;
    private String notes;

    public Pets(){

    }

    public Pets( PetType petType, String name, LocalDate birthDate, Customer owner, String notes) {
        this.petType = petType;
        this.name = name;
        this.birthDate = birthDate;
        this.customer = owner;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Pets{" +
                "id=" + id +
                ", petType=" + petType +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", customer=" + customer +
                ", notes='" + notes + '\'' +
                '}';
    }
}
