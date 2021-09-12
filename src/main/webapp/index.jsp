<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="locale" />

<html>
<head>
    <title><fmt:message key="index.title"/></title>
</head>
<body>
<div class="workspace">
    <jsp:include page="pages/shared/header.jsp" />
    <div>
        <c:url value="/ApiController?command=go_to_login_page" var="login"/>
        <a href="${login}">Sign in</a>
    </div>
    <div>
        <c:url value="/ApiController?command=go_to_sign_up_page" var="sign_up_page"/>
        <a href="${sign_up_page}">Sing Up</a>
    </div>
    <div>
        <c:url value="/ApiController?command=test" var="users_page"/>
        <a href="${users_page}">Users</a>
    </div>
    <div>
        <c:url value="/ApiController?command=go_to_ingredients_page" var="ingredients_page"/>
        <a href="${ingredients_page}">Ingredients</a>
    </div>
    <div>
        <c:url value="/ApiController?command=go_to_dishes_page" var="dishes_page"/>
        <a href="${dishes_page}">Dishes</a>
    </div>
    <div>
        <c:url value="/ApiController?command=go_to_customer_profile_page&id=4" var="profile"/>
        <a href="${profile}" >Profile test</a>
    </div>
    <div>
        <a><fmt:message key="index.title"/></a>
    </div>
</div>
</body>
<style>
    .workspace {
        margin-top: 35px;
        padding-top: 15px;
    }
</style>
</html>