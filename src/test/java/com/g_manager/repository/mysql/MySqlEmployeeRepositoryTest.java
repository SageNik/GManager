package com.g_manager.repository.mysql;

import com.g_manager.entity.Employee;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Nikolenko Oleh on 06.02.2018.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class MySqlEmployeeRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private MySqlEmployeeRepository mySqlEmployeeRepository;

    private String phone = "555";
    private String fullName = "Ivanov Ivan Ivanovich";

    public void setUp(){
        Employee employee = new Employee();
        employee.setPhone(phone);
        employee.setFullName(fullName);
        testEntityManager.persist(employee);
        testEntityManager.flush();
    }

    @Test
    public void whenFindByPhone_thenReturnFoundEmployee(){
        setUp();
        Employee found = mySqlEmployeeRepository.findByPhone(phone);

        assertThat(found.getFullName()).isEqualTo(fullName);
        assertThat(found.getPhone()).isEqualTo(phone);
    }

    @Test
    public void whenNotFoundByPhone_thenReturnNull(){
        Employee found = mySqlEmployeeRepository.findByPhone(phone);

        assertThat(found).isNull();
    }

    @Test
    public void whenFindByFullname_thenReturnFoundEmployee(){
        setUp();
        Employee found = mySqlEmployeeRepository.findByFullName(fullName);

        assertThat(found.getFullName()).isEqualTo(fullName);
        assertThat(found.getPhone()).isEqualTo(phone);
    }

    @Test
    public void whenNotFoundByFullName_thenReturnNull(){
        Employee found = mySqlEmployeeRepository.findByFullName(fullName);

        assertThat(found).isNull();
    }

    @Test
    public void whenFindById_thenReturnFoundEmployee(){
        setUp();
        LinkedList<Employee> employees = new LinkedList<Employee>(mySqlEmployeeRepository.findAll());
        Long current_id = employees.getLast().getId();
        Employee found = mySqlEmployeeRepository.findOne(current_id);

        assertThat(found.getFullName()).isEqualTo(fullName);
        assertThat(found.getPhone()).isEqualTo(phone);
    }

    @Test
    public void whenNotFoundById_thenReturnNull(){
        Employee found = mySqlEmployeeRepository.findOne(Long.MAX_VALUE);

        assertThat(found).isNull();
    }

    @Test
    public void whenFindAll_thenReturnFoundEmployees(){
        Employee employee = new Employee();
        employee.setPhone("111");
        Employee employeeTwo = new Employee();
        employeeTwo.setPhone("222");
        Employee employeeThree = new Employee();
        employeeThree.setPhone("333");
        testEntityManager.persist(employee);
        testEntityManager.persist(employeeTwo);
        testEntityManager.persist(employeeThree);
        testEntityManager.flush();

        List<Employee> found = mySqlEmployeeRepository.findAll();

        assertThat(found.size()).isEqualTo(3);
        assertThat(found.get(1).getPhone()).isEqualTo("222");
    }

    @Test
    public void whenNotFoundAny_thenReturnEmptyList(){
        List<Employee> found = mySqlEmployeeRepository.findAll();

        assertThat(found).isEqualTo(Lists.emptyList());
    }
}
