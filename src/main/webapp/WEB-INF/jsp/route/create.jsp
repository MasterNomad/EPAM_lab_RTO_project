<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

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
						<select name="args[]" required>
							<c:forEach items="${stations}" var="station">
								<option value="${station}" ${station==args[0] ? 'selected="selected"' : '' }>${station}
								</option>
							</c:forEach>
						</select>
					</span>
				</div>

				<c:if test="${fn:length(args) > 2}">
					<c:forEach begin="1" end="${fn:length(args)-2}" varStatus="loop">
						<div class='form-block'>
							Промежуточная станция:<span class='a_btn delete_btn'>X</span> <br>
							<select name="args[]" required>
								<c:forEach items="${stations}" var="station">
									<option value="${station}" ${station==args[loop.index] ? 'selected="selected"' : ''
										}>
										${station}
									</option>
								</c:forEach>
							</select>
						</div>
					</c:forEach>
				</c:if>

				<a id="add_btn" class="btn">+</a>
				<div class="form-block">
					Город назначения: <br>
					<select name="args[]" required>
						<c:forEach items="${stations}" var="station">
							<option value="${station}" "${station}" ${station==args[fn:length(args)-1]
								? 'selected="selected"' : '' }>${station}</option>
						</c:forEach>
					</select>
				</div>
				<input type="submit" class="btn" value="Расчитать">
			</form>


			<c:if test="${not empty answer}">
				<c:if test="${fn:length(answer) > 1}">
					<h4>Кратчайший маршрут по вашему запросу проходит через следующие станции:</h3>
						<div id="route-stations">
							<p>${answer[0]}</p>
							<c:set var="index" value="1" />
							<c:forEach begin="1" end="${fn:length(answer)-2}" varStatus="loop">
								<c:choose>
									<c:when test="${answer[loop.index] == args[index]}">
										<c:set var="index" value="${index + 1}" />
										<p>${answer[loop.index]}</p>
									</c:when>
									<c:otherwise>
										<span>${answer[loop.index]}</span><br>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<p>${answer[fn:length(answer)-1]}</p>
						</div>
						<div>
							<a href="/admin/route/edit" class="btn">Продолжить</a>
						</div>
				</c:if>
			</c:if>
		</div>
	</div>
</section>

<script src="/js/route.js"></script>

<%@include  file="/WEB-INF/jsp/additional/footer.jsp" %>