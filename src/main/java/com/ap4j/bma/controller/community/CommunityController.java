package com.ap4j.bma.controller.community;

import com.ap4j.bma.model.entity.board.BoardVO;
import com.ap4j.bma.service.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;

@Controller
public class CommunityController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/community/write")
    public  String boardWriteForm(){

        return "community/communityWrite";
    }
    @PostMapping("/community/writepro")
    public String boardWritePro(BoardVO board, Model model , MultipartFile file) throws Exception{

        boardService.boardWrite(board, file);
        model.addAttribute("message","글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "list");

        return "community/message";
    }

    @GetMapping("/community/list")
    public  String boardList(Model model , @PageableDefault(page = 0, size =10, sort ="id", direction = Sort.Direction.DESC) Pageable pageable){

        Page<BoardVO> list = boardService.boardList(pageable);

        int nowPage = list.getPageable().getPageNumber();
        int startPage = nowPage - 4;
        int endPage = nowPage + 5;
        model.addAttribute("List", boardService.boardList(pageable));

        return "community/communityList";
    }

    @GetMapping("/community/view") //localhost:8082/board/view?id=1...
    public String boardView(Model model , Integer id){

        model.addAttribute("board" , boardService.boardView(id));
        return "community/communityView";
    }

    @GetMapping("/community/delete")
    public String boardDelete(Integer id){

        boardService.boardDelete(id);

        return "redirect:/community/list";
    }

    @GetMapping("/community/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id,
                              Model model){

        model.addAttribute("board", boardService.boardView(id));
        return "community/communityModify";
    }

    @PostMapping("/community/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, BoardVO board, Model model , MultipartFile file) throws Exception{

        BoardVO boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        model.addAttribute("message","글 수정이 완료되었습니다.");
        model.addAttribute("searchUrl", "/community/list");

        boardService.boardWrite(boardTemp, file);

        return "community/message";

    }
}




