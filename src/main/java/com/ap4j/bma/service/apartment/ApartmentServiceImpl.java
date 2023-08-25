package com.ap4j.bma.service.apartment;

import com.ap4j.bma.model.entity.apt.AptDTO;
import com.ap4j.bma.model.entity.aptTest.AptEntity;
import com.ap4j.bma.model.repository.AptRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    @Autowired
    private AptRepository aptRepository;

    /** 전국 아파트 리스트 API 호출 메서드 */
    public String callApi() {
        String serviceKey = "5C%2FnyAagqz6%2F%2BnYRGcZyRNpteaEeTlrNaMf1KtU0CWaSMRID13wEXSHVJ0J7WMvTl864DTzD3rwHM5GPX1aWtA%3D%3D";
        StringBuilder result = new StringBuilder();

        try {
            String apiUrl = "http://apis.data.go.kr/1613000/AptListService2/getTotalAptList?"
                    + "serviceKey=" + serviceKey + "&"
                    + "pageNo=" + 1 + "&"
                    + "numOfRows=" + 10000;

            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(); // 연결
            urlConnection.connect();

            BufferedInputStream bufferedInputStream = new BufferedInputStream((urlConnection.getInputStream()));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));

            String returnLine;

            while ((returnLine = bufferedReader.readLine()) != null) {
                result.append(returnLine + "\n");
            }

            urlConnection.disconnect();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /** API -> DB 저장 메서드 */
    public void init(String jsonData) {
        // 필요한 데이터에 접근
        JSONObject jsonObject = XML.toJSONObject(jsonData.toString());
        JSONObject aptJson = jsonObject.getJSONObject("response").getJSONObject("body").getJSONObject("items");
        JSONArray jsonArray = aptJson.getJSONArray("item");

        // 루프 돌려서 JSON데이터를 DB에 저장
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            // optString 'opt'는 값이 없으면 null을 반환한다.
            String aptName = obj.getString("kaptName");
            String kaptCode = obj.optString("kaptCode");
            String bjdCode = obj.optString("bjdCode");
            String aptAddress1 = obj.optString("as1");
            String aptAddress2 = obj.optString("as2");
            String aptAddress3 = obj.optString("as3");
            String aptAddress4 = obj.optString("as4");
            String fullAptAddress = aptAddress1 + " " + aptAddress2 + " " + aptAddress3 + " " + aptAddress4;

            AptEntity aptEntity = AptEntity.builder()
                    .aptName(aptName)
                    .aptAddress(fullAptAddress)
                    .kaptCode(kaptCode)
                    .bjdCode(bjdCode)
                    .build();

            aptRepository.save(aptEntity);
        }
    }

    /** DB값 가져와서 html에 넘겨주기 (경도 위도 검색해서 값 가져오기 위해서) */
    public List<AptDTO> aptList() {
        List<AptDTO> aptDTOList = new ArrayList<>();
        List<AptEntity> aptEntityList = aptRepository.findAll();

        for (AptEntity aptEntity : aptEntityList) {
            AptDTO aptDTO = AptDTO.builder().
                            aptName(aptEntity.getAptName()).
                            aptAddress(aptEntity.getAptAddress()).
                            kaptCode(aptEntity.getKaptCode()).
                            bjdCode(aptEntity.getBjdCode()).
                            build();
            aptDTOList.add(aptDTO);
        }
        return aptDTOList;
    }

    /** 가져온 좌표값 DB에 저장하기 */
    @Transactional
    public void updateCoordinatesByAptName(String aptName, Double latitude, Double longitude) {
        AptEntity aptEntity = aptRepository.findByAptName(aptName);
        if (aptEntity != null) {
            AptEntity updateAptEntity = AptEntity.builder()
                    .id(aptEntity.getId())
                    .aptName(aptEntity.getAptName())
                    .aptAddress(aptEntity.getAptAddress())
                    .kaptCode(aptEntity.getKaptCode())
                    .bjdCode(aptEntity.getBjdCode())
                    .latitude(latitude)
                    .longitude(longitude)
                    .build();
            aptRepository.save(updateAptEntity);
        }
    }

    public List<AptDTO> findMarkersInBounds(Double southWestLat, Double southWestLng, Double northEastLat, Double northEastLng) {

        List<AptDTO> aptDTOList = new ArrayList<>();
        List<AptEntity> aptEntityList = aptRepository.findMarkersInBounds(southWestLat, southWestLng, northEastLat, northEastLng);

        for (AptEntity aptEntity : aptEntityList) {
            AptDTO aptDTO = AptDTO.builder().
                    aptName(aptEntity.getAptName()).
                    aptAddress(aptEntity.getAptAddress()).
                    kaptCode(aptEntity.getKaptCode()).
                    bjdCode(aptEntity.getBjdCode()).
                    latitude(aptEntity.getLatitude()).
                    longitude(aptEntity.getLongitude()).
                    build();
            aptDTOList.add(aptDTO);
        }
        return aptDTOList;

    }





