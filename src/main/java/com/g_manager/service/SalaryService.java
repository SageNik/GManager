package com.g_manager.service;

import com.g_manager.entity.Employee;
import com.g_manager.entity.Salary;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Nikolenko Oleh on 07.03.2018.
 */
public interface SalaryService extends BaseService<Salary>{

    List<Salary> getAllByMonthSalaryDate(LocalDate date);
    Salary getOneByMonthSalaryDateAndEmployee(LocalDate date, Employee employee );
    BigDecimal getFullCurrentSalary(LocalDate date, Employee employee);
}
