<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="m" uri="/WEB-INF/tld/BigDecimal.tld" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<!doctype html>
<html lang="${sessionScope.lang}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title><fmt:message key="jsp.transaction.title"/></title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/accountCard.css">

    <jsp:include page="../includes.jsp"/>
</head>
<body>

    <jsp:include page="../header.jsp"/>

    <div class="container d-flex flex-column justify-content-center align-items-center" style="width: 100%">
        <div class="d-flex flex-row justify-content-between align-items-center"
             style="width: 100%">
            <p style="font-weight: bold; font-size: 32px; margin-left: 15%;" class="align-self-start">
                <fmt:message key="jsp.transaction.title"/>
            </p>

            <div class="d-flex justify-content-around align-items-center">
                <a style="margin-left: 15px;" onclick="addUrlParameter('transaction-order', 'id')" type="button" class="btn btn-dark">
                    <fmt:message key="jsp.transaction.sortId"/>
                </a>
                <a style="margin-left: 15px;" onclick="addUrlParameter('transaction-order', 'time')" type="button" class="btn btn-dark">
                    <fmt:message key="jsp.transaction.sortTime"/>
                </a>
                <a style="margin-left: 15px;" onclick="addUrlParameter('transaction-order', 'amount')" type="button" class="btn btn-dark">
                    <fmt:message key="jsp.transaction.sortAmount"/>
                </a>
            </div>
        </div>

        <c:forEach items="${requestScope.transactions}" var="transaction">
            <div class="d-flex flex-column justify-content-around align-items-center"
                 style="margin-top: 15px; border: black 1px solid; padding: 20px; width: 50%;
                        overflow-wrap: anywhere">

                <p style="font-size: 12px">${transaction.id}</p>
                <p style="font-size: 14px">${transaction.time}</p>
                <p><fmt:message key="jsp.transaction.from"/> ${transaction.from} | <fmt:message key="jsp.transaction.to"/> ${transaction.to}</p>
                <p style="font-size: 24px; font-weight: bold"><m:BigDecimal amount="${transaction.amount}"/> ${transaction.currency}</p>
                <p style="text-align: center">${transaction.message}</p>
                <p style="font-weight: bold">${transaction.status}</p>

                <form action="app" method="post">
                    <input type="hidden" name="command" value="pdf-receipt">

                    <button type="submit" name="id" value="${transaction.id}" class="btn btn-link link-dark">
                        <fmt:message key="jsp.transaction.receipt"/>
                    </button>
                </form>
            </div>
        </c:forEach>

        <jsp:include page="../pagination.jsp"/>
    </div>

    <jsp:include page="newAccount.jsp"/>
    <jsp:include page="topUp.jsp"/>
    <jsp:include page="userAccounts.jsp"/>
    <jsp:include page="../messages.jsp"/>
</body>
</html>