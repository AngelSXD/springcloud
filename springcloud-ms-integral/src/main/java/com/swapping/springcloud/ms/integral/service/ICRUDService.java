package com.swapping.springcloud.ms.integral.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

public interface ICRUDService<T> {

    public T insert(T entity);

    public T update(T entity);

    public boolean delete(T entity);

    public Page<T> pageFind(T entity);

    public Page<T> pageSortFind(T entity, Map<String, Sort.Direction> map);

    public List<T> find(T entity);

    public List<T> findAll();
}

