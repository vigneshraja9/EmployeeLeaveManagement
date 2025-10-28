package com.techtez.EmployeeLeave.DAO;



import java.time.LocalDate;

public class LeaveDAO {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private String status;
    private Long employeeId;
    private Long approvedByManagerId;

    public LeaveDAO(Long id, LocalDate startDate, LocalDate endDate, String reason, String status, Long employeeId, Long approvedByManagerId) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.status = status;
        this.employeeId = employeeId;
        this.approvedByManagerId = approvedByManagerId;
    }

    // Getters only
    public Long getId() { return id; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public String getReason() { return reason; }
    public String getStatus() { return status; }
    public Long getEmployeeId() { return employeeId; }
    public Long getApprovedByManagerId() { return approvedByManagerId; }

	public void setId(Long id) {
		this.id = id;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public void setApprovedByManagerId(Long approvedByManagerId) {
		this.approvedByManagerId = approvedByManagerId;
	}
    
    
    
}
