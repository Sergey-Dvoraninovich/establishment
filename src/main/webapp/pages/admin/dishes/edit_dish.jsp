<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale" />

<c:set var="edit"><fmt:message key="edit" /></c:set>
<c:set var="add"><fmt:message key="add" /></c:set>
<c:set var="remove"><fmt:message key="remove" /></c:set>
<c:set var="id"><%= request.getParameter("id") %></c:set>
<c:set var="name">${sessionScope.dish.name}</c:set>
<c:set var="photo">${sessionScope.dish.photo}</c:set>
<c:set var="price">${sessionScope.dish.price}</c:set>
<c:set var="amount_grams">${sessionScope.dish.amountGrams}</c:set>
<c:set var="calories_amount">${sessionScope.dish.calories}</c:set>

<html>
<head>
    <title><fmt:message key="admin.dishes.edit_dish_title"/> ${sessionScope.dish.name}</title>
    <script src="../../../js/common.js"></script>
</head>
<body>
<jsp:include page="../../shared/header.jsp" />
<c:url value="/ApiController?command=edit_dish" var="var"/>
<form action="${var}" method="post" class="ui-form">
    <h3><fmt:message key="admin.dishes.add_dish_title"/></h3>
    <input type="hidden" id="id" name="id" value="${id}"/>
    <div class="form-row">
        <label for="name"><fmt:message key="admin.dishes.dish_name" /></label>
        <input type="text" name="name" id="name" value="${name}"
               pattern="^[A-Za-zА-Яа-я]{1}[A-Za-zА-Яа-я\s]{0,68}[A-Za-zА-Яа-я]{1}$"
               placeholder="${sessionScope.dish.name}"/>
        <c:if test="${sessionScope.invalid_dish_name}">
            <div class="local-error">
                <p><fmt:message key="admin.dishes.invalid_dish_name"/></p>
            </div>
        </c:if>
    </div>
    <input type="hidden" name="photo" id="photo" value="${photo}"/>
    <div class="form-row">
        <label for="price"><fmt:message key="admin.dishes.dish_price" /></label>
        <input type="number" step="0.01" min="0" name="price" id="price" value="${price}" placeholder="${sessionScope.dish.price}"/>
        <c:if test="${sessionScope.invalid_dish_price}">
            <div class="local-error">
                <p><fmt:message key="admin.dishes.invalid_dish_price"/></p>
            </div>
        </c:if>
    </div>
    <div class="form-row">
        <label for="amount_grams"><fmt:message key="admin.dishes.dish_amount_grams" /></label>
        <input type="number" step="1" min="0" name="amount_grams" id="amount_grams" value="${amount_grams}" placeholder="${sessionScope.dish.amountGrams}"/>
        <c:if test="${sessionScope.invalid_dish_amount_grams}">
            <div class="local-error">
                <p><fmt:message key="admin.dishes.invalid_dish_amount_grams"/></p>
            </div>
        </c:if>
    </div>
    <div class="form-row">
        <label for="calories_amount"><fmt:message key="admin.dishes.dish_calories_amount" /></label>
        <input type="number" step="1" min="0" name="calories_amount" id="calories_amount" value="${calories_amount}" placeholder="${sessionScope.dish.calories}"/>
        <c:if test="${sessionScope.invalid_dish_calories_amount}">
            <div class="local-error">
                <p><fmt:message key="admin.dishes.invalid_dish_calories_amount"/></p>
            </div>
        </c:if>
    </div>
    <div>
        <input type="submit" value="${edit}"/>
    </div>
    <c:if test="${sessionScope.dish_validation_error}">
        <div class="local-error">
            <p><fmt:message key="admin.dishes.dish_validation_error"/></p>
        </div>
    </c:if>
    <c:if test="${sessionScope.edit_dish_error}">
        <div class="local-error">
            <p><fmt:message key="admin.dishes.edit_dish_error"/></p>
        </div>
    </c:if>
</form>
<c:url value="/ApiController?command=add_dish_ingredient" var="add_dish_ingredient"/>
<form action="${add_dish_ingredient}" method="post" class="ui-form">
    <h3><fmt:message key="admin.dishes.add_dish_ingredient"/></h3>
    <input type="hidden" id="id" name="id" value="${id}"/>
    <div class="form-row">
        <label for="available_ingredients"><fmt:message key="name" /></label>
        <select class="ingredient-select" id="available_ingredients" name="available_ingredients">
            <c:forEach var="ingredient" items="${sessionScope.unused_ingredients}">
                <option class="ingredient-option" value="${ingredient.id}">${ingredient.name}</option>
            </c:forEach>
        </select>
    </div>
    <div>
        <input type="submit" value="${add}"/>
    </div>
    <c:if test="${sessionScope.add_dish_ingredient_error}">
        <div class="local-error">
            <p><fmt:message key="admin.dishes.add_dish_ingredient_error"/></p>
        </div>
    </c:if>
</form>
<c:url value="/ApiController?command=remove_dish_ingredient" var="remove_dish_ingredient"/>
<form action="${remove_dish_ingredient}" method="post" class="ui-form">
    <h3><fmt:message key="admin.dishes.remove_dish_ingredient"/></h3>
    <input type="hidden" id="id" name="id" value="${id}"/>
    <div class="form-row">
        <label for="ingredients"><fmt:message key="name" /></label>
        <select class="ingredient-select" id="ingredients" name="ingredients">
            <c:forEach var="ingredient" items="${sessionScope.ingredients}">
                <option class="ingredient-option" value="${ingredient.id}">${ingredient.name}</option>
            </c:forEach>
        </select>
    </div>
    <div>
        <input type="submit" value="${remove}"/>
    </div>
    <c:if test="${sessionScope.remove_dish_ingredient_error}">
        <div class="local-error">
            <p><fmt:message key="admin.dishes.remove_dish_ingredient_error"/></p>
        </div>
    </c:if>
</form>
</body>
<script>
    document.getElementById('placer').onclick = function(){
        document.getElementById('files').click();
    }

    function handleFileSelect(evt) {
        var files = evt.target.files;
        for (var i = 0, f; f = files[i]; i++) {
            if (!f.type.match('image.*')) {
                continue;
            }
            var reader = new FileReader();
            reader.onload = (function(theFile) {
                return function(e) {
                    document.getElementById('upload-image').src = [e.target.result].join('');
                };
            })(f);
            reader.readAsDataURL(f);
        }
    }
    document.getElementById('photo').addEventListener( 'change', handleFileSelect, false);
</script>
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
    .upload-photo-image {
        width: 70%;
        height: 120px;
        display: flex;
        flex-direction: column;
        align-items: center;
    }
    #placer {
        width: inherit;
        height: inherit;
        display: flex;
        flex-direction: column;
        align-items: center;
    }
    #upload-image {
        border-radius: 5px;
        height: inherit;
        width: auto;
    }
    #upload-photo-form {
        width: inherit;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: flex-start;
    }
    #upload-photo-submit {
        width: 70%;
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
    select{
        width:100%;
        border:2px solid #aaa;
        border-radius:5px;
        margin:8px 0;
        outline:none;
        padding:8px;
        box-sizing:border-box;
        transition:.3s;
    }
    select:focus{
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
    .block-item-header>h2 {
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
