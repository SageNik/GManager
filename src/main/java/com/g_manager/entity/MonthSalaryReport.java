package com.g_manager.entity;

import com.g_manager.entity.base.BaseEntity;
import com.g_manager.enums.Months;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

/**
 * Created by Nikolenko Oleh on 21.02.2018.
 */
@Data
@Entity
@Table(name = "month_salary_report")
public class MonthSalaryReport extends BaseEntity{

    private Months payMonth;
    private Integer payYear;
//    private List<Employee> employees;
}
