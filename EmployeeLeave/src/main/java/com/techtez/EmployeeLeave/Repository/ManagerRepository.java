package com.techtez.EmployeeLeave.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techtez.EmployeeLeave.Entities.LeaveRequest;
import com.techtez.EmployeeLeave.Entities.Manager;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {

    // Find manager by name (used for authentication or lookup)
    Manager findByName(String username);

    
    
    
 // Force case-sensitive search using BINARY keyword
    @Query(value = "SELECT * FROM manager WHERE BINARY name = :name", nativeQuery = true)
    Manager findByNameCaseSensitive(@Param("name") String name);
    
    
    // Optional finder by ID
    Optional<Manager> findById(Long id);

    // --- Custom Queries ---

    /**
     * Fetch all pending leave requests of employees under a given manager.
     * Make sure LeaveRequest has: employee.manager.id and status fields.
     */
    @Query("SELECT l FROM LeaveRequest l WHERE l.employee.manager.id = :managerId AND l.status = 'PENDING'")
    List<LeaveRequest> getPendingLeaves(@Param("managerId") Long managerId);

    /**
     * Approve or reject a leave request by updating its status.
     * Note: This performs a bulk update, returns the number of rows updated.
     */
    @Modifying
    @Transactional
    @Query("UPDATE LeaveRequest l SET l.status = :status WHERE l.id = :leaveId AND l.employee.manager.id = :managerId")
    int approveOrRejectLeave(@Param("leaveId") Long leaveId,
                             @Param("status") String status,
                             @Param("managerId") Long managerId);
}





//public interface ManagerRepository extends JpaRepository<Manager, Long>{
//
//	Manager findByName(String username);
//	
//	Optional<Manager> findById(Long id);
//	 // Fetch all pending leaves of employees under this manager
//    List<LeaveRequest> getPendingLeaves(Long managerId);
//
//    // Approve or reject leave by leaveId
//    LeaveRequest approveOrRejectLeave(Long leaveId, String status, Long managerId);
//
//}
