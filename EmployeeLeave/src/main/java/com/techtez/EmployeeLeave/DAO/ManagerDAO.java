package com.techtez.EmployeeLeave.DAO;



public class ManagerDAO {

    private Long id;
    private String name;
    private String email;

    public ManagerDAO(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getters only, no password
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
    
}
