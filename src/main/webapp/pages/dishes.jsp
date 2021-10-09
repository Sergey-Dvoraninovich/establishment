<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale"/>

<c:set var="disable"><fmt:message key="admin.dishes.disable" /></c:set>
<c:set var="make_available"><fmt:message key="admin.dishes.make_available" /></c:set>
<c:set var="edit"><fmt:message key="edit" /></c:set>
<c:set var="add_to_basket_text"><fmt:message key="dishes.add_to_basket" /></c:set>
<c:set var="more_information"><fmt:message key="more_information" /></c:set>
<c:set var="create_dish_message"><fmt:message key="admin.dishes.create_dish"/></c:set>
<c:set var="find"><fmt:message key="filter.find"/></c:set>

<c:set var="request_filter_name">${sessionScope.dishes_filter_name}</c:set>
<c:set var="request_filter_min_calories_amount">${sessionScope.dishes_filter_min_calories_amount}</c:set>
<c:set var="request_filter_max_calories_amount">${sessionScope.dishes_filter_max_calories_amount}</c:set>
<c:set var="request_filter_min_amount_grams">${sessionScope.dishes_filter_min_amount_grams}</c:set>
<c:set var="request_filter_max_amount_grams">${sessionScope.dishes_filter_max_amount_grams}</c:set>
<c:set var="request_filter_min_price">${sessionScope.dishes_filter_min_price}</c:set>
<c:set var="request_filter_max_price">${sessionScope.dishes_filter_max_price}</c:set>

<html>
<head>
    <title><fmt:message key="admin.dishes.title" /></title>
    <script src="../js/common.js"></script>
    <link href="../css/pages/dishes.css" rel = "stylesheet">
