package com.g_manager.entity;

import com.g_manager.entity.base.BasePerson;
import com.g_manager.enums.EmployeeStatus;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Set;

/**
 * Created by Nikolenko Oleh on 19.01.2018.
 */
@Data
@ToString(exclude = {"personalClients", "salaries"})
@Entity
@Table(name = "employee")
public class Employee extends BasePerson {

    private String position;
    private EmployeeStatus status;
    private LocalDate workStartDate;
    private BigDecimal rate;
    private BigDecimal workHoursPerWeek;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Salary> salaries;

    @ManyToMany(mappedBy = "serviceStaff", fetch = FetchType.EAGER)
    private Set<Client> personalClients;

    @ManyToOne
    @JoinColumn(name = "staff_category_id")
    private StaffCategory staffCategory;


    public Salary getSalaryByDate(LocalDate date) {
        Salary salary = salaries.stream().filter(s -> s.getSalaryMonth().equals(date.getMonth()) && s.getSalaryYear() == date.getYear())
                .findFirst().orElse(null);
        return salary;
    }
}
