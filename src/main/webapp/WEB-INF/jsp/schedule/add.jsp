<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include  file="/WEB-INF/jsp//additional/head.html" %>

<link rel="stylesheet" href="/css/schedule.css" class="css">
<link rel="stylesheet" href="/css/schedule-create.css" class="css">
</head>

<jsp:include page="/WEB-INF/jsp/additional/header${role}.jsp" />

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
                <input class="btn" type="submit" value="Утвердить до"><input name = "date" type="date" value="${date}" required>
                включительно
            </form>
            <table>
                <tbody id="trip_input">
                    <tr class="input">
                        <td><input name="route[]" list="routes" type="text" required>
                            <datalist id="routes">
                                <c:forEach items="${routes}" var="route">
                                    <option value="${route.title}">${route.title}</option>
                                </c:forEach>
                            </datalist>
                        </td>
                        <td><input name="departure[]" type="datetime-local" value="${currentDate}" required></td>
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