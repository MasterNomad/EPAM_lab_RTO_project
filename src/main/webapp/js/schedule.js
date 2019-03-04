$(document).ready(function () {
    $("#schedule_table tr:last").after($("#trip_input").html());
    $(".delete_btn").click(
        function () {
            $(this).closest('tr').remove();
            return false;
        }
    );
    $("#add_btn").click(
        function () {
            $("#schedule_table tr:last").after($("#trip_input").html());
            $(".delete_btn").click(
                function () {
                    $(this).closest('tr').remove();
                    return false;
                }
            );
        }
    );
});