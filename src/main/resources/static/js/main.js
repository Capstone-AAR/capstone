$(document).ready(function () {

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
            data: {goalId: goalId},
            error: function () {
                alert("There was an error getting the event dates");
            }
        },

        eventClick: function (calEvent, jsEvent, view) {
            var $modalBody = $('#modalBody');
            var $modal = $('#myModal');
            var taskId = $('#taskId');
            taskId.val(calEvent.id);
            $('#modalTitle').html(calEvent.title);
            $modalBody.html(calEvent.taskDescription);
            $modalBody.append(calEvent.id);
            console.log(calEvent.status);
            console.log(calEvent.id);
            if (calEvent.status === 'REQUEST_APPROVAL') {
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
            console.log(calEvent);
            if(calEvent.status === 'NEW') {
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
            console.log(calEvent);
        }

    });
});