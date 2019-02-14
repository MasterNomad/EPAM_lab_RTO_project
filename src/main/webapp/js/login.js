$(document).ready(function () {
    $("#login_form").submit(
        function () {
            if ($("#user").val() == "admin") {
                if ($("#password").val() == "admin") {
                    alert("Hello, " + $("#user").val() + "!");
                } else {
                    alert("Неверный пароль!");
                }
            } else {
                alert("Нет такого пользователя!");
            }
            return false;
        });
});
