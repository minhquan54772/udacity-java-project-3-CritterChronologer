package com.udacity.jdnd.course3.critter.dto.mappers;

import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.services.PetService;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.services.EmployeeService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScheduleMapper {
    private final PetService petService;
    private final EmployeeService employeeService;

    public ScheduleMapper(PetService petService, EmployeeService employeeService) {
        this.petService = petService;
        this.employeeService = employeeService;
    }

    public ScheduleDTO convertScheduleEntityToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setDate(schedule.getDate());

        List<Long> petIds = schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList());
        scheduleDTO.setPetIds(petIds);

        List<Long> employeeIds = schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList());
        scheduleDTO.setEmployeeIds(employeeIds);

        scheduleDTO.setActivities(schedule.getActivities());

        return scheduleDTO;
    }

    public Schedule convertScheduleDTOToEntity(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setId(scheduleDTO.getId());
        schedule.setDate(scheduleDTO.getDate());

        List<Long> petIds = scheduleDTO.getPetIds();
        List<Pet> pets = petIds.stream().map(this.petService::getPetById).collect(Collectors.toList());
        schedule.setPets(pets);

        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        List<Employee> employees = employeeIds.stream().map(this.employeeService::getEmployeeById).collect(Collectors.toList());
        schedule.setEmployees(employees);

        schedule.setActivities(scheduleDTO.getActivities());

        return schedule;
    }
}
