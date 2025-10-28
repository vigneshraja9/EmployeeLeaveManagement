package com.techtez.EmployeeLeave.Configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.techtez.EmployeeLeave.JWTFilter.JwtAuthenticationFilter;
import com.techtez.EmployeeLeave.Service.MyUserDetailsService;

//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Autowired
//    private MyUserDetailsService myUserDetailsService;
//
//    @Autowired
//    private JwtAuthenticationFilter jwtFilter;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        return http
//                // Disable CSRF for JWT-based authentication
//                .csrf(csrf -> csrf.disable())
//
//                // Define endpoint permissions
//                .authorizeHttpRequests(auth -> auth
//                        // Public endpoints
//                        .requestMatchers( "/auth/login","/manager/addmanager","/employee/addemployee").permitAll()
//
//                        // Employee endpoints (apply/check leave)
//                        .requestMatchers("/employee/**").hasRole("EMPLOYEE")
//
//                        // Manager endpoints (approve/reject/view)
//                        .requestMatchers("/manager/**").hasRole("MANAGER")
//
//                        // Anything else must be authenticated
//                        .anyRequest().authenticated()
//                )
//
//                // Use stateless session (JWT)
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//
//                // Authentication setup
//                .authenticationProvider(authenticationProvider(passwordEncoder()))
//
//                // Add JWT filter before username/password authentication
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//
//                .build();
//    }
//
//    // Password encoder (BCrypt)
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(12);
//    }
//
//    // DAO authentication provider
//    @Bean
//    public AuthenticationProvider authenticationProvider(PasswordEncoder encoder) {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(myUserDetailsService);
//       //provider.setUserDetailsService(myUserDetailsService);
//        provider.setPasswordEncoder(encoder);
//        return provider;
//    }
//
//    // Authentication manager (for login)
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//}





@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // public endpoints
                        .requestMatchers("/auth/login", "/manager/addmanager", "/employee/addemployee").permitAll()
                        // protected endpoints
                        .requestMatchers("/employee/**").hasRole("EMPLOYEE")
                        .requestMatchers("/manager", "/manager/**").hasRole("MANAGER")
                        // anything else
                        .anyRequest().authenticated()
                )
                // stateless (for JWT)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // authentication provider
                .authenticationProvider(authenticationProvider(passwordEncoder()))
                // jwt filter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(myUserDetailsService);
//        provider.setPasswordEncoder(passwordEncoder());
//        return provider;
//    }
    
    @Bean
  public AuthenticationProvider authenticationProvider(PasswordEncoder encoder) {
      DaoAuthenticationProvider provider = new DaoAuthenticationProvider(myUserDetailsService);
     //provider.setUserDetailsService(myUserDetailsService);
      provider.setPasswordEncoder(encoder);
      return provider;
  }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
