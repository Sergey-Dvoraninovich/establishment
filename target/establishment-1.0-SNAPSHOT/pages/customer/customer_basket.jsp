<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="locale" />

<html>
<head>
    <link href = "../../css/style.css" rel = "style" type = "text/css" />
    <title><fmt:message key="profile"/></title>
</head>
<body>
<jsp:include page="../shared/header.jsp" />
<div class="workspace-flex-container">
    <div class="basket-order">
        <div>
            <fmt:message key="basket.order_state"/>
            ${sessionScope.order.orderState}
        </div>
        <div>
            <fmt:message key="basket.order_time"/>
            ${sessionScope.order.orderTime}
        </div>
        <div>
            <fmt:message key="basket.finish_time"/>
            ${sessionScope.order.finishTime}
        </div>
        <div>
            <fmt:message key="basket.payment_type"/>
            ${sessionScope.order.paymentType}
        </div>
        <div>
            <fmt:message key="basket.card_number"/>
            ${sessionScope.order.cardNumber}
        </div>
        <div>
            <fmt:message key="basket.user_id"/>
            ${sessionScope.order.userId}
        </div>
        <div>
            <fmt:message key="basket.bonuses_in_payment"/>
            ${sessionScope.order.bonusesInPayment}
        </div>
        <div>
            <fmt:message key="basket.final_price"/>
            ${sessionScope.order.finalPrice}
        </div>
        <c:forEach var="dish_list_item" items="${sessionScope.order_dish_list_items}">
            <div>
                    ${dish_list_item.dishId}
            </div>
            <div>
                    ${dish_list_item.dishAmount}
            </div>
        </c:forEach>
    </div>
</div>
</body>
<style>
    .workspace-flex-container {
        margin-top: 35px;
        padding-top: 15px;
        display: flex;
        flex-direction: row;
        justify-content: center;
        align-items: flex-start;
    }
    .profile-picture {
        height: 250px;
        width: 250px;
        border-radius: 10px;
    }
    .profile-item-picture {
        height: 35px;
        width: 35px;
    }
    .workspace-column {
        font: 15px 'Roboto', Arial, Helvetica, sans-serif;
        margin: 25px;
        border-radius: 15px;
        -webkit-box-shadow: 0px 5px 8px 5px rgba(34, 60, 80, 0.2);
        -moz-box-shadow: 0px 5px 8px 5px rgba(34, 60, 80, 0.2);
        box-shadow: 0px 5px 8px 5px rgba(34, 60, 80, 0.2);
    }
    .flex-block {
        width: min-content;
    }
    a {
        font-size: 15px;
        text-decoration: none;
    }
</style>
</html>