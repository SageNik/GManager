package com.g_manager.repository.mysql;

import com.g_manager.entity.StaffCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Nikolenko Oleh on 19.01.2018.
 */
public interface MySqlStaffCategoryRepository extends JpaRepository<StaffCategory, Long> {
}
