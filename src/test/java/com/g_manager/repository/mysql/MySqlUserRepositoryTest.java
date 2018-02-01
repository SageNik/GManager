package com.g_manager.repository.mysql;

import com.g_manager.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Nikolenko Oleh on 31.01.2018.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class MySqlUserRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    MySqlUserRepository mySqlUserRepository;

    private User bob;

    @Before
    public void setUp() {
        //given
        bob = new User();
        bob.setLogin("Bobby");
        bob.setPassword("123");
        testEntityManager.persist(bob);
        testEntityManager.flush();
    }

    @Test
    public void whenFindByLogin_thenReturnUser() {

        //when
        User found = mySqlUserRepository.findByLogin(bob.getLogin());
        //then
        assertThat(found.getLogin()).isEqualTo(bob.getLogin());
        assertThat(found.getPassword()).isEqualTo(bob.getPassword());
    }

    @Test
    public void whenNotFindByLogin_thenReturnNull() {

        //when
        User found = mySqlUserRepository.findByLogin("wrongLogin");
        //then
        assertThat(found).isEqualTo(null);
    }

}
