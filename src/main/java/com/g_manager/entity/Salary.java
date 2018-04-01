package com.g_manager.entity;

import com.g_manager.entity.base.BaseEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikolenko Oleh on 16.02.2018.
 */
@Data
@Entity
@ToString(exclude = {"bonuses","penalties"})
@Table(name="salary")
public class Salary extends BaseEntity{

    private LocalDate payDate;
    private Month salaryMonth;
    private int salaryYear;
    private BigDecimal hoursWorked;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToMany(mappedBy = "salary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bonus> bonuses;

    @OneToMany(mappedBy = "salary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Penalty> penalties;

    public BigDecimal getFullCurrentSalary(BigDecimal rate){
        BigDecimal fullSalary = rate;
        if(!bonuses.isEmpty()){
            for (Bonus bonus: bonuses) {
                fullSalary = fullSalary.add(bonus.getAmount());
            }
        }
        if(!penalties.isEmpty()){
            for(Penalty penalty: penalties){
                fullSalary = fullSalary.subtract(penalty.getAmount());
            }
        }
        return fullSalary;
    }

    public static Salary newInstance(Employee employee){
        Salary newSalary = new Salary();
        newSalary.setLastChangeDate(LocalDateTime.now());
        newSalary.setEmployee(employee);
        newSalary.setSalaryYear(LocalDate.now().getYear());
        newSalary.setSalaryMonth(LocalDate.now().getMonth());
        newSalary.setPenalties(new ArrayList<>());
        newSalary.setBonuses(new ArrayList<>());
        return newSalary;
    }
}
