
<%@ page import="com.conference.model.UserRole" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="local" />

<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Speaker Page</title>
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

<form style="align-self: center">
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Russian</option>
    </select>
</form>

<nav class="navbar">

    <div class="navbar__container">
        <c:if test="${!empty user}">
            <a href="#" id="navbar__logo">
                <fmt:message key="mainpage.welcome"/>, <c:out value="${user.login}"/><br></a>

            <a href="logout" id="navbar__logo"><fmt:message key="mainpage.href.logout"/></a>
            <a href="." id="navbar__logo">Back</a>

        </c:if>

        <div class="navbar__toggle" id="mobile-menu">
            <span class="bar"></span>
            <span class="bar"></span>
            <span class="bar"></span>
        </div>
        <ul class="navbar__menu">

            <li class="navbar__item">
                <a href="#about" class="navbar__links" id="about-page">About</a>
            </li>
            <li class="navbar__item">
                <fmt:message key="mainpage.header.events" var="eventss"/>
                <a href="#services" class="navbar__links" id="services-page">${eventss}</a>
            </li>

        </ul>
    </div>
</nav>


<div class="services" id="services">
    <div class="main__content" align="center" style="width: 60%;">
        <h2><fmt:message key="speaker.page.events"/> </h2><br>
    </div>
    <div class="services__wrapper">
        <c:forEach var="event" items="${events}">
            <div class="services__card">
                <h2>${event.title}</h2>
                <p>${event.description}</p>
                <fmt:message key="mainpage.showmore" var="showmore"/>
                <div class="services__btn"><button><a href="showEventInfo?eventId=${event.eventId}">${showmore}</a></button></div>
            </div>
        </c:forEach>
    </div>

</div>



<script src="../js/app.js"></script>
</body>
