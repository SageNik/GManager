package com.g_manager.repository.mysql;

import com.g_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Nikolenko Oleh on 04.12.2017.
 */
@Repository
public interface MySqlUserRepository extends JpaRepository<User, Long> {

    User findByLogin (String username);
}
