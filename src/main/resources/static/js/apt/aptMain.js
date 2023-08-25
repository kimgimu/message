/** 맵 생성 */
var container = document.getElementById('map');
var options = {
    center: new kakao.maps.LatLng(37.55437, 126.974063),
    level: 3
};
var map = new kakao.maps.Map(container, options);

/** 확대축소 컨트롤러 생성 */
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);


var ps = new kakao.maps.services.Places();

var pageSize = 100; // 한 페이지당 처리할 데이터의 개수
var totalPages = Math.ceil(aptList.length / pageSize); // 전체 페이지 수 계산


// 페이지별로 처리하는 함수
function processPage(page) {
    var startIndex = (page - 1) * pageSize;
    var endIndex = Math.min(startIndex + pageSize, aptList.length);

    for (var i = startIndex; i < endIndex; i++) {
        var apt = aptList[i];
        var aptFullAddress = apt.aptAddress + " " + apt.aptName;
        searchKeyword(aptFullAddress, apt.aptName);
    }
}

// 페이지별로 데이터 처리
function processPagesSequentially(page) {
    if (page > totalPages) {
        console.log("All pages processed successfully.");
        return;
    }

    processPage(page);

    // 다음 페이지로 이동
    setTimeout(function() {
        processPagesSequentially(page + 1);
    }, 2000); // 2초 동안 대기
}

// 첫 번째 페이지부터 시작
processPagesSequentially(1);

function searchKeyword(aptFullAddress, aptName) {
    // 키워드로 좌표 변환 요청
    ps.keywordSearch(aptFullAddress, function(result, status) {
        if (status === kakao.maps.services.Status.OK) {
            var latitude = result[0].y; // 위도
            var longitude = result[0].x; // 경도

            // AJAX 요청을 통해 데이터 서버로 전송
            $.ajax({
                url: "/saveCoordinates",
                method: "POST",
                data: {
                    latitude: latitude,
                    longitude: longitude,
                    aptName: aptName
                },
                success: function(response) {
                    // 성공 시 처리
                },
                error: function(error) {
                    // 에러 시 처리
                }
            });
        }
    });
}











//var latitude = null;
//function searchKeyword(aptFullAddress, aptName) {
//    // 키워드로 좌표 변환 요청
//    ps.keywordSearch(aptFullAddress, function(result, status) {
//        if (status === kakao.maps.services.Status.OK) {
//            latitude = result[0].y; // 위도
//            var longitude = result[0].x; // 경도
////            if(aptFullAddress == "경상남도 창원마산회원구 내서읍 삼계리 마산삼계2")
//              if(aptFullAddress == "경상남도 거제시 문동동 삼오르네상스")
//                {
//                    console.log(aptFullAddress + aptName + " " + latitude + " " + longitude);
//                }
//            // AJAX 요청을 통해 데이터 서버로 전송
////            console.log(aptName + " " + latitude + " " + longitude)
//            $.ajax({
//                url: "/saveCoordinates", // Spring Boot 서버의 URL
//                method: "POST", // 전송 방식 (POST)
//                data: {
//                    latitude: latitude,
//                    longitude: longitude,
//                    aptName: aptName
//                    },
//                success: function(response) {
//                    resolve(response); // 요청 완료 시 resolve 호출
//                },
//                error: function(error) {
//                    reject(error); // 에러 발생 시 reject 호출
//                }
//
//            });
//        }
//    });
//}







// Places 서비스를 생성
//var places = new kakao.maps.services.Places();
//
//function searchKeywordAndAddMarker(keyword, name, amount) {
//    // 키워드로 장소 검색 요청
//    places.keywordSearch(keyword, function(result, status) {
//        if (status === kakao.maps.services.Status.OK) {
//            var place = result[0];
//            var coords = new kakao.maps.LatLng(place.y, place.x);
//
//            // 마커 생성
//            var marker = new kakao.maps.Marker({
//                map: map,
//                position: coords,
//                title: name
//            });
//
//            // 마커 클릭 시 인포윈도우 생성
//            kakao.maps.event.addListener(marker, 'click', function() {
//                var infowindow = new kakao.maps.InfoWindow({
//                    content: '<div style="padding:10px;">' + name + '</div>'
//                            + '<div style="padding:10px; ">' + keyword + '</div>'
//                            + '<div style="padding:10px;">' + amount + '만원</div>'
//                });
//                infowindow.open(map, marker);
//            });
//        }
//    });
//}
//
//// aptList에 저장된 아파트 정보를 순회하며 키워드 검색 및 마커 생성
//for (var i = 0; i < aptList.length; i++) {
//    var apt = aptList[i];
//
//    // 주소를 세분화하여 키워드로 사용
//    var keyword = apt.aptCityAddress + " " + apt.aptAddress1 + " " + apt.aptAddress2;
//
//    searchKeywordAndAddMarker(keyword, apt.aptName, apt.aptDealAmount);
//
//    console.log(keyword);
//}