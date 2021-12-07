<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="m" uri="/WEB-INF/tld/BigDecimal.tld" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<div class="modal fade" id="allAccountsModal" tabindex="-1" aria-labelledby="allAccountsLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="allAccountsLabel">
                    <fmt:message key="jsp.userHome.accounts"/>
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <div class="modal-body">

                <div class="d-flex justify-content-around align-items-center">
                    <a onclick="addUrlParameter('account-order', 'id')" type="button" class="btn btn-dark">
                        <fmt:message key="jsp.account.sortId"/>
                    </a>
                    <a onclick="addUrlParameter('account-order', 'name')" type="button" class="btn btn-dark">
                        <fmt:message key="jsp.account.sortName"/>
                    </a>
                    <a onclick="addUrlParameter('account-order', 'balance')" type="button" class="btn btn-dark">
                        <fmt:message key="jsp.account.sortBalance"/>
                    </a>
                </div>

                <c:forEach items="${sessionScope.accounts}" var="account">
                    <div class="card myCard">
                        <div class="card-body">
                            <p style="font-size: 26px; font-weight: bold">
                                <fmt:message key="jsp.account.accountName"/> ${account.name}
                            </p>
                            <p>
                                <fmt:message key="jsp.account.accountNumber"/> ${account.id}
                            </p>
                            <p>
                                <fmt:message key="jsp.account.accountBalance"/> <m:BigDecimal amount="${account.balance}"/> ${account.currency}
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
                </c:forEach>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                    <fmt:message key="jsp.userHome.close"/>
                </button>
            </div>
        </div>
    </div>
</div>
