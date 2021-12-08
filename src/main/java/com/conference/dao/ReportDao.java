package com.conference.dao;

import com.conference.dao.exceptions.DAOException;
import com.conference.model.Event;
import com.conference.model.Report;
import com.conference.model.User;

import java.util.Collection;
import java.util.HashMap;

public interface ReportDao extends AbstractDao<Report> {

    Integer getId() throws DAOException;

    Collection<Report> getByEvent(Event event) throws DAOException;

    Collection<Report> getReportsBySpeaker(User user) throws DAOException;

    void offerAsASpeaker(User user, Report report) throws DAOException;

    HashMap<Integer, Integer> getSpeakersRequests() throws DAOException;

    void deleteFromSpeakersRequests(User user, Report report) throws DAOException;

}
