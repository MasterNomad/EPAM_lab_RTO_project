<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include  file="/WEB-INF/jsp/additional/head.html" %>

<link rel="stylesheet" href="/css/route.css" class="css">
</head>

<jsp:include page="/WEB-INF/jsp/additional/menu-admin.jsp" />

<section id="route" class="content">
	<div class="container">
		<div class="form">
			<h2>Создать маршрут</h2>
			<form action="/admin/route/create" method="POST">
				<div class="form-block">
					Город отправления: <br>
					<span id="city_input">
						<input class ="clear" type="text" list="cities" name="args[]" required>
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
					<input class ="clear" type="text" list="cities" name="args[]" required>
				</div>
				<input type="submit" class="btn" value="Расчитать">
			</form>
			<div>${answer}</div>
			<c:if test="${next == 'true'}">
				<div>
					<a href="/admin/route/edit" class="btn">Продолжить</a>
				</div>
			</c:if>
		</div>
	</div>
</section>

<script src="/js/route.js"></script>

<%@include  file="/WEB-INF/jsp/additional/footer.jsp" %>