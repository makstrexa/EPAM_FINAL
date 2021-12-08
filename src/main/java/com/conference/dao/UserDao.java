package com.conference.dao;

import com.conference.dao.exceptions.DAOException;
import com.conference.model.Event;
import com.conference.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public interface UserDao extends AbstractDao<User> {

    User getByLogin(String login) throws DAOException;

    Collection<User> getUsersByEvent(Event event) throws DAOException;

    void deleteUsersFromEvent(Event event) throws DAOException;

    void deleteUsersEvents(User user) throws DAOException;

    String present(Event event, User user) throws DAOException;

    void presentUpdate(Event event, User user) throws DAOException;

}
