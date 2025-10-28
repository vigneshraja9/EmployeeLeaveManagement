package com.techtez.EmployeeLeave.Entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.UniqueConstraint;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
   
    @Column(unique = true)
    private String email;
    private String password; // hashed
    private String role; // "EMPLOYEE" or "MANAGER"

    @ManyToOne 
    @JoinColumn(name = "manager_id")
    @JsonBackReference
    private Manager manager;

    @OneToMany(mappedBy = "employee")
    
    private List<LeaveRequest> leaves;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public List<LeaveRequest> getLeaves() {
		return leaves;
	}

	public void setLeaves(List<LeaveRequest> leaves) {
		this.leaves = leaves;
	}
    
    
}
