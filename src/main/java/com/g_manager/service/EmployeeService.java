package com.g_manager.service;

import com.g_manager.entity.Employee;

/**
 * Created by Nikolenko Oleh on 21.01.2018.
 */
public interface EmployeeService extends BaseService<Employee> {

    Employee findByPhone(String phone);
    Employee findByFullName (String fullName);
}
