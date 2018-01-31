package com.g_manager.repository.mysql;

import com.g_manager.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Nikolenko Oleh on 21.01.2018.
 */
public interface MySqlEmployeeRepository extends JpaRepository<Employee, Long>{

    Employee findByPhone (String phone);
    Employee findByFullName(String fullName);
}
