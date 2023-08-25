IMP.init('imp87075225');

function requestPay() {
	IMP.request_pay({
		pg: "kcp.{TC0ONETIME}",
		pay_method: "card",
		merchant_uid: "ORD20180131-0000011",   // 주문번호
		name: "노르웨이 회전 의자",
		amount: 64900,                         // 숫자 타입
		buyer_email: "gildong@gmail.com",
		buyer_name: "홍길동, 윤지현",
		buyer_tel: "010-4242-4242",
		buyer_addr: "서울특별시 강남구 신사동",
		buyer_postcode: "01181"
	}, function (rsp) { // callback
		//rsp.imp_uid 값으로 결제 단건조회 API를 호출하여 결제결과를 판단합니다.
	});
}

/*
주문번호(merchant_uid) 생성 시 유의사항

주문번호는 결제창 요청 시 항상 고유 값으로 채번 되어야 합니다.
결제 완료 이후 결제 위변조 대사 작업시 주문번호를 이용하여 검증이 필요하므로 주문번호는 가맹점 서버에서 고유하게(unique)채번하여 DB 상에 저장해주세요
* */