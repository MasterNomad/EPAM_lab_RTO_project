<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include  file="/WEB-INF/jsp/additional/head.html" %>

<link rel="stylesheet" href="/css/schedule.css" class="css">
<link rel="stylesheet" href="/css/login.css" class="css">
<link rel="stylesheet" href="/css/find-train.css" class="css">
<link rel="stylesheet" href="/css/personal-area.css" class="css">
<link rel="stylesheet" href="/css/requests.css" class="css">
</head>

<jsp:include page="/WEB-INF/jsp/additional/header${role}.jsp" />

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