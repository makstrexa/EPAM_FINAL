package com.conference.dao.implementations;

import com.conference.dao.EventDao;
import com.conference.dao.db.ConnectionPool;
import com.conference.dao.db.DBManager;
import com.conference.dao.db.DBUtil;
import com.conference.dao.exceptions.DAOException;
import com.conference.model.Event;
import com.conference.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;



public class InMemoryEventDao implements EventDao {

    private static final Logger logger = LogManager.getLogger(InMemoryEventDao.class);

    ConnectionPool jdbcObj = new ConnectionPool();
    DataSource dataSource;
    {
        try {
            dataSource = jdbcObj.setUpPool();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final DBManager db= DBManager.getInstance();

    @Override
    public void setEventsForUser(User user, Event... events) throws DAOException {
        String sql = "insert into events_users(user_id, event_id) values (?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            for (Event event: events) {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, user.getUserId());
                preparedStatement.setInt(2, event.getEventId());
                preparedStatement.executeUpdate();
            }

        } catch (SQLException throwables) {
            throw new DAOException("Problem in setEventsForUser method", throwables);
        } finally {

            DBUtil.close(connection, preparedStatement);
        }
    }

    @Override
    public void deleteUserFromEvents(User user, Event... events) throws DAOException {
        String sql = "delete from events_users where user_id=? and event_id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            for (Event event: events) {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, user.getUserId());
                preparedStatement.setInt(2, event.getEventId());
                preparedStatement.executeUpdate();
            }

        } catch (SQLException throwables) {
            throw new DAOException("Problem in deleteUserFromEvents method", throwables);
        } finally {
            DBUtil.close(connection, preparedStatement);
        }
    }

    @Override
    public Event get(Integer id) throws DAOException {
        String sql = "select * from events where id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Event event = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                event = new Event(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        LocalDateTime.parse(resultSet.getString("date"), formatter),
                        resultSet.getLong("duration"),
                        resultSet.getString("place"));
            }
        } catch (Exception throwables) {
            logger.error("problem in method  Event get(Integer id)");
            throw new DAOException("Problem in Event get(Integer id) method", throwables);
        } finally {
            DBUtil.close(connection, preparedStatement, resultSet);
        }
        logger.info("worked out the method  Event get(Integer id)");
        return event;
    }

    @Override
    public Collection<Event> findAll() throws DAOException {
        String sql = "select * from events";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Collection<Event> events = new ArrayList<>();
        Event event;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                event = new Event(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        LocalDateTime.parse(resultSet.getString("date"), formatter),
                        resultSet.getLong("duration"),
                        resultSet.getString("place"));
                events.add(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, preparedStatement, resultSet);
        }

        return events;
    }

    @Override
    public Collection<Event> findAll(int offset, int noOfRecords) throws DAOException {
        String sql = "select * from events limit " + offset + ", " + noOfRecords;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Collection<Event> events = new HashSet<>();
        Event event;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                event = new Event(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        LocalDateTime.parse(resultSet.getString("date"), formatter),
                        resultSet.getLong("duration"),
                        resultSet.getString("place"));
                events.add(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, preparedStatement);
        }
        return events;
    }

    @Override
    public void insert(Event entity, boolean generateId) throws DAOException {
        String sql = "insert into events(title, description, date, place, duration) values (?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getTitle());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setString(3, entity.getDateTime().toString());
            preparedStatement.setString(4, entity.getPlace());
            preparedStatement.setInt(5, entity.getDuration().intValue());
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, preparedStatement);
        }
    }

    @Override
    public void delete(Event entity) throws DAOException {
        String sql = "delete from events where id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, entity.getEventId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Problem in delete event method", e);
        } finally {
            DBUtil.close(connection, preparedStatement);
        }
    }

    @Override
    public void update(Event entity) throws DAOException {
        String sql = "update events set title=?, description=?, date=?, place=?, duration=? where id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getTitle());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setString(3, entity.getDateTime().toString());
            preparedStatement.setString(4, entity.getPlace());
            preparedStatement.setInt(5, entity.getDuration().intValue());
            preparedStatement.setInt(6, entity.getEventId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Problem in update event method", e);
        } finally {
            DBUtil.close(connection, preparedStatement);
        }
    }

    @Override
    public Integer getId() throws DAOException {
        return null;
    }

    @Override
    public Collection<Event> getEventsByUser(User user) throws DAOException {
        String sqlGetUsersEvents = "select e.* from users u join events_users eu " +
                "on u.id = eu.user_id join events e on e.id = eu.event_id where u.id=?";
        Collection<Event> events = new HashSet<>();
        Event event;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sqlGetUsersEvents);
            preparedStatement.setInt(1, user.getUserId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                event = new Event(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        LocalDateTime.parse(resultSet.getString("date"), formatter),
                        resultSet.getLong("duration"),
                        resultSet.getString("place"));
                events.add(event);
            }
        } catch (SQLException throwables) {
            throw new DAOException("Problem in getUserEvents(User user)", throwables);
        } finally {
            DBUtil.close(connection, preparedStatement, resultSet);
        }
        return events;
    }
}
