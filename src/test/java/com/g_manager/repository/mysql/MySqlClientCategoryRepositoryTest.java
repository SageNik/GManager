package com.g_manager.repository.mysql;

import com.g_manager.entity.ClientCategory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Nikolenko Oleh on 02.02.2018.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class MySqlClientCategoryRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    MySqlClientCategoryRepository mySqlClientCategoryRepository;

    public void setUp() {
        ClientCategory clientCategory = new ClientCategory();
        clientCategory.setCategory("categoryOne");
        ClientCategory clientCategoryTwo = new ClientCategory();
        clientCategoryTwo.setCategory("categoryTwo");
        ClientCategory clientCategoryThree = new ClientCategory();
        clientCategoryThree.setCategory("categoryThree");
        testEntityManager.persist(clientCategory);
        testEntityManager.persist(clientCategoryTwo);
        testEntityManager.persist(clientCategoryThree);
        testEntityManager.flush();
    }

    @Test
    public void whenFindAll_thenReturnList(){
        setUp();
        List<ClientCategory> found = mySqlClientCategoryRepository.findAll();

        assertThat(found.size()).isEqualTo(3);
        assertThat(found.get(1).getCategory()).isEqualTo("categoryTwo");
    }

    @Test
    public void whenNotFindAny_thenReturnEmptyList(){
        List<ClientCategory> found = mySqlClientCategoryRepository.findAll();

        assertThat(found).isEmpty();
    }
}
