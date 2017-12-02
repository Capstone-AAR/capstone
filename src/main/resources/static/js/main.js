$(document).ready(function () {
    var testColor = 'red';

    var goalId = $("#goalId").val();
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
        // eventLimit: true,

        events: {
            url: '/tasks.json',
            type: 'GET',
            color: testColor,
            data: {goalId: goalId}
        },
        error:
            function () {
                alert("There was an error getting the event dates");
            }
        ,

        eventClick: function (calEvent, jsEvent, view) {
            var $modalBody = $('#modalBody');
            var $modal = $('#myModal');
            var taskId = $('#taskId');
            taskId.val(calEvent.id);
            $('#modalTitle').html(calEvent.title);
            $modalBody.html(calEvent.taskDescription);
            $modalBody.append(calEvent.id);
            console.log(jsEvent);
            console.log("/////////////////");
            console.log(calEvent);
            if (calEvent.status === 'REQUEST_APPROVAL') {
                testColor = 'black';
                $('#taskStatus').show();
                var $approve = $('#approve-task');
                $approve.val('Approve');
                $approve.on('click', function () {
                    var request = $.ajax('/tasks/approve/' + calEvent.id);
                    request.fail(function (e) {
                        console.log(e);
                    });
                    request.done(function () {
                        $modal.modal('hide');
                    })
                });
            }
            $modal.modal("show");
            if (calEvent.status === 'NEW') {
                // console.log(calEvent.source.color.);
                $('#taskStatus').hide();
                var completedTask = $('#completed-task');
                completedTask.val('completed');
                completedTask.on('click', function () {
                    var request = $.ajax('/tasks/completed/' + calEvent.id);
                    request.fail(function (e) {
                        console.log(e);
                    });
                    request.done(function () {
                        $modal.modal('hide');
                    })
                });
            }
            $modal.modal("show");
        },

        eventRender: function(event, element) {
            if(event.status === 'REQUEST_APPROVAL') {
               element.css({
                   'background-color': '#F27A33'
               })
            }

            if(event.status === 'NEW') {
                element.css({
                    'background-color': '#438052'
                })
            }

        }

    });
});