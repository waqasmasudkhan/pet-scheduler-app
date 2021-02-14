package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.web.bind.annotation.PutMapping;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "SCHEDULE")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Employee> employeeIds;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Pets> petIds;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private EmployeeSkill activities;

    public Schedule(){

    }

    public Schedule(long id, List<Employee> employeeIds, List<Pets> petIds, LocalDate date, EmployeeSkill activities) {
        this.id = id;
        this.employeeIds = employeeIds;
        this.petIds = petIds;
        this.date = date;
        this.activities = activities;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Employee> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Employee> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public List<Pets> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Pets> petIds) {
        this.petIds = petIds;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public EmployeeSkill getActivities() {
        return activities;
    }

    public void setActivities(EmployeeSkill activities) {
        this.activities = activities;
    }
}
