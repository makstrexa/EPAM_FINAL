package com.conference.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Event {

    private int eventId;
    private String title;
    private String description;
    private LocalDateTime dateTime;
    private Long duration;
    private String place;
    private Set<User> users;
    private Set<Report> reports;

    public Event(int eventId, String title, String description, LocalDateTime dateTime, Long duration, String place) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.duration = duration;
        this.place = place;
        users = new HashSet<>();
        reports = new HashSet<>();
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Set<Report> getReports() {
        return reports;
    }

    public void setReports(Set<Report> reports) {
        this.reports = reports;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int sizeOfUsers() {
        return users.size();
    }

    public int sizeOfReports() {
        return reports.size();
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        Event event = (Event) o;
        return eventId == event.eventId
                && title.equals(event.title)
                && description.equals(event.description)
                && dateTime.equals(event.dateTime)
                && Objects.equals(users, event.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, title, description, dateTime, users);
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dateTime=" + dateTime +
                ", users=" + users +
                ", reports=" + reports +
                '}';
    }
}
