<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="locale" />

<c:set var="disable"><fmt:message key="admin.dishes.disable" /></c:set>
<c:set var="make_available"><fmt:message key="admin.dishes.make_available" /></c:set>
<c:set var="edit"><fmt:message key="edit" /></c:set>
<c:set var="more_information"><fmt:message key="more_information" /></c:set>
<c:set var="create_dish_message"><fmt:message key="admin.dishes.create_dish"/></c:set>

<html>
<head>
  <title>${sessionScope.dish.name}</title>
</head>
<body>
<jsp:include page="../../shared/header.jsp" />
<div class="workspace-flex-container">
  <div class="workspace-column">
    <div id="icon" class="block-item">
      <img class="profile-picture"
           src="../../../images/${sessionScope.dish.photo}"
           onerror="this.src='../../../images/default_dish.png';">
    </div>
  </div>
  <div class="workspace-column">
    <div class="block-item">
      <div class="block-item-text">
        <a>${sessionScope.dish.name}</a>
      </div>
      <div id="description" class="info-block-item">
        <div class="row-item-flexbox">
          <a>${sessionScope.dish.calories} <fmt:message key="admin.dishes.calories"/></a>
        </div>
        <div class="row-item-flexbox">
          <a>${sessionScope.dish.amountGrams} <fmt:message key="admin.dishes.grams"/></a>
        </div>
        <div class="row-item-flexbox">
          <a>${sessionScope.dish.price} <fmt:message key="admin.dishes.BYN"/></a>
        </div>
      </div>
      <c:url value="/ApiController?command=go_to_edit_dish&id=${sessionScope.dish.id}" var="go_to_edit_dish_command"/>
      <div class="block-item-action">
        <form action="${go_to_edit_dish_command}" method="post">
           <input type="hidden" name="dish_id" value="${sessionScope.dish.id}">
           <input type="submit" value="${edit}">
        </form>
      </div>
    </div>
    <c:if test="${sessionScope.dish.isAvailable}">
    <div class="block-item">
      <div class="block-item-text">
        <h2><fmt:message key="admin.dishes.disable_info"/></h2>
      </div>
      <c:url value="/ApiController?command=disable_dish" var="disable_dish_command"/>
      <div class="block-item-action">
      <form action="${disable_dish_command}" method="post">
        <input type="hidden" name="dish_id" value="${sessionScope.dish.id}">
        <input type="submit" value="${disable}">
      </form>
      </div>
    </div>
    </c:if>
    <c:if test="${not sessionScope.dish.isAvailable}">
    <div class="block-item">
      <div class="block-item-text">
        <h2><fmt:message key="admin.dishes.make_available_info"/></h2>
      </div>
      <c:url value="/ApiController?command=make_dish_available" var="make_dish_available_command"/>
      <div class="block-item-action">
      <form action="${make_dish_available_command}" method="post">
        <input type="hidden" name="dish_id" value="${sessionScope.dish.id}">
        <input type="submit" value="${make_available}">
      </form>
      </div>
    </div>
    </c:if>
  </div>
</div>
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
  .workspace-flex-container {
    margin-top: 35px;
    padding-top: 15px;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: flex-start;
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
    text-decoration: none;
  }

  #description{
    width: inherit;
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
    color: #000000;
    font-size: 15px;
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
    width: 300px;
    -webkit-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.15);
    -moz-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.15);
    box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.15);
  }
  #icon{
    height: 250px;
    width: 440px;
    border-radius: 10px;
  }
  .profile-picture{
    height: 250px;
    width: 440px;
    border-radius: 10px;
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
    margin-top: 5px;
    text-align: center;
    width: 70%;
    background-color: #ffffff;
  }
  .block-item-action:hover {
    -webkit-box-shadow: none;
    -moz-box-shadow: none;
    box-shadow: none;
  }
  form {
    padding: 0px;
    margin: 0px;
  }
  input[type="submit"]{
    font-size: 20px;
    color: #ffffff;
    border: none;
    border-radius: 10px;
    padding: 5px;
    text-align: center;
    width: 100%;
    background-color: #a15566;
  }
  input[type="submit"]:hover {
    background-color: #804451;
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
