package com.ap4j.bma.model.entity.board;


import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class BoardEntity {

	@Id
//	@GeneratedValue 아래의 괄호 문은 생략해도 됨.
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String title;

	@Column
	private String content;

	@Column
	private String status;

	@Column
	private String writer;

	@Temporal(TemporalType.DATE)
	@Column
	private Date writtenDate;




	public void patch(BoardEntity boardEntity){
		if(boardEntity.title != null){
			this.title = boardEntity.title;
		}
		if(boardEntity.status != null){
			this.status = boardEntity.status;
		}
		if (boardEntity.writer != null) {
			this.writer = boardEntity.writer;
		}
		if (boardEntity.writtenDate != null) {
			this.writtenDate = boardEntity.writtenDate;
		}
		if (boardEntity.content != null) {
			this.content = boardEntity.content;
		}

	}

}
