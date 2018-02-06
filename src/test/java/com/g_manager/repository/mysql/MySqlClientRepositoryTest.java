package com.g_manager.repository.mysql;

import com.g_manager.entity.Client;
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
 * Created by Nikolenko Oleh on 05.02.2018.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class MySqlClientRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private MySqlClientRepository mySqlClientRepository;

    private String phone = "555";
    private String fullName = "Ivanov Ivan Ivanovich";

    public void setUp(){
        Client client = new Client();
        client.setPhone(phone);
        client.setFullName(fullName);
        testEntityManager.persist(client);
        testEntityManager.flush();
    }

    @Test
    public void whenFindByPhone_thenReturnFoundClient(){
        setUp();
        Client found = mySqlClientRepository.findByPhone(phone);

        assertThat(found.getFullName()).isEqualTo(fullName);
        assertThat(found.getPhone()).isEqualTo(phone);
    }

    @Test
    public void whenNotFoundByPhone_thenReturnNull(){
        Client found = mySqlClientRepository.findByPhone(phone);

        assertThat(found).isNotNull();
    }

    @Test
    public void whenFindByFullname_thenReturnFoundClient(){
        setUp();
        Client found = mySqlClientRepository.findByFullName(fullName);

        assertThat(found.getFullName()).isEqualTo(fullName);
        assertThat(found.getPhone()).isEqualTo(phone);
    }

    @Test
    public void whenNotFoundByFullName_thenReturnNull(){
        Client found = mySqlClientRepository.findByFullName(fullName);

        assertThat(found).isNull();
    }

    @Test
    public void whenFindById_thenReturnFoundClient(){
        setUp();
        LinkedList<Client> clients = new LinkedList<Client>(mySqlClientRepository.findAll());
        Long current_id = clients.getLast().getId();
        Client found = mySqlClientRepository.findOne(current_id);

        assertThat(found.getFullName()).isEqualTo(fullName);
        assertThat(found.getPhone()).isEqualTo(phone);
    }

    @Test
    public void whenNotFoundById_thenReturnNull(){
        Client found = mySqlClientRepository.findOne(Long.MAX_VALUE);

        assertThat(found).isNull();
    }

    @Test
    public void whenFindAll_thenReturnFoundClients(){
        Client client = new Client();
        client.setPhone("111");
        Client clientTwo = new Client();
        clientTwo.setPhone("222");
        Client clientThree = new Client();
        clientThree.setPhone("333");
        testEntityManager.persist(client);
        testEntityManager.persist(clientTwo);
        testEntityManager.persist(clientThree);
        testEntityManager.flush();

        List<Client> found = mySqlClientRepository.findAll();

        assertThat(found.size()).isEqualTo(3);
        assertThat(found.get(1).getPhone()).isEqualTo("222");
    }

    @Test
    public void whenNotFoundAny_thenReturnEmptyList(){
        List<Client> found = mySqlClientRepository.findAll();

        assertThat(found).isEqualTo(Lists.emptyList());
    }
}
