<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include  file="/WEB-INF/jsp//additional/head.html" %>

<link rel="stylesheet" href="/css/schedule.css" class="css">
</head>

<jsp:include page="/WEB-INF/jsp/additional/menu-admin.jsp" />

<section id="schedule" class="content">
    <div class="container">
        <div class="form">
            <h2>Новое расписание</h2>
            <form action="/admin/schedule/add" method="POST">
                <table id="schedule_table">
                    <tr>
                        <th>Маршрут</th>
                        <th>Дата/время отправления</th>
                        <th>Состав</th>
                        <th>Цена</th>
                        <th>Повтор каждые</th>
                        <th></th>
                    </tr>
                </table>
                <div><a id="add_btn" class="btn">+</a></div>
                <input class="btn" type="submit" value="Утвердить до"><input name="date" type="date" value="${date}"
                    required>
                включительно
            </form>
            <table>
                <tbody id="trip_input">
                    <tr class="input">
                        <td>
                            <select name="route[]" class = "route" required>
                                <c:forEach items="${routes}" var="route">
                                    <option value="${route.title}">${route.title}</option>
                                </c:forEach>
                            </select>
                            <span class="tooltip">?
                                    <span class="tooltiptext">
                                            Маршрут: ${routes[0].title}
                                            <table class="tip-table">
                                                <tr>
                                                    <td>Город</td>
                                                    <td>Остановка</td>
                                                </tr>
                                                <c:forEach items="${routes[0].stationList}" var="station">
                                                    <tr>
                                                        <td>${station.name}</td>
                                                        <td>
                                                            <c:choose>
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
                        <td><input name="departure[]" type="datetime-local" required></td>
                        <td>
                            <table class="carriages">
                                <c:forEach items="${carriages}" var="carriage">
                                    <tr>
                                        <td>
                                            <div class="tooltip">${carriage.name} <span
                                                    class="tooltiptext">${carriage.description}</span>
                                            </div>
                                            -
                                        </td>
                                        <td><input name="carriage[]" class="amount" type="number" value='0' required>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </td>
                        <td nowarp><input class="price" name="price[]" type="number" required> руб.</td>
                        <td><input class="repeat" name="repeat[]" type="number" required> дней</td>
                        <td><span class='a_btn delete_btn'>Удалить</span></td>
                    <tr>
                </tbody>
            </table>
        </div>
    </div>
    </div>
</section>

<script src="/js/schedule.js"></script>

<%@include  file="/WEB-INF/jsp/additional/footer.jsp" %>