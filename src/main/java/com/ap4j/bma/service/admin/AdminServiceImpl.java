package com.ap4j.bma.service.admin;

import com.ap4j.bma.model.entity.admin.AdminEntity;
import com.ap4j.bma.model.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminRepository adminRepository;

	@Transactional
	public List<AdminEntity> getAllUsers(){
		return adminRepository.findAll();
	}


	// 아래는 예시 메서드 입니다.
	@Transactional // 트랜잭션 처리
	public void testMethod(AdminEntity something) {
		adminRepository.save(something);
	}

	@Override
	public void addSomething(String something) {

	}
}
