<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="locale" />

<c:set var="find"><fmt:message key="filter.find" /></c:set>

<c:set var="request_filter_min_price">${sessionScope.orders_filter_min_price}</c:set>
<c:set var="request_filter_max_price">${sessionScope.orders_filter_max_price}</c:set>

<html>
<head>
    <title><fmt:message key="profile.orders_title"/></title>
</head>
<body>
<jsp:include page="../shared/header.jsp" />
<div class="filter-line">
    <c:url value="/ApiController?command=set_orders_filter_parameters" var="orders_filter"/>
    <form action="${orders_filter}" method="post">
        <div id="order-states" class="form-row">
            <div class="checkbox-container">
                <div><fmt:message key="orders.order_states"/></div>
                <div>
                    <div class="form-checkbox-btn">
                        <c:if test="${sessionScope.orders_filter_order_states.contains('CREATED')}">
                            <input id="check-created" type="checkbox" name="request_filter_order_states" value="CREATED" checked>
                        </c:if>
                        <c:if test="${not sessionScope.orders_filter_order_states.contains('CREATED')}">
                            <input id="check-created" type="checkbox" name="request_filter_order_states" value="CREATED">
                        </c:if>
                        <label for="check-created"><fmt:message key="order_state.created"/></label>
                    </div>
                    <div class="form-checkbox-btn">
                        <c:if test="${sessionScope.orders_filter_order_states.contains('ACTIVE')}">
                            <input id="check-active" type="checkbox" name="request_filter_order_states" value="ACTIVE" checked>
                        </c:if>
                        <c:if test="${not sessionScope.orders_filter_order_states.contains('ACTIVE')}">
                            <input id="check-active" type="checkbox" name="request_filter_order_states" value="ACTIVE">
                        </c:if>
                        <label for="check-active"><fmt:message key="order_state.active"/></label>
                    </div>
                    <div class="form-checkbox-btn">
                        <c:if test="${sessionScope.orders_filter_order_states.contains('COMPLETED')}">
                            <input id="check-completed" type="checkbox" name="request_filter_order_states" value="COMPLETED" checked>
                        </c:if>
                        <c:if test="${not sessionScope.orders_filter_order_states.contains('COMPLETED')}">
                            <input id="check-completed" type="checkbox" name="request_filter_order_states" value="COMPLETED">
                        </c:if>
                        <label for="check-completed"><fmt:message key="order_state.completed"/></label>
                    </div>
                    <div class="form-checkbox-btn">
                        <c:if test="${sessionScope.orders_filter_order_states.contains('EXPIRED')}">
                            <input id="check-expired" type="checkbox" name="request_filter_order_states" value="EXPIRED" checked>
                        </c:if>
                        <c:if test="${not sessionScope.orders_filter_order_states.contains('EXPIRED')}">
                            <input id="check-expired" type="checkbox" name="request_filter_order_states" value="EXPIRED">
                        </c:if>
                        <label for="check-expired"><fmt:message key="order_state.expired"/></label>
                    </div>
                </div>
                <c:if test="${sessionScope.invalid_order_states}">
                    <div class="local-error">
                        <p><fmt:message key="filter.invalid_order_states"/></p>
                    </div>
                </c:if>
            </div>
        </div>
        <div id="min-price" class="form-row">
            <label for="request_filter_min_price"><fmt:message key="filter.min_price" /></label>
            <input type="number" name="request_filter_min_price" id="request_filter_min_price"
                   step="0.01" min="0" value="${request_filter_min_price}" placeholder="${request_filter_min_price}"/>
            <c:if test="${sessionScope.invalid_min_price}">
                <div class="local-error">
                    <p><fmt:message key="filter.invalid_filter_params"/></p>
                </div>
            </c:if>
        </div>
        <div id="max-price" class="form-row">
            <label for="request_filter_max_price"><fmt:message key="filter.max_price" /></label>
            <input type="number" name="request_filter_max_price" id="request_filter_max_price"
                   step="0.01" min="0" value="${request_filter_max_price}" placeholder="${request_filter_max_price}"/>
            <c:if test="${sessionScope.invalid_max_price}">
                <div class="local-error">
                    <p><fmt:message key="filter.invalid_filter_params"/></p>
                </div>
            </c:if>
        </div>
        <div class="form-row">
            <div class="checkbox-container">
                <div><fmt:message key="basket.payment_type"/></div>
                <div>
                    <div class="form-checkbox-btn">
                        <c:if test="${sessionScope.orders_filter_payment_types.contains('CASH')}">
                            <input id="check-cash" type="checkbox" name="request_filter_payment_types" value="CASH" checked>
                        </c:if>
                        <c:if test="${not sessionScope.orders_filter_payment_types.contains('CASH')}">
                            <input id="check-cash" type="checkbox" name="request_filter_payment_types" value="CASH">
                        </c:if>
                        <label for="check-cash"><fmt:message key="cash"/></label>
                    </div>
                    <div class="form-checkbox-btn">
                        <c:if test="${sessionScope.orders_filter_payment_types.contains('CARD')}">
                            <input id="check-card" type="checkbox" name="request_filter_payment_types" value="CARD" checked>
                        </c:if>
                        <c:if test="${not sessionScope.orders_filter_payment_types.contains('CARD')}">
                            <input id="check-card" type="checkbox" name="request_filter_payment_types" value="CARD">
                        </c:if>
                        <label for="check-card"><fmt:message key="card"/></label>
                    </div>
                </div>
                <c:if test="${sessionScope.invalid_payment_types}">
                    <div class="local-error">
                        <p><fmt:message key="filter.invalid_payment_types"/></p>
                    </div>
                </c:if>
            </div>
        </div>
        <div id="find-action">
            <input type="submit" value="${find}"/>
            <c:if test="${sessionScope.invalid_filter_parameters}">
                <div class="local-error">
                    <p><fmt:message key="filter.invalid_filter_params"/></p>
                </div>
            </c:if>
        </div>
    </form>
