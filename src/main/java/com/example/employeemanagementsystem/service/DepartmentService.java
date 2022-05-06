package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.entity.Department;
import com.example.employeemanagementsystem.entity.Employee;
import com.example.employeemanagementsystem.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department save(Department department){
        return departmentRepository.save(department);
    }

    public Department add(Department department){
        return departmentRepository.save(department);
    }

    public Optional<Department> get(long id){
        return departmentRepository.findById(id);
    }

    public List<Department> getAll(){
        return departmentRepository.findAll();
    }

    public void addEmployee(long department_id, Employee employee){
        Department department = departmentRepository.getById(department_id);
        department.getEmployees().add(employee);
    }
}
