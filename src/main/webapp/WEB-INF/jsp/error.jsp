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

<section id="error" class="content">
    <div class="container">
        <div class="form">
            <h2>Что-то пошло не так, обратитесь в службу тех поддержки</h2>
            <a href="/home" class="btn">Продолжить</a>
        </div>
    </div>
</section>

<%@include  file="/WEB-INF/jsp/additional/footer.jsp" %>