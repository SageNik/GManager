package com.g_manager.repository.mysql;

import com.g_manager.entity.Employee;
import com.g_manager.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.util.List;

/**
 * Created by Nikolenko Oleh on 07.03.2018.
 */
@Repository
public interface MySqlSalaryRepository extends JpaRepository<Salary, Long>{

    List<Salary> findAllBySalaryMonthAndSalaryYear(Month month, int year);
    Salary findOneBySalaryMonthAndSalaryYearAndEmployee(Month month, int year, Employee employee);
}
