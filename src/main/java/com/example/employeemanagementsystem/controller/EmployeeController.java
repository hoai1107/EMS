package com.example.employeemanagementsystem.controller;

import com.example.employeemanagementsystem.entity.Employee;
import com.example.employeemanagementsystem.service.DepartmentService;
import com.example.employeemanagementsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/list")
    public String viewEmployee(Model model){
        List<Employee> employees = employeeService.getAll();

        model.addAttribute("employees", employees);
        return "employee";
    }

    @GetMapping("/add")
    public String addEmployeeForm(Model model){

        Employee employee = new Employee();
        model.addAttribute("employee", employee);

        return "employee-form";
    }

    @GetMapping("/update")
    public String updateEmployeeForm(@RequestParam("employeeId") long id, Model model){
        Optional<Employee> employee = employeeService.getEmployeeById(id);

        model.addAttribute("employee", employee.get());

        return "employee-form";
    }

    @GetMapping("/delete")
    public String deleteEmployee (@RequestParam("employeeId") long id){
        employeeService.deleteEmployeeById(id);
        return "redirect:/employee/list";
    }

    @PostMapping("/save")
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee){
        employeeService.save(employee);

        return "redirect:/employee/list";
    }
}
