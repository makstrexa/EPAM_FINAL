package com.conference.services;

import com.conference.dao.DaoFactory;
import com.conference.dao.exceptions.DAOException;
import com.conference.model.Event;
import com.conference.model.User;
import com.conference.model.UserRole;

import java.util.Collection;
import java.util.Set;

public class UserServiceImpl implements UserService {

    DaoFactory daoFactory;

    private static final String salt = "xcgLd+WCG6DXBSliq83x7mLV8Ln1pU32r34HpPI3jOWfc0QXJIRAc/" +
            "6tnol+IgSWhl9Vb4yFARHX1lE17f8SgR26vVehUfdDIgCLt73Zguh7tgFT9v" +
            "igOC2nm7K+lZaku6kBGwms4X6s552g/s0+fKFysEu9yuucmBqo8/bNX5Q4iB" +
            "gtD+MfHjkf+GLYr8b6fhSv35erdVK7To8MRutTuYUs/8GEO55iI9QjdahxAt" +
            "EYgi9sfBZUBfHJ6kE28LdlxLMwekUTlmODPq3M3jqJeft7nqLTFt+WRI/AKL" +
            "KKVC4Iwc7gqM9Pcli0xgP+7fLULSQULjcm9zRA+dS3oHbKPO0c/UnGTpFl9j" +
            "fVsBqfOeufjrxXLN7Hr9lAzk3/fMH7CJNtxgUn9ismV5S+ZhEyPr67wOZT3s" +
            "nD6dH39rR9xOn/9N/S8SM1BtgL7IusU0lhT3bIABQky1mj0hlvSDDiWVZfoW" +
            "OdQuyiHeW4Kpe6a2pQitomD3vOzGzWhrFO1FnLnpw0z45fV5assd4k2NEEgY" +
            "C59TdtSNi6otYyqqM+gWw6koNXxlGoynwhW8DiEjz/9MVf6wAzu/MDh8riHa" +
            "QF3iGtMu+jrScrDiwJjgQmDT1zrY4bUxoJoNeztXJWk7DkuH3hm+XgI8VOnb" +
            "ZOw0XEPCrzlUDvpS5/VMvj1l5oZ54=";

    public UserServiceImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public User getByLogin(String login) throws DAOException {
        User user = daoFactory.getUserDao().getByLogin(login);
        user.setEvents((Set<Event>) daoFactory.getEventDao().getEventsByUser(user));
        return user;
    }

    @Override
    public User get(Integer id) throws DAOException {
        User user = daoFactory.getUserDao().get(id);
        user.setEvents((Set<Event>) daoFactory.getEventDao().getEventsByUser(user));
        return user;
    }

    @Override
    public Collection<User> findAll() throws DAOException {
        Collection<User> users = daoFactory.getUserDao().findAll();
        for (User user: users) {
            user.setEvents((Set<Event>) daoFactory.getEventDao().getEventsByUser(user));
        }
        return users;
    }

    @Override
    public Collection<User> getUsersByEvent(Event event) throws DAOException {
        Collection<User> users = daoFactory.getUserDao().getUsersByEvent(event);
        for (User user: users) {
            user.setEvents((Set<Event>) daoFactory.getEventDao().getEventsByUser(user));
        }
        return users;
    }

    @Override
    public boolean checkPassword(User user, String password) {
        return PasswordHasher.verifyPassword(password, user.getPasswordHash(), salt);
    }

    @Override
    public void registrateUser(String name, String surname,
                               String login, String email,
                               String password, UserRole role) throws DAOException {
        daoFactory.getUserDao().insert(new User(0, name,
                surname, login, email, PasswordHasher.hashPassword(password, salt).get(), role), true);
    }

    @Override
    public void registrateToEvent(User user, Event... event) throws DAOException {
        daoFactory.getEventDao().setEventsForUser(user, event);
    }

    @Override
    public void deleteUsersFromEvent(Event event) throws DAOException {
        daoFactory.getUserDao().deleteUsersFromEvent(event);
    }

    @Override
    public void deleteUsersEvents(User user) throws DAOException {
        daoFactory.getUserDao().deleteUsersEvents(user);
    }

    @Override
    public void updateUser(User user) throws DAOException {
        daoFactory.getUserDao().update(user);
    }

    @Override
    public void deleteUser(User user) throws DAOException {
        daoFactory.getUserDao().delete(user);
    }

    @Override
    public void insertUser(User user) throws DAOException {
        daoFactory.getUserDao().insert(user, true);
    }

    @Override
    public boolean loginCheckAndEmail(String login, String email) throws DAOException {
        return daoFactory.getUserDao().findAll().stream()
                .anyMatch(user -> user.getEmail().equals(email)
                        && user.getLogin().equals(login)
                        || user.getLogin().equals(login)
                        || user.getEmail().equals(email));
    }

    @Override
    public String present(Event event, User user) throws DAOException {
        return daoFactory.getUserDao().present(event, user);
    }

    @Override
    public void presentUpdate(Event event, User user) throws DAOException {
        daoFactory.getUserDao().presentUpdate(event, user);
    }
}
