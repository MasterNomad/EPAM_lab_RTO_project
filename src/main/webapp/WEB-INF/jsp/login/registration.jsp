<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include  file="/WEB-INF/jsp/additional/head.html" %>

<link rel="stylesheet" href="/css/login.css" class="css">
</head>

<jsp:include page="/WEB-INF/jsp/additional/menu-login.jsp" />

<section id="login">
	<div class="container">
		<div class="form">
			<h2>Регистрация</h2>
			<form action="/login/registration" method="POST" id="register_form">
				<table>
					<tr>
						<td>E-mail:</td>
						<td><input type="email" name="email" placeholder="Введите e-mail" value="${user.email}"
								required>
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<div class="answer">${emailAnswer}</div>
						</td>
					</tr>
					<tr>
						<td>Пароль:</td>
						<td> <input minlength="5" name="password" type="password" id="password"
								placeholder="Введите пароль" required>
						</td>
						<td>Повторите <br> пароль:</td>
						<td><input minlength="5" type="password" name="confirmPassword" placeholder="Повторите пароль"
								required></td>
					</tr>
					<tr>
						<td colspan="4">
							<div class="answer">${passwordAnswer}</div>
						</td>
					</tr>
					<tr>
						<td>Фамилия:</td>
						<td><input type="text" name="surname" placeholder="Введите фамилию" value="${user.surname}"
								required></td>
						<td>Имя:</td>
						<td><input type="text" name="name" placeholder="Введите имя" value="${user.name}" required></td>
					</tr>
					<tr>
						<td>Отчество: </td>
						<td><input type="text" name="patronymic" placeholder="Введите отчество"
								value="${user.patronymic}" required></td>
						<td>Дата <br> рождения:</td>
						<td><input type="date" name="birthDate" value="${user.birthDate}" max="${maxDate}" required>
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
			</form>
		</div>
	</div>
</section>

<%@include  file="/WEB-INF/jsp/additional/footer.jsp" %>