</head>
<body>
<jsp:include page="shared/header.jsp" />
<div class="filter-line">
    <c:url value="/ApiController?command=set_dishes_filter_parameters" var="dishes_filter"/>
    <form action="${dishes_filter}" method="post">
        <c:if test="${sessionScope.user.role.name() == 'ADMIN'}">
        <div id="order-states" class="form-row">
            <div class="checkbox-container">
                <div><fmt:message key="filter.dish_states"/></div>
                <div>
                    <div class="form-checkbox-btn">
                        <c:if test="${sessionScope.dishes_filter_states.contains('AVAILABLE')}">
                            <input id="check-available" type="checkbox" name="request_filter_dish_states" value="AVAILABLE" checked>
                        </c:if>
                        <c:if test="${not sessionScope.dishes_filter_states.contains('AVAILABLE')}">
                            <input id="check-available" type="checkbox" name="request_filter_dish_states" value="AVAILABLE">
                        </c:if>
                        <label for="check-available"><fmt:message key="filter.available"/></label>
                    </div>
                    <div class="form-checkbox-btn">
                        <c:if test="${sessionScope.dishes_filter_states.contains('DISABLED')}">
                            <input id="check-disabled" type="checkbox" name="request_filter_dish_states" value="DISABLED" checked>
                        </c:if>
                        <c:if test="${not sessionScope.dishes_filter_states.contains('DISABLED')}">
                            <input id="check-disabled" type="checkbox" name="request_filter_dish_states" value="DISABLED">
                        </c:if>
                        <label for="check-disabled"><fmt:message key="filter.disabled"/></label>
                    </div>
                </div>
                <c:if test="${sessionScope.invalid_dish_states}">
                    <div class="local-error">
                        <p><fmt:message key="filter.invalid_dish_states"/></p>
                    </div>
                </c:if>
            </div>
        </div>
        </c:if>
        <div id="price" class="form-row">
            <label for="request_filter_min_price"><fmt:message key="filter.min_price" /></label>
            <input type="number" name="request_filter_min_price" id="request_filter_min_price"
                   step="0.01" min="0" value="${request_filter_min_price}" placeholder="${request_filter_min_price}"/>
            <c:if test="${sessionScope.invalid_min_price}">
                <div class="local-error">
                    <p><fmt:message key="filter.invalid_min_price"/></p>
                </div>
            </c:if>
            <label for="request_filter_max_price"><fmt:message key="filter.max_price" /></label>
            <input type="number" name="request_filter_max_price" id="request_filter_max_price"
                   step="0.01" min="0" value="${request_filter_max_price}" placeholder="${request_filter_max_price}"/>
            <c:if test="${sessionScope.invalid_max_price}">
                <div class="local-error">
                    <p><fmt:message key="filter.invalid_max_price"/></p>
                </div>
            </c:if>
        </div>
        <div id="calories-amount" class="form-row">
            <label for="request_filter_min_calories_amount"><fmt:message key="filter.min_calories_amount" /></label>
            <input type="number" name="request_filter_min_calories_amount" id="request_filter_min_calories_amount"
                   step="1" min="0" value="${request_filter_min_calories_amount}" placeholder="${request_filter_min_calories_amount}"/>
            <c:if test="${sessionScope.invalid_min_calories_amount}">
                <div class="local-error">
                    <p><fmt:message key="filter.invalid_min_calories_amount"/></p>
                </div>
            </c:if>
            <label for="request_filter_max_calories_amount"><fmt:message key="filter.max_calories_amount" /></label>
            <input type="number" name="request_filter_max_calories_amount" id="request_filter_max_calories_amount"
                   step="1" min="0" value="${request_filter_max_calories_amount}" placeholder="${request_filter_max_calories_amount}"/>
            <c:if test="${sessionScope.invalid_max_calories_amount}">
                <div class="local-error">
                    <p><fmt:message key="filter.invalid_max_calories_amount"/></p>
                </div>
            </c:if>
        </div>
        <div id="amount-grams" class="form-row">
            <label for="request_filter_min_amount_grams"><fmt:message key="filter.min_amount_grams" /></label>
            <input type="number" name="request_filter_min_amount_grams" id="request_filter_min_amount_grams"
                   step="1" min="0" value="${request_filter_min_amount_grams}" placeholder="${request_filter_min_amount_grams}"/>
            <c:if test="${sessionScope.invalid_min_amount_grams}">
                <div class="local-error">
                    <p><fmt:message key="filter.invalid_min_amount_grams"/></p>
                </div>
            </c:if>
            <label for="request_filter_max_amount_grams"><fmt:message key="filter.max_amount_grams" /></label>
            <input type="number" name="request_filter_max_amount_grams" id="request_filter_max_amount_grams"
                   step="1" min="0" value="${request_filter_max_amount_grams}" placeholder="${request_filter_max_amount_grams}"/>
            <c:if test="${sessionScope.invalid_max_amount_grams}">
                <div class="local-error">
                    <p><fmt:message key="filter.invalid_max_amount_grams"/></p>
                </div>
            </c:if>
        </div>
        <div id="name" class="form-row">
            <label for="request_filter_name"><fmt:message key="admin.dishes.dish_name" /></label>
            <input type="text" name="request_filter_name" id="request_filter_name" pattern="^[A-za-z\\s]{2,50}$"
                   value="${request_filter_name}" placeholder="${request_filter_name}"/>
            <c:if test="${sessionScope.invalid_dish_name}">
                <div class="local-error">
                    <p><fmt:message key="filter.invalid_name"/></p>
                </div>
            </c:if>
        </div>
        <div id="find-action">
            <input type="submit" value="${find}"/>
            <c:if test="${sessionScope.invalid_filter_parameters}">
                <div class="local-error">
                    <p><fmt:message key="filter.invalid_filter_params"/></p>
                </div>
            </c:if>
            <c:if test="${sessionScope.filter_error}">
                <div class="local-error">
                    <p><fmt:message key="filter.filter_error"/></p>
                </div>
            </c:if>
        </div>
    </form>
