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
    <title>
        <fmt:message key="jsp.account.title"/>
    </title>
    <jsp:include page="../includes.jsp"/>

    <script src="${pageContext.request.contextPath}/script/accountPageScript.js"></script>
</head>
<body   >

    <jsp:include page="../header.jsp"/>

    <div class="container d-flex flex-column justify-content-start align-items-center">

        <p style="font-weight: bold; font-size: 32px">
            <fmt:message key="jsp.account.accountNumber"/> ${requestScope.account.id}
        </p>
        <p style="font-size: 22px">
            <fmt:message key="jsp.account.accountName"/> ${requestScope.account.name}
        </p>
        <p style="font-size: 22px">
            <fmt:message key="jsp.account.accountBalance"/>
            <m:BigDecimal amount="${requestScope.account.balance}"/>
        </p>
        <p style="font-size: 22px">
            <fmt:message key="jsp.account.accountCurrency"/>: ${requestScope.account.currency}
        </p>
        <p style="font-size: 22px">
            cvv: ${requestScope.account.cvv}
        </p>
        <div style="font-size: 22px;" id="pin-wrapper">
            <label for="view-pin">Pin: </label>
            <input style="width: 70px; border: none; text-align: center; outline: none" id="view-pin"
                   value="${requestScope.account.pinCode}" disabled
                   type="password">
        </div>
        <p style="font-size: 22px">
            <fmt:message key="jsp.account.accountExpirationDate"/> ${requestScope.account.expirationDate}
        </p>
        <p style="font-size: 22px">
            <fmt:message key="jsp.account.accountStatus"/> ${requestScope.account.status}
        </p>

        <script>
            let pin = document.getElementById("view-pin");
            document.getElementById("pin-wrapper").onclick = function () {
                pin.setAttribute('type', (pin.getAttribute('type') === 'password' ? 'text' : 'password'));
            };
        </script>

        <button type="button" class="btn btn-dark"
                data-bs-toggle="modal" data-bs-target="#editAccountModal">
            <fmt:message key="jsp.account.editAccount"/>
        </button>

        <c:if test="${requestScope.account.status.value == 2}">
            <form action="app" method="post">
                <input type="hidden" name="command" value="send-request">
                <input type="hidden" name="request-type" value="2">
                <input type="hidden" name="account" value="${requestScope.account.id}">
                <button type="submit" class="btn btn-dark">
                    <fmt:message key="jsp.blocked.requestUnblock"/>
                </button>
            </form>
        </c:if>

        <a href="?command=user-transactions&account=${requestScope.account.id}">
            <fmt:message key="jsp.userHome.viewTransactions"/>
        </a>
    </div>

    <div class="modal fade" id="editAccountModal" tabindex="-1" aria-labelledby="editAccountLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editAccountLabel">
                        <fmt:message key="jsp.account.editAccount"/>
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>

                <div class="modal-body">
                    <form action="app" method="post" id="editAccountForm"
                          class="d-flex flex-column" onsubmit="return validateEditAccountForm()">
                        <input type="hidden" name="id" value="${requestScope.account.id}">
                        <input type="hidden" name="command" value="edit-account">

                        <div class="form-group">
                            <label for="new-name">
                                <fmt:message key="jsp.account.accountName"/>
                            </label>
                            <input type="text" id="new-name" name="new-name" class="form-control"
                                   value="${requestScope.account.name}" required>
                        </div>


                        <div class="form-group">
                            <label for="new-currency">
                                <fmt:message key="jsp.account.accountCurrency"/>
                            </label>
                            <select class="form-select" name="new-currency" id="new-currency">
                                <option value="0" disabled>
                                    <fmt:message key="jsp.userHome.chooseCurrency"/>
                                </option>

                                <c:forEach items="${applicationScope.supportedCurrencies}" var="currency">
                                    <option value="${currency.value}"
                                            <c:if test="${requestScope.account.currency == currency}">
                                                selected
                                            </c:if>
                                    >${currency}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="pin-code">
                                <fmt:message key="jsp.account.pinCode"/>
                            </label>
                            <input type="password" id="pin-code" name="pin-code" class="form-control"
                                   required>
                        </div>

                    </form>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                            <fmt:message key="jsp.userHome.close"/>
                        </button>
                        <button type="submit" form="editAccountForm" class="btn btn-dark">
                            <fmt:message key="jsp.userHome.save"/>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="userAccounts.jsp"/>
    <jsp:include page="../messages.jsp"/>
</body>
</html>
