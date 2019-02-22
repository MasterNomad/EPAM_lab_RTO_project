<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include  file="/WEB-INF/jsp/additional/head.html" %>

<link rel="stylesheet" href="/css/login.css" class="css">
</head>

<jsp:include page="/WEB-INF/jsp/additional/header${role}.jsp" />

<section id="login" class="content">
    <div class="container">
        <div class="form">
            <h2>Войти</h2>
            <form action="/login" method="POST" id="login_form">
                <div>
                    <input type="email" name="email" placeholder="Введите логин (e-mail)" value="${email}" required>
                </div>
                <div>
                    <input type="password" name="password" placeholder="Введите пароль" required>
                </div>
                <div class="answer">${answer}</div>
                <div>
                    <input class="btn" type="submit" value="Войти">
                    <a class="btn" href="login/registration">Регистрация</a>
                </div>
            </form>
        </div>
    </div>
</section>

<%@include  file="/WEB-INF/jsp/additional/footer.jsp" %>