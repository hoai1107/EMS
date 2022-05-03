package com.example.employeemanagementsystem.controller;

import com.example.employeemanagementsystem.entity.Department;
import com.example.employeemanagementsystem.entity.Employee;
import com.example.employeemanagementsystem.service.DepartmentService;
import com.example.employeemanagementsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/")
    public String indexPage(Model model) {
        return "redirect:/employee/list";
    }

    @GetMapping("/employee/list")
    public String viewEmployee(Model model){
        List<Employee> employees = employeeService.getAll();

        model.addAttribute("employees", employees);
        return "employee";
    }

    @GetMapping("/employee/add")
    public String addEmployeeForm(Model model){

        Employee employee = new Employee();
        model.addAttribute("employee", employee);

        return "form";
    }

    @GetMapping("/employee/update")
    public String updateEmployeeForm(@RequestParam("employeeId") long id, Model model){
        Optional<Employee> employee = employeeService.getEmployeeById(id);

        model.addAttribute("employee", employee.get());

        return "form";
    }

    @GetMapping("/employee/delete")
    public String deleteEmployee (@RequestParam("employeeId") long id){
        employeeService.deleteEmployeeById(id);
        return "redirect:/employee/list";
    }

    @PostMapping("/employee/save")
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee){
        employeeService.save(employee);

        return "redirect:/employee/list";
    }

    @GetMapping("/api/employee/all")
    public ResponseEntity<List<Employee>> getAllEmployee() {

        return new ResponseEntity<>(
                employeeService.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/api/employee/{id}")
    public ResponseEntity<Employee> getEmployeeByID(@PathVariable long id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);

        if (employee.isEmpty()) throw new NoSuchElementException("Item not found!");

        return new ResponseEntity<>(employee.get(), HttpStatus.OK);
    }

    @PostMapping("/api/employee")
    public ResponseEntity<Employee> addEmployee(@Valid @RequestBody Employee employee) {

        return new ResponseEntity<>(
                employeeService.save(employee),
                HttpStatus.OK
        );
    }

    @PostMapping("/api/employee/{employee_id}/department/{dep_id}")
    public ResponseEntity<Employee> setDepartmentOfEmployee(@PathVariable long employee_id,
                                                            @PathVariable long dep_id) {

        Optional<Employee> maybeEmployee = employeeService.getEmployeeById(employee_id);
        Optional<Department> maybeDepartment = departmentService.get(dep_id);

        if (maybeDepartment.isEmpty() || maybeEmployee.isEmpty()) {
            throw new NoSuchElementException("Employee or department not found!");
        }

        Employee employee = maybeEmployee.get();
        Department department = maybeDepartment.get();

        employee.setDepartment(department);
        department.addEmployee(employee);

        employeeService.save(employee);
        departmentService.save(department);

        return new ResponseEntity<>(
                employee,
                HttpStatus.OK
        );
    }
}
