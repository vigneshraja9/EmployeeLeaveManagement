package com.techtez.EmployeeLeave.Controller;

import com.techtez.EmployeeLeave.Entities.Manager;
import com.techtez.EmployeeLeave.DAO.ManagerDAO;
import com.techtez.EmployeeLeave.Entities.LeaveRequest;
import com.techtez.EmployeeLeave.Service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    // ==========================
    // ✅ Manager CRUD Endpoints
    // ==========================

    // Create a manager
    @PostMapping("/addmanager")
    public Manager createManager(@RequestBody Manager manager) {
        return managerService.createManager(manager);
    }

    // Update a manager
    @PutMapping("/{managerId}")
    public Manager updateManager(@PathVariable Long managerId, @RequestBody Manager manager) {
        manager.setId(managerId);
        return managerService.updateManager(manager);
    }

    // Delete a manager
    @DeleteMapping("/{managerId}")
    public String deleteManager(@PathVariable Long managerId) {
        managerService.deleteManager(managerId);
        return "Manager deleted successfully with ID: " + managerId;
    }

    // Get manager by ID
    @GetMapping("/{managerId}")
    public Manager getManagerById(@PathVariable Long managerId) {
        return managerService.getManagerById(managerId);
    }

    // Get all managers
    @GetMapping("/withpassword")
    public List<Manager> getAllManagers() {
        return managerService.getAllManagers();
    }
    
    @GetMapping
    public List<ManagerDAO> getAllManagersDAO() {
    	return managerService.getAllManagersDAO();
    }

    // ==========================
    // ✅ Leave Management Endpoints
    // ==========================

    // Get all pending leaves for a manager
    @GetMapping("/{managerId}/pending-leaves")
    public List<LeaveRequest> getPendingLeaves(@PathVariable Long managerId) {
        return managerService.getPendingLeaves(managerId);
    }

    // Approve or reject a leave
    @PutMapping("/{managerId}/leaves/{leaveId}")
    public LeaveRequest approveOrRejectLeave(
            @PathVariable Long managerId,
            @PathVariable Long leaveId,
            @RequestParam String status // "Approved" or "Rejected"
    ) {
        return managerService.approveOrRejectLeave(leaveId, status, managerId);
    }
}
