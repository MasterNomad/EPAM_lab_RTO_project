<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@include  file="/WEB-INF/jsp/additional/head.html" %>

<link rel="stylesheet" href="/css/login.css" class="css">
</head>

<jsp:include page="/WEB-INF/jsp/additional/menu.jsp" />

<section id="login">
	<div class="container">
		<div class="form">
			<h2>Регистрация</h2>
			<form:form action="/login/registration" modelAttribute="user" method="POST" id="register_form">
				<table>
					<tr>
						<td>
							<form:label path="email">E-mail:</form:label>
						</td>
						<td>
							<form:input path="email" placeholder="Введите e-mail" required="true" />
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<form:errors path="email" cssClass="answer" />
							<div class="answer">${emailAnswer}</div>
						</td>
					</tr>
					<tr>
						<td>
							<form:label path="password">Пароль:</form:label>
						</td>
						<td>
							<form:password path="password" placeholder="Введите пароль" required="true" />
						</td>
						<td>Повторите <br> пароль:</td>
						<td>
							<input type="password" name="confirmPassword" placeholder="Повторите пароль" required>
						</td>
					</tr>
					<tr>
						<td></td>
						<td colspan="3">
							<form:errors path="password" cssClass="answer" />
							<div class="answer">${passwordAnswer}</div>
						</td>
					</tr>
					<tr>
						<td>
							<form:label path="surname">Фамилия:</form:label>
						</td>
						<td>
							<form:input path="surname" placeholder="Введите фамилию" required="true" />
						</td>
						<td>
							<form:label path="name">Имя:</form:label>
						</td>
						<td>
							<form:input path="name" placeholder="Введите имя" required="true" />
						</td>
					</tr>
					<tr>
						<td>
							<form:label path="patronymic">Отчество:</form:label>
						</td>
						<td>
							<form:input path="patronymic" placeholder="Введите отчество" />
						</td>
						<td>
							<form:label path="birthDate">Дата <br> рождения:</form:label>
						</td>
						<td>
							<form:input path="birthDate" type="date" required="true" />
						</td>
					</tr>
					<tr>
						<td colspan="2"></td>
						<td colspan="2">
							<div class="answer">${ageAnswer}</div>
						</td>
					</tr>
					<tr>
						<td>Пол:</td>
						<td class="radioBtn">
							муж.<input type="radio" name="sex" value="1" required ${user.sex==true ?'checked':''}>
							жен.<input type="radio" name="sex" value="0" required ${user.sex==false ?'checked':''}></td>
					</tr>
					</tr>
				</table>
				<div>
					<input class="btn" type="submit" value="Зарегистрироваться">
				</div>
				<a href="/login">Уже есть аккаунт</a>
			</form:form>
		</div>
	</div>
</section>

<%@include  file="/WEB-INF/jsp/additional/footer.jsp" %>