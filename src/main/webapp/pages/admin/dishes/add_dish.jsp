<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale" />

<c:set var="add"><fmt:message key="add" /></c:set>

<html>
<head>
  <title><fmt:message key="admin.dishes.add_dish_title"/> </title>
  <script src="../../../js/common.js"></script>
</head>
<body>
<jsp:include page="../../shared/header.jsp" />
<c:url value="/ApiController?command=create_dish" var="var"/>
<form action="${var}" method="post" class="ui-form">
  <h3><fmt:message key="admin.dishes.add_dish_title"/></h3>
  <div class="form-row">
    <label for="name"><fmt:message key="admin.dishes.dish_name" /></label>
    <input type="text" name="name" id="name" value="${name}"
           pattern="^[A-Za-zА-Яа-я]{1}[A-Za-zА-Яа-я\s]{0,68}[A-Za-zА-Яа-я]{1}$"/>
    <c:if test="${sessionScope.invalid_dish_name}">
      <div class="local-error">
        <p><fmt:message key="admin.dishes.invalid_dish_name"/></p>
      </div>
    </c:if>
  </div>
  <div class="form-row">
    <label for="price"><fmt:message key="admin.dishes.dish_price" /></label>
    <input type="number" step="0.01" min="0" name="price" id="price" value="${price}"/>
    <c:if test="${sessionScope.invalid_dish_price}">
      <div class="local-error">
        <p><fmt:message key="admin.dishes.invalid_dish_price"/></p>
      </div>
    </c:if>
  </div>
  <div class="form-row">
    <label for="amount_grams"><fmt:message key="admin.dishes.dish_amount_grams" /></label>
    <input type="number" step="1" min="0" name="amount_grams" id="amount_grams" value="${amount_grams}"/>
    <c:if test="${sessionScope.invalid_dish_amount_grams}">
      <div class="local-error">
        <p><fmt:message key="admin.dishes.invalid_dish_amount_grams"/></p>
      </div>
    </c:if>
  </div>
  <div class="form-row">
    <label for="calories_amount"><fmt:message key="admin.dishes.dish_calories_amount" /></label>
    <input type="number" step="1" min="0" name="calories_amount" id="calories_amount" value="${calories_amount}"/>
    <c:if test="${sessionScope.invalid_dish_calories_amount}">
      <div class="local-error">
        <p><fmt:message key="admin.dishes.invalid_dish_calories_amount"/></p>
      </div>
    </c:if>
  </div>
  <div>
    <input type="submit" value="${add}"/>
  </div>
  <c:if test="${sessionScope.add_dish_error}">
    <div class="local-error">
      <p><fmt:message key="admin.dishes.add_dish_error"/></p>
    </div>
  </c:if>
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
