var trackProgress = document.getElementsByClassName('track-progress');
var totalPoints = document.getElementsByClassName('total-points');
var elems = document.getElementsByClassName("myBar");

for(var i = 0; i < trackProgress.length; i++){
    var progress = parseFloat(trackProgress[i].innerText);
    var points = parseFloat(totalPoints[i].innerText);
    var totalProgress = (progress / points) * 100;
    var elem = elems[i];


    $(elem).animate({'width': totalProgress + '%'});


    // var id = setInterval(function (tp, y, el) {
    //     console.log("width",width);
    //     console.log("totalProgress", tp);
    //     console.log("elem", el);
    //     console.log("id", y);
    //     if (width >= tp) {
    //         clearInterval(y);
    //     } else {
    //         console.log("width", width);
    //         width++;
    //         el.style.width = width + '%';
    //     }
    // }, 10, totalProgress, id, elem);
}