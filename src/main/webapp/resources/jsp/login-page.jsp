<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="local" />
<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <style><%@include file="../css/login-page.css"%></style>
    <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" >
</head>
<body>

<div class="form">
    <form action="login" method="POST" accept-charset="UTF-8">
        <h2><fmt:message key="login.nameofform.login"/></h2>
        <div class="input">
            <div class="inputBox">
                <label><fmt:message key="login.label.login"/></label>
                <input type="text" name="login">
            </div>
            <div class="inputBox">
                <label><fmt:message key="login.label.password"/></label>
                <input type="password" name="password">
            </div>
            <div class="inputBox">
                <fmt:message key="login.submit.button" var="sign"/>
                <input type="submit" name="" value="${sign}">
            </div>
        </div>
    </form>
    <p class="forgot"><fmt:message key="login.href.forgetpass"/> <a href="#"><fmt:message key="login.href.clickhere"/></a></p>
    <form>
        <select id="language" name="language" onchange="submit()">
            <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
            <option value="ru" ${language == 'ru' ? 'selected' : ''}>Russian</option>
        </select>
    </form>
    <c:if test="${message != null}">
        <p class="forgot">${message}</p>
    </c:if>
</div>
</body>
</html>
