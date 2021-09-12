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
                <h2 hidden="true"><fmt:message key="profile.login"/> : </h2>
                <img class="profile-item-picture" src="../../images/profile_login.png">
                <h1>${sessionScope.user.login}</h1>
            </div>
            <div>
                <h2 hidden="true"><fmt:message key="profile.mail"/> : </h2>
                <img class="profile-item-picture" src="../../images/profile_mail.png">
                <h1>${sessionScope.user.mail}</h1>
            </div>
            <div>
                <h2 hidden="true"><fmt:message key="profile.phone_num"/> : </h2>
                <img class="profile-item-picture" src="../../images/profile_phone.png">
                <h1>${sessionScope.user.phoneNumber}</h1>
            </div>
            <div>
                <h2 hidden="true" ><fmt:message key="profile.card_num"/>  : </h2>
                <img class="profile-item-picture" src="../../images/profile_card.png">
                <h1>${sessionScope.user.cardNumber}</h1>
            </div>
            <div>
                <h2><fmt:message key="profile.bonuses_amount"/> : </h2>
                <h1>${sessionScope.user.bonusesAmount}</h1>
            </div>
        <c:url value="/ApiController?command=go_to_verification_page_command" var="verification_page"/>
        <a href="${verification_page_page}"><fmt:message key="profile.verify_mail"/></a>
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
        color: white;
        font-size: 15px;
        text-decoration: none;
    }
</style>
</html>