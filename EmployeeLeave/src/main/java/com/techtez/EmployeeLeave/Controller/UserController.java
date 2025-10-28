package com.techtez.EmployeeLeave.Controller;

import com.techtez.EmployeeLeave.Entities.Employee;
import com.techtez.EmployeeLeave.Entities.Manager;
import com.techtez.EmployeeLeave.Repository.EmployeeRepository;
import com.techtez.EmployeeLeave.Repository.ManagerRepository;
import com.techtez.EmployeeLeave.Service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(12);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ManagerRepository managerRepository;

    /**
     * ✅ Login endpoint for both Employee and Manager.
     * Returns JWT token if credentials are correct.
     */
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        try {
            // Step 1: Find user (Manager or Employee)
        	System.out.println(username);
            //Manager manager = managerRepository.findByName(username);
            Manager manager = managerRepository.findByNameCaseSensitive(username);
            
            
//            Employee employee = employeeRepository.findByName(username);
            Employee employee = employeeRepository.findByNameCaseSensitive(username);
            
            
            System.out.println(manager);
            if (manager == null && employee == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }

            String storedHash = (manager != null)
                    ? manager.getPassword()
                    : employee.getPassword();

            // Step 2: Compare passwords using bcrypt.matches()
            if (!bcrypt.matches(password, storedHash)) {
                throw new BadCredentialsException("Invalid username or password");
            }

            // Step 3: Build userDetails manually
            String role = (manager != null) ? "MANAGER" : "EMPLOYEE";

            UserDetails userDetails = User.builder()
                    .username(username)
                    .password(storedHash)
                    .roles(role)
                    .build();

            // Optional Spring AuthenticationManager check
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // Step 4: Generate and return JWT
            return jwtService.generateToken(userDetails);

        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
    }

    // ✅ Simple DTO for login input
    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
