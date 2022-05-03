package com.example.employeemanagementsystem.controller;

import com.example.employeemanagementsystem.entity.Department;
import com.example.employeemanagementsystem.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@RestController
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @PostMapping("/department")
    public ResponseEntity<Department> addDepartment(@RequestBody Department department) {
        Department result = departmentService.add(department);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/department/{id}")
    public ResponseEntity<Department> getDepartment(@PathVariable long id) {
        Optional<Department> department = departmentService.get(id);

        if (department.isEmpty()) {
            throw new NoSuchElementException("Department not found!");
        }

        return new ResponseEntity<>(department.get(), HttpStatus.OK);
    }
}
