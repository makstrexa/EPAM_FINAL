package com.conference.dao.implementations;

import com.conference.dao.UserDao;
import com.conference.dao.db.ConnectionPool;
import com.conference.dao.db.DBUtil;
import com.conference.dao.exceptions.DAOException;
import com.conference.model.Event;
import com.conference.model.User;
import com.conference.model.UserRole;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class InMemoryUserDao implements UserDao {

    private final ConnectionPool jdbcObj = new ConnectionPool();
    private DataSource dataSource;
    {
        try {
            dataSource = jdbcObj.setUpPool();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<User> getUsersByEvent(Event event) throws DAOException {

        String sql = "select u.*, r.Role from users u, events e, events_users eu, roles r " +
                "where e.id=eu.event_id and u.id = eu.user_id and role_id = r.id and event_id=?";
        Collection<User> users = new HashSet<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, event.getEventId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("login"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        UserRole.fromString(resultSet.getString("Role")));
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.close(connection, preparedStatement, resultSet);
        }
        return users;
    }

    @Override
    public User getByLogin(String login) throws DAOException {
        String sql = "select * from users u, roles r where u.role_id=r.id and u.login=?";
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getInt("u.id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("login"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        UserRole.fromString(resultSet.getString("Role")));
            }
        } catch (SQLException | NullPointerException throwables) {
            throw new DAOException("Problem in getByLogin()", throwables);
        } finally {
            DBUtil.close(connection, preparedStatement, resultSet);
        }
        return user;
    }

    @Override
    public User get(Integer id) throws DAOException {
        String sql = "select * from users u, roles r where u.role_id=r.id and u.id=?";

        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getInt("u.id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("login"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        UserRole.fromString(resultSet.getString("Role")));
            }
        } catch (SQLException | NullPointerException throwables) {
            throw new DAOException("Problem in get(Integer id) method", throwables);
        } finally {
            DBUtil.close(connection, preparedStatement, resultSet);
        }
        return user;
    }

    @Override
    public Collection<User> findAll() throws DAOException {
        String sql = "select * from users JOIN roles r on users.role_id = r.id";
        List<User> users = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("login"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        UserRole.fromString(resultSet.getString("Role")));
                users.add(user);
            }
        } catch (Exception e) {
            throw new DAOException("Problem in findAll() method in InMemoryUserDao", e);
        } finally {
            DBUtil.close(connection, preparedStatement, resultSet);
        }

        return users;
    }

    @Override
    public void insert(User entity, boolean generateId) throws DAOException{
        String sql = "insert into users(name, surname, login, email, password, role_id) " +
                "values(?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getSurname());
            preparedStatement.setString(3, entity.getLogin());
            preparedStatement.setString(4, entity.getEmail());
            preparedStatement.setString(5, entity.getPasswordHash());
            preparedStatement.setInt(6, entity.getRole().getRoleId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Problem in insert(User user) method", e);
        } finally {
            DBUtil.close(connection, preparedStatement);
        }
    }

    @Override
    public void delete(User entity) throws DAOException {
        String sql = "delete from users where id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, entity.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DAOException("Problem in deleteUser method", throwables);
        } finally {
            DBUtil.close(connection, preparedStatement);
        }
    }

    @Override
    public void update(User entity) throws DAOException {
        String sql = "update users set name=?, surname=?, login=?, email=?, password=?, role_id=? where id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getSurname());
            preparedStatement.setString(3, entity.getLogin());
            preparedStatement.setString(4, entity.getEmail());
            preparedStatement.setString(5, entity.getPasswordHash());
            preparedStatement.setInt(6, entity.getRole().getRoleId());
            preparedStatement.setInt(7, entity.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Problem in update(User user) method", e);
        } finally {
            DBUtil.close(connection, preparedStatement);
        }
    }

    @Override
    public void deleteUsersFromEvent(Event event) throws DAOException {
        String sql = "delete from events_users where event_id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, event.getEventId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DAOException("Problem in deleteUsersFromEvent", throwables);
        } finally {
            DBUtil.close(connection, preparedStatement);
        }
    }

    @Override
    public void deleteUsersEvents(User user) throws DAOException {
        String sql = "delete from events_users where user_id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DAOException("Problem in deleteUsersEvents", throwables);
        } finally {
            DBUtil.close(connection, preparedStatement);
        }
    }

    @Override
    public String present(Event event, User user) throws DAOException {
        String sql = "select present from events_users where user_id=? and event_id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.setInt(2, event.getEventId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("present");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.close(connection, preparedStatement, resultSet);
        }
        return null;
    }

    @Override
    public void presentUpdate(Event event, User user) throws DAOException {
        String sql = "update events_users set present='+' where user_id=? and event_id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.setInt(2, event.getEventId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.close(connection, preparedStatement, resultSet);
        }
    }
}
