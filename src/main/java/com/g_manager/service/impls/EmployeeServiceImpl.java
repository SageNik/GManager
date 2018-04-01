package com.g_manager.service.impls;

import com.g_manager.entity.Employee;
import com.g_manager.entity.Salary;
import com.g_manager.enums.EmployeeStatus;
import com.g_manager.repository.mysql.MySqlEmployeeRepository;
import com.g_manager.service.EmployeeService;
import com.g_manager.service.SalaryService;
import com.g_manager.utils.SimpleDialogManager;
import javafx.event.ActionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Nikolenko Oleh on 21.01.2018.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private MySqlEmployeeRepository mySqlEmployeeRepository;
    @Autowired
    private SalaryService salaryService;
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("Bundle");

    @Override
    public Employee save(Employee entity) {

        return mySqlEmployeeRepository.save(entity);
    }

    @Override
    public Employee update(Employee entity) {
        return mySqlEmployeeRepository.save(entity);
    }

    @Override
    public void delete(Employee entity) {
        mySqlEmployeeRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        mySqlEmployeeRepository.delete(id);
    }

    @Override
    public void deleteInBatch(List<Employee> entities) {
        mySqlEmployeeRepository.deleteInBatch(entities);
    }

    @Transactional
    @Override
    public Employee findOne(Long id) {
        Employee employee = mySqlEmployeeRepository.findOne(id);
        if (employee != null) initFields(employee);
        return employee;
    }

    private void initFields(Employee employee) {
        employee.getSalaries().iterator();
        employee.getPersonalClients().iterator();
    }

    @Override
    public List<Employee> findAll() {

        Sort sort = new Sort(Sort.Direction.ASC, "fullName");
        return mySqlEmployeeRepository.findAll(sort);
    }

    @Override
    public List<Employee> findAllByStatus(EmployeeStatus status) {
        Sort sort = new Sort(Sort.Direction.ASC, "fullName");
        return mySqlEmployeeRepository.findAllByStatus(status, sort);
    }

    @Transactional
    @Override
    public List<Employee> findAllByMonthSalaryDate(LocalDate monthSalaryDate) {
        List<Employee> employees = new ArrayList<>();
        List<Salary> salariesByMonthSalaryDate = salaryService.getAllByMonthSalaryDate(monthSalaryDate);
        if (salariesByMonthSalaryDate != null) {
            for (Salary salary : salariesByMonthSalaryDate) {
                employees.add(findOne(salary.getEmployee().getId()));
            }
        }
        return employees;
    }

    @Override
    public Employee findByPhoneAndStatus(String phone, EmployeeStatus status) {
        Employee employee = mySqlEmployeeRepository.findByPhoneAndStatus(phone.trim(), status);
        if (employee != null) initFields(employee);
        return employee;
    }

    @Override
    public Employee findByFullNameAndStatus(String fullName, EmployeeStatus status) {
        Employee employee = mySqlEmployeeRepository.findByFullNameAndStatus(fullName, status);
        if (employee != null) initFields(employee);
        return employee;
    }

    @Transactional
    @Override
    public boolean checkAndSave(ActionEvent event, Employee employee) {
        boolean isSaved = false;
        Employee existEmployee = findByPhoneAndStatus(employee.getPhone(), EmployeeStatus.EMPLOYED);

        if ((existEmployee != null && employee.getId() == null) || (existEmployee != null && !existEmployee.getId().equals(employee.getId()))) {
            SimpleDialogManager.showErrorDialog(resourceBundle.getString("save.error"),
                    resourceBundle.getString("employee.exist.phone.error") + ": " + existEmployee.getFullName());
        } else {
            existEmployee = findByFullNameAndStatus(employee.getFullName(), EmployeeStatus.EMPLOYED);
            if ((existEmployee != null && employee.getId() == null) || (existEmployee != null && !existEmployee.getId().equals(employee.getId()))) {
                SimpleDialogManager.showErrorDialog(resourceBundle.getString("save.error"),
                        resourceBundle.getString("employee.exist.name.error"));

            } else {
                save(employee);
                isSaved = true;
            }
        }
        return isSaved;
    }

}
