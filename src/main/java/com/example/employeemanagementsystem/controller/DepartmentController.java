package com.example.employeemanagementsystem.controller;

import com.example.employeemanagementsystem.entity.Department;
import com.example.employeemanagementsystem.entity.Employee;
import com.example.employeemanagementsystem.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/list")
    public String viewDepartment(Model model, @RequestParam(required = false, name="page") Integer page){
        if(Objects.equals(page, null)){
            page = 1;
        }

        Page<Department> departments = departmentService.getDepartmentByPage(page);

        model.addAttribute("departments", departments);
        return "department";
    }

    @GetMapping("/add")
    public String addDepartment(Model model){
        Department department = new Department();

        model.addAttribute("department", department);
        return "department-form";
    }

    @PostMapping("/save")
    public String saveDepartment(@Valid @ModelAttribute("department") Department department){
        departmentService.save(department);

        return "redirect:/department/list";
    }

    @GetMapping("/update")
    public String updateDepartment(@RequestParam("departmentId") long id, Model model){
        Department department = departmentService.get(id).get();


        model.addAttribute("department", department);


        return "department-form";
    }

    @GetMapping("/delete")
    public String deleteDepartment(@RequestParam("departmentId") long id){
        departmentService.deleteById(id);

        return "redirect:/department/list";
    }

    @GetMapping("/employee-list")
    public String viewEmployeesOfDepartment(@RequestParam("departmentId") long id, Model model){
        Department department = departmentService.get(id).get();
        Set<Employee> employeeSet = department.getEmployees();

        model.addAttribute("employees", employeeSet);
        model.addAttribute("department", department);

        return "department-employee";
    }
}
