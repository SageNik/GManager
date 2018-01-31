package com.g_manager.service.impls;

import com.g_manager.entity.StaffCategory;
import com.g_manager.repository.mysql.MySqlStaffCategoryRepository;
import com.g_manager.service.StaffCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Nikolenko Oleh on 19.01.2018.
 */
@Service
public class StaffCategoryServiceImpl implements StaffCategoryService {

    @Autowired
    private MySqlStaffCategoryRepository mySqlStaffCategoryRepository;

    @Override
    public StaffCategory save(StaffCategory entity) {
        return mySqlStaffCategoryRepository.save(entity);
    }

    @Override
    public StaffCategory update(StaffCategory entity) {
        return mySqlStaffCategoryRepository.save(entity);
    }

    @Override
    public void delete(StaffCategory entity) {
        mySqlStaffCategoryRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        mySqlStaffCategoryRepository.delete(id);
    }

    @Override
    public void deleteInBatch(List<StaffCategory> entities) {
        mySqlStaffCategoryRepository.deleteInBatch(entities);
    }

    @Override
    public StaffCategory findOne(Long id) {
        return mySqlStaffCategoryRepository.findOne(id);
    }

    @Override
    public List<StaffCategory> findAll() {
        return mySqlStaffCategoryRepository.findAll();
    }
}
