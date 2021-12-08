package com.conference.dao.implementations;

import com.conference.dao.DaoFactory;
import com.conference.dao.EventDao;
import com.conference.dao.ReportDao;
import com.conference.dao.UserDao;

public class InMemoryDaoFactory implements DaoFactory {

    private UserDao userDao;
    private EventDao eventDao;
    private ReportDao reportDao;

    public InMemoryDaoFactory() {
        userDao = new InMemoryUserDao();
        eventDao = new InMemoryEventDao();
        reportDao = new InMemoryReportDao();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public EventDao getEventDao() {
        return eventDao;
    }

    public ReportDao getReportDao() {
        return reportDao;
    }
}
