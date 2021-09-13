<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="locale" />

<c:set var="login_submit"><fmt:message key="login.login" /></c:set>

<html>
<head>
    <title><fmt:message key="login.login" /></title>
</head>
<body>
    <jsp:include page="shared/header.jsp" />
    <c:url value="/ApiController?command=login_page" var="var"/>
    <form action="${var}" method="post" class="ui-form">
        <h3><fmt:message key="login.login"/></h3>
        <div class="form-row">
            <label for="login"><fmt:message key="login.login_placeholder" /></label>
            <input type="text" name="login" id="login" value="${login}" pattern="^[A-za-z_]{3,25}$"/>
            <c:if test="${sessionScope.invalid_login}">
                <div class="alert alert-danger" role="alert">
                    <fmt:message key="login.invalid_login" />
                </div>
            </c:if>
        </div>
        <div class="form-row">
            <label for="password"><fmt:message key="login.password_placeholder" /></label>
            <input type="password" name="password" id="password" value="${password}" pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$"/>
            <c:if test="${sessionScope.invalid_password}">
                <div class="alert alert-danger" role="alert">
                    <fmt:message key="login.invalid_password" />
                </div>
            </c:if>
        </div>
        <c:if test="${sessionScope.auth_error}">
            <div class="alert alert-danger" role="alert">
                <fmt:message key="login.auth_error" />
            </div>
        </c:if>
        <div class="action"><input type="submit" value="${login_submit}"/></div>
    </form>
    <div class="block-item">
        <div class="block-item-text">
            <a><fmt:message key="sign_up.info"/></a>
        </div>
        <div class="block-item-action">
            <c:url value="/ApiController?command=go_to_sign_up_page" var="sign_up"/>
            <a href="${sign_up}"><fmt:message key="sign_up"/></a>
        </div>
    </div>
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
   .alert {
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
