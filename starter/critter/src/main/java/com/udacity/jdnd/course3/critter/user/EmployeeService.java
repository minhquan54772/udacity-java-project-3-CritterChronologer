package com.udacity.jdnd.course3.critter.user;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
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

    // TODO
    public List<Employee> getAvailableEmployeesBySkills(EmployeeRequestDTO employeeRequestDTO) {
        Set<EmployeeSkill> skills = employeeRequestDTO.getSkills();
        DayOfWeek dayOfWeek = employeeRequestDTO.getDate().getDayOfWeek();
        return this.employeeRepository.findEmployeesBySkillsAndDayOfWeek(dayOfWeek, skills, skills.size());
    }


}
