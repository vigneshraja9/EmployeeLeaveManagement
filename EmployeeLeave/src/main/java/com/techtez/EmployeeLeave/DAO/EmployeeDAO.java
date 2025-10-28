package com.techtez.EmployeeLeave.DAO;



public class EmployeeDAO {

    private Long id;
    private String name;
    private String email;
    private String role;
    private Long managerId;

    public EmployeeDAO(Long id, String name, String email, String role, Long managerId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.managerId = managerId;
    }

    // Getters only, no password
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public Long getManagerId() { return managerId; }

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}
    
    
    
    
}
