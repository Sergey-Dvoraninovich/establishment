<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="locale" />

<c:set var="disable"><fmt:message key="admin.dishes.disable" /></c:set>
<c:set var="make_available"><fmt:message key="admin.dishes.make_available" /></c:set>
<c:set var="edit"><fmt:message key="edit" /></c:set>
<c:set var="more_information"><fmt:message key="more_information" /></c:set>

<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="../../shared/header.jsp" />
<div class="workspace">
    <c:forEach var="dish" items="${sessionScope.dishes}">
        <div class="dish-info">
            <p>${dish.name}</p>
            <p>
                <a><fmt:message key="admin.dishes.dish_price"/></a>
                <a>${dish.price}</a>
            </p>
            <p>
                <a><fmt:message key="admin.dishes.rating"/></a>
                <a>${dish.averageMark}</a>
            </p>
            <c:if test="${dish.isAvailable}">
                <c:url value="/ApiController?command=disable_dish" var="disable_dish_command"/>
                <form action="${disable_dish_command}" method="post">
                    <input type="hidden" name="dish_id" value="${dish.id}">
                    <input type="submit" value="${disable}">
                </form>
            </c:if>
            <c:if test="${not dish.isAvailable}">
                <c:url value="/ApiController?command=make_dish_available" var="make_dish_available_command"/>
                <form action="${make_dish_available_command}" method="post">
                    <input type="hidden" name="dish_id" value="${dish.id}">
                    <input type="submit" value="${make_available}">
                </form>
            </c:if>
            <c:url value="/ApiController?command=go_to_edit_dish&id=${dish.id}" var="go_to_edit_dish_command"/>
            <form action="${go_to_edit_dish_command}" method="post">
                <input type="hidden" name="dish_id" value="${dish.id}">
                <input type="submit" value="${edit}">
            </form>
            <c:url value="/ApiController?command=go_to_dish_page&id=${dish.id}" var="go_to_dish_page_command"/>
            <form action="${go_to_dish_page_command}" method="post">
                <input type="hidden" name="dish_id" value="${dish.id}">
                <input type="submit" value="${more_information}">
            </form>
        </div>
    </c:forEach>
    <div class="dish-info">
        <c:url value="/ApiController?command=go_to_create_dish" var="dishes_page"/>
        <a href="${dishes_page}"><fmt:message key="admin.dishes.create_dish"/></a>
    </div>
</div>
</body>
<style>
    .workspace {
        margin-top: 35px;
        padding-top: 15px;
    }
    .dish-info{
        margin: 15px;
        padding: 9px;
        color: white;
        background-color: dimgrey;
    }
</style>
</html>
