var trackProgress = document.getElementsByClassName('track-progress');
var totalPoints = document.getElementsByClassName('total-points');
var elems = document.getElementsByClassName("myBar");

for (var i = 0; i < trackProgress.length; i++) {
    var progress = parseFloat(trackProgress[i].innerText);
    var points = parseFloat(totalPoints[i].innerText);
    var totalProgress = (progress / points) * 100;
    var elem = elems[i];


    $(elem).animate({'width': totalProgress + '%'});
}