<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>Document</title>

    <jsp:include page="../includes.jsp"/>
</head>
<body>
<jsp:include page="../header.jsp"/>

<div class="container d-flex flex-column justify-content-center align-items-center">
    <p style="font-weight: bold; font-size: 32px; margin-left: 15%;" class="align-self-start">Requests</p>

    <form action="app" method="get"
          class="d-flex d-flex justify-content-around align-items-end"
          style="width: 100%">
        <input type="hidden" name="command" value="admin-requests">

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
        <c:forEach items="${requestScope.requests}" var="request">
            <div class="d-flex justify-content-around">
                <div class="d-flex flex-column justify-content-around align-items-center"
                     style="margin-top: 15px; border: black 1px solid; padding: 10px; width: 60%;
                        overflow-wrap: anywhere">

                    <p style="font-size: 12px">ID: ${request.id}</p>
                    <p style="font-size: 12px">REQUESTER: ${request.requesterId}</p>
                    <p style="font-size: 12px">REQUEST TYPE: ${request.requestType}</p>
                    <p style="font-size: 12px">REQUEST STATUS: ${request.requestStatus}</p>
                </div>

                <div class="d-flex flex-column justify-content-around" style="padding: 20px 0">
                    <a href="?command=admin-users&search=${request.requesterId}" type="button" class="btn btn-dark">View Requester Info</a>

                    <form action="app" method="post">
                        <input type="hidden" name="command" value="request-approve">
                        <input type="hidden" name="request" value="${request.id}">
                        <button type="submit" class="btn btn-dark w-100">Approve</button>
                    </form>

                    <form action="app" method="post">
                        <input type="hidden" name="command" value="request-disapprove">
                        <input type="hidden" name="request" value="${request.id}">
                        <button type="submit" class="btn btn-dark w-100">Disapprove</button>
                    </form>

                </div>
            </div>
        </c:forEach>
    </div>

    <jsp:include page="../pagination.jsp"/>
</div>

<jsp:include page="../messages.jsp"/>
</body>
</html>