package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.board.BoardVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardVO, Integer> {
}
