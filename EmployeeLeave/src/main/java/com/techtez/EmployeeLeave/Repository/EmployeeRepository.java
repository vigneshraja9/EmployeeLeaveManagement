package com.techtez.EmployeeLeave.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techtez.EmployeeLeave.Entities.Employee;
import com.techtez.EmployeeLeave.Entities.Manager;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Employee findByName(String username);
	
	@Query(value = "SELECT * FROM employee WHERE BINARY name = :name", nativeQuery = true)
    Employee findByNameCaseSensitive(@Param("name") String name);

	List<Employee> findByManager(Manager manager);
	
}
