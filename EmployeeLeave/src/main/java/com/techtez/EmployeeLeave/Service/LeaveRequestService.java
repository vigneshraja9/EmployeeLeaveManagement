package com.techtez.EmployeeLeave.Service;

import com.techtez.EmployeeLeave.Entities.LeaveRequest;
import com.techtez.EmployeeLeave.Repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    // Create leave request
    public LeaveRequest createLeaveRequest(LeaveRequest leaveRequest) {
        return leaveRequestRepository.save(leaveRequest);
    }

    // Update leave request
    public LeaveRequest updateLeaveRequest(LeaveRequest leaveRequest) {
        if (!leaveRequestRepository.existsById(leaveRequest.getId())) {
            throw new RuntimeException("Leave request not found");
        }
        return leaveRequestRepository.save(leaveRequest);
    }

    // Get leave request by ID
    public LeaveRequest getLeaveRequest(Long leaveId) {
        return leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
    }

    // Get all leave requests
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }

    // Delete leave request
    public void deleteLeaveRequest(Long leaveId) {
        if (!leaveRequestRepository.existsById(leaveId)) {
            throw new RuntimeException("Leave request not found");
        }
        leaveRequestRepository.deleteById(leaveId);
    }
}
