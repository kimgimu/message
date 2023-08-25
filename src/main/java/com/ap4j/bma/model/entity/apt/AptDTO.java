package com.ap4j.bma.model.entity.apt;

import com.ap4j.bma.model.entity.aptTest.AptEntity;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.Column;

@Component
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AptDTO {
    private String aptName;         // 아파트 이름
    private String aptAddress;      // 아파트 주소
    private String kaptCode;        // 아파트 코드
    private String bjdCode;         // 법정동 코드
    private Double latitude;        // 위도
    private Double longitude;       // 경도
}
