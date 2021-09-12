<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="locale" />

<header>
  <nav>
    <div class="header-container">
      <div class="header-block">
          <c:url value="/ApiController?command=go_to_dishes_page_command" var="dishes"/>
          <a class="header-block-text" href="${dishes}">
              <fmt:message key="header.admin.dishes"/>
          </a>
      </div>
      <div class="header-block">
        <c:url value="/ApiController?command=go_to_ingredients_page_command" var="ingredients"/>
        <a class="header-block-text" href="${ingredients}">
           <fmt:message key="header.admin.ingredients"/>
        </a>
      </div>
      <div class="header-block">
        <c:url value="/ApiController?command=go_to_users_page_command" var="users"/>
        <a class="header-block-text" href="${users}">
            <fmt:message key="header.admin.users"/>
        </a>
      </div>
    </div>
  </nav>
  </nav>
</header>
<style type="text/css">
  header {
    position: fixed;
    left: 0;
    top: 0;
    width: 100%;
    height: 35px;
    background-color: dimgrey;
  }
  .header-container{
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    align-items: center;
  }
  .header-block {

  }
  .header-block-text{
    color: #ffffff;
  }
</style>
