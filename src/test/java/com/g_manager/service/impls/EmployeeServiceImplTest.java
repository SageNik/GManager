package com.g_manager.service.impls;

import com.g_manager.entity.Employee;
import com.g_manager.entity.Salary;
import com.g_manager.enums.EmployeeStatus;
import com.g_manager.repository.mysql.MySqlEmployeeRepository;
import com.g_manager.service.EmployeeService;
import com.g_manager.service.SalaryService;
import com.g_manager.utils.SimpleDialogManager;
import javafx.event.ActionEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Nikolenko Oleh on 06.02.2018.
 */
@RunWith(SpringRunner.class)
public class EmployeeServiceImplTest {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration{
        @Bean
        EmployeeService employeeService(){return new EmployeeServiceImpl();}
    }
    @Autowired
    private EmployeeService employeeService;
    @MockBean
    private MySqlEmployeeRepository mySqlEmployeeRepository;
    @MockBean
    private SalaryService salaryService;
    @Mock
    private SimpleDialogManager simpleDialogManagerMock;

    private String phone = "555";
    private String fullName = "Ivanov Ivan Ivanovich";
    private Employee employee;

    @Before
    public void setUp(){

        employee = new Employee();
        employee.setId(1L);
        employee.setPhone(phone);
        employee.setFullName(fullName);
        employee.setSalaries(new ArrayList<>());
        employee.setPersonalClients(new HashSet<>());

    }

    @Test
    public void whenFindByPhoneAndStatus_thenReturnFoundEmployee(){
        Mockito.when(mySqlEmployeeRepository.findByPhoneAndStatus(phone, EmployeeStatus.EMPLOYED)).thenReturn(employee);
        Employee found = employeeService.findByPhoneAndStatus(phone,EmployeeStatus.EMPLOYED);

        assertThat(found).isNotNull();
        assertThat(found.getFullName()).isEqualTo(fullName);
        assertThat(found.getPhone()).isEqualTo(phone);
    }

    @Test
    public void whenNotFoundByPhoneAndSatus_thenReturnNull(){
        Employee found = employeeService.findByPhoneAndStatus("Not Correct Phone", EmployeeStatus.EMPLOYED);

        assertThat(found).isNull();
    }

    @Test
    public void whenFindByFullNameAndStatus_thenReturnFoundEmployee(){
        Mockito.when(mySqlEmployeeRepository.findByFullNameAndStatus(fullName, EmployeeStatus.EMPLOYED)).thenReturn(employee);
        Employee found = employeeService.findByFullNameAndStatus(fullName, EmployeeStatus.EMPLOYED);

        assertThat(found).isNotNull();
        assertThat(found.getFullName()).isEqualTo(fullName);
        assertThat(found.getPhone()).isEqualTo(phone);
    }

    @Test
    public void whenNotFoundByFullNameAndStatus_thenReturnNull(){
        Employee found = employeeService.findByFullNameAndStatus("Not Correct Full name", EmployeeStatus.EMPLOYED);

        assertThat(found).isNull();
    }

    @Test
    public void whenFindOne_thenReturnEmployee(){
        Mockito.when(mySqlEmployeeRepository.findOne(3L)).thenReturn(employee);
        Employee found = employeeService.findOne(3L);

        assertThat(found).isNotNull();
        assertThat(found.getFullName()).isEqualTo(fullName);
        assertThat(found.getPhone()).isEqualTo(phone);
    }

    @Test
    public void whenNotFoundById_thenReturnNull(){
        Employee found = employeeService.findOne(1L);

        assertThat(found).isNull();
    }

    @Test
    public void whenFindAll_thenReturnAllEmployees(){
        Employee employeeTwo = new Employee();
        employeeTwo.setPhone("222");
        employeeTwo.setFullName("Ivan");
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        employees.add(employeeTwo);
        Sort sort = new Sort(Sort.Direction.ASC, "fullName");

        Mockito.when(mySqlEmployeeRepository.findAll(sort)).thenReturn(employees);
        List<Employee> found = employeeService.findAll();

        assertThat(found).isNotEmpty();
        assertThat(found.size()).isEqualTo(2);
        assertThat(found.get(1).getPhone()).isEqualTo("222");
        assertThat(found.get(1).getFullName()).isEqualTo("Ivan");
    }

