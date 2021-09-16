<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="locale" />

<c:set var="disable"><fmt:message key="admin.dishes.disable" /></c:set>
<c:set var="make_available"><fmt:message key="admin.dishes.make_available" /></c:set>
<c:set var="edit"><fmt:message key="edit" /></c:set>
<c:set var="set"><fmt:message key="set" /></c:set>
<c:set var="more_information"><fmt:message key="more_information" /></c:set>
<c:set var="create_dish_message"><fmt:message key="admin.dishes.create_dish"/></c:set>

<html>
<head>
    <title>${sessionScope.dish.name}</title>
</head>
<body>
<jsp:include page="shared/header.jsp" />
<div class="workspace-flex-container">
    <div class="workspace-column">
        <div id="icon" class="block-item">
            <img class="profile-picture"
                 src="../images/dish/${sessionScope.dish.photo}"
                 onerror="this.src='../images/default_dish.png';">
        </div>
    </div>
    <div class="workspace-column">
        <div class="block-item">
            <div class="block-item-header">
                <h2>${sessionScope.dish.name}</h2>
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
            <c:if test="${sessionScope.user.role.name() == 'ADMIN'}">
                <c:url value="/ApiController?command=go_to_edit_dish&id=${sessionScope.dish.id}" var="go_to_edit_dish_command"/>
                <div class="block-item-action">
                    <form action="${go_to_edit_dish_command}" method="post">
                        <input type="hidden" name="dish_id" value="${sessionScope.dish.id}">
                        <input type="submit" value="${edit}">
                    </form>
                </div>
            </c:if>
        </div>
        <div class="block-item">
            <div class="block-item-header">
                <h2><fmt:message key="admin.ingredients.title"/></h2>
            </div>
            <div id="ingredients-description" class="info-block-item">
                <c:forEach var="ingredient" items="${sessionScope.ingredients}">
                    <div class="row-item-flexbox">
                        <a>${ingredient.name}</a>
                    </div>
                </c:forEach>
            </div>
            <c:if test="${sessionScope.user.role.name() == 'ADMIN'}">
                <c:url value="/ApiController?command=go_to_edit_dish&id=${sessionScope.dish.id}" var="edit_dish_ingredients"/>
                <div class="block-item-action">
                    <form action="${edit_dish_ingredients}" method="post">
                        <input type="hidden" name="dish_id" value="${sessionScope.dish.id}">
                        <input type="submit" value="${edit}">
                    </form>
                </div>
            </c:if>
        </div>
        <c:if test="${sessionScope.user.role.name() == 'ADMIN'}">
            <c:url value="/ApiController?command=upload_dish_photo&id=${sessionScope.dish.id}" var="edit_dish_photo"/>
            <div class="block-item">
                <div class="block-item-header">
                    <h2><fmt:message key="admin.dishes.photo_update"/></h2>
                </div>
                <form enctype="multipart/form-data" action="${edit_dish_photo}" method="post" id="upload-photo-form">
                    <input type="hidden" name="dish_id" value="${sessionScope.dish.id}">
                    <div class="upload-photo-image">
                        <label for="photo" id="placer">
                            <img id="upload-image" src="../images/photo_add.png">
                        </label>
                    </div>
                    <input style="display:none;" type="file" id="photo" name="photo" >
                    <div id="file-name"></div>
                    <input type="submit" value="${edit}" class="block-item-action" id="upload-photo-submit">
                    <c:if test="${sessionScope.impossible_to_upload_dish_photo}">
                        <div class="local-error">
                            <p><fmt:message key="admin.dishes.impossible_to_upload_dish_photo"/></p>
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.edit_dish_error}">
                        <div class="local-error">
                            <p><fmt:message key="admin.dishes.edit_dish_error"/></p>
                        </div>
                    </c:if>
                </form>
            </div>
            <c:if test="${sessionScope.dish.isAvailable}">
                <div class="block-item">
                    <div class="block-item-text">
                        <a><fmt:message key="admin.dishes.disable_info"/></a>
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
                        <a><fmt:message key="admin.dishes.make_available_info"/></a>
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
        </c:if>
    </div>
</div>
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
    .upload-photo-image {
        width: 70%;
        height: 120px;
        display: flex;
        flex-direction: column;
        align-items: center;
    }
    label {
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
    #ingredients-description{
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
    .row-item-flexbox>a{
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
        height: 100%;
        width: auto;
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
        margin: 0px;
        padding: 0px;
    }
    .block-item-text{
    }
    .block-item-text>a{
        color: #4d4d4d;
        font-size: 15px;
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
    .block-item-action>a {
        color: #ffffff;
        font-size: 30px;
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
    input[type=file]{
        width:100%;
        border:2px solid #aaa;
        border-radius:5px;
        margin:8px 0;
        outline:none;
        padding:8px;
        box-sizing:border-box;
        transition:.3s;
    }
    input[type=file]:focus{
        border-color:#a15566;
        box-shadow:0 0 8px 0 #a15566;
    }
    h3 {
        font-size: 25px;
    }
    .local-error {
        font-size: 15px;
        color: red;
    }
</style>
</html>
