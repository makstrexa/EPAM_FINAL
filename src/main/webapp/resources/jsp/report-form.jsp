<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.conference.model.UserRole" %>
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

<c:if test="${empty report}">
<div class="form">
    <form action="addReport" method="POST" accept-charset="UTF-8">
        <h2><fmt:message key="reportpage.title.add"/></h2>
        <div class="input">
            <div class="inputBox">
                <label><fmt:message key="reportpage.report.title"/></label>
                <input type="text" name="title" required>
            </div>
            <div class="inputBox">
                <label><fmt:message key="reportpage.report.theme"/></label>
                <input type="text" name="theme" required>
            </div>
            <div class="inputBox">
                <label><fmt:message key="reportpage.report.summary"/></label>
                <input type="text" name="summary" required>
            </div>
            <div class="inputBox">
                <label><fmt:message key="reportpage.report.speaker"/></label>
                <input type="text" name="speakerLogin">
            </div>
            <c:if test="${user.role == UserRole.SPEAKER}">
                <input type="hidden" name="status" value="non-active">
            </c:if>
            <c:if test="${user.role == UserRole.ADMIN}">
            <div class="inputBox">
                <label><fmt:message key="reportpage.report.status"/></label>
                <input type="text" name="status" value="non-active">
            </div>
            </c:if>
            <input type="hidden" name="eventId" value="${eventId}"/>

            <div class="inputBox">
                <fmt:message key="reportpage.button.add" var="add"/>
                <input type="submit" value="${add}">
            </div>
        </div>
    </form>

        <c:if test="${message != null}">
            <p class="forgot">${message}</p>
        </c:if>
    </c:if>


    <c:if test="${!empty report}">
    <div class="form">
        <form action="editReport" method="POST" accept-charset="UTF-8">
            <h2><fmt:message key="reportpage.title.edit"/></h2>
            <div class="input">
                <div class="inputBox">
                    <label><fmt:message key="reportpage.report.title"/></label>
                    <input type="text" name="title" value="${report.title}" required>
                </div>
                <div class="inputBox">
                    <label><fmt:message key="reportpage.report.theme"/></label>
                    <input type="text" name="theme" value="${report.theme}" required>
                </div>
                <div class="inputBox">
                    <label><fmt:message key="reportpage.report.summary"/></label>
                    <input type="text" name="summary" value="${report.summary}" required>
                </div>
                <div class="inputBox">
                    <label><fmt:message key="reportpage.report.speaker"/></label>
                    <input type="text" name="speakerLogin" value="${report.speaker.login}">
                </div>

                <div class="inputBox">
                    <label><fmt:message key="reportpage.report.status"/></label>
                    <input type="text" name="status" value="${report.status.getValue()}" required pattern="[Aa]ctive|[Nn]on-active">
                </div>

                <input type="hidden" name="eventId" value="${eventId}"/>
                <input type="hidden" name="reportId" value="${report.reportId}"/>
                <div class="inputBox">
                    <fmt:message key="reportpage.button.save" var="save"/>
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
