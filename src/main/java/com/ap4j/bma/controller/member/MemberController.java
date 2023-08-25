package com.ap4j.bma.controller.member;
// pjm - use m o p q
// controller -q | model -m | html -o | etc.. -p
import com.ap4j.bma.model.entity.member.MemberDTO;
import com.ap4j.bma.model.entity.member.MemberEntity;
import com.ap4j.bma.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import javax.validation.Valid;
import java.util.HashMap;

@Slf4j
@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService qMemberService;

    /** 로그인 페이지 매핑 */
    @RequestMapping(value="/qLoginForm")
    public String qLoginForm(Model model) {
        log.info("MemberController - qLoginForm() 실행");
        model.addAttribute("memberDTO", new MemberDTO());
        return "/userView/oLoginForm";
    }

    /** 카카오 로그인 실행 */
    @RequestMapping(value="/qLogin")
    public String qLogin(@RequestParam(value = "code", required = false) String code, Model model, HttpSession session) {
        log.info("MemberController - qLogin() 실행");
        log.info("####code : " + code);

        // 1번 인증코드 요청 전달
        String accessToken = qMemberService.getAccessToken(code);     // code로 토큰 받음
        log.info("accessToken : " + accessToken);

        // 2번 인증코드로 토큰 전달
//        MemberDTO userInfo = qMemberServiceImpl.getUserInfo2(accessToken);   // 사용자 정보 받음
        HashMap<String, Object> userInfo = qMemberService.getUserInfo(accessToken);   // 사용자 정보 받음
        log.info("login info : " + userInfo.toString());
        // https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info
        log.info("login userUniqueId : " + userInfo.get("id"));
        log.info("login userEmail : " + userInfo.get("email"));
        log.info("login userName : " + userInfo.get("nickname"));
//        log.info("login thumbnail_image_url : " + userInfo.get("thumbnail_image_url"));

        if(userInfo.get("email") != null) {
            session.setAttribute("userEmail", userInfo.get("email"));
            session.setAttribute("userName", userInfo.get("nickname"));
            session.setAttribute("accessToken", accessToken);
        }
        model.addAttribute("userEmail", userInfo.get("email"));
        model.addAttribute("userName", userInfo.get("nickname"));
//        model.addAttribute("thumbnail_image_url", userInfo.get("thumbnail_image_url"));
//        if(userInfo.getEmail() != null) {
//            session.setAttribute("userId", userInfo.getEmail());
//            session.setAttribute("userName", userInfo.getName());
//            session.setAttribute("accessToken", accessToken);
//        }
//        model.addAttribute("userId", userInfo.getEmail());
//        model.addAttribute("userName", userInfo.getName());

        MemberEntity member = new MemberEntity();
        member.setEmail((String) userInfo.get("email"));
        member.setName((String) userInfo.get("nickname"));
        member.setRoot("카카오");
        member.setPwd("암호화 작업 필요");
        log.info(member.toString());
        qMemberService.joinBasic(member);   // 카카오정보로 회원가입 실행

        return "/userView/oMyPage";
    }

    /** 카카오 로그아웃 */
    @RequestMapping(value="/qLogout")
    public String qLogout(HttpSession session) {
        log.info("MemberController - qLogout() 실행");

        /* token 재사용으로 콘솔에 400에러가 확인되어 주석 처리(정확한 원인 파악 필요) */
//        qMemberServiceImpl.kakaoLogout((String)session.getAttribute("accessToken"));
//        session.removeAttribute("accessToken");
        session.removeAttribute("userEmail");
        session.removeAttribute("userName");

        log.info("로그아웃 완료되었습니다.");  // 로그아웃 후 메인화면 리다이렉트
        log.info("--- 로그아웃 후 세션 상태 체크 ---");
        log.info((String) session.getAttribute("userEmail"));
        log.info((String) session.getAttribute("userName"));
        return "redirect:/";
    }

    /** 기본 로그인 */
    @PostMapping(value="/qLoginBasic")
    public String qBasicLogin(@Valid @ModelAttribute MemberDTO memberDTO, BindingResult bindingResult,
                              Model model, HttpSession session, HttpServletResponse response) {
        log.info("MemberController - qBasicLogin() 실행");
        log.info("memberDTO : " + memberDTO);

//        if(bindingResult.hasErrors()) {
//            log.info("유효성 검사 에러 발생");
//            return "/userView/oLoginForm";
//        }

        MemberDTO loginMember = qMemberService.login(memberDTO);
        if(loginMember != null) {
            log.info(loginMember.toString());
            log.info("로그인 성공");
            String userEmail = loginMember.getEmail();
            String userName = loginMember.getName();
            model.addAttribute("userEmail", userEmail);
            model.addAttribute("userName",userName);
            session.setAttribute("loginMember", loginMember);

            // 쿠키 설정
            Cookie idCookie = new Cookie("userEmail",  String.valueOf(loginMember.getEmail()));
            response.addCookie(idCookie);
            return "/userView/oMyPage";
        } else {
            log.info("로그인 실패헀어요.");
            model.addAttribute("msg","로그인 실패");
            return "redirect:/member/qLoginForm";
//            model.addAttribute("goURL","redirect:/member/qLoginForm");
        }
/*        MemberEntity member = new MemberEntity();
        member.setEmail(memberDTO.getEmail());
        member.setPwd(memberDTO.getPwd());
        log.info(member.toString());
        
        if(bindingResult.hasErrors()) {
            log.info("유효성 검사 에러 발생");
            return "/userView/oLoginForm";
        }

        MemberEntity result = qMemberService.login(member.getEmail());

//        if(qMemberServiceImpl.loginByEmail(member.getEmail())) {
        if(result != null) {
            log.info("로그인 성공!");
            log.info("result : " + result);
            log.info("result.email : " + result.getEmail());
            model.addAttribute("userId", result.getEmail());
            model.addAttribute("userName", result.getName());
            session.setAttribute("userId", result.getEmail());
            return "/userView/oLoginForm";
        }*/
//        return "/userView/oMyPage";
//        return "/userView/oLoginForm";    // 원래 사용 코드
//        return "redirect:/member/qLoginForm";
    }

    /** 기본 회원가입 폼 */
    @RequestMapping(value="/qJoinForm")
    public String qJoinForm(Model model) {
        log.info("MemberController - qJoinForm() 실행");
        model.addAttribute("memberDTO", new MemberDTO());
        return "/userView/oJoinForm";
    }

    /** 기본 회원가입 */
    @PostMapping(value="/qJoinBasic") /* @ModelAttribute(value="memberT") */
    public String qJoinBasic(@Valid @ModelAttribute MemberDTO memberDTO, BindingResult bindingResult, Model model) {  // @Valid : UserDTO 유효성 검사 애노테이션(통과X -> errors)
        log.info("MemberController - qJoinBasic() 실행");
        log.info("memberDTO : " + memberDTO);

//        if(bindingResult.hasErrors()) {
//            log.info("유효성 검사 에러 발생");
////            model.addAttribute("memberDTO", memberDTO); // 회원가입 실패 시 입력 데이터 유지
//            return "/userView/oJoinForm";
//        }
            // 이메일 중복체크는 html 내 ajax로 별도로 호출
//        boolean emailCheck = qMemberService.existsByEmail(memberDTO.getEmail());
//        if(emailCheck) {
//            log.info("이메일 중복입니다.");
//            model.addAttribute("emailCheck", emailCheck);
//            return "redirect:/member/qJoinForm";
//        }

        MemberEntity member = new MemberEntity();
        member.setEmail(memberDTO.getEmail());
        member.setName(memberDTO.getName());
        member.setPwd(memberDTO.getPwd());
        member.setRoot("기본회원");

        model.addAttribute("member",member);

        MemberEntity entity = memberDTO.toEntity();
        log.info("entity : " + entity.toString());

        qMemberService.joinBasic(member);
        log.info("qMemberService.joinBasic(member) 실행 한 후");
        
        // DB save 안돼서 회원전체조회 테스트
//        List<MemberEntity> members = qMemberService.findMembers();
//        log.info("members 값 : {}", members);
//        model.addAttribute("members",members);

        return "redirect:/member/qLoginForm";
    }
    @RequestMapping(value="/testnaverLogin")
    public String textnaver() {
        return "/userView/pjm_naverLogin";
    }
    /** 네이버 로그인 */
    @RequestMapping(value="/qLoginNaver")
    public String qLoginNaver(@RequestParam("code") String code,
                              @RequestParam("state") String state, Model model, HttpSession session) {
        log.info("MemberController - qLoginNaver() 실행");
        log.info("####code : " + code);

        // 1번 인증코드 요청 전달
        String accessToken = qMemberService.getAccessTokenNaver(code);     // code로 토큰 받음
        log.info("accessToken : " + accessToken);

//        // 2번 인증코드로 토큰 전달
        HashMap<String, Object> userInfo = qMemberService.getUserInfo(accessToken);   // 사용자 정보 받음
        log.info("login info : " + userInfo.toString());
        // https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info
        log.info("login userUniqueId : " + userInfo.get("id"));
        log.info("login userEmail : " + userInfo.get("email"));
        log.info("login userName : " + userInfo.get("nickname"));

        if(userInfo.get("email") != null) {
            session.setAttribute("userEmail", userInfo.get("email"));
            session.setAttribute("userName", userInfo.get("nickname"));
            session.setAttribute("accessToken", accessToken);
        }
        model.addAttribute("userEmail", userInfo.get("email"));
        model.addAttribute("userName", userInfo.get("nickname"));

        MemberEntity member = new MemberEntity();
        member.setEmail((String) userInfo.get("email"));  // 비밀번호
        member.setName((String) userInfo.get("nickname"));  // 아이디
        log.info(member.toString());

        qMemberService.joinBasic(member);   // 네이버정보로 회원가입 실행

        return "redirect:/qLoginForm";
    }

    @GetMapping(value="/qLoginNaverCallback")
    public String qLoginNaverCallback(@RequestParam String code) {
        log.info("MemberController - qLoginNaverCallback() 실행");

        return "code : " + code;
//        return "redirect:/qLoginForm";
    }

    /** 네이버 로그아웃 */
    @RequestMapping(value="/qLogoutNaver")
    public String qLogoutNaver(HttpSession session) {
        log.info("MemberController - qLogout() 실행");

        /* token 재사용으로 콘솔에 400에러가 확인되어 주석 처리(정확한 원인 파악 필요) */
//        qMemberServiceImpl.kakaoLogout((String)session.getAttribute("accessToken"));
//        session.removeAttribute("accessToken");
        session.removeAttribute("userEmail");
        session.removeAttribute("userName");

        log.info("로그아웃 완료되었습니다.");  // 로그아웃 후 메인화면 리다이렉트

        return "redirect:/";
    }

    /** 마이페이지 매핑 */
    @RequestMapping(value="/qMyPage")
    public String qMyPage(HttpSession session, Model model) {
        String userEmail = (String) session.getAttribute("userEmail");
        String userName = (String) session.getAttribute("userName");
        model.addAttribute("userEmail",userEmail);
        model.addAttribute("userName",userName);
        return "/userView/oMyPage";
    }

    /** 내정보 수정페이지 매핑 */
    @GetMapping(value="/qMyInfoUpdate")
    public String qMyInfoUpdate(HttpSession session, Model model) {
        String userEmail = (String) session.getAttribute("userEmail");
        String userName = (String) session.getAttribute("userName");

//        MemberDTO findmember = qMemberService.findMemberOne();

        model.addAttribute("userEmail",userEmail);
        model.addAttribute("userName",userName);
//        MemberDTO memberDTO = qMemberService.updateMember(userId);
//        model.addAttribute("memberDTO", memberDTO);
        return "/userView/oMyInfoUpdate";
    }

    /** 내정보 수정하기 */
