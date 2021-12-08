package com.conference.dao;

import com.conference.dao.exceptions.DAOException;
import com.conference.model.Event;
import com.conference.model.User;

import java.util.Collection;

public interface EventDao extends AbstractDao<Event> {

    Integer getId() throws DAOException;

    Collection<Event> getEventsByUser(User user) throws DAOException;

    Collection<Event> findAll(int offset, int noOfRecords) throws DAOException;

    void setEventsForUser(User user, Event... events) throws DAOException;

    void deleteUserFromEvents(User user, Event... events) throws DAOException;

}
