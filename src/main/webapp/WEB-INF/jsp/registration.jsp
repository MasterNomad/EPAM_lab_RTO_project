<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include  file="head.jsp" %>

</head>

<body>
	<div id="header" class = "content"></div>
	<div class="separator"></div>
	<section class="login">
		<div class="container">
			<div class="form">
				<h2>Регистрация</h2>
				<form id="register_form">
					<table>
						<tr>
							<td>Имя:</td>
							<td><input type="text" name="name" placeholder="Введите имя" required></td>
							<td>Фамилия:</td>
							<td><input type="text" name="surname" placeholder="Введите фамилию" required></td>
						</tr>
						<tr>
							<td>Отчество: </td>
							<td><input type="text" name="patronymic" placeholder="Введите отчество" required></td>
							<td>Дата <br> рождения:</td>
							<td><input type="date" name="birthDate" required></td>
						</tr>
						<tr>
							<td>Пол:</td>
							<td class="radioBtn">
								<span>муж.</span> <input type="radio" name="sex" value="1" required>
								жен.<input type="radio" name="sex" value="0" required></td>
						</tr>
						<tr>
							<td>e-mail:</td>
							<td><input type="email" name="mail" placeholder="Введите e-mail" required></td>
						</tr>
						<tr>
							<td>Пароль:</td>
							<td> <input minlength="5" type="password" id="password" placeholder="Введите пароль" required></td>
							<td>Повторите <br> пароль:</td>
							<td><input minlength="5" type="password" id="confirm_password" placeholder="Повторите пароль" required></td>
						</tr>
						<tr>

						</tr>
					</table>
					<div>
						<input class="btn" type="submit" value="Зарегистрироваться">
					</div>
					<div></div>
					<a href="/html/login.html">Уже есть аккаунт</a>
				</form>
			</div>
			</form>
		</div>
		</div>
	</section>
	<div id="footer"></div>
</body>

<script src="/js/jquery-3.3.1.min.js"></script>
<script src="/js/script.js"></script>
<script src="/js/registration.js"></script>

</html>