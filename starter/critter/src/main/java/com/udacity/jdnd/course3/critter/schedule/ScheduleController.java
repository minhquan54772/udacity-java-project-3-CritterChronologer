package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;

    public ScheduleController(ScheduleService scheduleService, ScheduleMapper scheduleMapper) {
        this.scheduleService = scheduleService;
        this.scheduleMapper = scheduleMapper;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleMapper.convertScheduleDTOToEntity(scheduleDTO);
        Schedule persistedSchedule = scheduleService.saveSchedule(schedule);
        return scheduleMapper.convertScheduleEntityToDTO(persistedSchedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.getAllSchedules().stream()
                .map(this.scheduleMapper::convertScheduleEntityToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return scheduleService.getScheduleByPetId(petId).stream()
                .map(scheduleMapper::convertScheduleEntityToDTO)
                .collect(Collectors.toList());

    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService.getScheduleByEmployeeId(employeeId).stream()
                .map(scheduleMapper::convertScheduleEntityToDTO)
                .collect(Collectors.toList());

    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return scheduleService.getScheduleByCustomerId(customerId).stream()
                .map(scheduleMapper::convertScheduleEntityToDTO)
                .collect(Collectors.toList());
    }
}
