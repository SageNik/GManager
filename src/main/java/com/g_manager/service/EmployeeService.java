package com.g_manager.service;

import com.g_manager.entity.Employee;
import com.g_manager.enums.EmployeeStatus;

import java.util.List;

/**
 * Created by Nikolenko Oleh on 21.01.2018.
 */
public interface EmployeeService extends BaseService<Employee> {

    List<Employee> findAllByStatus(EmployeeStatus status);
    Employee findByPhoneAndStatus(String phone, EmployeeStatus status);
    Employee findByFullNameAndStatus(String fullName, EmployeeStatus status);
}
