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
            <form action="">
                <div>За период: <input type="date"> - <input type="date"></div>
                <table>
                    <tr>
                        <th>id рейса</th>
                        <th>Маршрут</th>
                        <th>Дата/время отправления</th>
                        <th>Цена</th>
                    </tr>
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </table>
                <input class="btn" type="submit" value="Обновить">
                <a class="btn" href="/admin/schedule/add">Новое Расписание</a>
            </form>
        </div>
    </div>
    </div>
</section>

<%@include  file="/WEB-INF/jsp/additional/footer.jsp" %>