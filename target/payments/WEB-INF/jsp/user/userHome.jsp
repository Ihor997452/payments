<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="m" uri="/WEB-INF/tld/BigDecimalTag.tld" %>
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
        <fmt:message key="jsp.userHome.home"/>
    </title>

    <jsp:include page="../includes.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/accountCard.css">

    <script src="${pageContext.request.contextPath}/script/userPageScript.js"></script>

</head>
<body>

    <jsp:include page="../header.jsp"/>

    <div class="container d-flex flex-column justify-content-around align-items-center">

        <section id="accounts-section" class="accounts" style="overflow: hidden; width: 100%">
            <div class="d-flex justify-content-around align-items-baseline"
                 style="margin-top: 5%;">
                <p style="font-weight: bold; font-size: 32px">
                    <fmt:message key="jsp.userHome.accounts"/>
                </p>

                <div class="d-flex justify-content-end" style="width: 430px">
                    <button type="button" class="btn btn-dark"
                            data-bs-toggle="modal" data-bs-target="#allAccountsModal">
                        <fmt:message key="jsp.userHome.view"/>
                    </button>
               </div>
            </div>
            <c:if test='${empty sessionScope.accounts}'>
                <p style="font-size: 22px">
                    <fmt:message key="jsp.userHome.noAccountsMessage"/>
                </p>
                <button type="button" class="btn btn-dark" data-bs-toggle="modal" data-bs-target="#newAccountModal">
                    <fmt:message key="jsp.userHome.newAccount"/>
                </button>
            </c:if>

            <div id="accounts" class="carousel slide"
                 data-interval="false">
                <div class="carousel-inner">
                    <c:forEach items="${sessionScope.accounts}" var="account">
                        <div class="carousel-item">
                            <div class="card myCard">
                                <div class="card-body">
                                    <p style="font-size: 26px; font-weight: bold">
                                        <fmt:message key="jsp.account.accountName"/> ${account.name}
                                    </p>
                                    <p>
                                        <fmt:message key="jsp.account.accountNumber"/> ${account.id}
                                    </p>
                                    <p>
                                        <fmt:message key="jsp.account.accountBalance"/>
                                        <m:BigDecimal amount="${account.balance}"/> ${account.currency}
                                    </p>
                                    <p>
                                        <fmt:message key="jsp.account.accountExpirationDate"/> Expiration Date: ${account.expirationDate}
                                    </p>
                                    <p>
                                        <fmt:message key="jsp.account.accountStatus"/> ${account.status}
                                    </p>

                                    <div class="d-flex flex-row align-items-center justify-content-start">
                                        <a href="?command=user-account&account=${account.id}">
                                            <fmt:message key="jsp.account.accountDetails"/>
                                        </a>

                                        <button type="button" value="${account.id}" name="topUpButton"
                                                class="btn btn-link link-dark" data-bs-toggle="modal" data-bs-target="#topUpModal">
                                            <fmt:message key="jsp.account.accountTopUp"/>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    <script>document.getElementsByClassName("carousel-item")[0].classList.add("active");</script>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#accounts"
                        data-bs-slide="prev" style="filter: invert(100%)">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#accounts"
                        data-bs-slide="next" style="filter: invert(100%)">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
        </section>

        <c:if test="${not empty sessionScope.accounts}">
            <section id="transaction-section" class="transactions" style="width: 100%;">
                <p style="font-weight: bold; font-size: 32px">
                    <fmt:message key="jsp.userHome.transactions"/>
                </p>

                <div class="d-flex flex-column justify-content-center">
                    <div class="new-transaction-form">
                        <form action="app" method="post"
                              class="d-flex flex-column" id="transaction-form" onsubmit="return validateTransactionForm()">
                            <input type="hidden" name="command" value="new-transaction">

                            <div class="form-group">
                                <label for="account-sender">
                                    <fmt:message key="jsp.userHome.sender"/>
                                </label>
                                <select name="account-sender" id="account-sender" class="form-select" required>
                                    <option value="0" selected disabled>
                                        <fmt:message key="jsp.userHome.chooseAccount"/>
                                    </option>
                                    <c:forEach items="${sessionScope.accounts}" var="account">
                                        <option value="${account.id};${account.balance}">
                                            <fmt:message key="jsp.account.accountName"/> ${account.name},
                                            <fmt:message key="jsp.account.accountNumber"/> ${account.id},
                                            <fmt:message key="jsp.account.accountBalance"/> <m:BigDecimal amount="${account.balance}"/>  ${account.currency}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="amount">
                                    <fmt:message key="jsp.userHome.amount"/>
                                </label>
                                <input type="number" step="0.01" class="form-control"  name="amount"
                                       id="amount" placeholder="123.." required>
                            </div>

                            <div class="form-group">
                                <label for="recipient">
                                    <fmt:message key="jsp.userHome.recipient"/>
                                </label>
                                <input type="number" class="form-control"  name="recipient"
                                       id="recipient" placeholder="123.." required>
                            </div>

                            <div class="form-group">
                                <label for="message">
                                    <fmt:message key="jsp.userHome.message"/>
                                </label>
                                <input type="text" class="form-control" name="message"
                                       id="message" placeholder="Hi, ..">
                            </div>

                            <button type="submit" class="btn btn-dark">
                                <fmt:message key="jsp.userHome.send"/>
                            </button>
                        </form>

                        <a href="?command=user-transactions">
                            <fmt:message key="jsp.userHome.viewTransactions"/>
                        </a>
                    </div>
                </div>

            </section>
        </c:if>
   </div>

    <jsp:include page="editProfile.jsp"/>
    <jsp:include page="newAccount.jsp"/>
    <jsp:include page="topUp.jsp"/>
    <jsp:include page="userAccounts.jsp"/>
    <jsp:include page="../messages.jsp"/>
</body>
</html>
