<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include  file="../meta/head.html" %>

<link rel="stylesheet" href="../css/route.css" class="css">
</head>

<%@include  file="../meta/header.jsp" %>

<section id="route" class="content">
    <div class="container">
        <div class="form">
            <h2>Редактировать маршрут: <br>
                ${route.title}
            </h2>
            <form action="/route/refresh"  method="POST">
                <div class="form-block">
                    День отправления:
                    <input type="text" list="days" name="departureDay" value="${route.departureDay}">
                    <datalist id="days">
                        <c:forEach items="${days}" var="day">
                            <option value="${day}">${day}</option>
                        </c:forEach>
                    </datalist>
                </div>
                <div class="form-block">
                    Время отправления:
                    <input type="time" name="departureTime" value="${route.departureTime}">
                </div>
                <br>
                <div class="form-block">
                    Средняя скорость:
                    <input type="number" name="averageSpeed" value="${route.averageSpeed}">
                </div>
                <table>
                    <tr>
                        <th> № </th>
                        <th> Станция </th>
                        <th> Время в пути </th>
                        <th> Время остановки (мин.) </th>
                    </tr>
                    <c:set var="id" scope="page" value="${1}" />
                    <c:forEach items="${stations}" var="station">
                        <tr>
                            <td>${id}</td>
                            <td>${station.stationName}</td>
                            <td>${station.travelTime}</td>
                            <td class="input_td"><input name="time[]" type="text" value="${station.stopDuration}"></td>
                        <tr>
                            <c:set var="id" value="${id + 1}" scope="page" />
                    </c:forEach>
                </table>
                <input type="submit" class="btn" value="Обновить">
            </form>
        </div>
    </div>
    </div>
</section>

<%@include  file="../meta/footer.jsp" %>