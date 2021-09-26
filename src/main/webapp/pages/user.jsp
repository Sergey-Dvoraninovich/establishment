<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="locale" />

<c:set var="edit"><fmt:message key="edit" /></c:set>
<c:set var="user_profile">${sessionScope.user_profile}</c:set>
<c:set var="user">${sessionScope.user}</c:set>
<c:set var="phone_num">${sessionScope.user_profile.phoneNumber}</c:set>
<c:set var="card_num">${sessionScope.user.cardNumber}</c:set>
<c:set var="mail">${sessionScope.user.mail}</c:set>
<c:set var="isEditPage">${sessionScope.is_editing_page}</c:set>

<html>
<head>
    <link href = "../css/style.css" rel = "style" type = "text/css" />
    <title><fmt:message key="profile"/></title>
</head>
<body>
<jsp:include page="shared/header.jsp" />
<div class="workspace-flex-container">
    <div id="profile" class="workspace-column">
        <div id="icon" class="block-item">
            <img class="profile-picture"
                 src="../images/user/${sessionScope.user_profile.photo}"
                 onerror="this.src='../images/default_profile.png';">
        </div>
        <c:if test="${sessionScope.user.role == 'CUSTOMER'}">
            <div class="block-item">
                <div class="block-item-header">
                    <h2><fmt:message key="profile.bonuses_amount"/></h2>
                </div>
                <div class="block-item-main-info">
                    <h2>${sessionScope.user_profile.bonusesAmount}</h2>
                </div>
            </div>
            <div class="block-item">
                <div class="block-item-text">
                    <a><fmt:message key="profile.basket_info"/></a>
                </div>
                <div class="block-item-action">
                    <c:url value="/ApiController?command=go_to_customer_basket" var="basket_page"/>
                    <a href="${basket_page}"><fmt:message key="profile.basket"/></a>
                </div>
            </div>
        </c:if>
        <c:url value="/ApiController?command=upload_user_photo&id=${sessionScope.user_profile.id}" var="edit_user_photo"/>
        <div class="block-item">
            <div class="block-item-header">
                <h2><fmt:message key="profile.change_profile_photo"/></h2>
            </div>
            <form enctype="multipart/form-data" action="${edit_user_photo}" method="post" id="upload-photo-form">
                <input type="hidden" name="dish_id" value="${sessionScope.user_profile.id}">
                <div class="upload-photo-image">
                    <label for="photo" id="placer">
                        <img id="upload-image" src="../images/photo_add.png">
                    </label>
                </div>
                <input style="display:none;" type="file" id="photo" name="photo" >
                <div id="file-name"></div>
                <input type="submit" value="${edit}" class="block-item-action" id="upload-photo-submit">
                <c:if test="${sessionScope.impossible_to_upload_user_photo}">
                    <div class="local-error">
                        <p><fmt:message key="profile.impossible_to_upload_user_photo"/></p>
                    </div>
                </c:if>
                <c:if test="${sessionScope.change_profile_photo_error}">
                    <div class="local-error">
                        <p><fmt:message key="profile.edit_profile_photo_error"/></p>
                    </div>
                </c:if>
            </form>
        </div>
    </div>
    <div class="workspace-column">
        <div id="description" class="block-item">
            <div class="row-item-flexbox">
                <div class="row-item-flexbox-item">
                    <img class="profile-item-picture" src="../images/profile_icon.png">
                </div>
                <div class="row-item-flexbox-item">
                    <h2>${sessionScope.user_profile.login}</h2>
                </div>
            </div>
            <div class="row-item-flexbox">
                <div class="row-item-flexbox-item">
                    <img class="profile-item-picture" src="../images/profile_mail.png">
                </div>
                <div class="row-item-flexbox-item">
                    <h2>${sessionScope.user_profile.mail}</h2></div>
                </div>
            <div class="row-item-flexbox">
                <div class="row-item-flexbox-item">
                    <img class="profile-item-picture" src="../images/profile_phone.png">
                </div>
                <div class="row-item-flexbox-item">
                    <h2>${sessionScope.user_profile.phoneNumber}</h2>
                </div>
            </div>
            <c:if test="${sessionScope.user.role == 'CUSTOMER'}">
                <div class="row-item-flexbox">
                    <div class="row-item-flexbox-item">
                        <img class="profile-item-picture" src="../images/profile_card.png">
                    </div>
                    <div class="row-item-flexbox-item">
                        <h2>${sessionScope.user_profile.cardNumber}</h2>
                    </div>
                </div>
            </c:if>
            <c:if test="${sessionScope.user.role == 'ADMIN'
                          && sessionScope.user.id != sessionScope.user_profile.id}">
                <div class="row-item-flexbox">
                    <div class="row-item-flexbox-item">
                        <img class="profile-item-picture" src="../images/profile_card.png">
                    </div>
                    <div class="row-item-flexbox-item">
                        <h2>${sessionScope.user_profile.cardNumber}</h2>
                    </div>
                </div>
            </c:if>
            <div id=description-action class="block-item-action">
                <a><fmt:message key="profile.edit"/></a>
            </div>
        </div>
        <div id="edit-block" class="block-item">
            <c:url value="/ApiController?command=edit_user_data?id=${sessionScope.user_profile.id}&edit_form=true" var="edit_user_phone_num"/>
            <form action="${edit_user_phone_num}" method="post">
                <div class="form-row">
                    <label for="phone_num"><fmt:message key="registration.phone_num_placeholder" /></label>
                    <input type="text" name="phone_num" id="phone_num" pattern="^\+\d{12}$" value="${phone_num}" placeholder="${phone_num}"/>
                    <c:if test="${sessionScope.invalid_phone_num}">
                        <div class="local-error">
                            <p><fmt:message key="registration.invalid_phone_num" /><p>
                        </div>
                    </c:if>
                </div>
                <div>
                    <input type="submit" value="${edit}"/>
                </div>
            </form>
            <c:if test="${ sessionScope.user.role == 'CUSTOMER'
            || (sessionScope.user.role == 'ADMIN'
            && sessionScope.user.id != sessionScope.user_profile.id)}">
            <c:url value="/ApiController?command=edit_user_data?id=${sessionScope.user_profile.id}&edit_form=true" var="edit_user_card_num"/>
            <form action="${edit_user_card_num}" method="post">
                <div class="form-row">
                    <label for="card_num"><fmt:message key="registration.card_num_placeholder" /></label>
                    <input type="text" name="card_num" id="card_num" pattern="^\d{16}$" value="${card_num}" placeholder="${card_num}"/>
                    <c:if test="${sessionScope.invalid_card_num}">
                        <div class="local-error">
                            <p><fmt:message key="registration.invalid_card_num" /></p>
                        </div>
                    </c:if>
                </div>
                <div>
                    <input type="submit" value="${edit}"/>
                </div>
            </form>
            </c:if>
            <form>
                <div class="form-row">
                    <label for="mail"><fmt:message key="registration.mail_placeholder" /></label>
                    <div id="mail-info-text" class="block-item-text">
                        <a><fmt:message key="profile.change_mail_info"/></a>
                    </div>
                    <input type="text" name="mail" pattern = ^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$
                           id="mail" value="${mail}" placeholder="${mail}"/>
                    <c:if test="${sessionScope.invalid_mail}">
                        <div class="local-error">
                            <p><fmt:message key="registration.invalid_mail" /></p>
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.not_unique_mail}">
                        <div class="local-error">
                            <p><fmt:message key="registration.not_unique_mail" /></p>
                        </div>
                    </c:if>
                    <div>
                        <input type="submit" value="${edit}"/>
                    </div>
                </div>
            </form>
        </div>
        <c:if test="${sessionScope.user.role == 'ADMIN'
                      && sessionScope.user.id != sessionScope.user_profile.id}">
            <div class="block-item">
                <div class="block-item-text">
                    <a><fmt:message key="profile.orders_info"/></a>
                </div>
                <div class="block-item-action">
                    <c:url value="/ApiController?command=go_to_customer_orders&next_min_pos=1&next_max_pos=5&new_total_amount=true" var="orders_page"/>
                    <a href="${orders_page}"><fmt:message key="profile.orders"/></a>
                </div>
            </div>
        </c:if>
        <c:if test="${sessionScope.user.role == 'CUSTOMER'}">
            <div class="block-item">
                <div class="block-item-text">
                    <a><fmt:message key="profile.orders_info"/></a>
                </div>
                <div class="block-item-action">
                    <c:url value="/ApiController?command=go_to_customer_orders&next_min_pos=1&next_max_pos=5&new_total_amount=true" var="orders_page"/>
                    <a href="${orders_page}"><fmt:message key="profile.orders"/></a>
                </div>
            </div>
        </c:if>
        <c:if test="${sessionScope.user_profile.status == 'IN_REGISTRATION'}">
            <div class="block-item">
                <div class="block-item-header">
                    <h2><fmt:message key="profile.mail_verification"/></h2>
                </div>
                <div class="block-item-text">
                    <a><fmt:message key="profile.mail_verification_info"/></a>
                </div>
                <div class="block-item-action">
                    <c:url value="/ApiController?command=go_to_verify_code_page" var="verification_page"/>
                    <a href="${verification_page}"><fmt:message key="profile.verify_mail"/></a>
                </div>
            </div>
        </c:if>
    </div>
