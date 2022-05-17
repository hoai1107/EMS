package com.example.employeemanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeFormData {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @Email
    private String email;

    @NotNull
    private String address;

    @NotNull
    private String phone;

    private long employeeId;
    private long departmentId = 0;

    public EmployeeFormData(Employee employee) {
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.email = employee.getEmail();
        this.dateOfBirth = employee.getDateOfBirth();
        this.phone = employee.getPhone();
        this.address = employee.getAddress();
        this.employeeId = employee.getId();

        if (employee.isInDepartment()) {
            this.departmentId = employee.getDepartment().getId();
        } else {
            this.departmentId = 0;
        }
    }
}