//    @PatchMapping(value="/qUpdate/{idx}")
//    public Long qUpdate(@PathVariable final Long idx, @RequestBody final MemberDTO memberDTO) {
//        return qMemberService.updateMember(idx, memberDTO);
//    }

    /** 이메일/비밀번호 찾기 페이지 매핑 */
    @RequestMapping(value="/qFindMemberInfo")
    public String qFindMemberInfo() {
        return "/userView/oFindMemberInfo";
    }

//    @GetMapping(value = "/qEmailCheck")
//    public ResponseEntity<Boolean> qEmailCheck(@RequestBody String email) {
//        log.info("email : " + email);
//        qMemberService.existsByEmail(email);
//        log.info(ResponseEntity.ok(qMemberService.existsByEmail(email)).toString());
//
//        return res;
//    }

//    @RequestMapping("/qEmailCheck")
//    public String qEmailCheck(@RequestParam(value="email") String email, Model model) {
//        log.info("email : " + email);
//
//        boolean emailCheck = qMemberService.existsByEmail(email);
//        if(emailCheck) {
//            log.info("이메일 중복입니다.");
//            model.addAttribute("emailCheck", emailCheck);
//
//        }
//        return "redirect:/qLoginForm";
//    }

    // 이메일 중복 체크 (js ajax 활용)
    @PostMapping("/qEmailCheck")
    @ResponseBody
    public int qEmailCheck(@RequestParam("email") String email) {
        int cnt = 0;
        boolean emailCheck = qMemberService.existsByEmail(email);
        cnt = emailCheck ? 1 : 0;
        return cnt;
    }

}