</div>
<div class="workspace">
    <div class="workspace-flex-container">
        <c:forEach var="dish" items="${sessionScope.dishes}">
            <div class="flex-block">
                <div class="block-item">
                    <h3>${dish.name}</h3>
                </div>
                <div id="dish-picture" class="block-item">
                    <img class="dish-picture"
                         src="../images/dish/${dish.photo}"
                         onerror="this.src='../images/default_dish.png';">
                </div>
                <div id="description" class="block-item">
                    <div class="row-item-flexbox">
                        <a>${dish.calories} <fmt:message key="admin.dishes.calories"/></a>
                    </div>
                    <div class="row-item-flexbox">
                        <a>${dish.amountGrams} <fmt:message key="admin.dishes.grams"/></a>
                    </div>
                    <div class="row-item-flexbox">
                        <a>${dish.price} <fmt:message key="admin.dishes.BYN"/></a>
                    </div>
                </div>
                <c:if test="${sessionScope.user != null}">
                  <c:if test="${sessionScope.user.role.name() == 'ADMIN'}">
                    <c:if test="${dish.isAvailable}">
                      <c:url value="/ApiController?command=disable_dish" var="disable_dish_command"/>
                      <form action="${disable_dish_command}" method="post">
                        <input type="hidden" name="dish_id" value="${dish.id}">
                        <input type="submit" value="${disable}">
                      </form>
                    </c:if>
                    <c:if test="${not dish.isAvailable}">
                      <c:url value="/ApiController?command=make_dish_available" var="make_dish_available_command"/>
                        <form action="${make_dish_available_command}" method="post">
                          <input type="hidden" name="dish_id" value="${dish.id}">
                          <input type="submit" value="${make_available}">
                        </form>
                    </c:if>
                    <c:url value="/ApiController?command=go_to_edit_dish&id=${dish.id}" var="go_to_edit_dish_command"/>
                    <form action="${go_to_edit_dish_command}" method="post">
                      <input type="hidden" name="dish_id" value="${dish.id}">
                      <input type="submit" value="${edit}">
                    </form>
                  </c:if>
                  <c:if test="${sessionScope.user.role.name() == 'CUSTOMER'}">
                      <c:if test="${dish.isAvailable && sessionScope.user.status.name() == 'CONFIRMED'}">
                          <c:url value="/ApiController?command=add_to_basket&id=${dish.id}" var="add_to_basket"/>
                          <form action="${add_to_basket}" method="post">
                             <input type="hidden" name="dish_id" value="${dish.id}">
                             <input type="submit" value="${add_to_basket_text}">
                         </form>
                      </c:if>
                  </c:if>

                </c:if>
                <c:url value="/ApiController?command=go_to_dish_page&id=${dish.id}" var="go_to_dish_page_command"/>
                <form action="${go_to_dish_page_command}" method="post">
                    <input type="hidden" name="dish_id" value="${dish.id}">
                    <input type="submit" value="${more_information}">
                </form>
            </div>
        </c:forEach>
        <c:if test="${sessionScope.user.role.name() == 'ADMIN'}">
            <div class="dish-info">
                <div class="flex-block">
                    <div class="block-item">
                        <h3><fmt:message key="admin.dishes.new_dish"/></h3>
                    </div>
                    <div id="dish-picture" class="block-item">
                        <img class="dish-picture"
                             src="../images/default_dish.png">
                    </div>
                    <c:url value="/ApiController?command=go_to_create_dish" var="create_dish_page"/>
                    <div id="dish-add-picture" class="block-item">
                        <a href="${create_dish_page}">
                            <img id="add-icon" class="dish-picture"
                                 src="../images/add.png">
                        </a>
                    </div>
                    <form action="${create_dish_page}" method="post">
                        <input type="submit" value="${create_dish_message}">
                    </form>
                </div>
            </div>
        </c:if>
    </div>
    <div class="pagination">
        <c:if test="${sessionScope.min_pos != 1}">
            <div class="block-item-action">
                <c:url value="/ApiController?command=go_to_dishes_page&next_min_pos=${sessionScope.min_pos-sessionScope.page_items_amount}&next_max_pos=${sessionScope.min_pos-1}" var="prev_dish_page"/>
                <a href="${prev_dish_page}"><fmt:message key="previous"/></a>
            </div>
        </c:if>
        <c:if test="${sessionScope.max_pos != sessionScope.total_amount}">
            <div class="block-item-action">
                <c:url value="/ApiController?command=go_to_dishes_page&next_min_pos=${sessionScope.max_pos+1}&next_max_pos=${sessionScope.max_pos+sessionScope.page_items_amount}" var="next_dish_page"/>
                <a href="${next_dish_page}"><fmt:message key="next"/></a>
            </div>
        </c:if>
    </div>
</div>
</body>
<style>
    .block-item {
        margin-top: 0px;
        margin-bottom: 0px;
        padding-top: 0px;
        padding-bottom: 0px;
        border: none;
        -webkit-box-shadow: none;
        -moz-box-shadow: none;
        box-shadow: none;
    }
    .block-item:hover {
        border: none;
        -webkit-box-shadow: none;
        -moz-box-shadow: none;
        box-shadow: none;
    }
</style>
</html>