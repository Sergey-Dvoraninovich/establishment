<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="locale" />

<c:set var="customer_bonuses">${sessionScope.user.bonusesAmount}</c:set>
<c:set var="bonuses_in_payment">${sessionScope.order.bonusesInPayment}</c:set>
<c:set var="recalculate_price_info"><fmt:message key="basket.recalculate_price"/></c:set>
<c:set var="basket_price"><fmt:message key="basket.buy_for"/> ${sessionScope.order.finalPrice}</c:set>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><fmt:message key="profile.orders_title"/></title>
</head>
<body>
<jsp:include page="../shared/header.jsp" />
<div class="workspace-flex-container">
    <c:forEach var="order" items="${sessionScope.orders}">
        <div class="container-line">
            <div class="line-item">
                <div><fmt:message key="basket.order_state"/></div>
                <div>${order.orderStete}</div>
            </div>
            <div class="line-item">
                <div><fmt:message key="basket.order_time"/></div>
                <div>${order.orderTime}</div>
            </div>
            <div class="line-item">
                <div><fmt:message key="basket.payment_type"/></div>
                <div>${order.paymentTime}</div>
            </div>
            <div class="line-item">
                <div><fmt:message key="basket.bonuses_in_payment"/></div>
                <div>${order.bonusesInPayment}</div>
            </div>
            <div class="line-item">
                <div><fmt:message key="basket.final_price"/></div>
                <div>${order.finalPrice}</div>
            </div>
            <div class="line-item">
                <div class="block-item-action">
                    <c:url value="/ApiController?command=go_to_order_page" var="order_page"/>
                    <a href="${order_page}"><fmt:message key="profile.verify_mail"/></a>
                </div>
            </div>
        </div>
    </c:forEach>
    <div class="pagination">
        <c:if test="${sessionScope.min_pos != 1}">
            <div class="block-item-action">
                <c:url value="/ApiController?go_to_customer_orders&next_min_pos=${sessionScope.max_pos-sessionScope.page_items_amount}&next_max_pos=${sessionScope.max_pos-1}" var="prev_order_page"/>
                <a href="${prev_order_page}"><fmt:message key="profile.verify_mail"/></a>
            </div>
        </c:if>
        <c:if test="${sessionScope.max_pos != sessionScope.total_amount}">
            <div class="block-item-action">
                <c:url value="/ApiController?go_to_customer_orders&next_min_pos=${sessionScope.max_pos+1}&next_max_pos=${sessionScope.max_pos+sessionScope.page_items_amount}" var="next_order_page"/>
                <a href="${next_order_page}"><fmt:message key="profile.verify_mail"/></a>
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
        justify-content: center;
        align-items: flex-start;
    }
    .container-line {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        align-items: flex-start;
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
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        align-items: flex-start;
    }
    .line-item {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: flex-start;
    }
    .block-item-action {
        position: center;
        border-radius: 10px;
        margin-top: 15px;
        padding: 5px;
        text-align: center;
        width: 70%;
        background-color: #a15566;
    }
    .block-item-action:hover {
        -webkit-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
        -moz-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
        box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
        background-color: #a15566;
    }

</style>
</html>
