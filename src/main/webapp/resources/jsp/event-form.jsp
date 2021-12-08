<%@ page import="javafx.util.converter.LocalDateTimeStringConverter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="local" />
<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta charset="UTF-8">
    <title>Edit Event</title>
    <style><%@include file="../css/login-page.css"%></style>
    <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" >
</head>
<body>

<form>
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Russian</option>
    </select>
</form>
<br>

<c:if test="${empty event}">

<div class="form">
    <form action="addEvent" method="POST">
        <h2><fmt:message key="eventform.addevent"/> </h2>
        <div class="input">
            <div class="inputBox">
                <label><fmt:message key="eventform.title"/></label>
                <input type="text" name="title" required>
            </div>
            <div class="inputBox">
                <label><fmt:message key="eventform.description"/></label>
                <input type="text" name="description" required>
            </div>
            <div class="inputBox">
                <label><fmt:message key="eventform.date"/></label>
                <input type="datetime-local" name="dateTime" min="${minDate}" step="any" required>
            </div>
            <div class="inputBox">
                <label><fmt:message key="eventform.duration"/></label>
                <input type="number" name="duration" min="1" step="1" required>
            </div>
            <div class="inputBox">
                <label><fmt:message key="eventform.place"/></label>
                <input type="text" name="place" required>
            </div>

            <div class="inputBox">
                <fmt:message key="eventform.button.add" var="add"/>
                <input type="submit" value="${add}">
            </div>
        </div>
    </form>

    <c:if test="${message != null}">
    <p class="forgot">${message}</p>
    </c:if>
</c:if>


<c:if test="${!empty event}">
<div class="form">
    <form action="editEvent" method="POST">
        <h2><fmt:message key="eventform.editevent"/></h2>
        <div class="input">
            <div class="inputBox">
                <label><fmt:message key="eventform.title"/></label>
                <input type="text" name="title" value="${event.title}" required>
            </div>
            <div class="inputBox">
                <label><fmt:message key="eventform.description"/></label>
                <input type="text" name="description" value="${event.description}" required>
            </div>
            <div class="inputBox">
                <label><fmt:message key="eventform.date"/></label>
                <input type="datetime-local" name="dateTime" value="${event.dateTime}" min="${minDate}" step="any" required>
            </div>
            <div class="inputBox">
                <label><fmt:message key="eventform.duration"/></label>
                <input type="number" name="duration" value="${event.duration}" min="1" step="1" required>
            </div>
            <div class="inputBox">
                <label><fmt:message key="eventform.place"/></label>
                <input type="text" name="place" value="${event.place}" required>
            </div>
            <input type="hidden" name="eventId" value="${eventId}"/>
            <div class="inputBox">
                <fmt:message key="eventform.button.save" var="save"/>
                <input type="submit" value="${save}">
            </div>
        </div>
    </form>

    <c:if test="${message != null}">
        <p class="forgot">${message}</p>
    </c:if>

    </c:if>
</div>
</body>
</html>

