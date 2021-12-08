package com.conference.controller;

import com.conference.emailutil.EmailUtil;
import com.conference.dao.exceptions.DAOException;
import com.conference.model.*;
import com.conference.services.*;

import javax.mail.SendFailedException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(name = "FrontControllerServlet", value = "/do/*")
public class FrontControllerServlet extends HttpServlet {

    UserService userService;
    EventService eventService;
    ReportService reportService;
    Logger logger;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserService) config.getServletContext().getAttribute("userService");
        eventService = (EventService) config.getServletContext().getAttribute("eventService");
        reportService = (ReportService) config.getServletContext().getAttribute("reportService");
        logger = LogManager.getLogger(FrontControllerServlet.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "/";
        }

        try {
            switch (pathInfo) {
                case "/login":
                    login(request, response);
                    break;
                case "/toLogin":
                    showLoginForm(request,response);
                    break;
                case "/logout":
                    logout(request, response);
                    break;
                case "/registration":
                    registration(request, response);
                    break;
                case "/showRegistrationForm":
                    showRegistrationForm(request, response);
                    break;
                case "/showEvents":
                    showEvents(request, response);
                    break;
                case "/showEventInfo":
                    showEventInfo(request, response);
                    break;
                case "/showEditEventForm":
                    showEditEventForm(request, response);
                    break;
                case "/editEvent":
                    editEvent(request, response);
                    break;
                case "/deleteEvent":
                    deleteEvent(request, response);
                    break;
                case "/joinEvent":
                    joinEvent(request, response);
                    break;
                case "/showAddEventForm":
                    showAddEventForm(request, response);
                    break;
                case "/addEvent":
                    addEvent(request, response);
                    break;
                case "/adminHome":
                    adminHome(request, response);
                    break;
                case "/showAddReportForm":
                    showAddReportForm(request, response);
                    break;
                case "/addReport":
                    addReport(request, response);
                    break;
                case "/showEditReportForm":
                    showEditReportForm(request, response);
                    break;
                case "/editReport":
                    editReport(request, response);
                    break;
                case "/deleteReport":
                    deleteReport(request, response);
                    break;
                case "/deleteUserFromEvent":
                    deleteUserFromEvent(request, response);
                    break;
                case "/offerAsASpeakerToExist":
                    offerAsASpeakerToExist(request, response);
                    break;
                case "/acceptSpeakerRequest":
                    acceptSpeakerRequest(request, response);
                    break;
                case "/deleteSpeakerRequest":
                    deleteSpeakerRequest(request, response);
                    break;
                case "/speakerHome":
                    speakerHome(request, response);
                    break;
                case "/presentEvent":
                    presentEvent(request, response);
                    break;
                default:
                    showEvents(request, response);
                    break;
            }
        } catch (RuntimeException | DAOException | SendFailedException err) {
            error(request, response, "Oops " + err.getMessage());
        }

    }

    protected void login(HttpServletRequest request, HttpServletResponse response) throws DAOException, ServletException, IOException {
        request.getSession().invalidate();

        String login = request.getParameter("login");
        User user = userService.getByLogin(login);
        if (user == null) {
            error(request, response, "Sorry, user with login '" + login + "' not exists");
            return;
        }

        String password = request.getParameter("password");

        if (!userService.checkPassword(user, password)) {
            request.setAttribute("message", "Incorrect password!");
            request.getRequestDispatcher("/resources/jsp/login-page.jsp").forward(request, response);
            return;
        }
        request.getSession().setAttribute("user", user);
        response.sendRedirect(".");
    }

    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath() + "/");
    }

    protected void showLoginForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/resources/jsp/login-page.jsp").forward(request, response);
    }

    protected void registration(HttpServletRequest request, HttpServletResponse response) throws DAOException, ServletException, IOException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirm");

        if (userService.loginCheckAndEmail(login, email)) {
            request.setAttribute("message", "Account with this login is already exist, please choose another!");
            request.getRequestDispatcher("./showRegistrationForm").forward(request, response);
        } else if (password.equals(confirm)) {
            userService.registrateUser(name, surname, login, email, password, UserRole.USER);
            response.sendRedirect(".");
        } else {
            request.setAttribute("message", "Passwords don't match, please try again!");
            request.getRequestDispatcher("./showRegistrationForm").forward(request, response);
        }
    }

    protected void showRegistrationForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/resources/jsp/sign-up-page.jsp").forward(request, response);
    }

    protected void showEvents(HttpServletRequest request, HttpServletResponse response) throws DAOException, ServletException, IOException {
        String active = request.getParameter("active");
        String sort = request.getParameter("sort");
        String searchText = request.getParameter("text");

        EventSortCriteria eventSortCriteria;

        if (sort == null || sort.equals("")
                || active == null || active.equals("")) {
            eventSortCriteria = EventSortCriteria.NEW_FIRST;
            active = "true";
        } else {
            eventSortCriteria = EventSortCriteria.valueOf(sort);
        }

        Collection<Event> events = eventService.search(searchText, eventSortCriteria, active);

        // trying to add pagination

        Collection<Event> eventsWithPag;
        int page = 1;
        int recordsPerPage = 8;
        if(request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        eventsWithPag = events
                .stream()
                .sequential()
                .skip((long) (page - 1) * recordsPerPage)
                .limit(recordsPerPage)
                .collect(Collectors.toList());
        int noOfRecords = events.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        // end
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("events", eventsWithPag);
        request.setAttribute("text", searchText);
        request.getRequestDispatcher("/resources/jsp/main-page.jsp").forward(request, response);
    }

    protected void allEvents(HttpServletRequest request, HttpServletResponse response)
            throws DAOException, ServletException, IOException {
        Collection<Event> events = eventService.findAll();
        request.setAttribute("events", events);
        request.getRequestDispatcher("/resources/jsp/main-page.jsp").forward(request, response);
    }

    protected void showEventInfo(HttpServletRequest request, HttpServletResponse response)
            throws DAOException, ServletException, IOException {
        Integer eventId = Integer.parseInt(request.getParameter("eventId"));
        Event event = eventService.get(eventId);
        Collection<User> users = event.getUsers();

        HashMap<Report, User> rU = reportService.getSpeakersRequests();
        request.setAttribute("speakersRequests", rU);

        HashMap<User, String> userPresent = new HashMap<>();
        for (User user: users) {
            userPresent.put(user, userService.present(event, user));
        }

        request.setAttribute("present", userPresent);
        request.setAttribute("users", users);
        request.setAttribute("event", event);
        request.getRequestDispatcher("/resources/jsp/event.jsp").forward(request, response);
    }

    protected void showEditEventForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DAOException {
        int eventId = Integer.parseInt(request.getParameter("eventId"));
        Event event = eventService.get(eventId);
        LocalDateTime minDate = LocalDateTime.now();
        request.setAttribute("minDate", minDate);

        request.setAttribute("eventId", eventId);
        request.setAttribute("event", event);
        request.getRequestDispatcher("/resources/jsp/event-form.jsp").forward(request, response);
    }

    protected void editEvent(HttpServletRequest request, HttpServletResponse response)
            throws DAOException, IOException, SendFailedException {

        int eventId = Integer.parseInt(request.getParameter("eventId"));

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String dateLocal = request.getParameter("dateTime");
        DateTimeFormatter formatter = null;
        if (dateLocal.length() > 19)
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        else if (dateLocal.length() == 19) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        }
        else
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateLocal, formatter);

        Long duration = Long.parseLong(request.getParameter("duration"));
        String place = request.getParameter("place");

        Event event = new Event(eventId, title, description, dateTime, duration, place);
        eventService.update(event);

        StringBuilder emails = new StringBuilder();
        Event forMail = eventService.get(eventId);
        if (!forMail.getUsers().isEmpty()) {
            for (User user : forMail.getUsers()) {
                emails.append(user.getEmail());
                emails.append(", ");
            }
            System.out.println(emails);
            String subject = "Edit in event: " + event.getTitle();
            String text = "Visit our web app to see changes!" +
                    "\nhttp://localhost:8082/EpamConference_war_exploded/do/";

            EmailUtil.sendMail(emails.toString(), subject, text);
        }
        response.sendRedirect(".");
    }

    protected void deleteEvent(HttpServletRequest request, HttpServletResponse response) throws DAOException, IOException {
        int eventId = Integer.parseInt(request.getParameter("eventId"));
        Event event = eventService.get(eventId);
        userService.deleteUsersFromEvent(event);
        eventService.delete(event);
        response.sendRedirect(".");
    }

    protected void joinEvent(HttpServletRequest request, HttpServletResponse response) throws DAOException, IOException, ServletException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int eventId = Integer.parseInt(request.getParameter("eventId"));
        User user = userService.get(userId);
        Event event = eventService.get(eventId);
        if (event.getUsers().contains(user)) {
            request.setAttribute("message", "Account with this login is already exist, please choose another!");
            request.getRequestDispatcher("/resources/jsp/event.jsp").forward(request, response);
        }
        userService.registrateToEvent(user, event);
        response.sendRedirect(".");
    }

    protected void showAddEventForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LocalDateTime minDate = LocalDateTime.now();
        request.setAttribute("minDate", minDate);
        request.getRequestDispatcher("/resources/jsp/event-form.jsp").forward(request, response);
    }

    protected void addEvent(HttpServletRequest request, HttpServletResponse response) throws DAOException, IOException {
        request.setCharacterEncoding("UTF-8");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String dateLocal = request.getParameter("dateTime");
        DateTimeFormatter formatter = null;
        if (dateLocal.length() > 16)
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        else
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateLocal, formatter);

        Long duration = Long.parseLong(request.getParameter("duration"));
        String place = request.getParameter("place");
        Event event = new Event(0, title, description, dateTime, duration, place);
        eventService.insert(event);
        response.sendRedirect(".");
    }

    protected void adminHome(HttpServletRequest request, HttpServletResponse response) throws DAOException, ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        User admin = userService.get(userId);
        request.setAttribute("admin", admin);
        HashMap<Report, User> rU = reportService.getSpeakersRequests();
        request.setAttribute("speakersRequests", rU);
        request.getRequestDispatcher("/resources/jsp/admin.jsp").forward(request, response);
    }

    protected void speakerHome(HttpServletRequest request, HttpServletResponse response) throws DAOException, ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        User speaker = userService.get(userId);
        request.setAttribute("speaker", speaker);
        Collection<Event> events = new ArrayList<>();
        for (Event event: eventService.findAll()) {
            if (event.getReports()
                    .stream()
                    .anyMatch(report -> report.getSpeaker().equals(speaker))) {
                events.add(event);
            }
        }

        request.setAttribute("events", events);
        request.getRequestDispatcher("/resources/jsp/speaker.jsp").forward(request, response);
    }

    protected void showAddReportForm(HttpServletRequest request, HttpServletResponse response) throws DAOException, ServletException, IOException {
        int eventId = Integer.parseInt(request.getParameter("eventId"));
        Event event = eventService.get(eventId);
        request.setAttribute("eventId", eventId);
        request.getRequestDispatcher("/resources/jsp/report-form.jsp").forward(request, response);
    }

    protected void addReport(HttpServletRequest request ,HttpServletResponse response) throws DAOException, IOException {
        request.setCharacterEncoding("UTF-8");
        String title = request.getParameter("title");
        String theme = request.getParameter("theme");
        String summary =  request.getParameter("summary");
        String speakerLogin = request.getParameter("speakerLogin");
        Integer eventId = Integer.parseInt(request.getParameter("eventId"));
        User user;
        if (speakerLogin.isEmpty()) {
            user = userService.getByLogin("nospeaker");
        } else {
            user = userService.getByLogin(speakerLogin);
        }
        Event event = eventService.get(eventId);
        String statusStr = request.getParameter("status");

        Report report = new Report(0, title, theme, summary, user, event, ReportStatus.fromString(statusStr));
        reportService.insert(report);
        response.sendRedirect(".");
    }

    protected void showEditReportForm(HttpServletRequest request, HttpServletResponse response) throws DAOException, ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Integer eventId = Integer.parseInt(request.getParameter("eventId"));
        Integer reportId = Integer.parseInt(request.getParameter("reportId"));
        request.setAttribute("eventId", eventId);
        Report report = reportService.get(reportId);
        request.setAttribute("report", report);
        request.getRequestDispatcher("/resources/jsp/report-form.jsp").forward(request, response);
    }

    protected void editReport(HttpServletRequest request, HttpServletResponse response) throws DAOException, IOException {
        Integer eventId = Integer.parseInt(request.getParameter("eventId"));
        int reportId = Integer.parseInt(request.getParameter("reportId"));
        request.setAttribute("eventId", eventId);
        String title = request.getParameter("title");
        String theme = request.getParameter("theme");
        String summary =  request.getParameter("summary");
        String speakerLogin = request.getParameter("speakerLogin");
        User user;
        if (speakerLogin.isEmpty()) {
            user = userService.getByLogin("nospeaker");
        } else {
            user = userService.getByLogin(speakerLogin);
        }
        Event event = eventService.get(eventId);
        String statusStr = request.getParameter("status");
        Report report = new Report(reportId, title, theme, summary, user, event, ReportStatus.fromString(statusStr));
        reportService.update(report);
        response.sendRedirect(".");
    }

    protected void deleteReport(HttpServletRequest request, HttpServletResponse response) throws DAOException, IOException {
        int reportId = Integer.parseInt(request.getParameter("reportId"));
        Report report = reportService.get(reportId);
        reportService.delete(report);
        response.sendRedirect(".");
    }

    protected void deleteUserFromEvent(HttpServletRequest request, HttpServletResponse response) throws DAOException, IOException {
        int eventId = Integer.parseInt(request.getParameter("eventId"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        Event event = eventService.get(eventId);
        User user = userService.get(userId);
        eventService.deleteUserFromEvents(user, event);
        response.sendRedirect(".");
    }

    protected void offerAsASpeakerToExist(HttpServletRequest request, HttpServletResponse response) throws DAOException, IOException, ServletException {
        Integer userId = Integer.parseInt(request.getParameter("userId"));
        Integer reportId = Integer.parseInt(request.getParameter("reportId"));
        User user = userService.get(userId);
        Report report = reportService.get(reportId);
        HashMap<Report, User> rU = reportService.getSpeakersRequests();
        if (ReportServiceImpl.containsAPairInMap(rU, user, report)) {
            error(request, response, "You already offer yourself to this report");
        }
        reportService.offerAsASpeaker(user, report);
        response.sendRedirect(".");
    }

    protected void acceptSpeakerRequest(HttpServletRequest request, HttpServletResponse response) throws DAOException, IOException {
        Integer userId = Integer.parseInt(request.getParameter("userId"));
        Integer reportId = Integer.parseInt(request.getParameter("reportId"));
        User user = userService.get(userId);
        Report report = reportService.get(reportId);
        report.setSpeaker(user);
        reportService.update(report);
        reportService.deleteFromSpeakersRequests(user, report);
        response.sendRedirect(".");
    }

    protected void deleteSpeakerRequest(HttpServletRequest request, HttpServletResponse response) throws DAOException {
        Integer userId = Integer.parseInt(request.getParameter("userId"));
        Integer reportId = Integer.parseInt(request.getParameter("reportId"));
        User user = userService.get(userId);
        Report report = reportService.get(reportId);
        reportService.deleteFromSpeakersRequests(user, report);
    }

    protected void presentEvent(HttpServletRequest request, HttpServletResponse response) throws DAOException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int eventId = Integer.parseInt(request.getParameter("eventId"));
        User user = userService.get(userId);
        Event event = eventService.get(eventId);
        userService.presentUpdate(event, user);
        response.sendRedirect(".");
    }

    protected void error(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException{
        request.setAttribute("message", message);
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }

}
