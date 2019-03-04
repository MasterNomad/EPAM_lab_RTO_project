$(document).ready(function () {
    $(".carriage").change(
        function () {
            var $call = $(this);
            $.ajax({
                type: 'GET',
                url: '/find-train/carriage-change',
                data: {
                    'tripId': $call.closest('form').find('.trip-id').val(),
                    'carriageName': $call.val()
                }
            }).done(
                function (answer) {
                    console.log(answer);
                    $call.closest('table').find('.tooltiptext').html(answer[0]);
                    $call.closest('table').find('.price').html(answer[1] + " руб.");
                }
            )
        })
}
)
