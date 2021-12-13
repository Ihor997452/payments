<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <title><fmt:message key="jsp.verify.title"/></title>

    <jsp:include page="includes.jsp"/>
</head>
<body>

    <jsp:include page="header.jsp"/>

    <div class="container d-flex justify-content-center align-items-center">
        <form action="app" method="post" class="d-flex flex-column">
            <input type="hidden" name="command" value="verify">

            <div class="form-group">
                <label for="code"><fmt:message key="jsp.verify.code"/> </label>
                <input type="number" name="code" id="code" class="form-control" placeholder="123.." required/>
            </div>

            <button type="submit" class="btn btn-dark" style="margin-top: 20px">
                <fmt:message key="jsp.verify.submit"/>
            </button>
        </form>
    </div>

    <c:if test='${not empty requestScope.get("error")}'>
        <script>
            insertErrorMessage(document.body,
                "register-error",
                '${requestScope.get("error")}');
        </script>
    </c:if>

    <c:if test='${empty requestScope.get("error")}'>
        <script>
            insertInfoMessage(document.body,
                "register-info",
                '<fmt:message key="jsp.verify.sent"/>');
        </script>
    </c:if>

</body>
</html>