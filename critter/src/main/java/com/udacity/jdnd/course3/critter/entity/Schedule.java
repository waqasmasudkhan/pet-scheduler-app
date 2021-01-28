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




}
