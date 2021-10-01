<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale"/>

<html>
<head>
    <title><fmt:message key="error500"/></title>
    <link href="../../css/pages/errors/error500.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../shared/header.jsp" />
<div class="error-messages">
    <div class="error-message-title">
        <a><fmt:message key="error500"/></a>
    </div>
    <div class="error-message-text">
        <a><fmt:message key="error500.info"/></a>
    </div>
    <div class="error-message-text">
        <a>${sessionScope.error}</a>
    </div>
</div>
</body>
</html>
