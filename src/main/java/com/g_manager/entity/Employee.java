package com.g_manager.entity;

import com.g_manager.entity.base.BasePerson;
import com.g_manager.enums.EmployeeStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * Created by Nikolenko Oleh on 19.01.2018.
 */
@Data
@Entity
@Table(name = "employee")
public class Employee extends BasePerson {

    private String position;
    private EmployeeStatus status;
    private LocalDate workStartDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "salary_id")
    private Salary salary;

    @ManyToMany(mappedBy = "serviceStaff",fetch = FetchType.EAGER)
    private Set<Client> personalClients;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "staff_category_id")
    private StaffCategory staffCategory;
}
