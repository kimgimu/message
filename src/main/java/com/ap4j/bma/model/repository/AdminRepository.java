package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.admin.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// JPA 전용 인터페이스
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
//	@Query("SELECT admin_id FROM admin ORDER BY admin.name")
//	@Transactional(readOnly = true)
//	List<AdminEntity> findUserList(); // 사용 예시 입니다.
}
