package com.udacity.jdnd.course3.critter.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.web.bind.annotation.PutMapping;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "SCHEDULE")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

/*    @OneToMany(cascade = CascadeType.ALL)
    private List<Employee> employees;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Pets> pets;*/

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "schedule_employees",
            joinColumns = {@JoinColumn(name = "schedule_id")},
            inverseJoinColumns = {@JoinColumn(name = "employee_id")}
    )
    private List<Employee> employees;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "schedule_pets",
            joinColumns = {@JoinColumn(name = "schedule_id")},
            inverseJoinColumns = {@JoinColumn(name = "pet_id")}
    )
    private List<Pets> pets;




    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @JsonDeserialize(as = EmployeeSkill.class)
    @ElementCollection
    private Set<EmployeeSkill> activities= new HashSet<>();

    public Schedule(){

    }

    public Schedule(long id, List<Employee> employees, List<Pets> pets, LocalDate date, Set<EmployeeSkill> activities) {
        this.id = id;
        this.employees = employees;
        this.pets = pets;
        this.date = date;
        this.activities = activities;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Pets> getPets() {
        return pets;
    }

    public void setPets(List<Pets> petIds) {
        this.pets = petIds;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }
}
