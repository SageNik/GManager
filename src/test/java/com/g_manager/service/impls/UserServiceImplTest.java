package com.g_manager.service.impls;

import com.g_manager.entity.User;
import com.g_manager.repository.mysql.MySqlUserRepository;
import com.g_manager.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Nikolenko Oleh on 01.02.2018.
 */
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration{

        @Bean
        public UserService userService(){
            return new UserServiceImpl();
        }
    }

    @Autowired
    private UserService userService;
    @MockBean
    private MySqlUserRepository mySqlUserRepository;

    @Before
    public void setUp(){
        User bob = new User();
        bob.setLogin("Bobby");
        bob.setPassword("123");
        Mockito.when(mySqlUserRepository.findByLogin(bob.getLogin())).thenReturn(bob);
    }

    @Test
    public void whenValidLogin_thenUserShouldBeFound(){
        String login = "Bobby";
        User found = userService.findByLogin(login);

        assertThat(found.getLogin()).isEqualTo(login);
    }

    @Test
    public void whenNotValidLogin_thenReturnNull(){
        String login = "Ivan";
        User found = userService.findByLogin(login);

        assertThat(found).isEqualTo(null);
    }

    @Test
    public void whenAuthenticate_thenReturnTrue(){
        String login = "Bobby";
        String password = "123";
        Boolean authenticate = userService.authenticate(login,password);

        assertThat(authenticate).isEqualTo(true);
    }

    @Test
    public void whenNotAuthenticate_thenReturnFalse(){
        String login = "Bobby";
        String password = "wrongPassword";
        Boolean authenticate = userService.authenticate(login,password);

        assertThat(authenticate).isEqualTo(false);
    }
}
