<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<%@include  file="/WEB-INF/jsp/additional/head.html" %>

<link rel="stylesheet" href="/css/route.css" class="css">
</head>

<jsp:include page="/WEB-INF/jsp/additional/menu.jsp" />

<section id="route" class="content">
	<div class="container">
		<div class="form">
			<h2>Маршруты</h2>

			<a href="/admin/route/create" class="btn">Создать маршрут</a>

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

				<c:forEach items="${routes}" var="route">
					<tr>
						<td>
							<span class="tooltip">
								${route.title}
								<span class="tooltiptext">
									Маршрут: ${route.title}
									<table class="tip-table">
										<tr>
											<td>Город</td>
											<td>Остановка</td>
										</tr>
										<c:forEach items="${route.stationList}" var="station">
											<tr>
												<td>${station.name}</td>
												<td>
													<c:choose>
														<c:when test="${0 > station.stopDuration}">
														</c:when>
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
							<a href='/admin/route/edit?title=${route.title}'>Редактировать</a>
							<br>
							<a href='/admin/route/delete?title=${route.title}'>Удалить</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	</div>
</section>

<script src="/js/route.js"></script>

<%@include  file="/WEB-INF/jsp/additional/footer.jsp" %>