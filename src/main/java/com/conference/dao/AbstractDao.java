package com.conference.dao;

import com.conference.dao.exceptions.DAOException;

import java.util.Collection;

public interface AbstractDao<T> {

    T get(Integer id) throws DAOException;

    Collection<T> findAll() throws DAOException;

    void insert(T entity, boolean generateId) throws DAOException;

    void delete(T entity) throws DAOException;

    void update(T entity) throws DAOException;

}
