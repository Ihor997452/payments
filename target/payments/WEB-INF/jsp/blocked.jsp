<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<!doctype html>
<html lang="${sessionScope.lang}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>
        <fmt:message key="jsp.blocked.title"/>
    </title>
    <jsp:include page="includes.jsp"/>
</head>
<body>
    <jsp:include page="header.jsp"/>

    <div class="d-flex justify-content-around align-items-baseline"
         style="margin-top: 5%;">
        <p style="font-weight: bold; font-size: 32px">
            <fmt:message key="jsp.blocked.profileMessage"/>
        </p>
        <form action="app" method="post">
            <input type="hidden" name="command" value="send-request">
            <input type="hidden" name="request-type" value="1">
            <button type="submit" class="btn btn-dark">
                <fmt:message key="jsp.blocked.requestUnblock"/>
            </button>
        </form>
    </div>

    <jsp:include page="messages.jsp"/>
</body>
</html>
