package com.udacity.jdnd.course3.critter.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CUSTOMER")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;
    private String phoneNumber;
    private String notes;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private List<Pets> pets;

    public Customer(){

    }

    public Customer(Long id, String name, String phoneNumber, String notes, List<Pets> pets) {
        Id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.notes = notes;
        this.pets = pets;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Pets> getPets() {
        return pets;
    }

    public void setPets(List<Pets> pets) {
        this.pets = pets;
    }

}
