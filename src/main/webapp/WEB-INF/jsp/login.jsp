<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include  file="head.html" %>

    <link rel="stylesheet" href="/css/login.css" class="css">
</head>

<body>
    <div id="header"></div>
    <div class="separator"></div>

    <section id="login" class = "content">
        <div class="container">
            <div class="form">
                <h2>Войти</h2>
                <form id="login_form">
                <div>
                    <input id="user" type="text" name="login" placeholder="Введите логин (e-mail)" required>
                </div>
                <div>
                    <input id="password" type="password" name="password" placeholder="Введите пароль" required>
                </div>
                <div>
                    <input class="btn" type="submit" value="Войти">
                    <a class="btn" href="/html/registration.html">Регистрация</a>
                </div>
            </form>
                <a href="#">Восстановить пароль</a>
            </div>
        </div>
    </section>
    <div id="footer"></div>
</body>

<script src="/js/jquery-3.3.1.min.js"></script>
<script src="/js/script.js"></script>
<script src="/js/login.js"></script>

</html>