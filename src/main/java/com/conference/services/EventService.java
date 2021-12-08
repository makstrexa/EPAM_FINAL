package com.conference.services;

import com.conference.dao.exceptions.DAOException;
import com.conference.model.Event;
import com.conference.model.User;

import java.util.Collection;

public interface EventService {

    Event get(Integer id) throws DAOException;

    Collection<Event> findAll() throws DAOException;

    Collection<Event> findAllActive(EventSortCriteria eventSortCriteria) throws DAOException;

    Collection<Event> findAll(EventSortCriteria eventSortCriteria) throws DAOException;

    Collection<Event> getEventsByUser(User user) throws DAOException;

    void deleteUserFromEvents(User user, Event... events) throws DAOException;

    void insert(Event event) throws DAOException;

    void delete(Event event) throws DAOException;

    void update(Event event) throws DAOException;

    public Collection<Event> search(String text, EventSortCriteria eventSortCriteria, String active) throws DAOException;

}
