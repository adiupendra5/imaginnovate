package com.imagenovate.employee.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.imagenovate.employee.entity.Employee;
import com.imagenovate.employee.service.EmployeeService;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return ResponseEntity.ok(savedEmployee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getEmployeeTax(@PathVariable Long id) {
        Employee employee = employeeService.employeeById(id);
            
        double tax = employeeService.calculateTax(employee);
        double cess = employeeService.calculateCess(employee);
        double yearlySalary = employee.getSalary() * employeeService.calculateMonths(employee.getDateOfJoining());

        Map<String, Object> response = new HashMap<>();
        response.put("employeeId", employee.getId());
        response.put("firstName", employee.getFirstName());
        response.put("lastName", employee.getLastName());
        response.put("yearlySalary", yearlySalary);
        response.put("taxAmount", tax);
        response.put("cessAmount", cess);

        return ResponseEntity.ok(response);
    }
}
