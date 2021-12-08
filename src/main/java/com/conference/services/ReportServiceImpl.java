package com.conference.services;

import com.conference.dao.DaoFactory;
import com.conference.dao.exceptions.DAOException;
import com.conference.model.Event;
import com.conference.model.Report;
import com.conference.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ReportServiceImpl implements ReportService {

    DaoFactory daoFactory;

    public ReportServiceImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public Collection<Report> findAll() throws DAOException {
        Collection<Report> reports = daoFactory.getReportDao().findAll();
        for (Report report: reports) {
            report.setSpeaker(daoFactory.getUserDao().get(report.getReportId()));
            report.setEvent(daoFactory.getEventDao().get(report.getEvent().getEventId()));
        }
        return reports;
    }

    @Override
    public Collection<Report> getByEvent(Event event) throws DAOException {
        Collection<Report> reports = daoFactory.getReportDao().getByEvent(event);
        for (Report report: reports) {
            report.setSpeaker(daoFactory.getUserDao().get(report.getReportId()));
            report.setEvent(daoFactory.getEventDao().get(report.getEvent().getEventId()));
        }
        return reports;
    }

    @Override
    public Collection<Report> getReportsBySpeaker(User user) throws DAOException {
        Collection<Report> reports = daoFactory.getReportDao().getReportsBySpeaker(user);
        for (Report report: reports) {
            report.setSpeaker(daoFactory.getUserDao().get(report.getReportId()));
            report.setEvent(daoFactory.getEventDao().get(report.getEvent().getEventId()));
        }
        return reports;
    }

    @Override
    public Report get(Integer id) throws DAOException {
        Report report = daoFactory.getReportDao().get(id);
        report.setEvent(daoFactory.getEventDao().get(report.getEvent().getEventId()));
        return report;
    }

    @Override
    public void insert(Report report) throws DAOException {
        daoFactory.getReportDao().insert(report, true);
    }

    @Override
    public void delete(Report report) throws DAOException {
        daoFactory.getReportDao().delete(report);
    }

    @Override
    public void update(Report report) throws DAOException {
        daoFactory.getReportDao().update(report);
    }

    @Override
    public void offerAsASpeaker(User user, Report report) throws DAOException {
        daoFactory.getReportDao().offerAsASpeaker(user, report);
    }

    @Override
    public void deleteFromSpeakersRequests(User user, Report report) throws DAOException {
        daoFactory.getReportDao().deleteFromSpeakersRequests(user, report);
    }

    @Override
    public HashMap<Report, User> getSpeakersRequests() throws DAOException {
        HashMap<Integer, Integer> reportUserIdMap = daoFactory.getReportDao().getSpeakersRequests();
        HashMap<Report, User> reportUserMap = new HashMap<>();
        for (Map.Entry<Integer, Integer> mapId: reportUserIdMap.entrySet()) {
            reportUserMap.put(daoFactory.getReportDao().get(mapId.getKey()), daoFactory.getUserDao().get(mapId.getValue()));
        }
        return reportUserMap;
    }


    public static boolean containsAPairInMap(HashMap<Report, User> reportUserHashMap, User user, Report report) {
        for (Map.Entry<Report, User> entry: reportUserHashMap.entrySet()) {
            if (entry.getKey().equals(report) && entry.getValue().equals(user))
                return true;
        }
        return false;
    }

}
