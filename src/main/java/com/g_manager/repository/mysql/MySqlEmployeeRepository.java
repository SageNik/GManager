package com.g_manager.repository.mysql;

import com.g_manager.entity.Employee;
import com.g_manager.enums.EmployeeStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.g_manager.enums.EmployeeStatus.EMPLOYED;

/**
 * Created by Nikolenko Oleh on 21.01.2018.
 */
@Repository
public interface MySqlEmployeeRepository extends JpaRepository<Employee, Long>{

    List<Employee> findAllByStatus (EmployeeStatus status, Sort sort);
    Employee findByPhoneAndStatus (String phone, EmployeeStatus status);
    Employee findByFullNameAndStatus(String fullName, EmployeeStatus status);
}