</div>
<div class="workspace-flex-container">
    <c:forEach var="order" items="${sessionScope.orders}">
        <div class="container-line">
            <c:if test="${order.orderState == 'ACTIVE'}">
                <div id="green-state" class="line-item">
                    <div><fmt:message key="basket.order_state"/></div>
                    <div><fmt:message key="order_state.active"/></div>
                </div>
            </c:if>
            <c:if test="${order.orderState == 'COMPLETED'}">
                <div id="green-state" class="line-item">
                    <div><fmt:message key="basket.order_state"/></div>
                    <div><fmt:message key="order_state.completed"/></div>
                </div>
            </c:if>
            <c:if test="${order.orderState == 'EXPIRED'}">
                <div id="red-state" class="line-item">
                    <div><fmt:message key="basket.order_state"/></div>
                    <div><fmt:message key="order_state.expired"/></div>
                </div>
            </c:if>
            <c:if test="${order.orderState == 'CREATED'}">
                <div id="base-state" class="line-item">
                    <div><fmt:message key="basket.order_state"/></div>
                    <div><fmt:message key="order_state.created"/></div>
                </div>
            </c:if>
            <div id="time-info" class="line-item">
                <div><fmt:message key="basket.order_time"/></div>
                <div>${order.orderTime}</div>
            </div>
            <c:if test="${order.paymentType == 'CASH'}">
                <div id="main-info" class="line-item">
                    <div>${order.finalPrice} <fmt:message key="currency"/> <fmt:message key="payment_type.cash"/></div>
                </div>
            </c:if>
            <c:if test="${order.paymentType == 'CARD'}">
                <div id="main-info" class="line-item">
                    <div>${order.finalPrice} <fmt:message key="currency"/> <fmt:message key="payment_type.card"/></div>
                </div>
            </c:if>
            <div class="line-item">
                <div class="block-item-action">
                    <c:url value="/ApiController?command=go_to_order_page&id_order=${order.id}" var="order_page"/>
                    <a href="${order_page}"><fmt:message key="details"/></a>
                </div>
            </div>
        </div>
    </c:forEach>
    <div class="pagination">
        <c:if test="${sessionScope.min_pos != 1}">
            <div class="block-item-action">
                <c:url value="/ApiController?command=go_to_customer_orders&next_min_pos=${sessionScope.min_pos-sessionScope.page_items_amount}&next_max_pos=${sessionScope.min_pos-1}&user_id=${sessionScope.user_profile_id}" var="prev_order_page"/>
                <a href="${prev_order_page}"><fmt:message key="previous"/></a>
            </div>
        </c:if>
        <c:if test="${sessionScope.max_pos != sessionScope.total_amount}">
            <div class="block-item-action">
                <c:url value="/ApiController?command=go_to_customer_orders&next_min_pos=${sessionScope.max_pos+1}&next_max_pos=${sessionScope.max_pos+sessionScope.page_items_amount}&user_id=${sessionScope.user_profile_id}" var="next_order_page"/>
                <a href="${next_order_page}"><fmt:message key="next"/></a>
            </div>
        </c:if>
    </div>
