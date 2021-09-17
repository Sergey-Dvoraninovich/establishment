<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="locale" />

<c:set var="customer_bonuses">${sessionScope.user.bonusesAmount}</c:set>
<c:set var="bonuses_in_payment">${sessionScope.order.bonusesInPayment}</c:set>
<c:set var="basket_price"><fmt:message key="basket.buy_for"/> ${sessionScope.order.finalPrice}</c:set>

<html>
<head>
    <link href = "../../css/style.css" rel = "style" type = "text/css" />
    <title><fmt:message key="basket.title"/></title>
</head>
<body>
<jsp:include page="../shared/header.jsp" />
<div class="workspace-flex-container">
    <div class="block-item">
        <div class="block-item-header">
            <h2><fmt:message key="basket.title"/></h2>
        </div>
        <div class="block-item-text">
            <c:forEach var="dish_list_item" items="${sessionScope.order_dish_list_items}">
                <img class="profile-picture"
                     src="../../images/dish/${sessionScope.order_dishes_map.get(dish_list_item.dishId).photo}"
                     onerror="this.src='../../images/default_dish.png';">
                ${sessionScope.order_dishes_map.get(dish_list_item.dishId).name}
                ${dish_list_item.dishAmount}
            </c:forEach>
            <form>
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
                            <input id="radio-cash-copy" type="radio" name="payment_type" value="CASH" checked>
                            <label for="radio-cash"><fmt:message key="basket.pay_in_cash"/></label>
                        </div>
                        <div class="form_radio_btn">
                            <input id="radio-card-copy" type="radio" name="payment_type" value="CARD">
                            <label for="radio-card"><fmt:message key="basket.pay_by_card"/></label>
                        </div>
                        </div>
                    </c:if>
                </div>
                <div class="form-row">
                    <div>
                        <label for="bonuses_in_payment"><fmt:message key="basket.your_bonuses"/>: ${customer_bonuses}</label>
                    </div>
                    <label for="bonuses_in_payment"><fmt:message key="basket.bonuses_in_payment" /></label>
                    <input type="number" step="1" min="0" max="${customer_bonuses}" name="bonuses_in_payment" id="bonuses_in_payment"
                           value="${bonuses_in_payment}" placeholder="${bonuses_in_payment}"/>
                    <c:if test="${sessionScope.invalid_bonuces_amount}">
                        <div class="local-error">
                            <p><fmt:message key="admin.dishes.invalid_dish_calories_amount"/></p>
                        </div>
                    </c:if>
                </div>
                <div>
                    <input type="submit" value="${basket_price}"/>
                </div>
            </form>
        </div>
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
    }
    a {
        font-size: 15px;
        text-decoration: none;
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
        flex-direction: column;
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
</style>
</html>