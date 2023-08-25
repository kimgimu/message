package com.ap4j.bma.service.apartment;

import com.ap4j.bma.model.entity.apt.AptDTO;

import java.util.ArrayList;

public interface ApartmentService {

//	public ArrayList<AptDTO> getAptLists();

	/** 전국 아파트 리스트 호출 */
	public String callApi();

	/** 호출한 아파트 리스트 DB 저장 */
	public void init(String jsonData);
}
