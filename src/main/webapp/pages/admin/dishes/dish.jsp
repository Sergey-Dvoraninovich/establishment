<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="locale" />

<html>
<head>
  <title>Title</title>
</head>
<body>
<jsp:include page="../../shared/header.jsp" />
<div class="workspace">
  <div class="column">
    <picture>
    <source srcset="src="${sessionScope.dish.photo}"">
        <img src="https://yandex.by/images/search?text=%D1%84%D0%BE%D1%82%D0%BE%20pfukeirf%20%D0%B1%D0%BB%D1%8E%D0%B4%D0%B0&from=tabbar&pos=9&img_url=https%3A%2F%2Fcdn.myshoptet.com%2Fusr%2Fwww.gastroexpert.cz%2Fuser%2Fshop%2Fbig%2F48925_miska-dim-sum-22-cm.jpg%3F5db05b15&rpt=simage">
   </picture>
  </div>
  <div class="column">
    <div class="description">
      <p><fmt:message key="admin.dishes.dish_name"/> : ${sessionScope.dish.name}</p>
      <p><fmt:message key="admin.dishes.dish_price"/> : ${sessionScope.dish.price}</p>
      <p><fmt:message key="admin.dishes.dish_amount_grams"/> : ${sessionScope.dish.amountGrams}</p>
      <c:if test="${sessionScope.dish.averageMark}">
        <p><fmt:message key="admin.dishes.dish_amount_grams"/> : ${sessionScope.dish.averageMark}</p>
      </c:if>
    </div>
    <div class="ingredients">
      <p><fmt:message key="admin.ingredients.title"/>:</p>
      <c:forEach var="ingredient" items="${sessionScope.ingredients}">
        <p>${ingredient.name}</p>
      </c:forEach>
    </div>
  </div>
</div>
</body>
<style>
  .workspace {
    margin-top: 35px;
    padding-top: 35px;
  }
  .dish-info{
    margin: 15px;
    padding: 9px;
    color: white;
    background-color: dimgrey;
  }
</style>
</html>
