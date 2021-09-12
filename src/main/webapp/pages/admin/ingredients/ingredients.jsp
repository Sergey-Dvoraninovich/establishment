<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="locale" />

<html>
<head>
    <title><fmt:message key="admin.ingredients.title"/></title>
</head>
<body>
<jsp:include page="../../shared/header.jsp" />
<div class="workspace-flex-container">
<c:forEach var="ingredient" items="${sessionScope.ingredients}">
    <div class="flex-block">
        <a>${ingredient.name}</a>
    </div>
</c:forEach>
<div id="add-button" class="flex-block">
    <c:url value="/ApiController?command=go_to_create_ingredient_page" var="users_page"/>
    <a href="${users_page}"><fmt:message key="add"/></a>
</div>
</div>
</body>
<style>
    .workspace-flex-container {
        margin-top: 35px;
        display: flex;
        flex-flow: row wrap;
        align-content: space-around;
    }
    .flex-block {
        flex-flow: row nowrap;
        align-content: space-around;
        background-color: dimgrey;
        color: white;
        font-size: 15px;
        margin: 15px;
        padding: 9px 25px;
        width: min-content;
    }
    #add-button {
        flex-flow: row nowrap;
        align-content: space-around;
        background-color: #F77A52;
        color: white;
        font-size: 15px;
        margin: 15px;
        padding: 9px 25px;
        width: min-content;
    }

    a {
        color: white;
        font-size: 15px;
        text-decoration: none;
    }
</style>
</html>
