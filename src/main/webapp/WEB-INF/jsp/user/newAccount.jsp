<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<div class="modal fade" id="newAccountModal" tabindex="-1" aria-labelledby="newAccountLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="newAccountLabel">
                    <fmt:message key="jsp.userHome.newAccount"/>
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="app" method="post" id="newAccountForm"
                      class="d-flex flex-column" onsubmit="return validateNewAccountForm()">
                    <input type="hidden" name="command" value="new-account">

                    <div class="form-group">
                        <label for="account-name">
                            <fmt:message key="jsp.account.accountName"/>
                        </label>
                        <input type="text" class="form-control"
                               name="new-account-name" id="account-name">
                    </div>

                    <div class="form-group">
                        <label for="account-currency">
                            <fmt:message key="jsp.userHome.accountCurrency"/>
                        </label>
                        <select class="form-select" name="new-account-currency" id="account-currency">
                            <option value="0" selected disabled>
                                <fmt:message key="jsp.userHome.chooseCurrency"/>
                            </option>

                            <c:forEach items="${applicationScope.supportedCurrencies}" var="currency">
                                <option value="${currency.value}">${currency}</option>
                            </c:forEach>
                        </select>
                    </div>

                </form>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        <fmt:message key="jsp.userHome.close"/>
                    </button>
                    <button type="submit" form="newAccountForm" class="btn btn-dark">
                        <fmt:message key="jsp.userHome.newAccount"/>
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
