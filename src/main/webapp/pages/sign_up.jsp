<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale" />

<html>
<head>
    <title><fmt:message key="login.password_placeholder" /></title>
    <script src="../js/common.js"></script>
    <link href="../css/pages/sign_up.css" rel="stylesheet">
</head>
<body>
<jsp:include page="shared/header.jsp" />
<c:url value="/ApiController?command=sign_up_page" var="var"/>
<form action="${var}" method="post" class="ui-form">
    <h3><fmt:message key="registration.sign_up"/></h3>
    <div class="form-row">
        <label for="login"><fmt:message key="registration.login_placeholder" /></label>
        <input type="text" name="login" id="login" pattern="^[A-za-z_]{3,25}$" value="${login}"/>
        <c:if test="${sessionScope.not_unique_login}">
            <div class="local-error">
                <p><fmt:message key="registration.not_unique_login"/></p>
            </div>
        </c:if>
        <c:if test="${sessionScope.invalid_login}">
            <div class="local-error">
                <p><fmt:message key="registration.invalid_login"/></p>
            </div>
        </c:if>
    </div>
    <div class="form-row">
        <label for="password"><fmt:message key="registration.password_placeholder" /></label>
        <input type="password" name="password" pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$"
               id="password" value="${password}"/>
        <c:if test="${sessionScope.invalid_password}">
            <div class="local-error">
                <p><fmt:message key="registration.invalid_password" /></p>
            </div>
        </c:if>
        <c:if test="${sessionScope.different_passwords}">
            <div class="local-error">
                <p><fmt:message key="registration.different_passwords" /></p>
            </div>
        </c:if>
    </div>
    <div class="form-row">
        <label for="repeat_password"><fmt:message key="registration.password_repeat_placeholder" /></label>
        <input type="password" name="repeat_password" pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$"
               id="repeat_password" value="${repeat_password}"/>
        <c:if test="${sessionScope.different_passwords}">
            <div class="local-error">
                <p><fmt:message key="registration.different_passwords" /></p>
            </div>
        </c:if>
    </div>
    <div class="form-row">
        <label for="mail"><fmt:message key="registration.mail_placeholder" /></label>
        <input type="text" name="mail" pattern = ^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$
               id="mail" value="${mail}"/>
        <c:if test="${sessionScope.invalid_mail}">
            <div class="local-error">
                <p><fmt:message key="registration.invalid_mail" /></p>
            </div>
        </c:if>
    </div>
    <div class="form-row">
        <label for="phone_num"><fmt:message key="registration.phone_num_placeholder" /></label>
        <input type="tel" name="phone_num" id="phone_num" pattern="^\+\d{12}$" value="${phone_num}"/>
        <c:if test="${sessionScope.invalid_phone_num}">
            <div class="local-error">
                <p><fmt:message key="registration.invalid_phone_num" /><p>
            </div>
        </c:if>
    </div>
    <div class="form-row">
        <label for="card_num"><fmt:message key="registration.card_num_placeholder" /></label>
        <input type="text" name="card_num" id="card_num" pattern="^\d{16}$" value="${card_num}"/>
        <c:if test="${sessionScope.invalid_card_num}">
            <div class="local-error">
                <p><fmt:message key="registration.invalid_card_num" /></p>
            </div>
        </c:if>
    </div>
    <div><input type="submit" value="Sign Up"/></div>
    <c:if test="${sessionScope.registration_error}">
        <div class="global-error">
            <p><fmt:message key="registration.registration_error"/></p>
        </div>
    </c:if>
    <c:if test="${sessionScope.user_already_authenticated}">
        <div class="global-error">
            <p><fmt:message key="registration.user_already_authenticated"/></p>
        </div>
    </c:if>
</form>
<script type="text/javascript">
    var login_input = document.getElementById();
    login_input.oninvalid = function(event) {
        event.target.setCustomValidity('Login should contain only symbols A-z, a-z, _');
    }
</script>
</body>
<style type="text/css">
    body {
        color: #000000;
        text-decoration: none;
        font: 15px 'Roboto', Arial, Helvetica, sans-serif;
        display: flex;
        flex-direction: column;
        justify-content: space-around;
        align-items: center;
        margin-top: 55px;
    }
    h3 {
        font-size: 25px;
    }
    form {
        width: 250px;
        border-radius: 10px;
        padding: 10px;
        margin: 10px;
        -webkit-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.15);
        -moz-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.15);
        box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.15);
    }
    form:hover {
        -webkit-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
        -moz-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
        box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
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
    input[type=password]{
        width:100%;
        border:2px solid #aaa;
        border-radius:5px;
        margin:8px 0;
        outline:none;
        padding:8px;
        box-sizing:border-box;
        transition:.3s;
    }
    input[type=password]:focus{
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
    input[type=tel]{
        width:100%;
        border:2px solid #aaa;
        border-radius:5px;
        margin:8px 0;
        outline:none;
        padding:8px;
        box-sizing:border-box;
        transition:.3s;
    }
    input[type=tel]:focus{
        border-color:#a15566;
        box-shadow:0 0 8px 0 #a15566;
    }
    .global-error {
        font-size: 15px;
        color: red;
    }
    .local-error {
        font-size: 15px;
        color: red;
    }
    .block-item {
        width: 250px;
        border-radius: 10px;
        padding: 10px;
        margin: 10px;
        -webkit-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.15);
        -moz-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.15);
        box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.15);
    }
    .block-item:hover {
        -webkit-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
        -moz-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
        box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
    }
    .block-item-header {
        text-align: left;
    }
    .block-item-header>a {
        font-size: 15px;
    }
    .block-item-text {
        margin-right: 5px;
        margin-left: 5px;
    }
    .block-item-text>a{
        color: #4d4d4d;
        font-size: 15px;
    }
    .block-item-action {
        color: #ffffff;
        border: none;
        border-radius: 10px;
        margin-top: 15px;
        padding: 5px;
        text-align: center;
        width: 100%;
        background-color: #a15566;
    }
    .block-item-action:hover {
        background-color: #804451;
    }
    .block-item-action>a{
        font-size: 20px;
    }
</style>
</html>
