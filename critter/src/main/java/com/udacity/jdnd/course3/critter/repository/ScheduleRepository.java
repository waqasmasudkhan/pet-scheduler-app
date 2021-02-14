package com.udacity.jdnd.course3.critter.repository;


import com.udacity.jdnd.course3.critter.entity.Pets;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    public List<Schedule> getSchedulesByPetId(long petId);

    public List<Schedule> getSchedulesByEmployeeIds(long employeeId);

    public List<Schedule> getSchedulesByPetIds(Long petIds);

    public List<Schedule> getSchedulesByPetIds(Pets pets);

}
