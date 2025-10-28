package com.techtez.EmployeeLeave.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techtez.EmployeeLeave.Entities.Employee;
import com.techtez.EmployeeLeave.Entities.LeaveRequest;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    // ✅ Fetch all leave requests (explicit query)
    @Query("SELECT l FROM LeaveRequest l")
    List<LeaveRequest> getAllLeaveRequests();

    // ✅ Fetch leave requests by employee entity
    List<LeaveRequest> findByEmployee(Employee employee);

    // ✅ Fetch leave requests by employee ID (alternative)
    @Query("SELECT l FROM LeaveRequest l WHERE l.employee.id = :employeeId")
    List<LeaveRequest> findByEmployeeId(@Param("employeeId") Long employeeId);

    // ✅ Fetch pending leave requests by manager ID
    @Query("SELECT l FROM LeaveRequest l WHERE l.employee.manager.id = :managerId AND l.status = 'PENDING'")
    List<LeaveRequest> findPendingByManagerId(@Param("managerId") Long managerId);
}
