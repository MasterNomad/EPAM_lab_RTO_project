$(document).ready(function () {
    $("#add_btn").click(
        function () {
            $("#add_btn").before("<div class='form-block'>Промежуточная станция: " +
                "<span class='a_btn delete_btn'>X</span> <br> " +
                $("#city_input").html() +
                "</div>");
            $(".delete_btn").click(
                function () {
                    $(this).closest('.form-block').remove();
                    return false;
                }
            );
        }
    );
});