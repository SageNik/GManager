package com.g_manager.service.impls;

import com.g_manager.entity.ClientCategory;
import com.g_manager.exception.ClientCategoryException;
import com.g_manager.repository.mysql.MySqlClientCategoryRepository;
import com.g_manager.service.ClientCategoryService;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Nikolenko Oleh on 29.01.2018.
 */
@RunWith(SpringRunner.class)
public class ClientCategoryServiceImplTest {

    @TestConfiguration
    static class ClientCategoryServiceImplTestContextConfiguration{
        @Bean
        ClientCategoryService clientCategoryService(){return new ClientCategoryServiceImpl();}
    }

    @Autowired
    private ClientCategoryService clientCategoryService;
    @MockBean
    private MySqlClientCategoryRepository mySqlClientCategoryRepository;


    public void setUp() {
        ClientCategory clientCategory = new ClientCategory();
        clientCategory.setCategory("categoryOne");
        ClientCategory clientCategoryTwo = new ClientCategory();
        clientCategoryTwo.setCategory("categoryTwo");
        ClientCategory clientCategoryThree = new ClientCategory();
        clientCategoryThree.setCategory("categoryThree");
        List<ClientCategory> allClientCategories = new ArrayList<>();
        allClientCategories.add(clientCategory);
        allClientCategories.add(clientCategoryTwo);
        allClientCategories.add(clientCategoryThree);
        Mockito.when(mySqlClientCategoryRepository.findAll()).thenReturn(allClientCategories);
    }

    @Test
    public void whenFindAll_thenReturnListClientCategory() throws ClientCategoryException{
        setUp();
        List<ClientCategory> found = clientCategoryService.findAllNotEmpty();

        assertThat(found).isNotNull();
        assertThat(found.size()).isEqualTo(3);
        assertThat(found.get(1).getCategory()).isEqualTo("categoryTwo");
    }

    @Test(expected = ClientCategoryException.class)
    public void whenNotFindAny_thenThrowClientCategoryException()throws ClientCategoryException{
        Mockito.when(mySqlClientCategoryRepository.findAll()).thenReturn(Lists.emptyList());

        List<ClientCategory> found = clientCategoryService.findAllNotEmpty();
    }
}