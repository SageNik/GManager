package com.g_manager.service.impls;

import com.g_manager.entity.Client;
import com.g_manager.repository.mysql.MySqlClientRepository;
import com.g_manager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Nikolenko Oleh on 12.12.2017.
 */
@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
   private MySqlClientRepository clientRepository;

    @Override
    public Client save(Client entity) {
        return clientRepository.save(entity);
    }

    @Override
    public Client update(Client entity) {
        return clientRepository.save(entity);
    }

    @Override
    public void delete(Client entity) {
        clientRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        clientRepository.delete(id);
    }

    @Override
    public void deleteInBatch(List<Client> entities) {
        clientRepository.deleteInBatch(entities);
    }

    @Override
    public Client findOne(Long id) {
        return clientRepository.findOne(id);
    }

    @Override
    public List<Client> findAll() {

        Sort sort = new Sort(Sort.Direction.ASC, "fullName");
        return clientRepository.findAll(sort);
    }

    @Override
    public Client findByPhone(String phone) {
        return clientRepository.findByPhone(phone);
    }

    @Override
    public Client findByFullName(String fullName) {
        return clientRepository.findByFullName(fullName);
    }
}
