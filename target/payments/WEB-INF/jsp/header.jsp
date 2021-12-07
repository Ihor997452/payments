<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<nav class="navbar sticky-top" style="padding: .5rem 20px">
    <button class="btn btn-dark" type="button" data-bs-toggle="offcanvas"
            data-bs-target="#menu" aria-controls="menu">
        <i class="fas fa-bars" style="font-size: 30px"></i>
    </button>
</nav>

<div class="offcanvas offcanvas-end"
     tabindex="-1" id="menu" aria-labelledby="close-menu">

    <div class="offcanvas-header">
        <h5 class="offcanvas-title" id="close-menu">
            <fmt:message key="jsp.header.menu"/>
        </h5>
        <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
    </div>

    <div class="offcanvas-body">
        <div class="d-flex flex-column justify-content-between h-100">
            <a href="?command=home" type="button" class="btn btn-dark">
                <fmt:message key="jsp.header.home"/>
            </a>

            <jsp:include page="user/userNav.jsp"/>
            <jsp:include page="admin/adminNavbar.jsp"/>

            <div class="dropdown">
                <button class="btn btn-dark dropdown-toggle" type="button"
                        id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                    <fmt:message key="jsp.header.lang"/>
                </button>
                <ul class="dropdown-menu dropdown-menu-start" aria-labelledby="dropdownMenuButton1">
                    <li class="d-flex" style="padding-right: 5px;">
                        <a class="dropdown-item" href="?lang=RU">
                            RUS
                        </a>
                        <img src="img/flag-ru.png" width="25" height="25" alt="">
                    </li>
                    <li><hr class="dropdown-divider"></li>
                    <li class="d-flex" style="padding-right: 5px;">
                        <a class="dropdown-item" href="?lang=en">
                            ENG
                        </a>
                        <img src="img/flag-en.png" width="25" height="25" alt="">
                    </li>
                    <li><hr class="dropdown-divider"></li>
                    <li class="d-flex" style="padding-right: 5px;">
                        <a class="dropdown-item" href="?lang=uk">
                            UKR
                        </a>
                        <img src="img/flag-ua.png" width="25" height="25" alt="">
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>