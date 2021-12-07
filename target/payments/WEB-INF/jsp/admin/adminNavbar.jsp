<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<c:if test="${sessionScope.user.roleId == 2}">
    <div class="d-flex flex-column">
        <div class="d-flex flex-row justify-content-end align-items-baseline">
            <p style="font-size: 20px">
                    <fmt:message key="jsp.header.hi"/> ${sessionScope.user.name} ${sessionScope.user.surname}
            </p>
            <i class="fas fa-user" style="font-size: 32px; margin-left: 15px"></i>
        </div>

        <c:if test="${sessionScope.user.statusId == 1}">
            <div class="d-flex flex-column user-menu align-self-end">
                <button type="button" class="btn btn-dark"
                        data-bs-toggle="modal" data-bs-target="#allAccountsModal">
                    <fmt:message key="jsp.header.accounts"/>
                </button>

                <a href="?command=user-transactions" type="button" class="btn btn-dark">
                    <fmt:message key="jsp.header.transactions"/>
                </a>

                <button type="button" class="btn btn-dark" data-bs-toggle="modal" data-bs-target="#editProfileModal">
                    <fmt:message key="jsp.header.editProfile"/>
                </button>
                <button type="button" class="btn btn-dark" data-bs-toggle="modal" data-bs-target="#newAccountModal">
                    <fmt:message key="jsp.header.newAccount"/>
                </button>

                <a href="?command=admin-users" type="button" class="btn btn-dark">
                    <fmt:message key="jsp.header.adminUsers"/>
                </a>
                <a href="?command=admin-requests" type="button" class="btn btn-dark">
                    <fmt:message key="jsp.header.adminRequests"/>
                </a>
                <a href="?command=admin-accounts" type="button" class="btn btn-dark">
                    <fmt:message key="jsp.header.adminAccounts"/>
                </a>
                <a href="?command=admin-transactions" type="button" class="btn btn-dark">
                    <fmt:message key="jsp.header.adminTransactions"/>
                </a>

                <a href="?command=logout" type="button" class="btn btn-dark">
                    <fmt:message key="jsp.header.logOut"/>
                </a>
            </div>
        </c:if>


        <c:if test="${sessionScope.user.statusId == 2}">
            <div class="d-flex flex-column user-menu align-self-end">
                <form action="app" method="post" class="align-self-end">
                    <input type="hidden" name="command" value="send-request">
                    <input type="hidden" name="request-type" value="1">
                    <button type="submit" class="btn btn-dark">
                        <fmt:message key="jsp.header.requestUnblock"/>
                    </button>
                </form>

                <a href="?command=logout" type="button" class="btn btn-dark">
                    <fmt:message key="jsp.header.logOut"/>
                </a>
            </div>
        </c:if>
    </div>
</c:if>

