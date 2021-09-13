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
    <div class="workspace-flex-container">
    <c:forEach var="dish" items="${sessionScope.dishes}">
        <div class="flex-block">
            <div>
                <a>${dish.name}</a>
            </div>
            <div id="icon" class="block-item">
                <img class="dish-picture"
                     src="../../images/default_dish.png">
            </div>
            <div class="block-item">
                <a>${dish.calories} <fmt:message key="admin.dishes.calories"/></a>
            </div>
            <div class="block-item">
                <a>${dish.amountGrams} <fmt:message key="admin.dishes.grams"/></a>
            </div>
            <div class="block-item">
                <a>${dish.price} <fmt:message key="admin.dishes.BYN"/></a>
            </div>
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
</div>
</body>
<style>
    a {
        font-size: 15px;
        text-decoration: none;
    }
    .workspace-flex-container {
        margin-top: 35px;
        display: flex;
        flex-flow: row wrap;
        align-content: space-around;
    }
    .flex-block {
        flex-flow: row nowrap;
        align-content: space-around;
        color: white;
        font-size: 15px;
        margin: 15px;
        padding: 9px 25px;
        width: min-content;
        border-radius: 5px;
        -webkit-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.15);
        -moz-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.15);
        box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.15);
    }
    .flex-block:hover {
        -webkit-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
        -moz-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
        box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
    }
    .dish-picture {
        height: 70px;
        width: 100px;
    }
    #add-button {
        background-color: #a15566;
    }
    #add-button>a {
        color: #ffffff;
    }
    #add-button:hover {
        background-color: #804451;
    }
</style>
</html>
