package com.ap4j.bma.service.board;

import com.ap4j.bma.model.entity.board.BoardVO;
import com.ap4j.bma.model.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;


import java.io.File;
import java.util.UUID;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	//게시글 작성 처리
	public void boardWrite(BoardVO board , MultipartFile file) throws Exception{

		//파일 저장 처리
		String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
		UUID uuid = UUID.randomUUID();
		String fileName = uuid + "_" + file.getOriginalFilename();
		File savaFile = new File(projectPath, fileName);
		file.transferTo(savaFile);

		//파일을 DB에 저장
		//board.setFilename(fileName);
		//board.setFilepath("/files/" + fileName);

		boardRepository.save(board);
	}

	//게시글 리스트 처리
	public Page<BoardVO> boardList(Pageable pageable){

		return boardRepository.findAll(pageable);
	}

	//특정 게시글 불러오기
	public BoardVO boardView(Integer id){

		return boardRepository.findById(id).get();
	}

	//특정 게시물 삭제

	public void boardDelete(Integer id){

		boardRepository.deleteById(id);
	}

}
