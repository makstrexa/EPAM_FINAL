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
    <title>Scroll Website</title>
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

    <form style="align-self: center">
        <select id="language" name="language" onchange="submit()">
            <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
            <option value="ru" ${language == 'ru' ? 'selected' : ''}>Russian</option>
        </select>
    </form>

    <div class="navbar__container">
        <c:if test="${!empty user}">
                <a href="#" id="navbar__logo">
                    <fmt:message key="mainpage.welcome"/>, <c:out value="${user.login}"/><br></a>

                <a href="logout" id="navbar__logo"><fmt:message key="mainpage.href.logout"/> </a>

        </c:if>
        <c:if test="${empty user}">
            <a href="toLogin" id="navbar__logo"><fmt:message key="mainpage.href.signin"/></a>
        </c:if>
        <div class="navbar__toggle" id="mobile-menu">
            <span class="bar"></span>
            <span class="bar"></span>
            <span class="bar"></span>
        </div>
        <ul class="navbar__menu">
            <c:if test="${user.role == UserRole.ADMIN}">
            <li class="navbar__item">
                <a href="adminHome?userId=${user.userId}" class="navbar__links" id="home-page"><fmt:message key="mainpage.admin.adminhome"/></a>
            </li>
            </c:if>

            <c:if test="${user.role == UserRole.SPEAKER}">
                <li class="navbar__item">
                    <a href="speakerHome?userId=${user.userId}" class="navbar__links" id="about-page"><fmt:message key="mainpage.admin.speakerhome"/></a>
                </li>
            </c:if>

            <li class="navbar__item">
                <a href="#services" class="navbar__links" id="services-page"><fmt:message key="mainpage.header.events"/></a>
            </li>
            <c:if test="${empty user}">
            <li class="navbar__btn">
                <a href="#sign-up" class="button" id="signup"><fmt:message key="mainpage.header.signup"/></a>
            </li>
            </c:if>
        </ul>
    </div>
</nav>

<!-- Hero Section
<div class="hero" id="home">
    <div class="hero__container">
        <h1 class="hero__heading">Choose your <span>colors</span></h1>
        <p class="hero__description">Unlimited Possibilities</p>
        <button class="main__btn"><a href="#">Explore</a></button>
    </div>
</div>
-->

<!-- About Section
<div class="main" id="about">
    <div class="main__container">
        <div class="main__img--container">
            <div class="main__img--card"><i class="fas fa-layer-group"></i></div>
        </div>
        <div class="main__content">
            <h1>What do we do?</h1>
            <h2>We help businesses scale</h2>
            <p>Schedule a call to learn more about our services</p>
            <button class="main__btn"><a href="#">Schedule Call</a></button>
        </div>
    </div>
</div>-->


<!-- Services Section -->
<div class="services" id="services">
        <div class="main__content" align="center" style="width: 60%;">

        <h2><fmt:message key="mainpage.content.all"/> </h2><br>

            <form action="showEvents" method="get" accept-charset="UTF-8">
                <input class="search-text" type="text" name="text" value="${text}" />
                <input class="search-button" type="submit" value="Search" />
            </form>

            <p style="font-size: 1rem">
            <a href="#"><fmt:message key="mainpage.evens.active"/></a>(
            <a href="showEvents?sort=OLD_FIRST&active=true&text=${text}" ><fmt:message key="mainpage.sortparam.oldfirst"/>,</a>
            <a href="showEvents?sort=NEW_FIRST&active=true&text=${text}" ><fmt:message key="mainpage.sortparam.newfirst"/>,</a>
            <a href="showEvents?sort=BY_DATE&active=true&text=${param.text}"><fmt:message key="mainpage.sortparam.bydate"/>,</a>
            <a href="showEvents?sort=BY_AMOUNT_OF_PARTICIPANTS&active=true&text=${param.text}" ><fmt:message key="mainpage.sortparam.byparticipants"/>,</a>
            <a href="showEvents?sort=BY_AMOUNT_OF_REPORTS&active=true&text=${param.text}"><fmt:message key="mainpage.sortparam.byreports"/></a>)
                <br>

                <a href="#"><fmt:message key="mainpage.events.all"/> </a>(
                <a href="showEvents?sort=OLD_FIRST&active=false&text=${param.text}" ><fmt:message key="mainpage.sortparam.oldfirst"/>,</a>
                <a href="showEvents?sort=NEW_FIRST&active=false&text=${param.text}" ><fmt:message key="mainpage.sortparam.newfirst"/>,</a>
                <a href="showEvents?sort=BY_DATE&active=false&text=${param.text}"><fmt:message key="mainpage.sortparam.bydate"/>,</a>
                <a href="showEvents?sort=BY_AMOUNT_OF_PARTICIPANTS&active=false&text=${param.text}" ><fmt:message key="mainpage.sortparam.byparticipants"/>,</a>
                <a href="showEvents?sort=BY_AMOUNT_OF_REPORTS&active=false&text=${param.text}" ><fmt:message key="mainpage.sortparam.byreports"/></a>)


            </p>



        </div>
    <div class="services__wrapper">
        <c:forEach var="event" items="${events}">
        <div class="services__card">
            <h2>${event.title}</h2>
            <p>${event.description}</p>
            <fmt:message key="mainpage.showmore" var="showmore"/>
            <div class="services__btn"><button><a href="showEventInfo?eventId=${event.eventId}">${showmore}</a></button></div>
            <c:if test="${user.role == UserRole.ADMIN}">
            <div class="services__btn"><button><a href="showEditEventForm?eventId=${event.eventId}">EDIT</a></button></div>
            <div class="services__btn"><button><a href="deleteEvent?eventId=${event.eventId}">DELETE</a></button></div>
            </c:if>
        </div>
        </c:forEach>
    </div>
    <!-- TRYING TO ADD PAGINATION -->
    <br>

    <%--For displaying Previous link except for the 1st page --%>
    <c:if test="${currentPage != 1}">
        <td><a href="showEvents?page=${currentPage - 1}"><fmt:message key="mainpage.pagination.previous"/></a></td>
    </c:if>

    <%--For displaying Page numbers.
    The when condition does not display a link for the current page--%>
    <table border="1" cellpadding="5" cellspacing="5">
        <tr>
            <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <td>${i}</td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="showEvents?page=${i}">${i}</a></td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </tr>
    </table>

    <%--For displaying Next link --%>
    <c:if test="${currentPage lt noOfPages}">
        <td><a href="showEvents?page=${currentPage + 1}"><fmt:message key="mainpage.pagination.next"/></a></td>
    </c:if>


    <!-- END TRYING TO ADD PAGINATION -->
</div>

<c:if test="${empty user}">
<div class="main" id="sign-up">
    <div class="main__container">
        <div class="main__content">
            <h1><fmt:message key="mainpage.signup.ju"/></h1>
            <h2><fmt:message key="mainpage.signup.sut"/></h2>
            <p><fmt:message key="mainpage.signup.seefunc"/></p>
            <fmt:message key="mainpage.signup.signup" var="sign"/>
            <button class="main__btn"><a href="showRegistrationForm">${sign}</a></button>
        </div>
        <div class="main__img--container">
            <div class="main__img--card" id="card-2">
                <i class="fas fa-users"></i>
            </div>
        </div>
    </div>
</div>
</c:if>

<script src="../js/app.js"></script>
</body>
</html>
