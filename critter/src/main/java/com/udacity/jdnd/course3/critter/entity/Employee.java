package com.udacity.jdnd.course3.critter.entity;


import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    private EmployeeSkill skills;

    private DayOfWeek daysAvailable;

    public Employee(){

    }

    public Employee(long id, String name, EmployeeSkill skills, DayOfWeek daysAvailable) {
        this.id = id;
        this.name = name;
        this.skills = skills;
        this.daysAvailable = daysAvailable;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EmployeeSkill getSkills() {
        return skills;
    }

    public void setSkills(EmployeeSkill skills) {
        this.skills = skills;
    }

    public DayOfWeek getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(DayOfWeek daysAvailable) {
        this.daysAvailable = daysAvailable;
    }
}
