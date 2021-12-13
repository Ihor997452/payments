<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>Document</title>

    <jsp:include page="../includes.jsp"/>
</head>
<body>
<jsp:include page="../header.jsp"/>

<div class="container d-flex flex-column justify-content-center align-items-center">
    <p style="font-weight: bold; font-size: 32px; margin-left: 15%;" class="align-self-start">Transactions</p>

    <form action="app" method="get"
          class="d-flex d-flex justify-content-around align-items-end"
          style="width: 100%">
        <input type="hidden" name="command" value="admin-transactions">

        <div class="form-group" style="width: 100%; margin-right: 15px">
            <label for="search">
                Search
            </label>
            <input type="text" value="${param.search}" class="form-control" name="search" id="search">
        </div>

        <button type="submit" class="btn btn-dark">
            <fmt:message key="jsp.verify.submit"/>
        </button>
    </form>

    <div class="w-100">
        <c:forEach items="${requestScope.transactions}" var="transactions">
            <div class="d-flex justify-content-around">
                <div class="d-flex flex-column justify-content-around align-items-center"
                     style="margin-top: 15px; border: black 1px solid; padding: 10px; width: 60%;
                        overflow-wrap: anywhere">

                    <p style="font-size: 12px">ID: ${transactions.id}</p>
                    <p style="font-size: 12px">FROM: ${transactions.from} | TO: ${transactions.to}</p>
                    <p style="font-size: 12px">AMOUNT: <m:BigDecimal amount="${transactions.amount}"/></p>
                    <p style="font-size: 12px">TIME: ${transactions.time}</p>
                    <p style="font-size: 12px">CURRENCY: ${transactions.currency}</p>
                    <p style="font-size: 12px">STATUS: ${transactions.status}</p>
                    <p style="font-size: 12px">MESSAGE: ${transactions.message}</p>
                </div>

                <div class="d-flex flex-column justify-content-around" style="padding: 20px 0">
                    <a href="?command=admin-accounts&search=${transactions.from}" type="button" class="btn btn-dark">View From</a>
                    <a href="?command=admin-accounts&search=${transactions.to}" type="button" class="btn btn-dark">View To</a>
                </div>
            </div>
        </c:forEach>
    </div>

    <jsp:include page="../pagination.jsp"/>
</div>


<jsp:include page="../user/editProfile.jsp"/>
<jsp:include page="../user/newAccount.jsp"/>
<jsp:include page="../user/topUp.jsp"/>
<jsp:include page="../user/userAccounts.jsp"/>
<jsp:include page="../messages.jsp"/>

</body>
</html>
