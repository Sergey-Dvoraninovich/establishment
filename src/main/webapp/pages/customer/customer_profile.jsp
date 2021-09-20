<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="locale" />

<c:set var="edit"><fmt:message key="edit" /></c:set>

<html>
<head>
    <link href = "../../css/style.css" rel = "style" type = "text/css" />
    <title><fmt:message key="profile"/></title>
</head>
<body>
<jsp:include page="../shared/header.jsp" />
<div class="workspace-flex-container">
    <div class="workspace-column">
        <div id="icon" class="block-item">
            <img class="profile-picture"
                 src="../../images/${sessionScope.user.photo}"
                 onerror="this.src='../../images/default_profile.png';">
        </div>
        <div class="block-item">
            <div class="block-item-header">
                <h2><fmt:message key="profile.bonuses_amount"/></h2>
            </div>
            <div class="block-item-main-info">
               <h2>${sessionScope.user.bonusesAmount}</h2>
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
        <c:url value="/ApiController?command=upload_user_photo&id=${sessionScope.user.id}" var="edit_user_photo"/>
        <div class="block-item">
            <div class="block-item-header">
                <h2><fmt:message key="profile.change_profile_photo"/></h2>
            </div>
            <form enctype="multipart/form-data" action="${edit_user_photo}" method="post" id="upload-photo-form">
                <input type="hidden" name="dish_id" value="${sessionScope.user.id}">
                <div class="upload-photo-image">
                    <label for="photo" id="placer">
                        <img id="upload-image" src="../../images/photo_add.png">
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
                    <img class="profile-item-picture" src="../../images/profile_icon.png">
                </div>
                <div class="row-item-flexbox-item">
                    <h2>${sessionScope.user.login}</h2>
                </div>
            </div>
            <div class="row-item-flexbox">
                <div class="row-item-flexbox-item">
                    <img class="profile-item-picture" src="../../images/profile_mail.png">
                </div>
                <div class="row-item-flexbox-item">
                    <h2>${sessionScope.user.mail}</h2></div>
                </div>
            <div class="row-item-flexbox">
                <div class="row-item-flexbox-item">
                    <img class="profile-item-picture" src="../../images/profile_phone.png">
                </div>
                <div class="row-item-flexbox-item">
                    <h2>${sessionScope.user.phoneNumber}</h2>
                </div>
            </div>
            <div class="row-item-flexbox">
                <div class="row-item-flexbox-item">
                    <img class="profile-item-picture" src="../../images/profile_card.png">
                </div>
                <div class="row-item-flexbox-item">
                    <h2>${sessionScope.user.cardNumber}</h2>
                </div>
            </div>
            <div id=description-action class="block-item-action">
                <c:url value="/ApiController?command=go_to_edit_customer_page" var="edit_page"/>
                <a href="${edit_page}"><fmt:message key="profile.edit"/></a>
            </div>
        </div>
        <div class="block-item">
            <div class="block-item-text">
                <a><fmt:message key="profile.orders_info"/></a>
            </div>
            <div class="block-item-action">
                <c:url value="/ApiController?command=go_to_customer_orders&next_min_pos=1&next_max_pos=5&new_total_amount=true" var="orders_page"/>
                <a href="${orders_page}"><fmt:message key="profile.orders"/></a>
            </div>
        </div>
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

    .local-error {
        font-size: 15px;
        color: red;
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
