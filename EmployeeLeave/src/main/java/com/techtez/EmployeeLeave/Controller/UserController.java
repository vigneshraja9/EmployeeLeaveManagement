package com.techtez.EmployeeLeave.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techtez.EmployeeLeave.DAO.LoginRequestDAO;
import com.techtez.EmployeeLeave.Service.UserService;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;
   
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDAO loginRequest) {
       return userService.login(loginRequest);
    }

 
}
