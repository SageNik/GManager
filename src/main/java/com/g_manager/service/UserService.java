package com.g_manager.service;

import com.g_manager.entity.User;

/**
 * Created by Nikolenko Oleh on 04.12.2017.
 */
public interface UserService extends BaseService<User> {

        boolean authenticate (String username, String password);
        User findByLogin (String login);
}
