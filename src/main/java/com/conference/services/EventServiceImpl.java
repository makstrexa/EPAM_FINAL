package com.conference.services;

import com.conference.dao.DaoFactory;
import com.conference.dao.exceptions.DAOException;
import com.conference.model.Event;
import com.conference.model.Report;
import com.conference.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventServiceImpl implements EventService {

    DaoFactory daoFactory;

    public EventServiceImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public Event get(Integer id) throws DAOException {
        Event event = daoFactory.getEventDao().get(id);
        event.setUsers((Set<User>) daoFactory.getUserDao().getUsersByEvent(event));
        event.setReports((Set<Report>) daoFactory.getReportDao().getByEvent(event));
        return event;
    }

    @Override
    public Collection<Event> findAll() throws DAOException {
        Collection<Event> events = daoFactory.getEventDao().findAll();
        for (Event event: events) {
            event.setUsers((Set<User>) daoFactory.getUserDao().getUsersByEvent(event));
            event.setReports((Set<Report>) daoFactory.getReportDao().getByEvent(event));
        }
        return events;
    }

    @Override
    public Collection<Event> findAllActive(EventSortCriteria eventSortCriteria) throws DAOException {
        Collection<Event> events = daoFactory.getEventDao().findAll();
        for (Event event: events) {
            event.setUsers((Set<User>) daoFactory.getUserDao().getUsersByEvent(event));
            event.setReports((Set<Report>) daoFactory.getReportDao().getByEvent(event));
        }
        LocalDateTime now = LocalDateTime.now();
        return sort(events.stream()
                .filter(event -> event.getDateTime().isAfter(now))
                .collect(Collectors.toList()), eventSortCriteria);
    }

    @Override
    public Collection<Event> findAll(EventSortCriteria eventSortCriteria) throws DAOException {
        Collection<Event> events = daoFactory.getEventDao().findAll();
        for (Event event: events) {
            event.setUsers((Set<User>) daoFactory.getUserDao().getUsersByEvent(event));
            event.setReports((Set<Report>) daoFactory.getReportDao().getByEvent(event));
        }
        return sort(events, eventSortCriteria);
    }

    @Override
    public void deleteUserFromEvents(User user, Event... events) throws DAOException {
        daoFactory.getEventDao().deleteUserFromEvents(user, events);
    }

    @Override
    public Collection<Event> getEventsByUser(User user) throws DAOException {
        Collection<Event> events = daoFactory.getEventDao().getEventsByUser(user);
        for (Event event: events) {
            event.setUsers((Set<User>) daoFactory.getUserDao().getUsersByEvent(event));
            event.setReports((Set<Report>) daoFactory.getReportDao().getByEvent(event));
        }
        return events;
    }

    @Override
    public void insert(Event event) throws DAOException {
        daoFactory.getEventDao().insert(event, true);
    }

    @Override
    public void delete(Event event) throws DAOException {
        daoFactory.getEventDao().delete(event);
    }

    @Override
    public void update(Event event) throws DAOException {
        daoFactory.getEventDao().update(event);
    }

    private Collection<Event> sort(Collection<Event> events, EventSortCriteria eventSortCriteria) {
        return events.stream()
                .sorted(EventSorters.sorters.get(eventSortCriteria))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Event> search(String text, EventSortCriteria eventSortCriteria, String active) throws DAOException {
        if (text == null || text.equals("")) {
            if (active.equals("true"))
                return findAllActive(eventSortCriteria);
            else
                return findAll(eventSortCriteria);
        }
        return findByText(text, active, eventSortCriteria);
    }

    public Collection<Event> findByText(String text, String active, EventSortCriteria eventSortCriteria) throws DAOException {
        String[] words = text.toLowerCase().split(" ");
        if (active.equals("true")) {
            return findAllActive(eventSortCriteria).stream()
                    .filter(city -> containsAllWords(city, words))
                    .collect(Collectors.toList());
        }
        return findAll(eventSortCriteria).stream()
                .filter(city -> containsAllWords(city, words))
                .collect(Collectors.toList());
    }

    private static boolean containsAllWords(Event event, String[] words) {
        String string = event.getTitle() + " " + event.getDescription();
        string = string.toLowerCase();
        return Stream.of(words).allMatch(string::contains);
    }

}
