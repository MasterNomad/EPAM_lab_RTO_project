<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include  file="/WEB-INF/jsp/additional/head.html" %>
</head>

<jsp:include page="/WEB-INF/jsp/additional/header-admin.jsp" />

<section id="requests">
    <div class="container">
        <div class="form">
            <h3>История заявок</h3>
            <form action="/admin/requests/history#tableMarker" method="POST">
                <div>
                    За период: <input name="firstDate" type="date" value="${firstDate}"> -
                    <input name="secondDate" type="date" value=${secondDate}>
                    <input class="btn" type="submit" value="Обновить">
                    <a class="btn" href="/admin/requests#tableMarker">Активные заявки</a>
                </div>
            </form>
            <a name="tableMarker"></a>

            <c:if test="${empty requests}">
                <p>В указанный период небыло заявок</p>
            </c:if>

            <c:if test="${not empty requests}">
                <table>
                    <tr>
                        <th>Код заявки</th>
                        <th>Рейс</th>
                        <th>Пользователь</th>
                        <th>Город отправления</th>
                        <th>Дата/время отправления</th>
                        <th>Город назначения</th>
                        <th>Дата/время прибытия</th>
                        <th>Тип вагона</th>
                        <th>Цена</th>
                        <th>Оплата</th>
                        <th>Статус</th>
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
                            <td>
                                <span class="tooltip">
                                    ${request.user.id}
                                    <span class="tooltiptext">
                                        ФИО: <p>${request.user.surname} ${request.user.name} ${request.user.patronymic}
                                        </p>
                                        Дата рождения:
                                        <p>${request.user.birthDate}</p>
                                        Пол: <c:choose>
                                            <c:when test="${request.user.sex == 'true'}">
                                                <span class="paid">муж.</span>
                                            </c:when>
                                            <c:when test="${request.user.sex == 'false'}">
                                                <span class="unpaid">жен.</span>
                                            </c:when>
                                            <c:otherwise>
                                                Неизвестно
                                            </c:otherwise>
                                        </c:choose>
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
                                <c:choose>
                                    <c:when test="${request.requestStatus == 'ACTIVE'}">
                                        <c:choose>
                                            <c:when test="${request.paymentState == 'true'}">
                                                <spa>Выполнен</span>
                                            </c:when>
                                            <c:when test="${request.paymentState == 'false'}">
                                                <span>Не оплачен</span>
                                            </c:when>
                                        </c:choose>
                                    </c:when>
                                    <c:when test="${request.requestStatus == 'REJECTED'}">
                                        Отклонён
                                    </c:when>
                                    <c:when test="${request.requestStatus == 'CANCELED'}">
                                        Отменён
                                    </c:when>
                                    <c:otherwise>
                                        Неизвестно
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>

        </div>
    </div>
</section>

<%@include  file="/WEB-INF/jsp/additional/footer.jsp" %>