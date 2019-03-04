<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include  file="/WEB-INF/jsp/additional/head.html" %>

<link rel="stylesheet" href="/css/personal-area.css" class="css">
</head>

<c:choose>
    <c:when test="${role == 'ADMIN'}">
        <jsp:include page="/WEB-INF/jsp/additional/menu-admin.jsp" />
    </c:when>
    <c:otherwise>
        <jsp:include page="/WEB-INF/jsp/additional/menu-user.jsp" />
    </c:otherwise>
</c:choose>

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
            <a name="tableMarker"></a>

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
                                        <table class="tip-table">
                                            <tr>
                                                <td>Город</td>
                                                <td>Остановка</td>
                                            </tr>
                                            <c:forEach items="${request.trip.route.stationList}" var="station">
                                                <tr>
                                                    <td>${station.name}</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${0 > station.stopDuration}">
                                                            </c:when>
                                                            <c:when test="${station.stopDuration > 0}">
                                                                ${station.stopDuration} мин.
                                                            </c:when>
                                                            <c:otherwise>
                                                                -
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </table>
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
                                    <c:when test="${request.paymentState == 'true'}">
                                        <span class="paid"> Оплачен</span>
                                    </c:when>
                                    <c:when test="${request.paymentState == 'false'}">
                                        <span class="unpaid">Не оплачен</span>
                                    </c:when>
                                    <c:otherwise>
                                        Неизвестно
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:if test="${request.paymentState == 'false'}">
                                    <p><a href="/request/paid?requestId=${request.id}">Оплатить</a></p>
                                </c:if>
                                <p><a href="/request/cancel?requestId=${request.id}">Отменить</a></p>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>

            <a class="btn" href="/personal-area?history=true#tableMarker">История</a>

        </div>
    </div>
</section>

<%@include  file="/WEB-INF/jsp/additional/footer.jsp" %>