</div>
<script>
    var isEditPage = ${sessionScope.is_editing_page}
    if (isEditPage) {
        document.getElementById("description-action").style.display = "none";
        document.getElementById("edit-block").style.display = "flex";
    }

    var editButton = document.getElementById("description-action");
    editButton.onclick = function openEditBlock(){
        document.getElementById("description-action").style.display = "none";
        document.getElementById("edit-block").style.display = "flex";
    }

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
    #profile {
        padding: 0px;
        width: 250px;
    }
    a {
        font-size: 15px;
        text-decoration: none;
    }

    #description{
        display: flex;
        flex-direction: column;
        align-items: flex-start;
        justify-content: flex-start;
    }
    #mail-info-text {
        width: 350px;
        margin-inline: auto;
    }
    #edit-block {
        display: none;
    }

    form {
        width: 100%;
        font-size: 20px;
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
    input {
        font-size: 18px;
    }
    input[type=text]:focus{
        border-color:#a15566;
        box-shadow:0 0 8px 0 #a15566;
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
        margin: 10px 0px;
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

    .local-error {
        font-size: 15px;
        color: red;
        padding-bottom: 0px;
    }
    .block-item-header{
        text-align: center;
        margin: 0px;
        padding: 5px 0px 0px 0px;
    }
    .block-item-header>h2{
        margin: 0px;
        padding: 0px;
        text-align: center;
        color: #000000;
        font-size: 25px;
    }
    .block-item-text{
        width: inherit;
        text-align: center;
    }
    .block-item-text>a{
        text-align: center;
        color: #4d4d4d;
        font-size: 20px;
    }
    .block-item-action {
        position: center;
        border-radius: 10px;
        margin-top: 15px;
        padding: 5px;
        text-align: center;
        width: 70%;
        background-color: #a15566;
    }
    #description-action {
        margin-left: 55px;
    }
    .block-item-action:hover {
        -webkit-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
        -moz-box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
        box-shadow: 4px 4px 8px 0px rgba(34, 60, 80, 0.25);
    }
    .block-item-action>a {
        color: #ffffff;
        font-size: 25px;
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
</style>
</html>