    @Test
    public void whenNotFoundAny_thenReturnEmptyList(){
        List<Employee> found = employeeService.findAll();

        assertThat(found).isNotNull();
        assertThat(found).isEmpty();
    }

    @Test
    public void whenFindAllByMonthSalaryDate_thenReturnFoundEmployees(){
        Employee employeeTwo = new Employee();
        LocalDate monthSalaryDate = LocalDate.now();
        prepareFindAllByMonthSalaryDateTest(employeeTwo, monthSalaryDate);

        List<Employee> found = employeeService.findAllByMonthSalaryDate(monthSalaryDate);

        assertThat(found).isNotEmpty();
        assertThat(found.size()).isEqualTo(2);
        assertThat(found.get(1).getPhone()).isEqualTo("222");
        assertThat(found.get(0).getPhone()).isEqualTo("555");
        assertThat(found.get(1).getFullName()).isEqualTo("Ivan");
    }

    @Test
    public void whenNotFindAnyByMonthSalaryDate_thenReturnEmptyList(){
        Employee employeeTwo = new Employee();
        LocalDate monthSalaryDate = LocalDate.now();
        prepareFindAllByMonthSalaryDateTest(employeeTwo, monthSalaryDate);

        List<Employee> found = employeeService.findAllByMonthSalaryDate(monthSalaryDate.plusYears(1));

        assertThat(found).isEmpty();
    }

    private void prepareFindAllByMonthSalaryDateTest(Employee employeeTwo, LocalDate monthSalaryDate) {
        employeeTwo.setId(3L);
        employeeTwo.setPhone("222");
        employeeTwo.setFullName("Ivan");
        employeeTwo.setPersonalClients(new HashSet<>());
        List<Employee> employees = new ArrayList<>();


        List<Salary> salaries = new ArrayList<>();
        Salary salaryOne = Salary.newInstance(employee);
        salaryOne.setSalaryMonth(monthSalaryDate.getMonth());
        salaryOne.setSalaryYear(monthSalaryDate.getYear());
        Salary salaryTwo = Salary.newInstance(employeeTwo);
        salaryTwo.setSalaryMonth(monthSalaryDate.getMonth());
        salaryTwo.setSalaryYear(monthSalaryDate.getYear());
        salaries.add(salaryOne);
        salaries.add(salaryTwo);

        employee.setSalaries(salaries);
        employeeTwo.setSalaries(salaries);
        employees.add(employee);
        employees.add(employeeTwo);

        Mockito.when(salaryService.getAllByMonthSalaryDate(monthSalaryDate)).thenReturn(salaries);
        Mockito.when(mySqlEmployeeRepository.findAllBySalaries(salaries)).thenReturn(employees);
        Mockito.when(mySqlEmployeeRepository.findOne(3L)).thenReturn(employeeTwo);
        Mockito.when(mySqlEmployeeRepository.findOne(1L)).thenReturn(employee);
    }

    @Test
    public void whenCheckAndSaveNotExistEmployee_thenReturnTrue(){
        ActionEvent event = new ActionEvent();
        Mockito.when(mySqlEmployeeRepository.findByPhoneAndStatus(phone, EmployeeStatus.EMPLOYED)).thenReturn(null);

        boolean result = employeeService.checkAndSave(event, employee);

        assertThat(result).isTrue();
    }

    @Test
    public void whenCheckAndSaveEditEmployee_thenReturnTrue(){
        ActionEvent event = new ActionEvent();
        Mockito.when(mySqlEmployeeRepository.findByPhoneAndStatus(phone, EmployeeStatus.EMPLOYED)).thenReturn(employee);

        boolean result = employeeService.checkAndSave(event, employee);

        assertThat(result).isTrue();
    }

//    @Test
//    public void whenNotCheckAndSave_thenReturnFalse(){
//        ActionEvent event = new ActionEvent();
//        Employee wrongEmployee = new Employee();
//        wrongEmployee.setId(0L);
//        wrongEmployee.setPhone(phone);
//        wrongEmployee.setFullName("Wrong Employee Name");
//        Mockito.when(mySqlEmployeeRepository.findByPhoneAndStatus(phone, EmployeeStatus.EMPLOYED)).thenReturn(employee);
//
//        boolean result = employeeService.checkAndSave(event, wrongEmployee);
//
//        assertThat(result).isFalse();
//    }
}
