<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="locale" />

<c:set var="change"><fmt:message key="change" /></c:set>

<html>
<head>
    <title><fmt:message key="profile"/></title>
</head>
<body>
<jsp:include page="../shared/header.jsp" />
<div class="workspace">
    <div class="column">
        <picture>
            <source srcset="src="${sessionScope.user.photo}"">
            <img src="../../images/default_profile.jpg">
        </picture>
        <form action="upload" enctype="multipart/form-data" method="POST">
            Upload file:
            <input type="file" name="content" height="130">
            <br/>
            <input type="submit" value="Upload file">
        </form>
    </div>
    <div class="column">
        <div class="edit-item">
            <c:url value="/ApiController?command=change_customer_password_command" var="change_password_command"/>
            <form action="${change_password_command}" method="post">
                <input type="hidden" name="dish_id" value="${sessionScope.user.id}">
                <input type="password" name="password" pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$"
                       id="password" value="${password}"/>
                <input type="password" name="repeat_password" pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$"
                       id="repeat_password" value="${repeat_password}"/>
                <input type="submit" value="${change}">
            </form>
            <c:if test="${sessionScope.invalid_password}">
                <div class="local-error">
                    <p><fmt:message key="registration.invalid_password" /></p>
                </div>
            </c:if>
            <c:if test="${sessionScope.different_passwords}">
                <div class="local-error">
                    <p><fmt:message key="registration.different_passwords" /></p>
                </div>
            </c:if>
            <c:if test="${sessionScope.change_password_error}">
                <div class="local-error">
                    <p><fmt:message key="profile.change_password_error" /></p>
                </div>
            </c:if>
        </div>
        <div class="edit-item">
            <c:url value="/ApiController?command=change_customer_mail_command" var="change_mail_command"/>
            <form action="${change_mail_command}" method="post">
                <input type="hidden" name="dish_id" value="${sessionScope.user.id}">
                <input type="text" name="mail" id="mail" pattern = "^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$"
                       value="${mail}" placeholder="${sessionScope.user.mail}"/>
                <input type="submit" value="${change}">
            </form>
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
            <c:if test="${sessionScope.change_mail_error}">
                <div class="local-error">
                    <p><fmt:message key="profile.change_mail_error" /></p>
                </div>
            </c:if>
        </div>
        <div class="edit-item">
            <c:url value="/ApiController?command=change_customer_phone_num_command" var="change_phone_num_command"/>
            <form action="${change_phone_num_command}" method="post">
                <input type="hidden" name="dish_id" value="${sessionScope.user.id}">
                <input type="tel" name="phone_num" id="phone_num" pattern="^\+\d{12}$"
                       value="${phone_num}" placeholder="${sessionScope.user.phoneNumber}"/>
                <input type="submit" value="${change}">
            </form>
            <c:if test="${sessionScope.invalid_phone_num}">
                <div class="local-error">
                    <p><fmt:message key="registration.invalid_phone_num" /><p>
                </div>
            </c:if>
            <c:if test="${sessionScope.change_phone_num_error}">
                <div class="local-error">
                    <p><fmt:message key="profile.change_phone_num_error" /></p>
                </div>
            </c:if>
        </div>
        <div class="edit-item">
            <c:url value="/ApiController?command=change_customer_card_num_command" var="change_card_num_command"/>
            <form action="${change_card_num_command}" method="post">
                <input type="hidden" name="dish_id" value="${sessionScope.user.id}">
                <input type="text" name="card_num" pattern="^\d{16}$"
                       id="card_num" value="${card_num}" placeholder="${sessionScope.user.cardNumber}"/>
                <input type="submit" value="${change}">
            </form>
            <c:if test="${sessionScope.invalid_card_num}">
                <div class="local-error">
                    <p><fmt:message key="registration.invalid_card_num" /></p>
                </div>
            </c:if>
            <c:if test="${sessionScope.change_card_num_error}">
                <div class="local-error">
                    <p><fmt:message key="profile.change_card_num_error" /></p>
                </div>
            </c:if>
        </div>
    </div>
</div>
</body>
<style>
    .workspace {
        margin-top: 35px;
        padding-top: 35px;
    }
</style>
</html>
