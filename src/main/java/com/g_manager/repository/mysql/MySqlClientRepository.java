package com.g_manager.repository.mysql;

import com.g_manager.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Nikolenko Oleh on 12.12.2017.
 */
public interface MySqlClientRepository extends JpaRepository<Client, Long>{

    Client findByPhone (String phone);
    Client findByFullName(String fullName);
}
