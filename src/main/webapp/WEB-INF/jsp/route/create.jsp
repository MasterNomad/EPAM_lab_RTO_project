<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include  file="../meta/head.html" %>

<link rel="stylesheet" href="../css/route.css" class="css">
</head>

<%@include  file="../meta/header.jsp" %>

<section id="route" class="content">
	<div class="container">
		<div class="form">
			<h2>Создать маршрут</h2>
			<form action="/route/create" method="POST">
				<div class="form-block">
					Город отправления: <br>
					<span id="city_input">
						<input type="text" list="cities" name="args[]">
						<datalist id="cities">
							<c:forEach items="${stations}" var="station">
								<option value="${station}">${station}</option>
							</c:forEach>
						</datalist>
					</span>
				</div>
				<a id="add_btn" class="btn">+</a>
			
				<div class="form-block">
					Город назначения: <br>
					<input type="text" list="cities" name="args[]">
					<datalist id="cities">
						<c:forEach items="${stations}" var="station">
							<option value="${station}">${station}</option>
						</c:forEach>
					</datalist>
				</div>
				<input type="submit" class="btn" value="Сгенерировать">
			</form>
			<div>${answer}</div>
			<div>
				<a style="display: ${next}" href="/route/edit" class="btn">Продолжить</a>
			</div> 
		</div>
	</div>
</section>

<%@include  file="../meta/footer.jsp" %>