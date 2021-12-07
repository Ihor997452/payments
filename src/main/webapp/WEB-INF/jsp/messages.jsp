<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test='${not empty sessionScope.error}'>
    <script>
        insertErrorMessage(document.body,
            "error",
            '${sessionScope.error}');
    </script>
    ${sessionScope.remove("error")}
</c:if>

<c:if test='${not empty sessionScope.info}'>
    <script>
        insertInfoMessage(document.body,
            "info",
            '${sessionScope.info}');
    </script>
    ${sessionScope.remove("info")}
</c:if>

<c:if test='${not empty sessionScope.success}'>

    <script>
        insertSuccessMessage(document.body,
            "success",
            '${sessionScope.success}');
    </script>
    ${sessionScope.remove("success")}
</c:if>