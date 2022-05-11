package com.example.employeemanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

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

    @ManyToOne(
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "department_id")
    @JsonIgnoreProperties("employees")
    private Department department;

    public Employee(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Employee(EmployeeFormData employeeFormData){
        this.id = employeeFormData.getEmployeeId();
        this.firstName = employeeFormData.getFirstName();
        this.lastName = employeeFormData.getLastName();
        this.dateOfBirth = employeeFormData.getDateOfBirth();
        this.email = employeeFormData.getEmail();
        this.address = employeeFormData.getAddress();
        this.phone = employeeFormData.getPhone();
    }
}
