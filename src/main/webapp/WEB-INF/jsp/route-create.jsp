<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include  file="head.html" %>

	<link rel="stylesheet" href="../css/route.css" class="css">
</head>

    <%@include  file="header.jsp" %>
    
    <section id="route" class="content">
		<div class="container">
			<div class="form">
                <h2>Создание маршрута</h2>
                <form action="/route-create" method="POST">
					<div class="form-block">
						Город отправления: <br>
						<input type="text" list="cities" name="args[]">
						<datalist id="cities">
							<c:forEach items="${stations}" var="station">
								<option value="${station}">${station}</option>
							</c:forEach>
						</datalist>
					</div>
					<div class="form-block">
						<label for="arrivalCity">Город назначения: </label>
						<br>
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
			</div>
		</div>
	</section>

	<%@include  file="footer.jsp" %>