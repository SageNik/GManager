package com.g_manager.service;

import com.g_manager.entity.Client;

/**
 * Created by Nikolenko Oleh on 12.12.2017.
 */
public interface ClientService extends BaseService<Client> {

    Client findByPhone(String phone);
    Client findByFullName (String fullName);
}
