<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include  file="/WEB-INF/jsp/additional/head.html" %>

<link rel="stylesheet" href="/css/personal-area.css" class="css">
</head>

<jsp:include page="/WEB-INF/jsp/additional/header${role}.jsp" />

<section id="personal-area">
    <div class="container">
        <div class="form">
            <h2>Личный кабинет</h2>

            <div id="personal-card">
                <img id="avatar" src="/img/avatar.jpg" alt="avatar">
                <div class="info">
                    <h3>ФИО:</h3>
                    <div class="form-block">${user.name} ${user.surname} ${user.patronymic}</div>
                    <h3>Дата рождения: </h3>
                    <div class="form-block"> ${user.birthDate} </div>
                </div>
            </div>
            <h3>Заявки</h3>
            <table>
                <tr>
                    <th>№</th>
                    <th>Рейс</th>
                    <th>Город отправления</th>
                    <th>Дата/время отправления</th>
                    <th>Город назначения</th>
                    <th>Дата/время прибытия</th>
                    <th>Тип вагона</th>
                    <th>Цена</th>
                    <th>Статус</th>
                    <th>Действия</th>
                </tr>
                <tr>
                    
                </tr>
            </table>
            <a class="btn" href="/personal-area?show=history">История</a>

        </div>
    </div>
</section>

<%@include  file="/WEB-INF/jsp/additional/footer.jsp" %>