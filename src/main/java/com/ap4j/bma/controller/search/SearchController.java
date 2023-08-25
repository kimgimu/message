package com.ap4j.bma.controller.search;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("search")
public class SearchController {

	@RequestMapping("kakao")
	public String search(){
		log.info("SearchController.search.execute");
		return "kakaoMap/search";
	}
}
