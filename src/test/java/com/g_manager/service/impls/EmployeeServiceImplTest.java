package com.g_manager.service.impls;

import com.g_manager.entity.Employee;
import com.g_manager.enums.EmployeeStatus;
import com.g_manager.repository.mysql.MySqlEmployeeRepository;
import com.g_manager.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
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

    private String phone = "555";
    private String fullName = "Ivanov Ivan Ivanovich";
    private Employee employee;

    @Before
    public void setUp(){

        employee = new Employee();
        employee.setPhone(phone);
        employee.setFullName(fullName);

    }

    @Test
    public void whenFindByPhone_thenReturnFoundEmployee(){
        Mockito.when(mySqlEmployeeRepository.findByPhoneAndStatus(phone, EmployeeStatus.EMPLOYED)).thenReturn(employee);
        Employee found = employeeService.findByPhoneAndStatus(phone,EmployeeStatus.EMPLOYED);

        assertThat(found).isNotNull();
        assertThat(found.getFullName()).isEqualTo(fullName);
        assertThat(found.getPhone()).isEqualTo(phone);
    }

    @Test
    public void whenNotFoundByPhone_thenReturnNull(){
        Employee found = employeeService.findByPhoneAndStatus("Not Correct Phone", EmployeeStatus.EMPLOYED);

        assertThat(found).isNull();
    }

    @Test
    public void whenFindByFullName_thenReturnFoundEmployee(){
        Mockito.when(mySqlEmployeeRepository.findByFullNameAndStatus(fullName, EmployeeStatus.EMPLOYED)).thenReturn(employee);
        Employee found = employeeService.findByFullNameAndStatus(fullName, EmployeeStatus.EMPLOYED);

        assertThat(found).isNotNull();
        assertThat(found.getFullName()).isEqualTo(fullName);
        assertThat(found.getPhone()).isEqualTo(phone);
    }

    @Test
    public void whenNotFoundByFullName_thenReturnNull(){
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
}
