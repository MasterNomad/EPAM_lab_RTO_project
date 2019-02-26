$(document).ready(function () {
    $(".clear").click(
        function () {
            $(this).val('');
        }
    );

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
                function (description, price) {
                    console.log(description);
                    $call.closest('table').find('.tooltiptext').html(description);
                    $call.closest(".price").html(price + " руб.");
                }
            )
        })
}
)
