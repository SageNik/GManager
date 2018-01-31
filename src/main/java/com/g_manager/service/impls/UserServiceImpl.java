package com.g_manager.service.impls;

import com.g_manager.entity.User;
import com.g_manager.repository.mysql.MySqlUserRepository;
import com.g_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Nikolenko Oleh on 04.12.2017.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MySqlUserRepository userRepository;

    @Override
    public User save(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public User update(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public void delete(User entity) {
        userRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }

    @Override
    public void deleteInBatch(List<User> entities) {
        userRepository.deleteInBatch(entities);
    }

    @Override
    public User findOne(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean authenticate(String username, String password) {
       User user = this.findByLogin(username);
        if(user == null){
            return false;
        }else{
            if(user.getPassword().equals(password)) return true;
            else return false;
        }
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
