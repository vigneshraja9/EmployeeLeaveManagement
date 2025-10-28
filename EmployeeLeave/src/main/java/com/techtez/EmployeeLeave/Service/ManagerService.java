package com.techtez.EmployeeLeave.Service;

import com.techtez.EmployeeLeave.Entities.Manager;
import com.techtez.EmployeeLeave.DAO.ManagerDAO;
import com.techtez.EmployeeLeave.Entities.Employee;
import com.techtez.EmployeeLeave.Entities.LeaveRequest;
import com.techtez.EmployeeLeave.Repository.EmployeeRepository;
import com.techtez.EmployeeLeave.Repository.LeaveRequestRepository;
import com.techtez.EmployeeLeave.Repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    
    private BCryptPasswordEncoder bycrpt = new BCryptPasswordEncoder(12) ;
    
    
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    // ==========================
    // ✅ Manager CRUD Methods
    // ==========================

    // Create (Post) a manager
    public Manager createManager(Manager manager) {
       
    	manager.setPassword(bycrpt.encode(manager.getPassword()));
    	return managerRepository.save(manager);
    }

    // Update manager
    public Manager updateManager(Manager updatedManager) {
        if (updatedManager.getId() == null) {
            throw new IllegalArgumentException("Manager ID must not be null for update.");
        }

        Manager existingManager = managerRepository.findById(updatedManager.getId())
                .orElseThrow(() -> new RuntimeException(
                        "Manager not found with ID: " + updatedManager.getId()));

        existingManager.setName(updatedManager.getName());
        existingManager.setEmail(updatedManager.getEmail());
        existingManager.setPassword(updatedManager.getPassword());
        

        return managerRepository.save(existingManager);
    }

    // Delete manager
    public void deleteManager(Long managerId) {
        if (!managerRepository.existsById(managerId)) {
            throw new RuntimeException("Cannot delete — manager not found with ID: " + managerId);
        }
        managerRepository.deleteById(managerId);
    }

    // Get manager by ID
    public Manager getManagerById(Long managerId) {
        return managerRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found with ID: " + managerId));
    }

    // Get all managers
    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }
    
    
    
    public List<ManagerDAO> getAllManagersDAO() {
        return managerRepository.findAll()
                .stream()
                .map(manager -> new ManagerDAO(
                        manager.getId(),
                        manager.getName(),
                        manager.getEmail()
                ))
                .collect(Collectors.toList());
    }

    // ==========================
    // ✅ Leave Management Methods
    // ==========================

    // Get all pending leaves for this manager
    public List<LeaveRequest> getPendingLeaves(Long managerId) {
        Manager manager = getManagerById(managerId);

        List<Employee> employees = employeeRepository.findByManager(manager);

        return employees.stream()
                .flatMap(emp -> leaveRequestRepository.findByEmployee(emp).stream())
                .filter(leave -> "Pending".equalsIgnoreCase(leave.getStatus()))
                .collect(Collectors.toList());
    }

    // Approve or reject leave
    public LeaveRequest approveOrRejectLeave(Long leaveId, String status, Long managerId) {
        LeaveRequest leave = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        Employee employee = leave.getEmployee();
        if (!employee.getManager().getId().equals(managerId)) {
            throw new RuntimeException("Manager not authorized for this employee");
        }

        leave.setStatus(status); // "Approved" or "Rejected"
        return leaveRequestRepository.save(leave);
    }
}
