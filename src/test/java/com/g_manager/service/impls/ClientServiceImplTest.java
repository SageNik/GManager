package com.g_manager.service.impls;

import com.g_manager.entity.Client;
import com.g_manager.repository.mysql.MySqlClientRepository;
import com.g_manager.service.ClientService;
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
public class ClientServiceImplTest {

    @TestConfiguration
    static class ClientServiceImplTestContextConfiguration{
        @Bean
        ClientService clientService(){return new ClientServiceImpl();}
    }
    @Autowired
    private ClientService clientService;
    @MockBean
    private MySqlClientRepository mySqlClientRepository;

    private String phone = "555";
    private String fullName = "Ivanov Ivan Ivanovich";
    private Client client;

    @Before
    public void setUp(){

        client = new Client();
        client.setPhone(phone);
        client.setFullName(fullName);

    }

    @Test
    public void whenFindByPhone_thenReturnFoundClient(){
        Mockito.when(mySqlClientRepository.findByPhone(phone)).thenReturn(client);
        Client found = clientService.findByPhone(phone);

        assertThat(found).isNotNull();
        assertThat(found.getFullName()).isEqualTo(fullName);
        assertThat(found.getPhone()).isEqualTo(phone);
    }

    @Test
    public void whenNotFoundByPhone_thenReturnNull(){
        Client found = clientService.findByPhone("Not Correct Phone");

        assertThat(found).isNull();
    }

    @Test
    public void whenFindByFullName_thenReturnFoundClient(){
        Mockito.when(mySqlClientRepository.findByFullName(fullName)).thenReturn(client);
        Client found = clientService.findByFullName(fullName);

        assertThat(found).isNotNull();
        assertThat(found.getFullName()).isEqualTo(fullName);
        assertThat(found.getPhone()).isEqualTo(phone);
    }

    @Test
    public void whenNotFoundByFullName_thenReturnNull(){
        Client found = clientService.findByFullName("Not Correct Full name");

        assertThat(found).isNull();
    }

    @Test
    public void whenFindOne_thenReturnClient(){
        Mockito.when(mySqlClientRepository.findOne(3L)).thenReturn(client);
        Client found = clientService.findOne(3L);

        assertThat(found).isNotNull();
        assertThat(found.getFullName()).isEqualTo(fullName);
        assertThat(found.getPhone()).isEqualTo(phone);
    }

    @Test
    public void whenNotFoundById_thenReturnNull(){
        Client found = clientService.findOne(1L);

        assertThat(found).isNull();
    }

    @Test
    public void whenFindAll_thenReturnAllClients(){
        Client clientTwo = new Client();
        clientTwo.setPhone("222");
        clientTwo.setFullName("Ivan");
        List<Client> clients = new ArrayList<>();
        clients.add(client);
        clients.add(clientTwo);
        Sort sort = new Sort(Sort.Direction.ASC, "fullName");

        Mockito.when(mySqlClientRepository.findAll(sort)).thenReturn(clients);
        List<Client> found = clientService.findAll();

        assertThat(found).isNotEmpty();
        assertThat(found.size()).isEqualTo(2);
        assertThat(found.get(1).getPhone()).isEqualTo("222");
        assertThat(found.get(1).getFullName()).isEqualTo("Ivan");
    }

    @Test
    public void whenNotFoundAny_thenReturnEmptyList(){
        List<Client> found = clientService.findAll();

        assertThat(found).isNotNull();
        assertThat(found).isEmpty();
    }
}
