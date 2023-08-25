//var aptList = /*[[ ${aptList} ]]*/ [];

var container = document.getElementById('map');
var options = {
    center: new kakao.maps.LatLng(37.55437, 126.974063),
    level: 3
};
var map = new kakao.maps.Map(container, options);

var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
map.setMaxLevel(7);

// 클러스터러 생성
var clusterer = new kakao.maps.MarkerClusterer({
    map: map, // 클러스터러를 지도에 표시
    gridSize: 200, // 클러스터 범위를 더 넓히기 위해 설정
    averageCenter: false,
    minLevel: 5 // 클러스터링을 시작할 지도 레벨
});


 // 마커 생성 함수
 function createMarker(position) {
     var marker = new kakao.maps.Marker({
         position: position
     });
     clusterer.addMarker(marker); // 클러스터에 마커 추가
 }

kakao.maps.event.addListener(map, 'idle', function() {
    var bounds = map.getBounds();
    var southWest = bounds.getSouthWest();
    var northEast = bounds.getNorthEast();

    var dataToSend = {
        southWestLat: southWest.getLat(),
        southWestLng: southWest.getLng(),
        northEastLat: northEast.getLat(),
        northEastLng: northEast.getLng()
    };


$.ajax({
    type: 'POST',
    url: 'markers',
    data: dataToSend,
    success: function(response) {
        if (response && response.length > 0) {
            for (var i = 0; i < response.length; i++) {
                var markerPosition = new kakao.maps.LatLng(response[i].latitude, response[i].longitude);
                createMarker(markerPosition);
            }
        } else {
            console.log("No markers to display.");
        }
    }
});
});