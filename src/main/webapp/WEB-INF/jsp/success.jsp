<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include  file="/WEB-INF/jsp/additional/head.html" %>
</head>

<c:choose>
    <c:when test="${role == 'ADMIN'}">
        <jsp:include page="/WEB-INF/jsp/additional/menu-admin.jsp" />
    </c:when>
    <c:otherwise>
        <jsp:include page="/WEB-INF/jsp/additional/menu-user.jsp" />
    </c:otherwise>
</c:choose>

<section id="${page}" class="content">
    <div class="container">
        <div class="form">
            <h2>${msg}</h2>
            <p>${additional}</p>
            <a href="${link}" class="btn">Продолжить</a>
        </div>
    </div>
</section>

<%@include  file="/WEB-INF/jsp/additional/footer.jsp" %>