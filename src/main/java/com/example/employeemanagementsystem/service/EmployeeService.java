package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.entity.Employee;
import com.example.employeemanagementsystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    static final int ITEM_PER_PAGE = 4;

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee save(Employee employee) {
        Employee saved = employeeRepository.save(employee);
        return saved;
    }

    public Optional<Employee> getEmployeeById(long id) {
        return employeeRepository.findById(id);
    }

    public void deleteEmployeeById(long id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> getAll() {
        return employeeRepository.findAllByOrderByFirstNameAsc();
    }

    public Page<Employee> getEmployeeByPage(int page){
        Pageable pagination = PageRequest.of(page - 1, ITEM_PER_PAGE, Sort.by("firstName").ascending());
        Page<Employee> employees = employeeRepository.findAll(pagination);

        return employees;
    }

    public Page<Employee> getEmployeeByDepartmentByPage(long departmentId, int page){
        Pageable pagination = PageRequest.of(page -1 , ITEM_PER_PAGE);

        return employeeRepository.findByDepartmentId(departmentId, pagination);
    }
}
