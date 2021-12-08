package com.conference.services;

import com.conference.dao.exceptions.DAOException;
import com.conference.model.Event;
import com.conference.model.User;
import com.conference.model.UserRole;

import java.util.Collection;

public interface UserService {

    User getByLogin(String login) throws DAOException;

    User get(Integer id) throws DAOException;

    Collection<User> findAll() throws DAOException;

    Collection<User> getUsersByEvent(Event event) throws DAOException;

    boolean checkPassword(User user, String password);

    void registrateUser(String name, String surname, String login,
                        String email, String password, UserRole role) throws DAOException;

    void registrateToEvent(User user, Event... event) throws DAOException;

    void deleteUsersFromEvent(Event event) throws DAOException;

    void deleteUsersEvents(User user) throws DAOException;

    void updateUser(User user) throws DAOException;

    void deleteUser(User user) throws DAOException;

    void insertUser(User user) throws DAOException;

    boolean loginCheckAndEmail(String login, String email) throws DAOException;

    String present(Event event, User user) throws DAOException;

    void presentUpdate(Event event, User user) throws DAOException;
}
