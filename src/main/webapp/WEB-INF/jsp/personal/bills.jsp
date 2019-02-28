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
                    <div class="form-block">${user.surname} ${user.name} ${user.patronymic}</div>
                    <h3>Дата рождения: </h3>
                    <div class="form-block"> ${user.birthDate} </div>
                </div>
            </div>

            <h3>Билеты</h3>

            <c:if test="${empty requests}">
                <p>Нет активных билетов.</p>
            </c:if>

            <c:if test="${not empty requests}">
                <table>
                    <tr>
                        <th>Код билета</th>
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
                    <c:forEach items="${requests}" var="request">
                        <tr>
                            <td>${request.id}</td>
                            <td>
                                <span class="tooltip">
                                    ${request.trip.id}
                                    <span class="tooltiptext">
                                        Маршрут: ${request.trip.route.title}
                                        <c:forEach items="${request.trip.route.stationList}" var="station">
                                            <p>${station.name}</p>
                                        </c:forEach>
                                    </span>
                                </span>
                            </td>
                            <td>${request.departureCity}</td>
                            <td>${request.departureDateTime}</td>
                            <td>${request.destinationCity}</td>
                            <td>${request.arrivalDateTime}</td>
                            <td>
                                <span class="tooltip">
                                    ${request.carriage.name}
                                    <span class="tooltiptext">
                                        ${request.carriage.description}
                                    </span>
                                </span>
                            </td>
                            <td>${request.price}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${request.requestStatus == 'PAID'}">
                                        <span class="paid"> Оплачено</span>
                                    </c:when>
                                    <c:when test="${request.requestStatus == 'UNPAID'}">
                                        <span class="unpaid"> Не оплачено</span>
                                    </c:when>
                                    <c:otherwise>
                                        Неизвестно
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>

            <a class="btn" href="/personal-area?history=true">История</a>

        </div>
    </div>
</section>

<%@include  file="/WEB-INF/jsp/additional/footer.jsp" %>