package com.ap4j.bma.model.entity.admin;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "admin") // 명시적으로 테이블 이름 지정.
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 기본 키

	private String admin_id; // 어드민 아이디
	private String admin_pw; // 어드민 비밀번호
}
