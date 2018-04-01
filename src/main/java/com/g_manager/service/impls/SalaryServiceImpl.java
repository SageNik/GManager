package com.g_manager.service.impls;

import com.g_manager.entity.Bonus;
import com.g_manager.entity.Employee;
import com.g_manager.entity.Penalty;
import com.g_manager.entity.Salary;
import com.g_manager.repository.mysql.MySqlSalaryRepository;
import com.g_manager.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Nikolenko Oleh on 07.03.2018.
 */
@Service
public class SalaryServiceImpl implements SalaryService{

    @Autowired
    private MySqlSalaryRepository mySqlSalaryRepository;

    @Override
    public Salary save(Salary entity) {
        return mySqlSalaryRepository.save(entity);
    }

    @Override
    public Salary update(Salary entity) {
        return mySqlSalaryRepository.save(entity);
    }

    @Override
    public void delete(Salary entity) {
        mySqlSalaryRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        mySqlSalaryRepository.delete(id);
    }

    @Override
    public void deleteInBatch(List<Salary> entities) {
        mySqlSalaryRepository.deleteInBatch(entities);
    }

    @Override
    public Salary findOne(Long id) {
        Salary salary = mySqlSalaryRepository.findOne(id);
        initFields(salary);
        return salary;
    }

    private void initFields(Salary salary) {
        salary.getBonuses().iterator();
        salary.getPenalties().iterator();
    }

    @Override
    public List<Salary> findAll() {
        return mySqlSalaryRepository.findAll();
    }


    @Override
    public List<Salary> getAllByMonthSalaryDate(LocalDate date) {
        return mySqlSalaryRepository.findAllBySalaryMonthAndSalaryYear(date.getMonth(), date.getYear());
    }

    @Transactional
    @Override
    public Salary getOneByMonthSalaryDateAndEmployee(LocalDate date, Employee employee) {
        Salary salary = mySqlSalaryRepository.findOneBySalaryMonthAndSalaryYearAndEmployee(date.getMonth(),date.getYear(),employee);
       initFields(salary);
        return salary;
    }

    @Transactional
    @Override
    public BigDecimal getFullCurrentSalary(LocalDate date, Employee employee){
        BigDecimal fullSalary = employee.getRate();

        Salary currentSalary = getOneByMonthSalaryDateAndEmployee(date,employee);
        if(currentSalary!=null && !currentSalary.getBonuses().isEmpty()){
            for (Bonus bonus: currentSalary.getBonuses()) {
                fullSalary = fullSalary.add(bonus.getAmount());
            }
        }
        if(currentSalary != null && !currentSalary.getPenalties().isEmpty()){
            for(Penalty penalty: currentSalary.getPenalties()){
                fullSalary = fullSalary.subtract(penalty.getAmount());
            }
        }
        return fullSalary;
    }
}
