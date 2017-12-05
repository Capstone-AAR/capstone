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
            $modalBody.html('Description:  <br>' + calEvent.taskDescription);
            if(calEvent.points == 1 || calEvent.points == '1') {
                console.log();
                $modalBody.append('<br><br>' + 'Value:  <br>' + calEvent.points + ' point');
            }

            if(calEvent.points !== '1') {
                console.log();
                $modalBody.append('<br><br>' + 'Value:  <br>' + calEvent.points + ' points');
            }
            // $modalBody.append('<br><br>' + 'Value:  <br>' + calEvent.points + ' points');
            $modalBody.css("font-size", "20px");
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

            if(event.status === 'APPROVED') {
                element.css({
                    'background-color': '#ff0017',
                    'border-color': '#ff0017',
                    'font-weight' : 'bold',
                    'height' :  '30px'
                })
            }


            if(event.status === 'REQUEST_APPROVAL') {
               element.css({
                   'background-color': '#F27A33',
                   'border-color': '#F27A33',
                   'font-weight' : 'bold',
                   'height' :  '30px'
               })
            }

            if(event.status === 'NEW') {
                element.css({
                    'background-color': '#438052',
                    'border-color': '#438052',
                    'font-weight' : 'bold',
                    'height' :  '30px',
                    'font-size' : '20px'

                })
            }

        }

    });
});