</div>
</body>
<style>
    body {
        font: 15px 'Roboto', Arial, Helvetica, sans-serif;
        font-size: 15px;
    }
    #min-price {
        width: 100px;
    }
    #max-price {
        width: 100px;
    }
    #find-action {
        width: 100px;
    }
    #order-states {
        width: 35%;
    }
    .filter-line {
        margin-top: 75px;
    }
    .filter-line>form {
        display: flex;
        flex-direction: row;
        justify-content: center;
        align-items: center;
    }
    .filter-line>form>div {
        width: 20%;
        margin: 0px 10px;
    }
    input[type=text]{
        width:100%;
        border:2px solid #aaa;
        border-radius:5px;
        margin:8px 0;
        outline:none;
        padding:8px;
        box-sizing:border-box;
        transition:.3s;
    }
    input[type=text]:focus{
        border-color:#a15566;
        box-shadow:0 0 8px 0 #a15566;
    }
    input[type=number]{
        width:100%;
        border:2px solid #aaa;
        border-radius:5px;
        margin:8px 0;
        outline:none;
        padding:8px;
        box-sizing:border-box;
        transition:.3s;
    }
    input[type=number]:focus{
        border-color:#a15566;
        box-shadow:0 0 8px 0 #a15566;
    }
    input[type="submit"]{
        font-size: 20px;
        color: #ffffff;
        border: none;
        border-radius: 10px;
        margin-top: 15px;
        padding: 5px;
        text-align: center;
        width: 100%;
        background-color: #a15566;
    }
    input[type="submit"]:hover {
        background-color: #804451;
    }
    .orders-user-actions {
        display: flex;
        flex-direction: column;
        align-items: flex-start;
        justify-content: center;
    }
    .user-info {
        display: flex;
        flex-direction: row;
        justify-content: center;
        align-items: flex-start;
    }
    .user-info>div {
        margin: 5px;
    }

    .workspace-flex-container {
        margin-top: 5px;
        padding-top: 5px;
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
    .profile-picture {
        height: 50px;
        width: 50px;
        border-radius: 25px;
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
    .action {
        margin-top: 5px;
        padding: 5px;
        border-radius: 10px;
        text-align: center;
        background-color: #a15566;
    }
    .action a {
        color: #ffffff;
    }
    .action:hover {
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
        width: 120px;
        color: #ffffff;
        background-color: #a15566;
    }
    #green-state {
        width: 120px;
        color: #ffffff;
        background-color: #7ba05b;
    }
    #red-state {
        width: 120px;
        color: #ffffff;
        background-color: #cf361b;
    }


    .checkbox-container {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
    }
    .form-checkbox-btn {
        display: inline-block;
        margin: 8px 10px;
        border-radius: 5px;
        width: -moz-available;
    }
    .form-checkbox-btn:hover {
        -webkit-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
        -moz-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
        box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
    }
    .form-checkbox-btn input[type=checkbox] {
        display: none;
    }
    .form-checkbox-btn label {
        width: available;
        display: inline-block;
        cursor: pointer;
        padding: 0px 15px;
        line-height: 34px;
        border:2px solid #aaa;
        border-radius: 5px;
        user-select: none;
    }

    .form-checkbox-btn input[type=checkbox]:checked + label {
        color: #ffffff;
        border:2px solid #804451;
        background-color: #804451;
    }
</style>
</html>
