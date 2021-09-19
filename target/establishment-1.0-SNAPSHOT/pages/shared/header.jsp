<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="locale" />

<c:set var="profile"><fmt:message key="profile" /></c:set>
<c:set var="sign_in"><fmt:message key="login.login" /></c:set>

<header>
  <nav>
    <div class="header-container">
      <div class="header-block">
        <c:url value="/ApiController?command=go_to_dishes_page" var="dishes_page"/>
        <a class="header-block-text" href="${dishes_page}">
          <fmt:message key="header.dishes"/>
        </a>
      </div>
    <c:if test="${sessionScope.user.role.name() == 'ADMIN'}">
      <div class="header-block">
        <c:url value="/ApiController?command=go_to_orders_page" var="orders_page"/>
        <a class="header-block-text" href="${orders_page}">
          <fmt:message key="header.orders"/>
        </a>
      </div>
      <div class="header-block">
        <c:url value="/ApiController?command=go_to_ingredients_page" var="ingredients_page"/>
        <a class="header-block-text" href="${ingredients_page}">
          <fmt:message key="header.ingredients"/>
        </a>
      </div>
      <div class="header-block">
        <c:url value="/ApiController?command=go_to_users_page" var="users_page"/>
        <a class="header-block-text" href="${users_page}">
          <fmt:message key="header.users"/>
        </a>
      </div>
    </c:if>

    <c:if test="${sessionScope.user.role.name() == 'CUSTOMER'}">
      <c:url value="/ApiController?command=go_to_customer_basket" var="basket"/>
      <div id="locale-header-block" class="header-block">
        <div class="locale-container">
          <div class="locale-item">
            <a class="header-block-text" href="${basket}">
              <fmt:message key="header.basket"/>
            </a>
          </div>
          <div class="locale-activated-item">
            <a class="header-block-text" href="${basket}">
                ${sessionScope.dishes_in_basket}
            </a>
          </div>
        </div>
      </div>
    </c:if>

    <c:if test="${sessionScope.user != null}">
      <c:if test="${sessionScope.user.role.name() == 'CUSTOMER'}">
        <div class="header-block">
          <c:url value="/ApiController?command=go_to_customer_profile_page&id=${sessionScope.user.id}" var="profile_page"/>
          <a class="header-block-text" href="${profile_page}">
              ${sessionScope.user.login} <fmt:message key="header.profile"/>
          </a>
        </div>
      </c:if>
      <c:if test="${sessionScope.user.role.name() == 'ADMIN'}">
        <div class="header-block">
           <c:url value="/ApiController?command=go_to_admin_page&id=${sessionScope.user.id}" var="admin_profile_page"/>
           <a class="header-block-text" href="${admin_profile_page}">
              ${sessionScope.user.login} <fmt:message key="header.profile"/>
           </a>
        </div>
      </c:if>
      <div class="header-block">
        <c:url value="/ApiController?command=sign_out" var="sign_out"/>
        <a class="header-block-text" href="${sign_out}">
          <fmt:message key="header.sign_out"/>
        </a>
      </div>
    </c:if>

    <c:if test="${!sessionScope.is_authenticated}">
       <div class="header-block">
         <c:url value="/ApiController?command=go_to_login_page" var="login_page"/>
         <a class="header-block-text" href="${login_page}">
           <fmt:message key="header.sign_in"/>
         </a>
       </div>
    </c:if>

    <div id="locale-header-block" class="header-block">
      <div class="locale-container">
        <c:choose>
          <c:when test="${sessionScope.locale == 'ru'}">
            <div class="locale-activated-item">
              <a class="header-block-text">
                <fmt:message key="locale.ru"/>
              </a>
            </div>
          </c:when>
          <c:otherwise>
            <div class="locale-item">
              <a class="header-block-text">
                <fmt:message key="locale.ru"/>
              </a>
            </div>
          </c:otherwise>
        </c:choose>
        <c:choose>
          <c:when test="${sessionScope.locale == 'en'}">
            <div class="locale-activated-item">
              <a class="header-block-text">
                <fmt:message key="locale.en"/>
              </a>
            </div>
          </c:when>
          <c:otherwise>
            <div class="locale-item">
              <a class="header-block-text">
                <fmt:message key="locale.en"/>
              </a>
            </div>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
    </div>
  </nav>
</header>
<style type="text/css">
  a {
    color: #000000;
    text-decoration: none;
    font: 15px 'Roboto', Arial, Helvetica, sans-serif;
  }
  header {
    position: fixed;
    left: 0;
    top: 0;
    width: 100%;
    height: 52px;
    background-color: #000000;
  }
  .header-container{
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    align-items: center;
    padding: 10px
  }
  .header-block {
    padding: 5px 15px;
    border-radius: 10px;
    background-color: white;
    -webkit-box-shadow: 0px 5px 10px 2px rgba(34, 60, 80, 0.2) inset;
    -moz-box-shadow: 0px 5px 10px 2px rgba(34, 60, 80, 0.2) inset;
    box-shadow: 0px 5px 10px 2px rgba(34, 60, 80, 0.2) inset;
  }
  #locale-header-block {
    padding: 0px;
  }
  #locale-header-block:hover {
  }
  .header-block:hover {
    -webkit-box-shadow: 4px 4px 9px 0px rgba(161, 85, 102, 0.62) inset;
    -moz-box-shadow: 4px 4px 9px 0px rgba(161, 85, 102, 0.62) inset;
    box-shadow: 4px 4px 9px 0px rgba(161, 85, 102, 0.62) inset;
  }
  .header-block-text{
    font-size: 20px;
  }
  .locale-container{
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    align-items: center;
  }
  .locale-item {
    border-radius: 10px;
    padding: 5px 15px;
  }
  .locale-activated-item {
    padding: 5px 15px;
    background-color: #a15566;
    color: #ffffff;
    border-radius: 10px;
    -webkit-box-shadow: 0px 5px 10px 2px rgba(34, 60, 80, 0.2) inset;
    -moz-box-shadow: 0px 5px 10px 2px rgba(34, 60, 80, 0.2) inset;
    box-shadow: 0px 5px 10px 2px rgba(34, 60, 80, 0.2) inset;
  }

  .locale-activated-item:hover {
    background-color: #804451;
  }

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
