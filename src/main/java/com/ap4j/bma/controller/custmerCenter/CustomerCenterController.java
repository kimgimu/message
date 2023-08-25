package com.ap4j.bma.controller.custmerCenter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerCenterController {

    //FAQ
    @GetMapping("/faq/list")
    public  String FAQListForm(){

        return "customerCenter/FAQBoard/FAQList";
    }

    @GetMapping("/faq/view")
    public  String FAQViewForm(){

        return "customerCenter/FAQBoard/FAQView";
    }

    //notice
    @GetMapping("/notice/list")
    public  String noticeListForm(){

        return "customerCenter/noticeBoard/noticeList";
    }

    @GetMapping("/notice/view")
    public  String noticeViewForm(){

        return "customerCenter/noticeBoard/noticeView";
    }

    //guide
    @GetMapping("/guide")
    public  String guideListForm() {

        return "customerCenter/guidePage";
    }


    //QNA
    @GetMapping("/qna")
    public  String QnAWriteForm(){

        return "customerCenter/QnABoard/QnAWrite";
    }
}
