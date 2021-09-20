<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="locale" />

<html>
<head>
    <title><fmt:message key="profile.orders_title"/></title>
</head>
<body>
<jsp:include page="../shared/header.jsp" />
<div class="workspace-flex-container">
    <c:forEach var="order" items="${sessionScope.orders}">
        <div class="container-line">
            <c:if test="${order.orderState == 'ACTIVE' || order.orderState == 'COMPLETED'}">
                <div id="green-state" class="line-item">
                    <div><fmt:message key="basket.order_state"/></div>
                    <div>${order.orderState}</div>
                </div>
            </c:if>
            <c:if test="${order.orderState == 'EXPIRED'}">
                <div id="red-state" class="line-item">
                    <div><fmt:message key="basket.order_state"/></div>
                    <div>${order.orderState}</div>
                </div>
            </c:if>
            <c:if test="${order.orderState == 'CREATED'}">
                <div id="base-state" class="line-item">
                    <div><fmt:message key="basket.order_state"/></div>
                    <div>${order.orderState}</div>
                </div>
            </c:if>
            <div id="time-info" class="line-item">
                <div><fmt:message key="basket.order_time"/></div>
                <div>${order.orderTime}</div>
            </div>
            <div id="main-info" class="line-item">
                <div>${order.finalPrice} <fmt:message key="currency"/> ${order.paymentType} </div>
            </div>
            <div class="line-item">
                <div class="block-item-action">
                    <c:url value="/ApiController?command=go_to_order_page&id=${order.id}" var="order_page"/>
                    <a href="${order_page}"><fmt:message key="details"/></a>
                </div>
            </div>
        </div>
    </c:forEach>
    <div class="pagination">
        <c:if test="${sessionScope.min_pos != 1}">
            <div class="block-item-action">
                <c:url value="/ApiController?command=go_to_customer_orders&next_min_pos=${sessionScope.min_pos-sessionScope.page_items_amount}&next_max_pos=${sessionScope.min_pos-1}" var="prev_order_page"/>
                <a href="${prev_order_page}"><fmt:message key="previous"/></a>
            </div>
        </c:if>
        <c:if test="${sessionScope.max_pos != sessionScope.total_amount}">
            <div class="block-item-action">
                <c:url value="/ApiController?command=go_to_customer_orders&next_min_pos=${sessionScope.max_pos+1}&next_max_pos=${sessionScope.max_pos+sessionScope.page_items_amount}" var="next_order_page"/>
                <a href="${next_order_page}"><fmt:message key="next"/></a>
            </div>
        </c:if>
    </div>
</div>
</body>
<style>
    .workspace-flex-container {
        font: 15px 'Roboto', Arial, Helvetica, sans-serif;
        font-size: 15px;
        margin-top: 35px;
        padding-top: 15px;
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        align-items: center;
    }
    .container-line {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
        border-radius: 10px;
        margin: 10px;
        padding: 5px;
        -webkit-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.15);
        -moz-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.15);
        box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.15);
    }
    .container-line:hover{
        -webkit-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
        -moz-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
        box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
    }
    .pagination {
        margin: 10px;
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        align-items: flex-start;
    }
    .line-item {
        padding: 5px;
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        align-items: center;
        border-radius: 10px;
    }
    .block-item-action {
        width: min-content;
        margin: 0px;
        padding: 5px;
        font-size: 25px;
        border-radius: 10px;
        text-align: center;
        background-color: #a15566;
    }
    .block-item-action a {
        color: #ffffff;
        font-size: 25px;
    }
    .block-item-action:hover {
        -webkit-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
        -moz-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
        box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
        background-color: #804451;
    }
    #main-info{
        width: 35%;
    }
    #time-info{
        width: 400px;
    }
    #base-state {
        width: 100px;
        color: #ffffff;
        background-color: #a15566;
    }
    #green-state {
        width: 100px;
        color: #ffffff;
        background-color: #7ba05b;
    }
    #red-state {
        width: 100px;
        color: #ffffff;
        background-color: #cf361b;
    }
</style>
</html>
