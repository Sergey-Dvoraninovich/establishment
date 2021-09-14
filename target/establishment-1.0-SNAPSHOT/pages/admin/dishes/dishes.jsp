<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="locale" />

<c:set var="disable"><fmt:message key="admin.dishes.disable" /></c:set>
<c:set var="make_available"><fmt:message key="admin.dishes.make_available" /></c:set>
<c:set var="edit"><fmt:message key="edit" /></c:set>
<c:set var="more_information"><fmt:message key="more_information" /></c:set>
<c:set var="create_dish_message"><fmt:message key="admin.dishes.create_dish"/></c:set>

<html>
<head>
    <title><fmt:message key="admin.dishes.title" /></title>
    <link href="../../../css/pages/admin/dishes/dishes.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../../shared/header.jsp" />
<div class="workspace">
    <div class="workspace-flex-container">
    <c:forEach var="dish" items="${sessionScope.dishes}">
        <div class="flex-block">
            <div class="block-item">
                <h3>${dish.name}</h3>
            </div>
            <div id="dish-picture" class="block-item">
                <img class="dish-picture"
                     src="../../../images/${dish.photo}"
                     onerror="this.src='../../../images/default_dish.png';">
            </div>
            <div id="description" class="block-item">
                <div class="row-item-flexbox">
                    <a>${dish.calories} <fmt:message key="admin.dishes.calories"/></a>
                </div>
                <div class="row-item-flexbox">
                    <a>${dish.amountGrams} <fmt:message key="admin.dishes.grams"/></a>
                </div>
                <div class="row-item-flexbox">
                    <a>${dish.price} <fmt:message key="admin.dishes.BYN"/></a>
                </div>
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
        <div class="flex-block">
            <div class="block-item">
                <h3><fmt:message key="admin.dishes.new_dish"/></h3>
            </div>
            <div id="dish-picture" class="block-item">
                <img class="dish-picture"
                     src="../../../images/default_dish.png">
            </div>
            <c:url value="/ApiController?command=go_to_create_dish" var="create_dish_page"/>
            <div id="dish-add-picture" class="block-item">
                <a href="${create_dish_page}">
                <img id="add-icon" class="dish-picture"
                     src="../../../images/add.png"
                     href="">
                </a>
            </div>
            <form action="${create_dish_page}" method="post">
               <input type="submit" value="${create_dish_message}">
            </form>
        </div>
    </div>
    </div>
</div>
</body>
<style>
    .block-item {
        margin-top: 0px;
        margin-bottom: 0px;
        padding-top: 0px;
        padding-bottom: 0px;
        border: none;
        -webkit-box-shadow: none;
        -moz-box-shadow: none;
        box-shadow: none;
    }
    .block-item:hover {
        border: 0px;
        -webkit-box-shadow: 0px 0px 0px 0px rgba(0, 0, 0, 0);
        -moz-box-shadow: 0px 0px 0px 0px rgba(0, 0, 0, 0);
        box-shadow: 0px 0px 0px 0px rgba(0, 0, 0, 0);
    }
</style>
</html>
