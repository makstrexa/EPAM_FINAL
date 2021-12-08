<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.conference.model.UserRole" %>
<%@ page import="com.conference.services.UserServiceImpl" %>
<%@ page import="com.conference.services.ReportServiceImpl" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="local" />
<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Event</title>
    <link rel="stylesheet" href="../css/style.css" />
    <link
            rel="stylesheet"
            href="https://use.fontawesome.com/releases/v5.14.0/css/all.css"
            integrity="sha384-HzLeBuhoNPvSl5KYnjx0BT+WB0QEEqLprO+NBkkk5gbc67FTaL7XIGa2w1L0Xbgc"
            crossorigin="anonymous"
    />
    <style><%@include file="../css/style.css"%></style>


</head>
<body>
<!-- Navbar Section -->
<nav class="navbar">
    <div class="navbar__container">
        <a href="." id="navbar__logo">Back</a>
        <c:if test="${!empty user}">
            <a href="#" id="navbar__logo">
                Welcome, <c:out value="${user.login}"/><br></a>

            <a href="logout" id="navbar__logo">Log Out</a>

        </c:if>
        <c:if test="${empty user}">
            <a href="toLogin" id="navbar__logo">Sign In</a>
        </c:if>
        <div class="navbar__toggle" id="mobile-menu">
            <span class="bar"></span>
            <span class="bar"></span>
            <span class="bar"></span>
        </div>
        <ul class="navbar__menu">
            <li class="navbar__item">
                <a href="#home" class="navbar__links" id="home-page">Home</a>
            </li>
            <li class="navbar__item">
                <a href="#about" class="navbar__links" id="about-page">About</a>
            </li>
            <li class="navbar__item">
                <a href="#services" class="navbar__links" id="services-page">Services</a>
            </li>

        </ul>
    </div>
</nav>


<div class="services" id="services">
    <div class="main__content" align="center" style="width: 60%;">

        <h2>${event.title}</h2><br>
        <h1 style="margin-bottom: -15px">${event.description}<br>
        ${event.dateTime}<br>
        ${event.place}<br>
        Duration: ${event.duration} hours
        </h1>
        <c:if test="${!empty user and !users.contains(user)}">
            <button class="main__btn"><a href="joinEvent?eventId=${event.eventId}&userId=${user.userId}">Join Event</a></button>
        </c:if>
        <c:if test="${!empty user and users.contains(user)}">

            <c:if test="${userService.present(event, user).equals('-')}">
                <button class="main__btn"><a href="presentEvent?eventId=${event.eventId}&userId=${user.userId}">Present</a></button>
            </c:if>

            <button class="main__btn"><a href="deleteUserFromEvent?userId=${user.userId}&eventId=${event.eventId}">Exit Event</a></button>
        </c:if>
        <c:if test="${message != null}">
            <p class="forgot">${message}</p>
        </c:if>
        <c:if test="${user.role == UserRole.ADMIN or user.role == UserRole.SPEAKER}">
            <button class="main__btn"><a href="showAddReportForm?eventId=${event.eventId}">Add Report</a></button>
        </c:if>
        <br>
    </div>
    <div class="services__wrapper">
        <c:forEach var="report" items="${event.reports}">
            <div class="services__card">
                <h2>${report.title}</h2>
                <p>${report.theme}</p>
                <p>Speaker: ${report.speaker.name} ${report.speaker.surname}</p>
                <c:if test="${user.role == UserRole.SPEAKER and report.speaker.userId == 13
                and !ReportServiceImpl.containsAPairInMap(speakersRequests, user, report)}">
                <div class="services__btn"><button><a href="offerAsASpeakerToExist?userId=${user.userId}&reportId=${report.reportId}">Offer Yourself As a Speaker</a></button></div>
                </c:if>
                <c:if test="${user.role == UserRole.ADMIN}">
                    <p>${report.status}</p>
                    <div class="services__btn"><button><a href="showEditReportForm?eventId=${event.eventId}&reportId=${report.reportId}">EDIT</a></button></div>
                    <div class="services__btn"><button><a href="deleteReport?eventId=${event.eventId}&reportId=${report.reportId}">DELETE</a></button></div>
                </c:if>
            </div>
        </c:forEach>
    </div>
</div>
<c:if test="${user.role == UserRole.ADMIN}">
<div class="hero" id="home">
    <div class="hero__container">
        <h1 class="hero__heading">Users <span>which</span></h1>
        <p class="hero__description">Participates In This Event</p>
        <!--<button class="main__btn"><a href="#">Explore</a></button>-->
        <section>
        <div class="tbl-header">
            <table cellpadding="0" cellspacing="0" border="0">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Surname</th>
                    <th>Login</th>
                    <th>Email</th>
                    <th>Present</th>
                    <th>Delete</th>
                </tr>
                </thead>
            </table>
        </div>
        <div class="tbl-content">
            <table cellpadding="0" cellspacing="0" border="0">
                <tbody>
                <c:forEach var="userE" items="${present}">
                    <tr>
                        <td>${userE.key.name}</td>
                        <td>${userE.key.surname}</td>
                        <td>${userE.key.login}</td>
                        <td>${userE.key.email}</td>
                        <td>${userE.value}</td>
                        <td><a href="deleteUserFromEvent?userId=${userE.key.userId}&eventId=${event.eventId}">Delete</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        </section>
    </div>
</div>
</c:if>
</div>
</section>
</div>

<script src="../js/app.js"></script>
</body>
</html>
