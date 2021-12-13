<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<!doctype html>
<html lang="${sessionScope.lang}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, userBean-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title><fmt:message key="jsp.index.home"/></title>

    <jsp:include page="includes.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/indexStyle.css" type="text/css">

</head>
<body>
    <jsp:include page="header.jsp"/>

    <div class="container vh-100 d-flex justify-content-around align-items-center">

        <div class="content">
            <h1 class="heading">
                <fmt:message key="jsp.index.lorem4"/>
            </h1>

            <p>
                <fmt:message key="jsp.index.lorem"/>
            </p>

            <a href="#" class="btn btn-dark" style="width: 150px !important;" id="signIn">
                <fmt:message key="jsp.index.signIn"/>
            </a>
            <a href="#" class="btn btn-dark" style="width: 150px !important;" id="signUp">
                <fmt:message key="jsp.index.signUp"/>
            </a>
        </div>

        <div class="login invisible" id="login">
            <form action="app" method="post" class="d-flex flex-column" id="login-form">
                <input type="hidden" name="command" value="login">

                <div class="form-group">
                    <label for="email-login">
                        <fmt:message key="jsp.index.email"/>
                    </label>
                    <input type="email" class="form-control login-input"
                           name="email-login" id="email-login" placeholder="example@mail.com" required>
                </div>

                <div class="form-group">
                    <label for="password-login">
                        <fmt:message key="jsp.index.password"/>
                    </label>
                    <input type="password" class="form-control login-input"
                           name="password-login" id="password-login"
                           placeholder="<fmt:message key="jsp.index.password"/>" required>
                </div>

                <button type="submit" class="btn btn-dark" style="margin-top: 20px;">
                    <fmt:message key="jsp.index.logIn"/>
                </button>
                <a href="#" class="link-secondary" id="forgot-password">
                    <fmt:message key="jsp.index.forgotPassword"/>
                </a>
            </form>
        </div>

        <div class="register invisible" id="register">
            <form action="app" method="post" class="d-flex flex-column" id="register-form">
                <input type="hidden" name="command" value="register">

                <div class="form-group">
                    <label for="email-register">
                        <fmt:message key="jsp.index.email"/>
                    </label>
                    <input type="email" class="form-control register-input"
                           name="email-register" id="email-register" placeholder="example@mail.com" required>
                </div>

                <div class="form-group">
                    <label for="name-register">
                        <fmt:message key="jsp.index.name"/>
                    </label>
                    <input type="text" class="form-control register-input"
                           name="name-register" id="name-register"
                           placeholder="<fmt:message key="jsp.index.name"/>" required>
                </div>

                <div class="form-group">
                    <label for="surname-register">
                        <fmt:message key="jsp.index.surname"/>
                    </label>
                    <input type="text" class="form-control register-input"
                           name="surname-register" id="surname-register"
                           placeholder="<fmt:message key="jsp.index.surname"/>" required>
                </div>

                <div class="form-group">
                    <label for="password-register">
                        <fmt:message key="jsp.index.password"/>
                    </label>
                    <input type="password" class="form-control register-input"
                           name="password-register" id="password-register"
                           placeholder="<fmt:message key="jsp.index.password"/>" required>
                </div>

                <div class="form-group">
                    <label for="password-register-confirm">
                        <fmt:message key="jsp.index.confirmPassword"/>
                    </label>
                    <input type="password" class="form-control register-input"
                           name="password-register-confirm" id="password-register-confirm"
                           placeholder="<fmt:message key="jsp.index.confirmPassword"/>" required>
                </div>

                <button type="submit" class="btn btn-dark" style="margin-top: 20px;">
                    <fmt:message key="jsp.index.signUp"/>
                </button>
            </form>
        </div>

        <div class="restore invisible" id="restore">
            <form action="app" method="post" class="d-flex flex-column" id="restore-form">
                <input type="hidden" name="command" value="restore">

                <h3>
                    <fmt:message key="jsp.index.findAccount"/>
                </h3>

                <div class="form-group">
                    <label for="email-restore">
                        <fmt:message key="jsp.index.email"/>
                    </label>
                    <input type="email" class="form-control" name="email-restore" id="email-restore" placeholder="example@mail.com" required>
                </div>

                <button type="submit" class="btn btn-dark" style="margin-top: 20px;">
                    <fmt:message key="jsp.index.sendEmail"/>
                </button>
            </form>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/script/indexPageScript.js"></script>

    <jsp:include page="messages.jsp"/>

</body>
</html>
