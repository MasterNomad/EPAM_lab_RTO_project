<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<%@include  file="/WEB-INF/jsp/additional/head.html" %>

<link rel="stylesheet" href="/css/schedule.css" class="css">
</head>

<c:choose>
    <c:when test="${role == 'ADMIN'}">
        <jsp:include page="/WEB-INF/jsp/additional/menu-admin.jsp" />
    </c:when>
    <c:otherwise>
        <jsp:include page="/WEB-INF/jsp/additional/menu-user.jsp" />
    </c:otherwise>
</c:choose>

<section id="schedule" class="content">
    <div class="container">
        <div class="form">
            <h2>Расписание</h2>
            <form action="/schedule" method="POST">

                <div>За период: <input name="firstDate" type="date" value="${firstDate}"> -
                    <input name="secondDate" type="date" value=${secondDate}>
                    <input class="btn" type="submit" value="Обновить">
                    <c:if test="${role == 'ADMIN'}">
                        <a class="btn" href="/admin/schedule/add">Новое Расписание</a>
                    </c:if>
                </div>

                <table>
                    <tr>
                        <th>id рейса</th>
                        <th>Маршрут</th>
                        <th>Город отправления</th>
                        <th>Дата/время отправления</th>
                        <th>Город назначения</th>
                        <th>Цена</th>
                    </tr>
                    <c:forEach items="${trips}" var="trip">
                        <tr>
                            <td>${trip.id}</td>
                            <td>
                                <span class="tooltip">
                                    ${trip.route.title}
                                    <span class="tooltiptext">
                                        Маршрут: ${trip.route.title}
                                        <table class="tip-table">
                                            <tr>
                                                <td>Город</td>
                                                <td>Остановка</td>
                                            </tr>
                                            <c:forEach items="${trip.route.stationList}" var="station">
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
                            <td>${trip.route.stationList[0].name}</td>
                            <td>${trip.departure}</td>
                            <td>${trip.route.stationList[fn:length(trip.route.stationList)-1].name}</td>
                            <td>${trip.price}</td>
                        </tr>
                    </c:forEach>
                </table>
            </form>
        </div>
    </div>
    </div>
</section>

<%@include  file="/WEB-INF/jsp/additional/footer.jsp" %>