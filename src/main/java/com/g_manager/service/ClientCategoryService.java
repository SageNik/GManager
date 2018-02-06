package com.g_manager.service;

import com.g_manager.entity.ClientCategory;
import com.g_manager.exception.ClientCategoryException;

import java.util.List;

/**
 * Created by Nikolenko Oleh on 17.12.2017.
 */

public interface ClientCategoryService extends BaseService<ClientCategory>{

    List<ClientCategory> findAllNotEmpty() throws ClientCategoryException;
}
