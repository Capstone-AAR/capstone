$(document).ready(function () {

    $('#calendar').fullCalendar({
        header: {
            left: 'prev, next today',
            center: 'title',
            right: 'month, agendaWeek, agendaDay'
        },
        selectable: true,
        selectHelper: true,
        editable: true,

        dayClick: function (date, jsEvent, view) {
            var eventStart = moment(date).format("YYYY-MM-DD");
            $('#startDateMoment').val(eventStart);
            $('#createModal').modal('show');
            // var eventTitle = $('#title').val();
            // var eventDetail = $('#description').val();

            // if (eventTitle) {
            //     // $('#calendar').fullCalendar('renderEvent', {
            //     //     title: eventTitle,
            //     //     start: eventStart,
            //     //     detail: eventDetail,
            //     //     stick: true
            //     // });
            //
            //
            //     console.log(view);
            //     console.log(jsEvent);
            //     console.log("This is the title: " + eventTitle);
            //     console.log(eventDetail);
            //     console.log("start date: " + eventStart);
            //     console.log("end date: " + eventStart);
            // }
        },

        eventClick: function (calEvent, jsEvent, view) {
            $('#modalTitle').html(calEvent.title);
            $('#modalBody').html(calEvent.detail);
            $('#myModal').modal("show");
        },
        events: tasks
    });
});