package com.techtez.EmployeeLeave.Controller;

import com.techtez.EmployeeLeave.DAO.EmployeeDAO;
import com.techtez.EmployeeLeave.Entities.Employee;
import com.techtez.EmployeeLeave.Entities.LeaveRequest;
import com.techtez.EmployeeLeave.Service.EmployeeService;
import com.techtez.EmployeeLeave.Service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private LeaveRequestService leaveRequestService;

    // ==========================
    // Employee CRUD Endpoints
    // ==========================

    // Create a single employee
    @PostMapping("/addemployee")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.postEmployee(employee);
    }

    // Create multiple employees
    @PostMapping("/bulk")
    public List<Employee> createAllEmployees(@RequestBody List<Employee> employees) {
        return employeeService.postAllEmployees(employees);
    }

    // Update employee
    @PutMapping("/{employeeId}")
    public Employee updateEmployee(@PathVariable Long employeeId, @RequestBody Employee employee) {
        employee.setId(employeeId);
        return employeeService.updateEmployee(employee);
    }

    // Delete employee
    @DeleteMapping("/{employeeId}")
    public String deleteEmployee(@PathVariable Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return "Employee deleted successfully with ID: " + employeeId;
    }

    // Get employee by ID
    @GetMapping("/{employeeId}")
    public Employee getEmployeeById(@PathVariable Long employeeId) {
        return employeeService.getEmployeeById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));
    }

    // Get all employees
    @GetMapping("/withpassword")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }
    
    @GetMapping
    public List<EmployeeDAO> getAllEmployeesDAO() {
        return employeeService.getAllEmployeesDAO();
    }

    
    // ==========================
    // Leave Endpoints
    // ==========================

    // Employee applies for a leave
    @PostMapping("/{employeeId}/leaves")
    public LeaveRequest applyLeave(@PathVariable Long employeeId, @RequestBody LeaveRequest leaveRequest) {
        return employeeService.applyLeave(employeeId, leaveRequest);
    }

    // Employee views all their leaves
    @GetMapping("/{employeeId}/leaves")
    public List<LeaveRequest> getLeaveRequests(@PathVariable Long employeeId) {
        return employeeService.getLeaveRequests(employeeId);
    }

    // Optional: Get specific leave by ID (only for this employee)
    @GetMapping("/{employeeId}/leaves/{leaveId}")
    public LeaveRequest getLeaveById(@PathVariable Long employeeId, @PathVariable Long leaveId) {
        LeaveRequest leave = leaveRequestService.getLeaveRequest(leaveId);
        if (!leave.getEmployee().getId().equals(employeeId)) {
            throw new RuntimeException("Not authorized to view this leave");
        }
        return leave;
    }
}
