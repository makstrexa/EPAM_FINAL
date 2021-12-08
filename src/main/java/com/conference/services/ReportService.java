package com.conference.services;

import com.conference.dao.exceptions.DAOException;
import com.conference.model.Event;
import com.conference.model.Report;
import com.conference.model.User;

import java.util.Collection;
import java.util.HashMap;

public interface ReportService {

    Collection<Report> getByEvent(Event event) throws DAOException;

    Collection<Report> getReportsBySpeaker(User user) throws DAOException;

    void offerAsASpeaker(User user, Report report) throws DAOException;

    void deleteFromSpeakersRequests(User user, Report report) throws DAOException;

    HashMap<Report, User> getSpeakersRequests() throws DAOException;

    Collection<Report> findAll() throws DAOException;

    Report get(Integer id) throws DAOException;

    void insert(Report report) throws DAOException;

    void delete(Report report) throws DAOException;

    void update(Report report) throws DAOException;


}
