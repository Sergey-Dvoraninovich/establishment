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
    <div class="workspace-column">
        <div id="icon" class="block-item">
            <img class="profile-picture"
                 src="../../images/${sessionScope.user.photo}"
                 onerror="this.src='../../images/default_profile.png';">
        </div>
        <div class="block-item">
            <div class="block-item-header">
                <h2><fmt:message key="profile.bonuses_amount"/></h2>
            </div>
            <div class="block-item-main-info">
               <h2>${sessionScope.user.bonusesAmount}</h2>
            </div>
        </div>
        <div class="block-item">
            <div class="block-item-text">
                <a><fmt:message key="profile.basket_info"/></a>
            </div>
            <div class="block-item-action">
                <c:url value="/ApiController?command=go_to_baskrt_page_command" var="orders_page"/>
                <a href="${orders_page}"><fmt:message key="profile.basket"/></a>
            </div>
        </div>
    </div>
    <div class="workspace-column">
        <div id="description" class="block-item">
            <div class="row-item-flexbox">
                <h2 hidden="true"><fmt:message key="profile.login"/> : </h2>
                <div class="row-item-flexbox-item">
                    <img class="profile-item-picture" src="../../images/profile_icon.png">
                </div>
                <div class="row-item-flexbox-item">
                    <h2>${sessionScope.user.login}</h2>
                </div>
            </div>
            <div class="row-item-flexbox">
                <h2 hidden="true"><fmt:message key="profile.mail"/> : </h2>
                <div class="row-item-flexbox-item">
                    <img class="profile-item-picture" src="../../images/profile_mail.png">
                </div>
                <div class="row-item-flexbox-item">
                    <h2>${sessionScope.user.mail}</h2></div>
                </div>
            <div class="row-item-flexbox">
                <h2 hidden="true"><fmt:message key="profile.phone_num"/> : </h2>
                <div class="row-item-flexbox-item">
                    <img class="profile-item-picture" src="../../images/profile_phone.png">
                </div>
                <div class="row-item-flexbox-item">
                    <h2>${sessionScope.user.phoneNumber}</h2>
                </div>
            </div>
            <div class="row-item-flexbox">
                <h2 hidden="true" ><fmt:message key="profile.card_num"/>  : </h2>
                <div class="row-item-flexbox-item">
                    <img class="profile-item-picture" src="../../images/profile_card.png">
                </div>
                <div class="row-item-flexbox-item">
                    <h2>${sessionScope.user.cardNumber}</h2>
                </div>
            </div>
            <div class="block-item-action">
                <c:url value="/ApiController?command=go_to_edit_customer_page" var="edit_page"/>
                <a href="${edit_page}"><fmt:message key="profile.edit"/></a>
            </div>
        </div>
        <div class="block-item">
            <div class="block-item-text">
                <a><fmt:message key="profile.orders_info"/></a>
            </div>
            <div class="block-item-action">
                <c:url value="/ApiController?command=go_to_orders_history" var="orders_page"/>
                <a href="${orders_page}"><fmt:message key="profile.orders"/></a>
            </div>
        </div>
        <div class="block-item">
            <div class="block-item-header">
                <h2><fmt:message key="profile.mail_verification"/></h2>
            </div>
            <div class="block-item-text">
                <a><fmt:message key="profile.mail_verification_info"/></a>
            </div>
            <div class="block-item-action">
                <c:url value="/ApiController?command=go_to_verify_code_page" var="verification_page"/>
                <a href="${verification_page}"><fmt:message key="profile.verify_mail"/></a>
            </div>
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
        color: white;
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
    }
    .block-item-action>a {
        color: #ffffff;
        font-size: 30px;
    }
</style>
</html>
