package com.g_manager.service.impls;

import com.g_manager.entity.Employee;
import com.g_manager.repository.mysql.MySqlEmployeeRepository;
import com.g_manager.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Nikolenko Oleh on 21.01.2018.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private MySqlEmployeeRepository mySqlEmployeeRepository;

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

    @Override
    public Employee findOne(Long id) {
        return mySqlEmployeeRepository.findOne(id);
    }

    @Override
    public List<Employee> findAll() {

        Sort sort = new Sort(Sort.Direction.ASC, "fullName");
        return mySqlEmployeeRepository.findAll(sort);
    }

    @Override
    public Employee findByPhone(String phone) {
        return mySqlEmployeeRepository.findByPhone(phone);
    }

    @Override
    public Employee findByFullName(String fullName) {
        return mySqlEmployeeRepository.findByFullName(fullName);
    }
}
