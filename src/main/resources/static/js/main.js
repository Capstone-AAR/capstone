$(document).ready(function () {

    $('#calendar').fullCalendar({
        header: {
            right: 'prev, next today'
        },
        selectable: true,
        selectHelper: true,
        editable: true,

        dayClick: function (date, jsEvent, view) {
            var eventStart = moment(date).format("YYYY-MM-DD");
            $('#startDateMoment').val(eventStart);
            $('#createModal').modal('show');

        },

        navLinks: true, // can click day/week names to navigate views
        editable: true,
        eventLimit: true,

        events: {
            url: '/tasks.json',
            type: 'GET',
            error: function () {
                alert("There was an error getting the event dates");
            }
        },

        eventClick: function (calEvent, jsEvent, view) {
            $('#modalTitle').html(calEvent.title);
            $('#modalBody').html(calEvent.detail);
            $('#myModal').modal("show");

            console.log(calEvent.detail);
        }
    });
});