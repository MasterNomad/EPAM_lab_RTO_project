<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include  file="head.html" %>

</head>

	<%@include  file="header.jsp" %>

	<section id="ticket" class = "content">
		<div class="container">
			<div class="form">
				<h2>Заказать билет</h2>
				<form action="/find-train" method="POST">
					<div class="form-block">
						Город отправления: <br>
						<input type="text" list="cities" name="departureCity">
						<datalist id="cities">
							<c:forEach items="${stations}" var="station">
								<option value="${station}">${station}</option>
							</c:forEach>
						</datalist>
					</div>
					<div class="form-block">
						Дата отправления: <br>
						<input type="date">
					</div>
					<div class="form-block">
						<label for="arrivalCity">Город назначения: </label>
						<br>
						<input type="text" list="cities" name="arrivalCity">
						<datalist id="cities">
							<c:forEach items="${stations}" var="station">
								<option value="${station}">${station}</option>
							</c:forEach>
						</datalist>
					</div>
					<input type="submit" class="btn" value="Поиск">
				</form>
				<div>
					${answer}
				</div>
			</div>
		</div>
	</section>

	<%@include  file="footer.jsp" %>