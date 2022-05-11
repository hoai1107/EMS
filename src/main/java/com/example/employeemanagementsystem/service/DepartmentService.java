package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.entity.Department;
import com.example.employeemanagementsystem.entity.Employee;
import com.example.employeemanagementsystem.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    static final int ITEM_PER_PAGE = 4;
    @Autowired
    private DepartmentRepository departmentRepository;

    public void save(Department department){
        departmentRepository.save(department);
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

    public Page<Department> getDepartmentByPage(int page){
        Pageable pageable = PageRequest.of(page - 1, ITEM_PER_PAGE, Sort.by("departmentName").ascending());
        Page<Department> departments = departmentRepository.findAll(pageable);

        return departments;
    }

    public void deleteById(long id){
        departmentRepository.deleteById(id);
    }
}
