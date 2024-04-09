package com.example.syLibrary2.user.model.dao;

import java.util.Map;

public interface CheckoutDAO {
	// (b_id 1개 당)도서 대출가능여부(소장상태): 대여가능(Y) or 대여불가 (N)
	String isAvailable(int b_id);

	// 회원의 대출가능여부: mloan 상태 확인
	int checkMloan(String m_id);
	
	// 중복요청 여부 확인
	int duplicate(Map<String, Object> map);
	
	// 도서 대출 신청(db insert)
	void insert(Map<String, Object> map);

}
