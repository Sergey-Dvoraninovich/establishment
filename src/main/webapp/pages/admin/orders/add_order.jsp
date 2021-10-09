<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale" />

<c:set var="login_submit"><fmt:message key="login.login" /></c:set>

<html>
<head>
    <title><fmt:message key="login.login" /></title>
    <script src="../../../js/common.js"></script>
</head>
<body>
<jsp:include page="../../shared/header.jsp" />
<c:url value="/ApiController?command=add_order" var="add_order"/>
<form action="${add_order}" method="post" class="ui-form">
    <h3><fmt:message key="order"/></h3>
    <c:forEach var="dish_info" items="${sessionScope.order_dishes_info}">
        <div class="form-row">
            <div>
                ${dish_info.getKey.name}
                ${dish_info.getValue.dishAmount}
            </div>
        </div>
    </c:forEach>
    <div class="form-row">
        <label for="payment"><fmt:message key="login.login_placeholder" /></label>
        <input id="payment" name="payment" type="radio" value="card">
        <input name="payment" type="radio" value="cash">
    </div>
    <div><input name="dzen" type="radio" value="nedzen"> Не дзен</div>
    <c:if test="${sessionScope.auth_error}">
        <div class="alert alert-danger" role="alert">
            <fmt:message key="login.auth_error" />
        </div>
    </c:if>
    <div class="action"><input type="submit" value="${login_submit}"/></div>
</form>
<c:url value="/ApiController?command=add_order_dish" var="add_order_dish"/>
<form action="${add_order_dish}" method="post" class="ui-form">
    <h3><fmt:message key="login.login"/></h3>
    <div class="form-row">
        <label for="available_ingredients"><fmt:message key="name" /></label>
        <select class="ingredient-select" id="available_ingredients" name="available_ingredients">
            <c:forEach var="ingredient" items="${sessionScope.unused_ingredients}">
                <option class="ingredient-option" value="${ingredient.id}">${ingredient.name}</option>
            </c:forEach>
        </select>
    </div>
    <c:if test="${sessionScope.auth_error}">
        <div class="alert alert-danger" role="alert">
            <fmt:message key="login.auth_error" />
        </div>
    </c:if>
    <div class="action"><input type="submit" value="${login_submit}"/></div>
</form>
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
