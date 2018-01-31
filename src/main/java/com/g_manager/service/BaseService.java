package com.g_manager.service;

import java.util.List;

/**
 * Created by Nikolenko Oleh on 04.12.2017.
 */
public interface BaseService<T extends Object> {

    T save(T entity);

    T update(T entity);

    void delete(T entity);

    void delete(Long id);

    void deleteInBatch(List<T> entities);

    T findOne(Long id);

    List<T> findAll();
}
