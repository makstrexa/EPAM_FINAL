package com.conference.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User {

    private Integer userId;
    private String name;
    private String surname;
    private String login;
    private String email;
    private String passwordHash;
    private UserRole role;
    private Set<Event> events;

    public User() {

    }

    public User(Integer userId, String name, String surname, String login, String email, String passwordHash, UserRole role) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        events = new HashSet<>();
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }



    @Override
    public int hashCode(){
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.userId);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return userId.equals(user.userId)
                && name.equals(user.name)
                && email.equals(user.email)
                && surname.equals(user.surname)
                && login.equals(user.login)
                && passwordHash.equals(user.passwordHash)
                && role == user.role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", role=" + role +
                ", events=" + events +
                '}';
    }
}
