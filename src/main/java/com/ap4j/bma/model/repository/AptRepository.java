package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.aptTest.AptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AptRepository extends JpaRepository<AptEntity, Long> {

    /** 카카오지도 API에서 가져온 아파트 이름으로 좌표 저장해줄 때 사용 */
    AptEntity findByAptName(String aptName);

    /** 카카오지도 API에서 가져온 화면 범위(좌표)를 이용하여 쿼리 조회 */
    @Query("SELECT a FROM AptEntity a WHERE a.latitude >= ?1 AND a.latitude <= ?3 AND a.longitude >= ?2 AND a.longitude <= ?4")
    List<AptEntity> findMarkersInBounds(Double southWestLat, Double southWestLng, Double northEastLat, Double northEastLng);



}
