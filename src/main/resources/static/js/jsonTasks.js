(function ($) {
    var request = $.ajax({'url': '/tasks.json'});
    request.done(function (data) {
        var html = '';
        console.log(data);
        data.forEach(function (task) {

            html += '<div>';
            html += '<h1>' + task.taskName + '</h1>';
            html += '<p>' + task.taskDescription + '</p>';
            html += '</div>';
        });
        $('#testJson').html(html);
    })
})(jQuery);