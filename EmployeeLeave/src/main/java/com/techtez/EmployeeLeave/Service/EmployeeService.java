package com.techtez.EmployeeLeave.Service;

import com.techtez.EmployeeLeave.DAO.EmployeeDAO;
import com.techtez.EmployeeLeave.Entities.Employee;
import com.techtez.EmployeeLeave.Entities.LeaveRequest;
import com.techtez.EmployeeLeave.Entities.Manager;
import com.techtez.EmployeeLeave.Repository.EmployeeRepository;
import com.techtez.EmployeeLeave.Repository.LeaveRequestRepository;
import com.techtez.EmployeeLeave.Repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ManagerRepository managerRepository;
    
    private BCryptPasswordEncoder bycrpt = new BCryptPasswordEncoder(12) ;

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    // --------------------------
    // Employee CRUD
    // --------------------------
    public Employee postEmployee(Employee employee) {
        if (employee.getManager() == null || employee.getManager().getId() == null) {
            throw new IllegalArgumentException("Manager ID must be provided for employee: " + employee.getName());
        }
        employee.setPassword(bycrpt.encode(employee.getPassword()));
        Manager manager = managerRepository.findById(employee.getManager().getId())
                .orElseThrow(() -> new RuntimeException("Manager not found with ID: " + employee.getManager().getId()));
        employee.setManager(manager);
        return employeeRepository.save(employee);
    }

    public List<Employee> postAllEmployees(List<Employee> employees) {
        for (Employee emp : employees) {
            if (emp.getManager() == null || emp.getManager().getId() == null) {
                throw new IllegalArgumentException("Manager ID must be provided for employee: " + emp.getName());
            }
            Manager manager = managerRepository.findById(emp.getManager().getId())
                    .orElseThrow(() -> new RuntimeException("Manager not found with ID: " + emp.getManager().getId()));
            emp.setManager(manager);
            emp.setPassword(bycrpt.encode(emp.getPassword()));
        }
        return employeeRepository.saveAll(employees);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<EmployeeDAO> getAllEmployeesDAO(){
    	return employeeRepository.findAll()
    							 .stream()
    							 .map(emp -> new EmployeeDAO(emp.getId(), emp.getName(), emp.getEmail(), emp.getRole(), emp.getManager().getId()))
    							 .collect(Collectors.toList());
    }
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee updateEmployee(Employee updatedEmployee) {
        if (updatedEmployee.getId() == null) {
            throw new IllegalArgumentException("Employee ID must not be null for update.");
        }
        Employee existingEmployee = employeeRepository.findById(updatedEmployee.getId())
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + updatedEmployee.getId()));

        if (updatedEmployee.getManager() != null && updatedEmployee.getManager().getId() != null) {
            Manager manager = managerRepository.findById(updatedEmployee.getManager().getId())
                    .orElseThrow(() -> new RuntimeException("Manager not found with ID: " + updatedEmployee.getManager().getId()));
            existingEmployee.setManager(manager);
        }

        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setLeaves(updatedEmployee.getLeaves());
        return employeeRepository.save(existingEmployee);
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete — employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }

    // --------------------------
    // Leave operations
    // --------------------------

    public LeaveRequest applyLeave(Long employeeId, LeaveRequest leaveRequest) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        leaveRequest.setEmployee(employee);
        leaveRequest.setStatus("Pending"); // default status
        return leaveRequestRepository.save(leaveRequest);
    }

    public List<LeaveRequest> getLeaveRequests(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return leaveRequestRepository.findByEmployee(employee);
    }
}
