package com.g_manager.service;

import com.g_manager.entity.Employee;
import com.g_manager.entity.Salary;
import com.g_manager.enums.EmployeeStatus;
import javafx.event.ActionEvent;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Nikolenko Oleh on 21.01.2018.
 */
public interface EmployeeService extends BaseService<Employee> {

    List<Employee> findAllByStatus(EmployeeStatus status);
    List<Employee> findAllByMonthSalaryDate(LocalDate monthSalaryDate);
    Employee findByPhoneAndStatus(String phone, EmployeeStatus status);
    Employee findByFullNameAndStatus(String fullName, EmployeeStatus status);
    boolean checkAndSave(ActionEvent event, Employee employee);
}
