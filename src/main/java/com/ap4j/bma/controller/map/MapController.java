package com.ap4j.bma.controller.map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("map")
public class MapController {

    @RequestMapping("main")
    public String main(){
        log.info("MapController.main.execute");
        return "kakaoMap/markerCluster";
    }

    @RequestMapping("map")
    public String map(){
        log.info("MapController.map.execute");
        return "map/map";
    }
}
