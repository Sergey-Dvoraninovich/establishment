<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="locale" />

<c:set var="find"><fmt:message key="filter.find" /></c:set>

<c:set var="request_filter_login">${sessionScope.users_filter_login}</c:set>
<c:set var="request_filter_mail">${sessionScope.users_filter_mail}</c:set>
<c:set var="request_filter_phone_number">${sessionScope.users_filter_phone_number}</c:set>
<c:set var="request_filter_card_number">${sessionScope.users_filter_card_number}</c:set>

<html>
<head>
    <title><fmt:message key="profile.orders_title"/></title>
</head>
<body>
<jsp:include page="../../shared/header.jsp" />
<div class="filter-line">
    <c:url value="/ApiController?command=set_orders_filter_parameters" var="orders_filter"/>
    <form action="${orders_filter}" method="post">
        <div id="user-statuses" class="form-row">
            <div class="checkbox-container">
                <div><fmt:message key="filter.user_statuses"/></div>
                <div>
                    <div class="form-checkbox-btn">
                        <c:if test="${sessionScope.users_filter_user_statuses.contains('IN_REGISTRATION')}">
                            <input id="check-in-registration" type="checkbox" name="request_filter_user_statuses" value="IN_REGISTRATION" checked>
                        </c:if>
                        <c:if test="${not sessionScope.users_filter_user_statuses.contains('IN_REGISTRATION')}">
                            <input id="check-in-registration" type="checkbox" name="request_filter_user_statuses" value="IN_REGISTRATION">
                        </c:if>
                        <label for="check-in-registration"><fmt:message key="user_statuses.in_registration"/></label>
                    </div>
                    <div class="form-checkbox-btn">
                        <c:if test="${sessionScope.orders_filter_order_states.contains('CONFIRMED')}">
                            <input id="check-confirmed" type="checkbox" name="request_filter_user_statuses" value="CONFIRMED" checked>
                        </c:if>
                        <c:if test="${not sessionScope.orders_filter_order_states.contains('CONFIRMED')}">
                            <input id="check-confirmed" type="checkbox" name="request_filter_user_statuses" value="CONFIRMED">
                        </c:if>
                        <label for="check-confirmed"><fmt:message key="user_statuses.confirmed"/></label>
                    </div>
                    <div class="form-checkbox-btn">
                        <c:if test="${sessionScope.orders_filter_order_states.contains('BLOCKED')}">
                            <input id="check-blocked" type="checkbox" name="request_filter_user_statuses" value="BLOCKED" checked>
                        </c:if>
                        <c:if test="${not sessionScope.orders_filter_order_states.contains('BLOCKED')}">
                            <input id="check-blocked" type="checkbox" name="request_filter_user_statuses" value="BLOCKED">
                        </c:if>
                        <label for="check-blocked"><fmt:message key="user_statuses.blocked"/></label>
                    </div>
                </div>
                <c:if test="${sessionScope.invalid_user_status}">
                    <div class="local-error">
                        <p><fmt:message key="filter.invalid_user_status"/></p>
                    </div>
                </c:if>
            </div>
        </div>
        <div id="user-roles" class="form-row">
            <div class="checkbox-container">
                <div><fmt:message key="filter.user_roles"/></div>
                <div>
                    <div class="form-checkbox-btn">
                        <c:if test="${sessionScope.users_filter_user_roles.contains('ADMIN')}">
                            <input id="check-admin" type="checkbox" name="request_filter_user_roles" value="ADMIN" checked>
                        </c:if>
                        <c:if test="${not sessionScope.users_filter_user_roles.contains('ADMIN')}">
                            <input id="check-admin" type="checkbox" name="request_filter_user_roles" value="ADMIN">
                        </c:if>
                        <label for="check-admin"><fmt:message key="user_roles.admin"/></label>
                    </div>
                    <div class="form-checkbox-btn">
                        <c:if test="${sessionScope.users_filter_user_roles.contains('CUSTOMER')}">
                            <input id="check-customer" type="checkbox" name="request_filter_user_roles" value="CUSTOMER" checked>
                        </c:if>
                        <c:if test="${not sessionScope.users_filter_user_roles.contains('CUSTOMER')}">
                            <input id="check-customer" type="checkbox" name="request_filter_user_roles" value="CUSTOMER">
                        </c:if>
                        <label for="check-customer"><fmt:message key="user_roles.admin"/></label>
                    </div>
                </div>
                <c:if test="${sessionScope.invalid_user_role}">
                    <div class="local-error">
                        <p><fmt:message key="filter.invalid_user_role"/></p>
                    </div>
                </c:if>
            </div>
        </div>
        <div id="login-mail" class="form-row">
            <label for="request_filter_login"><fmt:message key="profile.login" /></label>
            <input type="number" name="request_filter_login" id="request_filter_login"
                   step="0.01" min="0" value="${request_filter_login}" placeholder="${request_filter_login}"/>
            <c:if test="${sessionScope.invalid_login}">
                <div class="local-error">
                    <p><fmt:message key="login.invalid_login"/></p>
                </div>
            </c:if>
            <label for="request_filter_mail"><fmt:message key="profile.mail" /></label>
            <input type="number" name="request_filter_mail" id="request_filter_mail"
                   step="0.01" min="0" value="${request_filter_mail}" placeholder="${request_filter_mail}"/>
            <c:if test="${sessionScope.invalid_mail}">
                <div class="local-error">
                    <p><fmt:message key="registration.invalid_mail"/></p>
                </div>
            </c:if>
        </div>
        <div id="phone-num-card" class="form-row">
            <label for="request_filter_phone_number"><fmt:message key="profile.login" /></label>
            <input type="number" name="request_filter_phone_number" id="request_filter_phone_number"
                   step="0.01" min="0" value="${request_filter_login}" placeholder="${request_filter_login}"/>
            <c:if test="${sessionScope.invalid_phone_number}">
                <div class="local-error">
                    <p><fmt:message key="registration.invalid_phone_num"/></p>
                </div>
            </c:if>
            <label for="request_filter_card_number"><fmt:message key="profile.mail" /></label>
            <input type="number" name="request_filter_card_number" id="request_filter_card_number"
                   step="0.01" min="0" value="${request_filter_card_number}" placeholder="${request_filter_card_number}"/>
            <c:if test="${sessionScope.invalid_card_number}">
                <div class="local-error">
                    <p><fmt:message key="registration.invalid_card_num"/></p>
                </div>
            </c:if>
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
    <c:forEach var="user" items="${sessionScope.users}">
        <div class="container-line">
            <c:if test="${user.userStatus == 'IN_REGISTRATION'}">
                <div id="base-state" class="line-item">
                    <div><fmt:message key="status"/></div>
                    <div><fmt:message key="user_statuses.in_registration"/></div>
                </div>
            </c:if>
            <c:if test="${user.userStatus == 'CONFIRMED'}">
                <div id="green-state" class="line-item">
                    <div><fmt:message key="status"/></div>
                    <div><fmt:message key="user_statuses.confirmed"/></div>
                </div>
            </c:if>
            <c:if test="${user.userStatus == 'BLOCKED'}">
                <div id="red-state" class="line-item">
                    <div><fmt:message key="status"/></div>
                    <div><fmt:message key="user_statuses.blocked"/></div>
                </div>
            </c:if>
            <div class="line-item">
                <div class="user-info">
                    <div>
                        <a>
                            <img class="profile-picture"
                                 src="../../../images/user/${user.photo}"
                                 onerror="this.src='../../../images/default_profile.png';">
                        </a>
                    </div>
                    <div class="orders-user-actions">
                        <div>
                            <a>${user.login}</a>
                        </div>
                        <div class="action">
                            <a><fmt:message key="orders.orders"/></a>
                        </div>
                    </div>
                </div>
            </div>
            <div id="mail-info" class="line-item">
                <div><fmt:message key="profile.mail"/></div>
                <div>${user.mail}</div>
            </div>
            <div id="phone-number-number-info" class="line-item">
                <div><fmt:message key="profile.phone_num"/></div>
                <div>${user.phoneNumber}</div>
            </div>
            <div id="card-number-info" class="line-item">
                <div><fmt:message key="profile.card_num"/></div>
                <div>${user.cardNumberl}</div>
            </div>
            <div id="base-role-state" class="line-item">
                <div><fmt:message key="status"/></div>
                <c:if test="${user.role == 'ADMIN'}">
                    <div><fmt:message key="user_roles.admin"/></div>
                </c:if>
                <c:if test="${user.role == 'CUSTOMER'}">
                    <div><fmt:message key="user_roles.customer"/></div>
                </c:if>
            </div>
            <div class="line-item">
                <div class="block-item-action">
                    <c:url value="/ApiController?command=go_to_user_page&id=${user.id}" var="user_page"/>
                    <a href="${user_page}"><fmt:message key="details"/></a>
                </div>
            </div>
        </div>
    </c:forEach>
    <div class="pagination">
        <c:if test="${sessionScope.min_pos != 1}">
            <div class="block-item-action">
                <c:url value="/ApiController?command=go_to_userss&next_min_pos=${sessionScope.min_pos-sessionScope.page_items_amount}&next_max_pos=${sessionScope.min_pos-1}" var="prev_order_page"/>
                <a href="${prev_order_page}"><fmt:message key="previous"/></a>
            </div>
        </c:if>
        <c:if test="${sessionScope.max_pos != sessionScope.total_amount}">
            <div class="block-item-action">
                <c:url value="/ApiController?command=go_to_users&next_min_pos=${sessionScope.max_pos+1}&next_max_pos=${sessionScope.max_pos+sessionScope.page_items_amount}" var="next_order_page"/>
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
    #base-role-state {
        width: 120px;
        color: #ffffff;
        background-color: #a15566;
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
