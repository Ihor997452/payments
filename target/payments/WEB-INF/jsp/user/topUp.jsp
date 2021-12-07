<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<div class="modal fade" id="topUpModal" tabindex="-1" aria-labelledby="topUpLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="topUpLabel">
          <fmt:message key="jsp.account.accountTopUp"/>
        </h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <form action="app" method="post" id="topUpForm"
              class="d-flex flex-column" onsubmit="return validateTopUpForm()">
          <input type="hidden" value="top-up" name="command">

          <div class="form-group">
            <label for="top-up-amount">
              <fmt:message key="jsp.userHome.amount"/>
            </label>
            <input type="number" step="0.01" class="form-control"
                   name="topUp-amount" id="top-up-amount">
          </div>

          <div class="form-group">
            <label for="top-up-currency">
              <fmt:message key="jsp.account.accountCurrency"/>
            </label>
            <select class="form-select" name="topUp-currency" id="top-up-currency">
              <option value="0" selected disabled>
                <fmt:message key="jsp.userHome.chooseCurrency"/>
              </option>

              <c:forEach items="${applicationScope.supportedCurrencies}" var="currency">
                <option value="${currency.value}">${currency}</option>
              </c:forEach>
            </select>
          </div>

          <input type="hidden" id="topUp-id" name="topUp-id">

        </form>

        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
            <fmt:message key="jsp.userHome.close"/>
          </button>
          <button type="submit" form="topUpForm" class="btn btn-dark">
            <fmt:message key="jsp.account.accountTopUp"/>
          </button>
        </div>
      </div>
    </div>
  </div>
</div>

<script>
  document.getElementsByName("topUpButton").forEach(element => element.onclick = function () {
    document.getElementById("topUp-id").value = element.value;
  });
</script>
