<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale" />

<c:set var="find"><fmt:message key="filter.find" /></c:set>
<c:set var="request_filter_name">${sessionScope.ingredients_filter_name}</c:set>

<html>
<head>
    <title><fmt:message key="admin.ingredients.title"/></title>
    <link href="../../../css/pages/admin/ingredients/ingredients.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../../shared/header.jsp" />
<div class="filter-line">
    <c:url value="/ApiController?command=set_ingredients_filter_parameters" var="ingredients_filter"/>
    <form action="${ingredients_filter}" method="post">
        <div class="form-row">
            <label for="request_filter_name"><fmt:message key="name" /></label>
            <input type="text" name="request_filter_name" id="request_filter_name"
                   pattern="^[A-Za-zА-Яа-я]{1}[A-Za-zА-Яа-я\s]{0,30}[A-Za-zА-Яа-я]{1}$"
                   value="${request_filter_name}" placeholder="${request_filter_name}"/>
            <c:if test="${sessionScope.invalid_filter_parameters}">
                <div class="local-error">
                    <p><fmt:message key="filter.invalid_filter_params"/></p>
                </div>
            </c:if>
        </div>
        <div id="find-action">
            <input type="submit" value="${find}"/>
            <c:if test="${sessionScope.filter_error}">
                <div class="local-error">
                    <p><fmt:message key="filter.filter_error"/></p>
                </div>
            </c:if>
        </div>
    </form>
</div>
<div class="workspace-flex-container">
<c:forEach var="ingredient" items="${sessionScope.ingredients}">
    <div class="flex-block">
        <a>${ingredient.name}</a>
    </div>
</c:forEach>
<div id="add-button" class="flex-block">
    <c:url value="/ApiController?command=go_to_create_ingredient_page" var="users_page"/>
    <a href="${users_page}"><fmt:message key="add"/></a>
</div>
</div>
</body>
</html>
