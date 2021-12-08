<%@ page import="com.conference.model.UserRole" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Admin Page</title>
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
                <a href="#services" class="navbar__links" id="services-page">Services</a>
            </li>

        </ul>
    </div>
</nav>


<div class="hero" id="home">
    <div class="hero__container">
        <h1 class="hero__heading">Add new <span>event</span></h1>

        <button class="main__btn"><a href="showAddEventForm">Add New Event</a></button>
    </div>
</div>


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

<!-- Services Section
<div class="services" id="services">
    <div class="main__content" align="center" style="width: 60%;">

        <h2>All Events </h2><br>

        <form action="showEvents" method="get">
            <input class="search-text" type="text" name="text" value="${text}" />
            <input class="search-button" type="submit" value="Search" />
        </form>

        <p style="font-size: 1rem">
            <a href="#">ACTIVE</a>(
            <a href="showEvents?sort=OLD_FIRST&active=true&text=${text}" >Old First,</a>
            <a href="showEvents?sort=NEW_FIRST&active=true&text=${text}" >New First,</a>
            <a href="showEvents?sort=BY_DATE&active=true&text=${param.text}">By Date,</a>
            <a href="showEvents?sort=BY_AMOUNT_OF_PARTICIPANTS&active=true&text=${param.text}" >Amount Of Participants,</a>
            <a href="showEvents?sort=BY_AMOUNT_OF_REPORTS&active=true&text=${param.text}">Amount Of Reports</a>)
            <br>
            <a href="#">All</a>(
            <a href="showEvents?sort=OLD_FIRST&active=false&text=${param.text}" >Old First,</a>
            <a href="showEvents?sort=NEW_FIRST&active=false&text=${param.text}" >New First,</a>
            <a href="showEvents?sort=BY_DATE&active=false&text=${param.text}">By Date,</a>
            <a href="showEvents?sort=BY_AMOUNT_OF_PARTICIPANTS&active=false&text=${param.text}" >Amount Of Participants,</a>
            <a href="showEvents?sort=BY_AMOUNT_OF_REPORTS&active=false&text=${param.text}" >Amount Of Reports</a>)


        </p>



    </div>
    <div class="services__wrapper">
        <c:forEach var="event" items="${events}">
            <div class="services__card">
                <h2>${event.title}</h2>
                <p>${event.description}</p>
                <div class="services__btn"><button><a href="showEventInfo?eventId=${event.eventId}">Show more</a></button></div>
                <c:if test="${user.role == UserRole.ADMIN}">
                    <div class="services__btn"><button><a href="showEditEventForm?eventId=${event.eventId}">EDIT</a></button></div>
                    <div class="services__btn"><button><a href="deleteEvent?eventId=${event.eventId}">DELETE</a></button></div>
                </c:if>
            </div>
        </c:forEach>
    </div>
</div>-->

<!-- Features Section -->

<div class="hero" id="">
    <div class="hero__container">
        <h1 class="hero__heading">Offers <span>From Speakers For Free Reports</span></h1>

        <section>
            <div class="tbl-header">
                <table cellpadding="0" cellspacing="0" border="0">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Surname</th>
                        <th>Login</th>
                        <th>Email</th>
                        <th>Report Title</th>
                        <th>Report Theme</th>
                        <th>Accept</th>
                        <th>Delete</th>
                    </tr>
                    </thead>
                </table>
            </div>
            <div class="tbl-content">
                <table cellpadding="0" cellspacing="0" border="0">
                    <tbody>
                    <c:forEach var="map" items="${speakersRequests}">
                        <tr>
                            <td>${map.value.name}</td>
                            <td>${map.value.surname}</td>
                            <td>${map.value.login}</td>
                            <td>${map.value.email}</td>
                            <td>${map.key.title}</td>
                            <td>${map.key.theme}</td>
                            <td><a href="acceptSpeakerRequest?userId=${map.value.userId}&reportId=${map.key.reportId}">Accept</a></td>
                            <td><a href="deleteSpeakerRequest?userId=${map.value.userId}&reportId=${map.key.reportId}">Delete</a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </section>

    </div>
</div>

<!-- Footer Section
<div class="footer__container">
    <div class="footer__links">
        <div class="footer__link--wrapper">
            <div class="footer__link--items">
                <h2>About Us</h2>
                <a href="/sign__up">How it works</a> <a href="/">Testimonials</a>
                <a href="/">Careers</a> <a href="/">Terms of Service</a>
            </div>
            <div class="footer__link--items">
                <h2>Contact Us</h2>
                <a href="/">Contact</a> <a href="/">Support</a>
                <a href="/">Destinations</a>
            </div>
        </div>
        <div class="footer__link--wrapper">
            <div class="footer__link--items">
                <h2>Videos</h2>
                <a href="/">Submit Video</a> <a href="/">Ambassadors</a>
                <a href="/">Agency</a>
            </div>
            <div class="footer__link--items">
                <h2>Social Media</h2>
                <a href="/">Instagram</a> <a href="/">Facebook</a>
                <a href="/">Youtube</a> <a href="/">Twitter</a>
            </div>
        </div>
    </div>
    <section class="social__media">
        <div class="social__media--wrap">
            <div class="footer__logo">
                <a href="/" id="footer__logo">COLOR</a>
            </div>
            <p class="website__rights">© COLOR 2020. All rights reserved</p>
            <div class="social__icons">
                <a href="/" class="social__icon--link" target="_blank"
                ><i class="fab fa-facebook"></i
                ></a>
                <a href="/" class="social__icon--link"
                ><i class="fab fa-instagram"></i
                ></a>
                <a href="/" class="social__icon--link"
                ><i class="fab fa-youtube"></i
                ></a>
                <a href="/" class="social__icon--link"
                ><i class="fab fa-linkedin"></i
                ></a>
                <a href="/" class="social__icon--link"
                ><i class="fab fa-twitter"></i
                ></a>
            </div>-->
</div>
</section>
</div>

<script src="../js/app.js"></script>
</body>
</html>
