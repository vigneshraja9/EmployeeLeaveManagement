package com.techtez.EmployeeLeave.DAO;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.techtez.EmployeeLeave.Entities.Employee;
import com.techtez.EmployeeLeave.Entities.Manager;

import java.util.Collection;
import java.util.List;

public class UserPrinciples implements UserDetails {
	
	
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private String role;

    // 🔹 For Manager
    public UserPrinciples(Manager manager) {
        this.username = manager.getName();
        this.password = manager.getPassword();
        this.role = "MANAGER"; // use uppercase for clarity
    }

    // 🔹 For Employee
    public UserPrinciples(Employee employee) {
        this.username = employee.getName();
        this.password = employee.getPassword();
        this.role = "EMPLOYEE";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // ✅ Add ROLE_ prefix so Spring Security recognizes it
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getRole() {
        return role;
    }
}
