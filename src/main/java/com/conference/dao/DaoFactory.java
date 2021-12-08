package com.conference.dao;

public interface DaoFactory {

    UserDao getUserDao();

    EventDao getEventDao();

    ReportDao getReportDao();

}
