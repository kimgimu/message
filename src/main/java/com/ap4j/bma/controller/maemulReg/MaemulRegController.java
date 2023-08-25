package com.ap4j.bma.controller.maemulReg;

import com.ap4j.bma.service.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MaemulRegController {

    @GetMapping("/agree") //약관동의 페이지
    public  String agreementForm(){

        return "maemulReg/agree";
    }

    @GetMapping("/maemulinfo") //매물정보페이지
    public  String maemulinfoForm(){

        return "maemulReg/maemulInfo";
    }

    @GetMapping("/moreinfo") //상세정보페이지
    public  String moreinfoForm(){

        return "maemulReg/moreinfo";
    }

}
