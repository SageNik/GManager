package com.g_manager.repository.mysql;

import com.g_manager.entity.ClientCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Nikolenko Oleh on 17.12.2017.
 */
@Repository
public interface MySqlClientCategoryRepository extends JpaRepository<ClientCategory, Long>{
}
