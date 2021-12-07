<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<div class="modal fade" id="editProfileModal" tabindex="-1" aria-labelledby="editProfileLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="editProfileLabel">
          <fmt:message key="jsp.userHome.editProfile"/>
        </h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <form action="app" method="post" id="editProfileForm"
              class="d-flex flex-column" onsubmit="return validateEditForm()">
          <input type="hidden" name="command" value="edit-profile">

          <div class="form-group">
            <label for="email-edit">
              <fmt:message key="jsp.userHome.newEmail"/>
            </label>
            <input type="email" class="form-control" name="email-edit"
                   id="email-edit" value="${sessionScope.user.email}" required>
          </div>

          <div class="form-group">
            <label for="name-edit">
              <fmt:message key="jsp.userHome.newName"/>
            </label>
            <input type="text" class="form-control" name="name-edit"
                   id="name-edit" value="${sessionScope.user.name}" required>
          </div>

          <div class="form-group">
            <label for="surname-edit">
              <fmt:message key="jsp.userHome.newSurname"/>
            </label>
            <input type="text" class="form-control" name="surname-edit"
                   id="surname-edit" value="${sessionScope.user.surname}" required>
          </div>

          <div class="form-group">
            <label for="password-edit">
              <fmt:message key="jsp.userHome.newPassword"/>
            </label>
            <input type="password" class="form-control" name="password-edit"
                   id="password-edit" placeholder="Password.." required>
          </div>
        </form>

        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
            <fmt:message key="jsp.userHome.close"/>
          </button>
          <button type="submit" form="editProfileForm" class="btn btn-dark">
            <fmt:message key="jsp.userHome.save"/>
          </button>
        </div>
      </div>
    </div>
  </div>
</div>
