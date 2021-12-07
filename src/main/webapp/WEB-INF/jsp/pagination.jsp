<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav style="margin-top: 20px">
  <ul class="pagination">
    <c:set var="page" value="${requestScope.page}"/>
    <c:if test="${page != 0}">
      <li style="cursor: pointer;" class="page-item">
        <a class="page-link"
           onclick="addUrlParameter('page', '${page - 1}')"
           aria-label="Previous">
          <span aria-hidden="true">&laquo;</span>
        </a>
      </li>
    </c:if>

    <c:set var="begin" value="${page - 4}"/>
    <c:if test="${begin < 0}">
      <c:set var="begin" value="0"/>
    </c:if>

    <c:set var="end" value="${begin + 9}"/>
    <c:if test="${end > requestScope.size}">
      <c:set var="end" value="${requestScope.size}"/>
    </c:if>

    <c:forEach var="i" begin="${begin}" end="${end}">
      <c:choose>
        <c:when test="${page == i}">
          <li style="cursor: pointer;" class="page-item active">
            <a class="page-link" onclick="addUrlParameter('page', '${i}')">${i + 1}<span class="sr-only">(current)</span></a>
          </li>
        </c:when>

        <c:otherwise>
          <li style="cursor: pointer;" class="page-item">
            <a class="page-link" onclick="addUrlParameter('page', '${i}')">${i + 1}</a>
          </li>
        </c:otherwise>
      </c:choose>
    </c:forEach>

    <c:if test="${page != requestScope.size}">
      <li style="cursor: pointer;" class="page-item">
        <a class="page-link"
           onclick="addUrlParameter('page', '${page + 1}')"
           aria-label="Next">
          <span aria-hidden="true">&raquo;</span>
        </a>
      </li>
    </c:if>
  </ul>
</nav>

