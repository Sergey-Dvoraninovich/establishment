<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="establishment-tags" %>

<fmt:setBundle basename="locale" />

<c:set var="customer_bonuses">${sessionScope.order_user.bonusesAmount}</c:set>
<c:set var="bonuses_in_payment">${sessionScope.order.bonusesInPayment}</c:set>
<c:set var="recalculate_price_info"><fmt:message key="basket.recalculate_price"/></c:set>
<c:set var="set_price"><fmt:message key="set"/> ${sessionScope.order.finalPrice}</c:set>

<html>
<head>
    <title><fmt:message key="orders.order_title"/></title>
    <link href="../css/pages/order.css" rel="stylesheet">
</head>
<body>
<jsp:include page="shared/header.jsp" />
<div class="workspace-flex-container">
    <div class="block-item">
        <div class="block-item-header">
            <h2><fmt:message key="orders.order_title"/></h2>
        </div>
        <c:if test="${order.orderState == 'ACTIVE'}">
            <div class="line-item">
                <div><fmt:message key="basket.order_state"/></div>
                <div id="green-state-1"><fmt:message key="order_state.active"/></div>
            </div>
        </c:if>
        <c:if test="${order.orderState == 'COMPLETED'}">
            <div class="line-item">
                <div><fmt:message key="basket.order_state"/></div>
                <div id="green-state"><fmt:message key="order_state.completed"/></div>
            </div>
        </c:if>
        <c:if test="${order.orderState == 'EXPIRED'}">
            <div class="line-item">
                <div><fmt:message key="basket.order_state"/></div>
                <div id="red-state"><fmt:message key="order_state.expired"/></div>
            </div>
        </c:if>
        <c:if test="${order.orderState == 'CREATED'}">
            <div class="line-item">
                <div><fmt:message key="basket.order_state"/></div>
                <div id="base-state"><fmt:message key="order_state.created"/></div>
            </div>
        </c:if>
        <div class="line-item">
            <div><fmt:message key="basket.order_time"/></div>
            <div>
                <ctg:localDateTime time="${sessionScope.order.orderTime}"/>
            </div>
        </div>
        <c:if test="${sessionScope.order.orderState == 'COMPLETED' || sessionScope.order.orderState == 'EXPIRED'}">
            <div class="line-item">
                <div><fmt:message key="basket.finish_time"/></div>
                <div>
                    <ctg:localDateTime time="${sessionScope.order.finishTime}"/>
                </div>
            </div>
        </c:if>
        <div class="block-item-text">
            <div class="dishes-container">
                <c:forEach var="dish_list_item" items="${sessionScope.order_dish_list_items}">
                    <div class="dish-container">
                        <div class="dish-container-column">
                            <div id="icon-container" class="photo-container">
                                <img class="dish-picture"
                                     src="../images/dish/${sessionScope.order_dishes_map.get(dish_list_item.dishId).photo}"
                                     onerror="this.src='../images/default_dish.png';">
                            </div>
                        </div>
                        <div class="dish-container-column">
                            <c:if test="${sessionScope.user.role == 'ADMIN'}">
                            <div class="dish-container-line">
                                <c:url value="/ApiController?command=delete_order_dish&id_dish_list_item=${dish_list_item.id}&id_order=${sessionScope.order.id}" var="delete_order_dish"/>
                                <a href="${delete_order_dish}">
                                    <img class="control-picture"
                                         src="../images/delete.png">
                                </a>
                            </div>
                            </c:if>
                            <div class="dish-container-line">
                                <div class="dish-text">
                                    <a>
                                            ${sessionScope.order_dishes_map.get(dish_list_item.dishId).name}
                                    </a>
                                </div>
                            </div>
                            <div id="amount-control" class="dish-container-line">
                                <c:if test="${sessionScope.user.role == 'ADMIN'}">
                                <c:url value="/ApiController?command=decrement_order_dish&id_dish_list_item=${dish_list_item.id}&id_order=${sessionScope.order.id}" var="decrement_order_dish"/>
                                <a href="${decrement_order_dish}">
                                    <img class="control-picture"
                                         src="../images/reduce.png">
                                </a>
                                </c:if>
                                <div class="dish-text">
                                    <a>${dish_list_item.dishAmount}</a>
                                </div>
                                <c:if test="${sessionScope.user.role == 'ADMIN'}">
                                <c:url value="/ApiController?command=increment_order_dish&id_dish_list_item=${dish_list_item.id}&id_order=${sessionScope.order.id}" var="increment_order_dish"/>
                                <a href="${increment_order_dish}">
                                    <img class="control-picture"
                                         src="../images/add.png">
                                </a>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <c:if test="${sessionScope.user.role == 'CUSTOMER'}">
            <div class="line-item">
                <c:if test="${sessionScope.order.paymentType == 'CASH'}">
                    <div class="line-item">
                        <div><fmt:message key="basket.payment_type"/></div>
                        <div><fmt:message key="payment_type.cash"/></div>
                    </div>
                </c:if>
                <c:if test="${sessionScope.order.paymentType == 'CARD'}">
                    <div class="line-item">
                        <div><fmt:message key="basket.payment_type"/></div>
                        <div><fmt:message key="payment_type.card"/></div>
                    </div>
                </c:if>
            </div>
            <div class="line-item">
                <div class="line-item">
                    <div><fmt:message key="basket.bonuses_in_payment"/></div>
                    <div>${sessionScope.order.bonusesInPayment}</div>
                </div>
            </div>
            <div class="line-item">
                <div class="line-item">
                    <div><fmt:message key="basket.final_price"/></div>
                    <div>${sessionScope.order.finalPrice} <fmt:message key="currency"/> </div>
                </div>
            </div>
        </c:if>
        <c:if test="${sessionScope.user.role == 'ADMIN'}">
            <c:url value="/ApiController?command=edit_order&id=${sessionScope.order.id}" var="edit_order"/>
            <form action="${edit_order}" method="post">
                <div class="form-row">
                    <div class="radio-container">
                        <div id="active" class="form_radio_btn">
                            <c:if test="${sessionScope.order.orderState == 'ACTIVE'}">
                                <input id="radio-active" type="radio" name="order_state" value="ACTIVE" checked>
                                <label for="radio-active"><fmt:message key="order_state.active"/></label>
                            </c:if>
                            <c:if test="${sessionScope.order.orderState != 'ACTIVE'}">
                                <input id="radio-active" type="radio" name="order_state" value="ACTIVE">
                                <label for="radio-active"><fmt:message key="order_state.active"/></label>
                            </c:if>
                        </div>
                        <div id="completed" class="form_radio_btn">
                            <c:if test="${sessionScope.order.orderState == 'COMPLETED'}">
                                <input id="radio-completed" type="radio" name="order_state" value="COMPLETED" checked>
                                <label for="radio-completed"><fmt:message key="order_state.completed"/></label>
                            </c:if>
                            <c:if test="${sessionScope.order.orderState != 'COMPLETED'}">
                                <input id="radio-completed" type="radio" name="order_state" value="COMPLETED">
                                <label for="radio-completed"><fmt:message key="order_state.completed"/></label>
                            </c:if>
                        </div>
                        <div id="expired" class="form_radio_btn">
                            <c:if test="${sessionScope.order.orderState == 'EXPIRED'}">
                                <input id="radio-expired" type="radio" name="order_state" value="EXPIRED" checked>
                                <label for="radio-expired"><fmt:message key="order_state.expired"/></label>
                            </c:if>
                            <c:if test="${sessionScope.order.orderState != 'EXPIRED'}">
                                <input id="radio-expired" type="radio" name="order_state" value="EXPIRED">
                                <label for="radio-expired"><fmt:message key="order_state.expired"/></label>
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="form-row">
                    <c:if test="${sessionScope.order.paymentType == 'CASH'}">
                        <div class="radio-container">
                            <div class="form_radio_btn">
                                <input id="radio-cash" type="radio" name="payment_type" value="CASH" checked>
                                <label for="radio-cash"><fmt:message key="basket.pay_in_cash"/></label>
                            </div>
                            <div class="form_radio_btn">
                                <input id="radio-card" type="radio" name="payment_type" value="CARD">
                                <label for="radio-card"><fmt:message key="basket.pay_by_card"/></label>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.order.paymentType == 'CARD'}">
                        <div class="radio-container">
                            <div class="form_radio_btn">
                                <input id="radio-cash-copy" type="radio" name="payment_type" value="CASH">
                                <label for="radio-cash-copy"><fmt:message key="basket.pay_in_cash"/></label>
                            </div>
                            <div class="form_radio_btn">
                                <input id="radio-card-copy" type="radio" name="payment_type" value="CARD" checked>
                                <label for="radio-card-copy"><fmt:message key="basket.pay_by_card"/></label>
                            </div>
                        </div>
                    </c:if>
                </div>
                <div class="form-row">
                    <div>
                        <label for="bonuses_in_payment"><fmt:message key="basket.customer_bonuses"/>: ${customer_bonuses}</label>
                    </div>
                    <label for="bonuses_in_payment"><fmt:message key="basket.bonuses_in_payment" /></label>
                    <input type="number" step="1" min="0" max="${customer_bonuses}" name="bonuses_in_payment" id="bonuses_in_payment"
                           value="${bonuses_in_payment}" placeholder="${bonuses_in_payment}"/>
                </div>
                <div>
                    <input type="submit" value="${set_price}"/>
                </div>
                <c:if test="${sessionScope.edit_order_error}">
                    <div class="local-error">
                        <p><fmt:message key="orders.edit_order_error"/></p>
                    </div>
                </c:if>
                <c:if test="${sessionScope.too_many_bonuses}">
                    <div class="local-error">
                        <p><fmt:message key="basket.too_many_bonuses"/></p>
                    </div>
                </c:if>
                <c:if test="${sessionScope.not_enough_bonuses}">
                    <div class="local-error">
                        <p><fmt:message key="basket.not_enough_bonuses"/></p>
                    </div>
                </c:if>
                <c:if test="${sessionScope.you_should_choose_something}">
                    <div class="local-error">
                        <p><fmt:message key="basket.you_should_order_smth"/></p>
                    </div>
                </c:if>
                <c:if test="${sessionScope.invalid_order_states}">
                    <div class="local-error">
                        <p><fmt:message key="filter.invalid_order_states"/></p>
                    </div>
                </c:if>
                <c:if test="${sessionScope.invalid_payment_types}">
                    <div class="local-error">
                        <p><fmt:message key="filter.invalid_payment_types"/></p>
                    </div>
                </c:if>
            </form>
        </c:if>
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
        font: 20px 'Roboto', Arial, Helvetica, sans-serif;
        margin: 25px;
    }
    a {
        font: 20px 'Roboto', Arial, Helvetica, sans-serif;
        font-size: 15px;
        text-decoration: none;
    }
    div {
        font: 20px 'Roboto', Arial, Helvetica, sans-serif;
    }

    #description{
        display: flex;
        flex-direction: column;
        align-items: flex-start;
        justify-content: flex-start;
    }
    .row-item-flexbox{
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: flex-start;
    }
    .row-item-flexbox-item>h2{
        margin-left: 10px;
    }

    .block-item{
        border-radius: 10px;
        padding: 10px;
        margin: 10px;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        -webkit-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.15);
        -moz-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.15);
        box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.15);
    }
    #icon{
        padding: 0px;
    }
    .block-item:hover{
        -webkit-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
        -moz-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
        box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
    }

    .block-item-header{
        margin: 0px;
        padding: 0px;
    }
    .block-item-header>h2{
        color: #000000;
        font-size: 30px;
    }
    .block-item-text{
    }
    .block-item-text>a{
        color: #4d4d4d;
        font-size: 20px;
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
    #description-action {
        margin-left: 55px;
    }
    .block-item-action:hover {
        -webkit-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
        -moz-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
        box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
    }
    .block-item-action>a {
        color: #ffffff;
        font-size: 25px;
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
    .radio-container {
        display: flex;
        flex-direction: row;
        align-items: flex-start;
        justify-content: center;
    }
    .form_radio_btn {
        display: inline-block;
        margin-right: 10px;
        margin-bottom: 15px;
        border-radius: 5px;
        width: -moz-available;
        -webkit-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.15);
        -moz-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.15);
        box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.15);
    }
    .form_radio_btn:hover {
        -webkit-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
        -moz-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
        box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
    }
    .form_radio_btn input[type=radio] {
        display: none;
    }
    .form_radio_btn label {
        width: available;
        display: inline-block;
        cursor: pointer;
        padding: 0px 15px;
        line-height: 34px;
        border:2px solid #aaa;
        border-radius: 5px;
        user-select: none;
    }

    .form_radio_btn input[type=radio]:checked + label {
        color: #ffffff;
        border:2px solid #804451;
        background-color: #804451;
    }
    #active input[type=radio]:checked + label {
        color: #ffffff;
        border:2px solid #7ba05b;
        background-color: #7ba05b;
    }
    #completed input[type=radio]:checked + label {
        color: #ffffff;
        border:2px solid #7ba05b;
        background-color: #7ba05b;
    }
    #expired input[type=radio]:checked + label {
        color: #ffffff;
        border:2px solid #cf361b;
        background-color: #cf361b;
    }
    .local-error {
        font-size: 15px;
        color: red;
    }
    .dishes-container{
        display: flex;
        flex-direction: column;
        align-items: flex-start;
        justify-content: stretch;
    }
    .dish-container{
        display: flex;
        flex-direction: row;
        align-items: flex-start;
        justify-content: space-between;
    }
    #icon-container {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: space-between;
    }
    .dish-container-column {
        height: 150px;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
    }
    #amount-control {
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: center;
    }
    .photo-container {
        height: 150px;
        width: 270px;
    }
    .dish-picture {
        height: inherit;
        width: auto;
    }
    .control-picture {
        margin: 10px;
        height: 30px;
        width: 30px;
    }
    .dish-text a {
        font-size: 25px;
    }
    #base-state {
        width: max-content;
        padding: 5px;
        border-radius: 5px;
        color: #ffffff;
        background-color: #a15566;
    }
    #green-state {
        width: max-content;
        padding: 5px;
        border-radius: 5px;
        color: #ffffff;
        background-color: #7ba05b;
    }
    #green-state-1 {
        width: max-content;
        padding: 5px;
        border-radius: 5px;
        color: #ffffff;
        background-color: #7ba05b;
    }
    #red-state {
        width: max-content;
        padding: 5px;
        border-radius: 5px;
        color: #ffffff;
        background-color: #cf361b;
    }
    .line-item {
        width: -webkit-fill-available;
        margin: 5px;
        display: flex;
        flex-direction: row;
        align-items: flex-start;
        justify-content: space-between;
    }
    .line-item>div {
        margin: 0px 10px;
        font-size: 20px;
    }
    form {
        width: 100%;
    }
</style>
</html>
