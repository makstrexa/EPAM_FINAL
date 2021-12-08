package com.conference.controller;

import com.conference.dao.DaoFactory;
import com.conference.dao.implementations.InMemoryDaoFactory;
import com.conference.services.*;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DaoFactory daoFactory = new InMemoryDaoFactory();

        UserService userService = new UserServiceImpl(daoFactory);
        sce.getServletContext().setAttribute("userService", userService);

        EventService eventService = new EventServiceImpl(daoFactory);
        sce.getServletContext().setAttribute("eventService", eventService);

        ReportService reportService = new ReportServiceImpl(daoFactory);
        sce.getServletContext().setAttribute("reportService", reportService);

    }

}
