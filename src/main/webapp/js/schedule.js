$(document).ready(function () {

    function deleterow() {
        $(this).closest('tr').remove();
        return false;
    }

    function refreshroute() {
        var $call = $(this);
        $.ajax({
            type: 'GET',
            url: '/route/getstations',
            data: {
                'routeTitle': $call.val()
            }
        }).done(
            function (answer) {
                var html = "<table class='tip-table'>Маршрут: " + answer.title + "<tr><td>Город</td><td>Остановка</td></tr>";
                answer.stations.forEach(
                    function (station) {
                        html += "<tr><td>" + station.name + "</td><td>";
                        if (station.stopDuration < 0) {
                            html += "-";
                        } else {
                            html += station.stopDuration + " мин.";
                        }
                        html += "</td></tr>";
                    }
                );
                html += "</table>";
                $call.closest('tr').find('.route-discription').html(html);
            }
        );
    }


    $("#schedule_table tr:last").after($("#trip_input").html());

    $(".delete_btn").click(deleterow);

    $(".route").change(refreshroute);

    $("#add_btn").click(
        function () {
            $("#schedule_table tr:last").after($("#trip_input").html());
            $(".delete_btn").click(deleterow);
            $(".route").change(refreshroute);
        }
    );
});