//    @Override
//    public ArrayList<AptDTO> getAptLists() {
//
//        String serviceKey = "5C%2FnyAagqz6%2F%2BnYRGcZyRNpteaEeTlrNaMf1KtU0CWaSMRID13wEXSHVJ0J7WMvTl864DTzD3rwHM5GPX1aWtA%3D%3D";
//
//        ArrayList<AptDTO> aptList = new ArrayList<AptDTO>();
//        StringBuilder result = new StringBuilder();
//
//        try {
//            String apiUrl = "http://apis.data.go.kr/1613000/AptListService2/getTotalAptList?"
//                    + "serviceKey=" + serviceKey + "&"
//                    + "pageNo=" + 1 + "&"
//                    + "numOfRows=" + 1000;
//
//            URL url = new URL(apiUrl);
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(); // 연결
//            urlConnection.connect();
//
//            BufferedInputStream bufferedInputStream = new BufferedInputStream((urlConnection.getInputStream()));
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
//
//            String returnLine;
//
//            while ((returnLine = bufferedReader.readLine()) != null) {
//                result.append(returnLine + "\n");
//            }
//
//            JSONObject jsonObject = XML.toJSONObject(result.toString());
//
//            // 필요한 데이터에 접근
//            JSONObject aptJson = jsonObject.getJSONObject("response").getJSONObject("body").getJSONObject("items");
//            JSONArray jsonArray = aptJson.getJSONArray("item");
//
//            System.out.println("jsonArray = " + jsonArray);
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject obj = jsonArray.getJSONObject(i);
//                // optString 'opt'는 값이 없으면 null을 반환한다.
//                String aptName = obj.getString("kaptName");
//                String aptAddress1 = obj.optString("as1");
//                String aptAddress2 = obj.optString("as2");
//                String aptAddress3 = obj.optString("as3");
//                String aptAddress4 = obj.optString("as4");
//                String kaptCode = obj.optString("kaptCode");
//                String bjdCode = obj.optString("bjdCode");
//
//                String fullAptAddress = aptAddress1 + " " + aptAddress2 + " " + aptAddress3 + " " + aptAddress4;
//
//
//                AptDTO apt = new AptDTO(aptName, fullAptAddress);
//
//                aptList.add(apt);
//            }
//
//            System.out.println(aptList);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return aptList;
//    }

}


//try {
//		String apiUrl = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev?"
//		+ "serviceKey=" + serviceKey + "&"
//		+ "LAWD_CD=" + startLaCode + "&"
//		+ "DEAL_YMD=" + startDate;
//
//		URL url = new URL(apiUrl);
//
//		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(); // 연결
//
//		urlConnection.connect();
//
//		BufferedInputStream bufferedInputStream = new BufferedInputStream((urlConnection.getInputStream()));
//
//		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
//
//		String returnLine;
//
//		while ((returnLine = bufferedReader.readLine()) != null) {
//		result.append(returnLine + "\n");
//		}
//
//		JSONObject jsonObject = XML.toJSONObject(result.toString());
//
//		System.out.println("jsonObject = " + jsonObject);
//
//		// 필요한 데이터에 접근
//		JSONObject aptJson = jsonObject.getJSONObject("response").getJSONObject("body").getJSONObject("items");
//
//		JSONArray jsonArray = aptJson.getJSONArray("item");
//
//		aptList = new ArrayList<AptDTO>();
//
//		for (int i = 0; i < jsonArray.length(); i++) {
//		JSONObject obj = jsonArray.getJSONObject(i);
//
//		String aptName = obj.getString("아파트");
//		String aptCityAddress = obj.getString("중개사소재지");
//		String aptAddress1 = obj.getString("법정동");
//
//		Object aptAddressValue = obj.get("지번");
//		String aptAddress2 = ""; // 초기화
//		if (aptAddressValue instanceof String) {
//		aptAddress2 = (String) aptAddressValue;
//		} else if (aptAddressValue instanceof Number) {
//		aptAddress2 = String.valueOf(aptAddressValue);
//		}
//
//		String aptDealAmount = obj.getString("거래금액");
//
//		AptDTO apt = new AptDTO(aptName, aptCityAddress, aptAddress1, aptAddress2, aptDealAmount);
//
//		aptList.add(apt);
//		}
