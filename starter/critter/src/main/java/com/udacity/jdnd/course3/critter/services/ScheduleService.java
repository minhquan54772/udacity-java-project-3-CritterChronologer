package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.exceptions.ScheduleNotFoundException;
import com.udacity.jdnd.course3.critter.repositories.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getScheduleByPetId(long petId) {
        Optional<List<Schedule>> scheduleByPetId = scheduleRepository.findScheduleByPetId(petId);
        if (scheduleByPetId.isEmpty()) {
            throw new ScheduleNotFoundException("Pet with id " + petId + " does not have any schedule");
        }
        return scheduleByPetId.get();
    }

    public List<Schedule> getScheduleByEmployeeId(long employeeId) {
        Optional<List<Schedule>> scheduleByEmployeeId = scheduleRepository.findScheduleByEmployeeId(employeeId);
        if (scheduleByEmployeeId.isEmpty()) {
            throw new ScheduleNotFoundException("Employee with id " + employeeId + " does not have any schedule");
        }
        return scheduleByEmployeeId.get();
    }

    public List<Schedule> getScheduleByCustomerId(long customerId) {
        Optional<List<Schedule>> scheduleByCustomerId = scheduleRepository.findScheduleByCustomerId(customerId);
        if (scheduleByCustomerId.isEmpty()) {
            throw new ScheduleNotFoundException("Customer with id " + customerId + " does not have any schedule");
        }
        return scheduleByCustomerId.get();
    }
}