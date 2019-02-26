<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include  file="/WEB-INF/jsp/additional/head.html" %>

<link rel="stylesheet" href="/css/find-train.css" class="css">
</head>

<jsp:include page="/WEB-INF/jsp/additional/header${role}.jsp" />

<section id="find-train" class="content">
	<div class="container">
		<div class="form">
			<h2>Заказать билет</h2>
			<form action="/find-train" method="POST">
				<div class="form-block">
					<label for="departureCity">Город отправления:</label>
					<br>
					<input type="text" list="cities" name="departureCity" value="${departureCity}" required>
					<datalist id="cities">
						<c:forEach items="${stations}" var="station">
							<option value="${station}">${station}</option>
						</c:forEach>
					</datalist>
				</div>
				<div class="form-block">
					<label for="departure">Дата отправления:</label>
					<br>
					<input name="departure" id = "departure" type="datetime-local" value="${departure}" required>
				</div>
				<div class="form-block">
					<label for="destinationCity">Город назначения: </label>
					<br>
					<input type="text" list="cities" name="destinationCity" id="destinationCity"
						value="${destinationCity}" required>
				</div>
				<input type="submit" class="btn" value="Поиск">
			</form>
			<c:forEach items="${answer}" var="request">
				<div class="request">
					<form action="">
						<div class="form-block">Рейс №${request.trip.id}</div>
						<div class="form-block">Маршрут: ${request.trip.route.title}</div>
						<br>
						<table>
							<tr>
								<td>Город отправления</td>
								<td>Город прибытия</td>
								<td>Тип Вагона</td>
							</tr>
							<tr>
								<td>${request.departureCity}</td>
								<td>${request.destinationCity}</td>
								<td class="input">
									<input type="text" value="${request.carriage.name}">?
								</td>
							</tr>
							<tr>
								<td>Дата/время отправления</td>
								<td>Дата/время прибытия</td>
								<td>Цена</td>
							</tr>
							<tr>
								<td>${request.departureDateTime}</td>
								<td>${request.arrivalDateTime}</td>
								<td class="price">${request.price} руб.</td>
							</tr>
						</table>
						<input type="submit" class="btn" value="выбрать">
					</form>
				</div>
			</c:forEach>
		</div>
	</div>
</section>

<%@include  file="/WEB-INF/jsp/additional/footer.jsp" %>