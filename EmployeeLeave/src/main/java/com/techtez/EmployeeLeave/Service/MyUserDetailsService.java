package com.techtez.EmployeeLeave.Service;


import com.techtez.EmployeeLeave.DAO.UserPrinciples;
import com.techtez.EmployeeLeave.Entities.Employee;
import com.techtez.EmployeeLeave.Entities.Manager;
import com.techtez.EmployeeLeave.Repository.EmployeeRepository;
import com.techtez.EmployeeLeave.Repository.ManagerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 🔹 Try to find a Manager
        Manager manager = managerRepository.findByName(username);
        if (manager != null) {
            return new UserPrinciples(manager);
        }

        // 🔹 Try to find an Employee
        Employee employee = employeeRepository.findByName(username);
        if (employee != null) {
            return new UserPrinciples(employee);
        }

        // 🔹 If neither found, throw exception
        System.out.println(username + " not found in database!");
        throw new UsernameNotFoundException("User not found: " + username);
    }
}
