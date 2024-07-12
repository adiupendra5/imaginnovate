package com.imagenovate.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagenovate.employee.entity.Employee;
import com.imagenovate.employee.repository.EmployeeRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    
    public Employee employeeById(Long employeeId) {
        return employeeRepository.findById(employeeId).get();
    }

    public double calculateTax(Employee employee) {
        double yearlySalary = employee.getSalary() * calculateMonths(employee.getDateOfJoining());
        double tax = 0;

        if (yearlySalary <= 250000) {
            tax = 0;
        } else if (yearlySalary <= 500000) {
            tax = (yearlySalary - 250000) * 0.05;
        } else if (yearlySalary <= 1000000) {
            tax = (250000 * 0.05) + (yearlySalary - 500000) * 0.10;
        } else {
            tax = (250000 * 0.05) + (500000 * 0.10) + (yearlySalary - 1000000) * 0.20;
        }

        return tax;
    }

    public double calculateCess(Employee employee) {
        double yearlySalary = employee.getSalary() * calculateMonths(employee.getDateOfJoining());
        if (yearlySalary > 2500000) {
            return (yearlySalary - 2500000) * 0.02;
        }
        return 0;
    }

    public int calculateMonths(LocalDate dateOfJoining) {
        LocalDate now = LocalDate.now();
        int months = (int) ChronoUnit.MONTHS.between(dateOfJoining.withDayOfMonth(1), now.withDayOfMonth(1));
        return months;
    }
}
