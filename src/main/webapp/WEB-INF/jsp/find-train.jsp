<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ru">

<head>
	<meta charset="UTF-8">
	<title>Transgalaxy railway</title>
	<link rel="stylesheet" href="../css/main.css" class="css">
	<link rel="stylesheet" href="../css/header.css" class="css">
	<link rel="shortcut icon" href="../favicon.ico">
</head>

<body>
	<%@include  file="header.jsp" %>
	<div class="separator"></div>
	<section class="ticket">
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
</body>

</html>