$(document).ready(function () {
    $("#register_form").submit(
        function () {
            if (validatePassword) {
                alert("Вы успешно зарегистрированны! (нет)")
            }
            return false;
        });

    validatePassword = function () {
        if ($("#password").val() != $("#confirm_password").val()) {
            $("#confirm_password")[0].setCustomValidity("Passwords Don't Match");
            return false;
        } else {
            $("#confirm_password")[0].setCustomValidity('');
            return true;
        }
    };

    $("#password").change(
        validatePassword
    );


    $("#confirm_password").change(
        validatePassword
    );
});
