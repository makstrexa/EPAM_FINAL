package com.conference.services;

import com.conference.model.Event;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;

public class EventSorters {

    public static final Map<EventSortCriteria, Comparator<Event>> sorters;

    static {
        sorters = new HashMap<>();
        sorters.put(EventSortCriteria.OLD_FIRST, comparing(Event::getEventId));
        sorters.put(EventSortCriteria.NEW_FIRST, comparing(Event::getEventId, reverseOrder()));
        sorters.put(EventSortCriteria.BY_DATE, comparing(Event::getDateTime));
        sorters.put(EventSortCriteria.BY_AMOUNT_OF_PARTICIPANTS, comparing(Event::sizeOfUsers));
        sorters.put(EventSortCriteria.BY_AMOUNT_OF_REPORTS, comparing(Event::sizeOfReports));
    }
}
