package com.ap4j.bma.controller.apartment;

import com.ap4j.bma.model.entity.apt.AptDTO;
import com.ap4j.bma.model.entity.aptTest.AptEntity;
import com.ap4j.bma.service.apartment.ApartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class ApartmentController {

    @Autowired
    ApartmentServiceImpl aptServiceImpl;

    /** 전국 아파트 리스트 api DB저장 (최초 1회 DB에 APT 정보 저장했으면 이후 호출 안해도 됨)*/
    @GetMapping("apartmentApiCall")
    public String apartmentApiCall(Model model) {
        // DB 저장 메서드(init), api 호출 메서드(callApi)
        aptServiceImpl.init(aptServiceImpl.callApi());
        return "kakaoMap/aptMain";
    }

    /** DB정보를 기반으로 지도에서 좌표값 가져오기 (최초 1회 DB에 좌표 저장했으면 이후 호출 안해도 됨) */
    @GetMapping("getCoordinates")
    public String getCoordinates(Model model) {
        model.addAttribute("aptList", aptServiceImpl.aptList());
        return "kakaoMap/aptMain";
    }
    /** 가져온 좌표값 DB에 추가하기 (따로 호출할 필요 없이 getCoordinates 호출하면 자동으로 호출됨)*/
    @PostMapping("/saveCoordinates")
    public String saveCoordinates(Double latitude, Double longitude, String aptName) {
        System.out.println("Updating coordinates for: " + aptName);
        aptServiceImpl.updateCoordinatesByAptName(aptName, latitude, longitude);

        return "redirect:/getCoordinates";

    }

    /** 화면 좌표 범위의 DB값 데이터 보내주기 (클라이언트가 사용할 페이지)*/
    @PostMapping("/markers")
    public ResponseEntity<List<AptDTO>> getMarkersInBounds(Double southWestLat, Double southWestLng, Double northEastLat, Double northEastLng) {
        List<AptDTO> aptList = aptServiceImpl.findMarkersInBounds(southWestLat, southWestLng, northEastLat, northEastLng);
        return ResponseEntity.ok(aptList);
    }
    @GetMapping("/markers")
    public String getMarkersInBounds() {
        return "kakaoMap/apiTest";

    }




}
