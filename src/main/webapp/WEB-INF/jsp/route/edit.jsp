<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include  file="../additional/head.html" %>

<link rel="stylesheet" href="../css/route.css" class="css">
</head>

<%@include  file="../additional/header.jsp" %>

<section id="route" class="content">
    <div class="container">
        <div class="form">
            <h2>Mаршрут: ${route.title} </h2>
            <form action="/route/update" method="POST">
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
                            <td class="input_td"><input name="time[]" type="number" value="${station.stopDuration}"></td>
                        <tr>
                            <c:set var="id" value="${id + 1}" scope="page" />
                    </c:forEach>
                </table>
                <div>${answer}</div>
                <button type="submit" name="action" value="update" class="btn">Расчитать</button>
                <button type="submit" name="action" value="save" class="btn">Сохранить</button>
            </form>
        </div>
    </div>
    </div>
</section>

<script src="../js/route.js"></script>

<%@include  file="../additional/footer.jsp" %>