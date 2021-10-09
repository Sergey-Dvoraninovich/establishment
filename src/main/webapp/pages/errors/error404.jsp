<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale"/>

<html>
<head>
    <title><fmt:message key="error404"/></title>
    <script src="../../js/common.js"></script>
    <link href="../../css/pages/errors/error404.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../shared/header.jsp" />
<div class="error-messages">
    <div class="error-message-title">
        <a><fmt:message key="error404"/></a>
    </div>
    <div class="error-message-text">
        <a><fmt:message key="error404.info"/></a>
    </div>
</div>
</body>
</html>
