<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include  file="head.html" %>

	<link rel="stylesheet" href="../css/route.css" class="css">
</head>

	<%@include  file="header.jsp" %>

		<section id="route" class="content">
		<div class="container">
			<div class="form">
				<h2>Маршруты</h2>
				<table>
					<tr>
						<th>
							<label for="title">Идентификатор <br> маршрута</label>
						</th>
						<th>
							<label for="departureDay">День <br> отправления</label>
						</th>
						<th>
							<label for="departureTime">Время <br> отправления</label>
						</th>
						<th>
							<label for="departureStation"> Станция <br> отправления</label>
						</th>
						<th>
							<label for="arrivalStation">Конечная <br> станция</label>
						</th>
						<th>Время <br> в пути</th>
						<th></th>
					</tr>
					<tr id="search">
						<td><input id="title" name="title" type="text"></td>
						<td><input id="departureDay" name="departureDay" type="text"></td>
						<td><input id="departureTime" name="departureTime" type="text"></td>
						<td><input id="departureStation" name="departureStation" type="text"></td>
						<td><input id="arrivalStation" name="arrivalStation" type="text"></td>
						<td></td>
						<td>
							<a href="#">Поиск</a>
						</td>
					<c:forEach items="${routes}" var="route">
						<tr>
						<td>${route.title}</td>
						<td>${route.departureDay}</td>
						<td>${route.departureTime}</td>
						<td></td>
						<td></td>
						<td></td>
						<td>
							<a href="#">Редактировать</a>
						</td>
						</tr>
						</c:forEach>
				</table>
				<a href="/route-create" class= "btn">Создать маршрут</a>	
			</div>
		</div>
		</div>
	</section>

	<%@include  file="footer.jsp" %>