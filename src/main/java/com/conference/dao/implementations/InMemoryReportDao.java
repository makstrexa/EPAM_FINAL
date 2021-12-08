package com.conference.dao.implementations;

import com.conference.dao.ReportDao;
import com.conference.dao.db.ConnectionPool;
import com.conference.dao.db.DBUtil;
import com.conference.dao.exceptions.DAOException;
import com.conference.model.Event;
import com.conference.model.Report;
import com.conference.model.ReportStatus;
import com.conference.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class InMemoryReportDao implements ReportDao {

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
    public Collection<Report> getReportsBySpeaker(User user) throws DAOException {
        String sql = "select * from reports where speaker_id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Collection<Report> reports = new HashSet<>();
        Report report;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user.getUserId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                report = new Report(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("theme"),
                        resultSet.getString("summary"),
                        new InMemoryUserDao().get(resultSet.getInt("speaker_id")),
                        new InMemoryEventDao().get(resultSet.getInt("event_id")),
                        ReportStatus.fromString(resultSet.getString("status")));
                reports.add(report);
            }
        } catch (SQLException throwables) {
            throw new DAOException("Problem in getReportsBySpeaker method", throwables);
        } finally {
            DBUtil.close(connection, preparedStatement, resultSet);
        }
        return reports;
    }

    @Override
    public Report get(Integer id) throws DAOException {
        String sql = "select * from reports where id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Report report = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                report = new Report(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("theme"),
                        resultSet.getString("summary"),
                        new InMemoryUserDao().get(resultSet.getInt("speaker_id")),
                        new InMemoryEventDao().get(resultSet.getInt("event_id")),
                        ReportStatus.fromString(resultSet.getString("status")));
            }
        } catch (Exception e) {
            throw new DAOException("problem in get(Integer id) report method", e);
        } finally {
            DBUtil.close(connection, preparedStatement, resultSet);
        }
        return report;
    }

    @Override
    public Collection<Report> findAll() throws DAOException {
        String sql = "select * from reports";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Collection<Report> reports = new HashSet<>();
        Report report;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                report = new Report(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("theme"),
                        resultSet.getString("summary"),
                        new InMemoryUserDao().get(resultSet.getInt("speaker_id")),
                        new InMemoryEventDao().get(resultSet.getInt("event_id")),
                        ReportStatus.fromString(resultSet.getString("status")));
                reports.add(report);
            }
        } catch (SQLException throwables) {
            throw new DAOException("Problem in Collection<Report> findAll() method", throwables);
        } finally {
            DBUtil.close(connection, preparedStatement, resultSet);
        }
        return reports;
    }

    @Override
    public void insert(Report entity, boolean generateId) throws DAOException {
        String sql = "insert into reports(title, theme, summary, event_id, speaker_id, status) values (?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getTitle());
            preparedStatement.setString(2, entity.getTheme());
            preparedStatement.setString(3, entity.getSummary());
            preparedStatement.setInt(4, entity.getEvent().getEventId());
            preparedStatement.setInt(5, entity.getSpeaker().getUserId());
            preparedStatement.setString(6, entity.getStatus().getValue());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DAOException("Problem in insert(Report) method", throwables);
        } finally {
            DBUtil.close(connection, preparedStatement);
        }
    }

    @Override
    public void delete(Report entity) throws DAOException {
        String sql = "delete from reports where id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, entity.getReportId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DAOException("problem in delete report method", throwables);
        } finally {
            DBUtil.close(connection, preparedStatement);
        }
    }

    @Override
    public void update(Report entity) throws DAOException {
        String sql = "update reports set title=?, theme=?, summary=?, event_id=?, speaker_id=?, status=? where id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getTitle());
            preparedStatement.setString(2, entity.getTheme());
            preparedStatement.setString(3, entity.getSummary());
            preparedStatement.setInt(4, entity.getEvent().getEventId());
            preparedStatement.setInt(5, entity.getSpeaker().getUserId());
            preparedStatement.setString(6, entity.getStatus().getValue());
            preparedStatement.setInt(7, entity.getReportId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DAOException("Problem in update report method",throwables);
        } finally {
            DBUtil.close(connection, preparedStatement);
        }
    }

    @Override
    public Integer getId() throws DAOException {
        return null;
    }

    @Override
    public Collection<Report> getByEvent(Event event) throws DAOException {

        final String sql = "select * from reports where event_id=?";

        Collection<Report> reports = new HashSet<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, event.getEventId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reports.add(new Report(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("theme"),
                        resultSet.getString("summary"),
                        new InMemoryUserDao().get(resultSet.getInt("speaker_id")),
                        new InMemoryEventDao().get(resultSet.getInt("event_id")),
                        ReportStatus.fromString(resultSet.getString("status"))));
            }
        } catch (SQLException throwables) {
            throw new DAOException("Problem in getByEvent() method", throwables);
        } finally {
            DBUtil.close(connection, preparedStatement, resultSet);
        }
        return reports;
    }

    @Override
    public void offerAsASpeaker(User user, Report report) throws DAOException {
        String sql = "insert into speakers_requests(speaker_id, report_id) values (?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.setInt(2, report.getReportId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DAOException("Problem in offerAsASpeaker method", throwables);
        } finally {
            DBUtil.close(connection, preparedStatement);
        }
    }

    @Override
    public HashMap<Integer, Integer> getSpeakersRequests() throws DAOException {
        String sql = "select speaker_id, report_id from speakers_requests";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        HashMap<Integer, Integer> reportSpeakerMap = new HashMap<>();
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reportSpeakerMap.put(resultSet.getInt("report_id"),
                        resultSet.getInt("speaker_id"));
            }
        } catch (SQLException throwables) {
            throw new DAOException("problem in getSpeakersRequests method", throwables);
        } finally {
            DBUtil.close(connection, preparedStatement, resultSet);
        }
        return reportSpeakerMap;
    }

    @Override
    public void deleteFromSpeakersRequests(User user, Report report) throws DAOException {
        String sql = "delete from speakers_requests where report_id=? and speaker_id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, report.getReportId());
            preparedStatement.setInt(2, user.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DAOException("Problem in deleteFromSpeakersRequests method", throwables);
        } finally {
            DBUtil.close(connection, preparedStatement);
        }
    }
}
