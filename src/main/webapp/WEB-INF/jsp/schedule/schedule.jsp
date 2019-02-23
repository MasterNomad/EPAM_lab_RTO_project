<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include  file="/WEB-INF/jsp/additional/head.html" %>

<link rel="stylesheet" href="/css/schedule.css" class="css">
</head>

<jsp:include page="/WEB-INF/jsp/additional/header${role}.jsp" />

<section id="schedule" class="content">
    <div class="container">
        <div class="form">
            <h2>Расписание</h2>
            <form action="/admin/schedule" method="POST">
                <div>За период: <input name="firstDate" type="date" value="${firstDate}"> -
                    <input name="secondDate" type="date" value=${secondDate}></div>
                <table>
                    <tr>
                        <th>id рейса</th>
                        <th>Маршрут</th>
                        <th>Дата/время отправления</th>
                        <th>Цена</th>
                    </tr>
                    <c:forEach items="${trips}" var="trip">
                        <tr>
                            <td>${trip.id}</td>
                            <td>${trip.route.title}</td>
                            <td>${trip.departure}</td>
                            <td>${trip.price}</td>
                        </tr>
                    </c:forEach>
                </table>
                <input class="btn" type="submit" value="Обновить">
                <a class="btn" href="/admin/schedule/add">Новое Расписание</a>
            </form>
        </div>
    </div>
    </div>
</section>

<%@include  file="/WEB-INF/jsp/additional/footer.jsp" %>