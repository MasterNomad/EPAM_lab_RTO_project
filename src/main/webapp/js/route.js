$(document).ready(function () {

    $(".delete_btn").click(
        function () {
            $(this).closest('.form-block').remove();
            return false;
        }
    );

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

    $(".edit").change(function () {
        var $call = $(this);

        $.ajax({
            type: 'GET',
            url: '/route/getlocomotivespeed',
            data: {
                'locomotiveName': $call.val()
            }
        }).done(
            function (answer) {
                $call.siblings('#speed').html(answer);
            }
        )
    })
});