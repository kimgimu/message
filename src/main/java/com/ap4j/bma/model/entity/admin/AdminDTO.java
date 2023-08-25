package com.ap4j.bma.model.entity.admin;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {
	private Long id; // 기본 키 값
	private String admin_id; // 어드민 아이디
	private String admin_pw; // 어드민 비밀번호

}
