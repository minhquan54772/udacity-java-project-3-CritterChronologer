package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.exceptions.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.enumerations.EmployeeSkill;
import com.udacity.jdnd.course3.critter.dto.mappers.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserMapper userMapper;

    public EmployeeService(EmployeeRepository employeeRepository, UserMapper userMapper) {
        this.employeeRepository = employeeRepository;
        this.userMapper = userMapper;
    }

    public Employee save(Employee employee) {
        return this.employeeRepository.save(employee);
    }

    public Employee getEmployeeById(long id) {
        Optional<Employee> employeeById = this.employeeRepository.findById(id);
        if (employeeById.isEmpty()) {
            throw new EmployeeNotFoundException("Employee with id " + id + " not found");
        }
        return employeeById.get();
    }

    public void setEmployeeAvailability(long employeeId, Set<DayOfWeek> daysAvailable) {
        Optional<Employee> employeeById = this.employeeRepository.findById(employeeId);
        if (employeeById.isEmpty()) {
            throw new EmployeeNotFoundException("Employee with id " + employeeId + " not found");
        }
        Employee employee = employeeById.get();
        employee.setDaysAvailable(daysAvailable);
        this.employeeRepository.save(employee);
    }

    public List<Employee> getAvailableEmployeesBySkills(EmployeeRequestDTO employeeRequestDTO) {
        Set<EmployeeSkill> skills = employeeRequestDTO.getSkills();
        DayOfWeek dayOfWeek = employeeRequestDTO.getDate().getDayOfWeek();
        return this.employeeRepository.findEmployeesBySkillsAndDayOfWeek(dayOfWeek, skills, skills.size());
    }


}
