<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="locale" />

<c:set var="add"><fmt:message key="add" /></c:set>

<html>
<head>
  <title><fmt:message key="admin.dishes.add_dish_title"/> </title>
</head>
<body>
<jsp:include page="../../shared/header.jsp" />
<c:url value="/ApiController?command=create_dish" var="var"/>
<form action="${var}" method="post" class="ui-form">
  <h3><fmt:message key="admin.dishes.add_dish_title"/></h3>
  <div class="form-row">
    <label for="photo"><fmt:message key="photo" /></label>
    <input type="text" name="photo" id="photo" value="${photo}"/>
  </div>
  <div class="form-row">
    <label for="name"><fmt:message key="admin.dishes.dish_name" /></label>
    <input type="text" name="name" id="name" value="${name}"/>
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
  * {
    box-sizing: border-box;
  }
  body {
    background: #e6f4fd;
    font-family: 'Roboto', sans-serif;
  }
  .ui-form {
    max-width: 350px;
    padding: 80px 30px 30px;
    margin: 50px auto 30px;
    background: white;
  }
  .ui-form h3 {
    position: relative;
    z-index: 5;
    margin: 0 0 60px;
    text-align: center;
    color: dimgrey;
    font-size: 30px;
    font-weight: normal;
  }
  .form-row {
    position: relative;
    margin-bottom: 40px;
  }
  .form-row input {
    display: block;
    width: 100%;
    padding: 0 10px;
    line-height: 40px;
    font-family: 'Roboto', sans-serif;
    background: none;
    border-width: 0;
    border-bottom: 2px solid #9d959d;
    transition: all 0.2s ease;
  }
  .form-row label {
    position: absolute;
    left: 13px;
    color: #9d959d;
    font-size: 17px;
    font-weight: 300;
    transform: translateY(-20px);
    transition: all 0.2s ease;
  }
  .form-row input:focus {
    outline: 0;
    border-color: #F77A52;
  }
  .form-row input:focus+label, .form-row input:valid+label {
    transform: translateY(-60px);
    margin-left: -14px;
    font-size: 14px;
    font-weight: 400;
    outline: 0;
    border-color: #F77A52;
    color: #F77A52;
  }
  .ui-form input[type="submit"] {
    width: 100%;
    padding: 0;
    line-height: 42px;
    background: dimgrey;
    border-width: 0;
    color: white;
    font-size: 20px;
  }
  .ui-form p {
    margin: 0;
    padding-top: 5px;
  }
  .local-button {
    width: 290px;
    margin-right: auto;
    margin-left: auto;
    padding: 0;
    line-height: 42px;
    background: dimgrey;
    border-width: 0;
    text-align: center;
  }
  .local-button>a {
    color: white;
    font-size: 20px;
    text-decoration: none;
  }
</style>
</html>
