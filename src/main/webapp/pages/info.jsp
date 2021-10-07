<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale" />

<c:set var="find"><fmt:message key="filter.find" /></c:set>
<c:set var="request_filter_login">${sessionScope.users_filter_login}</c:set>
<c:set var="request_filter_mail">${sessionScope.users_filter_mail}</c:set>
<c:set var="request_filter_phone_number">${sessionScope.users_filter_phone_number}</c:set>
<c:set var="request_filter_card_number">${sessionScope.users_filter_card_number}</c:set>
<c:set var="user_statuses">${sessionScope.users_filter_user_statuses}</c:set>
<c:set var="user_roles">${sessionScope.users_filter_user_roles}</c:set>

<html>
<head>
    <title><fmt:message key="header.users"/></title>
</head>
<body>
<jsp:include page="shared/header.jsp" />
<div class="workspace-flex-container">
    <div class="container-line">
        <div class="line-item">
            <div><fmt:message key="info_phone_num_text"/></div>
            <div><fmt:message key="info_phone_num"/></div>
        </div>
    </div>
    <div class="container-line">
        <div class="line-item">
            <div><fmt:message key="info_mail_text"/></div>
            <div><fmt:message key="info_mail"/></div>
        </div>
    </div>
    <c:forEach var="user" items="${sessionScope.admins_info}">
        <div id="admin-info" class="container-line">
            <div id="mail-info" class="line-item">
                <div><fmt:message key="profile.mail"/></div>
                <div>${user.mail}</div>
            </div>
            <div id="user-info-line" class="line-item">
                <div class="user-info">
                    <div>
                        <a>
                            <img class="profile-picture"
                                 src="../images/user/${user.photo}"
                                 onerror="this.src='../images/default_profile.png';">
                        </a>
                    </div>
                    <div class="orders-user-actions">
                        <div>
                            <a>${user.login}</a>
                        </div>
                    </div>
                </div>
            </div>
            <div id="phone-number-number-info" class="line-item">
                <div><fmt:message key="profile.phone_num"/></div>
                <div>${user.phoneNumber}</div>
            </div>
        </div>
    </c:forEach>
</div>
</body>
<style>
    body {
        margin-top: 50px;
        font: 15px 'Roboto', Arial, Helvetica, sans-serif;
        font-size: 15px;
    }
    #user-info-line {
        width: 20%;
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
        justify-content: center;
        align-items: center;
        border-radius: 10px;
        width: 550px;
        margin: 10px;
        padding: 5px;
        -webkit-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.15);
        -moz-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.15);
        box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.15);
    }
    #admin-info {
        justify-content: space-between;
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
        margin: 0px 10px;
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
</style>
</html>
