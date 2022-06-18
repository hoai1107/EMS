package com.example.employeemanagementsystem.controller;

import com.example.employeemanagementsystem.entity.Employee;
import com.example.employeemanagementsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("")
    public List<Employee> getAllEmployees(){
        return employeeService.getAll();
    }

    @GetMapping("/{employee_id}")
    public ResponseEntity<Employee> getSingleEmployee(@PathVariable long employee_id){
        Optional<Employee> employeeOptional = employeeService.getEmployeeById(employee_id);

        return employeeOptional.map(employee -> new ResponseEntity<>(employee, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping("")
    public Employee addEmployee(@Valid @RequestBody Employee employee){
        employee.setId(0);
        employeeService.save(employee);

        return employee;
    }

    @DeleteMapping("/{employee_id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable long employee_id){
        Optional<Employee> employeeOptional = employeeService.getEmployeeById(employee_id);

        if(employeeOptional.isEmpty()){
            return new ResponseEntity<>("Employee not found!", HttpStatus.NOT_FOUND);
        }

        employeeService.deleteEmployeeById(employee_id);

        return new ResponseEntity<>("Delete employee successfully.", HttpStatus.OK);
    }

    @PutMapping("")
    public Employee updateEmployee(@Valid @RequestBody Employee employee){
        employeeService.save(employee);
        return employee;
    }
}
