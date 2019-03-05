<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include  file="/WEB-INF/jsp/additional/head.html" %>

<link rel="stylesheet" href="/css/find-train.css" class="css">
</head>

<c:choose>
	<c:when test="${role == 'ADMIN'}">
		<jsp:include page="/WEB-INF/jsp/additional/menu-admin.jsp" />
	</c:when>
	<c:otherwise>
		<jsp:include page="/WEB-INF/jsp/additional/menu-user.jsp" />
	</c:otherwise>
</c:choose>

<section id="find-train" class="content">
	<div class="container">
		<div class="form">
			<h2>Заказать билет</h2>

			<form action="/find-train" method="POST">
				<div class="form-block">
					<label for="departureCity">Город отправления:</label>
					<br>
					<select name="departureCity" required>
						<c:forEach items="${stations}" var="station">
							<option value="${station}" ${station==departureCity ? 'selected="selected"' : '' }>
								${station}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-block">
					<label for="departure">Дата отправления:</label>
					<br>
					<input name="departure" id="departure" type="datetime-local" value="${departure}"
						min="${currentDate}" required>
				</div>
				<div class="form-block">
					<label for="destinationCity">Город назначения: </label>
					<br>
					<select name="destinationCity" required>
						<c:forEach items="${stations}" var="station">
							<option value="${station}" ${station==destinationCity ? 'selected="selected"' : '' }>
								${station}</option>
						</c:forEach>
					</select>
				</div>
				<input type="submit" class="btn" value="Поиск">
			</form>

			<c:choose>
				<c:when test="${answer == 'none'}">
				</c:when>

				<c:when test="${empty answer}">
					<p>По вашему запросу подходящих поездов не найденно</p>
				</c:when>

				<c:otherwise>
					<c:forEach items="${answer}" var="request">
						<div class="request">
							<form action="/find-train/сonfirm" method="POST">
								<div class="form-block">Рейс №
									<input name="tripId" class="inactive trip-id" readonly="readonly" type="number"
										value="${request.trip.id}">
								</div>
								<div class="form-block">
									Маршрут:
									<span class="tooltip">
										${request.trip.route.title}
										<span class="tooltiptext">
											<c:forEach items="${request.trip.route.stationList}" var="station">
												<p>${station.name}</p>
											</c:forEach>
										</span>
									</span>
								</div>
								<br>
								<table>
									<tr>
										<td>Город отправления</td>
										<td>Город назначения</td>
										<td>Тип Вагона</td>
									</tr>
									<tr>
										<td>
											<input name="departureCity" class="inactive" readonly="readonly" type="text"
												value="${request.departureCity}">
										</td>
										<td>
											<input name="destinationCity" class="inactive" readonly="readonly"
												type="text" value="${request.destinationCity}">
										</td>
										<td class="input">

											<select name="carriageName" class="carriage" required>
												<c:forEach items="${request.trip.tripComposition}" var="carriage">
													<option value="${carriage.carriage.name}"
														${carriage.carriage.name==request.carriage.name
														? 'selected="selected"' : '' }>
														${carriage.carriage.name}
													</option>
												</c:forEach>
											</select>
											<span class="tooltip">?
												<span class="tooltiptext">
													${request.carriage.description}
												</span>
											</span>
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
								<input type="submit" class="btn" value="оформить">
							</form>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>

		</div>
	</div>
</section>

<script src="/js/find-train.js"></script>

<%@include  file="/WEB-INF/jsp/additional/footer.jsp" %>