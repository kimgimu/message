package com.ap4j.bma.controller.introduce;


import com.ap4j.bma.model.entity.aptTest.AptEntity;
import com.ap4j.bma.model.repository.AptRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@RestController
@Controller
@Slf4j
public class introduceController {

    @Autowired
    private AptRepository aptRepository;

//    @GetMapping("/tt")
//    public String index(Model model) {
//        List<AptEntity> allPositions = aptRepository.findAll();
//
//        // 중복 주소 제거를 위해 Set 사용
//        Set<AptEntity> uniquePositions = new HashSet<>(allPositions);
//
////        log.info("positions :{}", uniquePositions);
//        model.addAttribute("positions", uniquePositions);
//        log.info(uniquePositions.toString());
//        return "kakaoMap/ttt";
//    }


    /* 전문가 소개 페이지*/
    @RequestMapping("introduce")
    public String introduce(){
        return "introduce/proIntroduce";
    }

    /* 해당 부분 마커클러스터링 확인중 */
//    @GetMapping("/tt")
//    public String index(Model model) {
//        List<AptEntity> allPositions = aptRepository.findAll();
//
//        // 중복 주소 제거를 위해 Set 사용
//        Set<String> uniqueCombinedPositions = new HashSet<>();
//
//        for (AptEntity position : allPositions) {
//            String combinedPosition = position.getDistrict() + " " + position.getAddress();
//            uniqueCombinedPositions.add(combinedPosition);
//        }
//
//        model.addAttribute("positions", uniqueCombinedPositions);
//        log.info(uniqueCombinedPositions.toString());
//        return "kakaoMap/ttt";
//    }

    /* ******************* */
}

