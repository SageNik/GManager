package com.g_manager.service.impls;

import com.g_manager.entity.ClientCategory;
import com.g_manager.repository.mysql.MySqlClientCategoryRepository;
import com.g_manager.service.ClientCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Nikolenko Oleh on 17.12.2017.
 */
@Service
public class ClientCategoryServiceImpl implements ClientCategoryService{

    @Autowired
    MySqlClientCategoryRepository clientCategoryRepository;

    @Override
    public ClientCategory save(ClientCategory entity) {
        return clientCategoryRepository.save(entity);
    }

    @Override
    public ClientCategory update(ClientCategory entity) {
        return clientCategoryRepository.save(entity);
    }

    @Override
    public void delete(ClientCategory entity) {
        clientCategoryRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        clientCategoryRepository.delete(id);
    }

    @Override
    public void deleteInBatch(List<ClientCategory> entities) {
        clientCategoryRepository.deleteInBatch(entities);
    }

    @Override
    public ClientCategory findOne(Long id) {
        return clientCategoryRepository.findOne(id);
    }

    @Override
    public List<ClientCategory> findAll() {
        return clientCategoryRepository.findAll();
    }
}
