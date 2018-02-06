package com.g_manager.service;

import com.g_manager.entity.StaffCategory;
import com.g_manager.exception.StaffCategoryException;

import java.util.List;

/**
 * Created by Nikolenko Oleh on 19.01.2018.
 */
public interface StaffCategoryService extends BaseService<StaffCategory>{
    List<StaffCategory> findAllNotEmpty() throws StaffCategoryException;
}
