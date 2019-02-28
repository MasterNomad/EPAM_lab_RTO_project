<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<%@include  file="/WEB-INF/jsp/additional/head.html" %>

<link rel="stylesheet" href="/css/route.css" class="css">
</head>

<jsp:include page="/WEB-INF/jsp/additional/header${role}.jsp" />

<section id="route" class="content">
	<div class="container">
		<div class="form">
			<h2>Маршруты</h2>
			<table>
				<tr>
					<th>
						<label for="title">Идентификатор маршрута</label>
					</th>
					<th>
						<label for="departureStation"> Станция отправления</label>
					</th>
					<th>
						<label for="arrivalStation">Конечная станция</label>
					</th>
					<th>Локомотив</th>
					<th>Время в пути</th>
					<th></th>
				</tr>
				<tr id="search">
					<td><input id="title" name="title" type="text"></td>
					<td><input id="departureStation" name="departureStation" type="text"></td>
					<td><input id="arrivalStation" name="arrivalStation" type="text"></td>
					<td></td>
					<td></td>
					<td>
						<a href="#">Поиск</a>
					</td>
				</tr>
					<c:forEach items="${routes}" var="route">
				<tr>
					<td>${route.title}</td>
					<td>${route.stationList[0].name}</td>
					<td>${route.stationList[fn:length(route.stationList)-1].name}</td>
					<td>${route.locomotive.name}</td>
					<td>
						<fmt:formatNumber var="expiry" value="${(expire.time - now.time) / (60 * 1000)}"
							maxFractionDigits="0" />
						<c:set var="time" scope="session"
							value="${route.stationList[fn:length(route.stationList)-1].travelTime}" />
						${fn:substringBefore(time/1440, '.')}д.
						${fn:substringBefore(time%1440/60, '.')}ч.
						${time%60}м.
					</td>
					<td>
						<a href='/route/edit?title=${route.title}'>Редактировать</a>
						<br>
						<a href='/route/delete?title=${route.title}'>Удалить</a>
					</td>
				</tr>
				</c:forEach>
			</table>
			<a href="/route/create" class="btn">Создать маршрут</a>
		</div>
	</div>
	</div>
</section>

<script src="/js/route.js"></script>

<%@include  file="/WEB-INF/jsp/additional/footer.jsp" %>