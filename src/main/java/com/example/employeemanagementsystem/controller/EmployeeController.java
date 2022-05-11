package com.example.employeemanagementsystem.controller;

import com.example.employeemanagementsystem.entity.Department;
import com.example.employeemanagementsystem.entity.Employee;
import com.example.employeemanagementsystem.entity.EmployeeFormData;
import com.example.employeemanagementsystem.service.DepartmentService;
import com.example.employeemanagementsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/list")
    public String viewEmployee(Model model, @RequestParam(required = false, name = "page") Integer page) {
        if(Objects.equals(page, null)){
            page = 1;
        }

        Page<Employee> employees = employeeService.getEmployeeByPage(page);
        List<Department> departmentList = departmentService.getAll();

        model.addAttribute("employees", employees);
        model.addAttribute("departmentList", departmentList);

        return "employee";
    }

    @GetMapping("/add")
    public String addEmployeeForm(Model model) {
        //Employee employee = new Employee();
        EmployeeFormData employee = new EmployeeFormData();
        List<Department> departmentList = departmentService.getAll();

        model.addAttribute("employee", employee);
        model.addAttribute("departmentList", departmentList);

        return "employee-form";
    }

    @GetMapping("/update")
    public String updateEmployeeForm(@RequestParam("employeeId") long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id).get();
        EmployeeFormData employeeFormData = new EmployeeFormData(employee);
        List<Department> departmentList = departmentService.getAll();

        model.addAttribute("employee", employeeFormData);
        model.addAttribute("departmentList", departmentList);

        return "employee-form";
    }

    @GetMapping("/delete")
    public String deleteEmployee(@RequestParam("employeeId") long id) {
        employeeService.deleteEmployeeById(id);
        return "redirect:/employee/list";
    }

    @PostMapping("/save")
    public String saveEmployee(@Valid @ModelAttribute("employee") EmployeeFormData employeeFormData) {
        Employee employee = new Employee(employeeFormData);
        long departmentId = employeeFormData.getDepartmentId();

        if (departmentId != 0) {
            Department department = departmentService.get(departmentId).get();
            department.addEmployee(employee);
            departmentService.save(department);
        } else {
            employeeService.save(employee);
        }
        return "redirect:/employee/list";
    }
}
