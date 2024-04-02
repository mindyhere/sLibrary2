package com.example.syLibrary2.user.model.dao;

public interface CheckoutDAO {
	// (b_id 1개 당)도서 대출가능여부(소장상태): 대여가능(Y) or 대여불가 (N)
	String isAvailable(int b_id);

	// 회원의 대출가능여부: mloan 상태 & 이미 대출상태인 책 확인
	int checkMloan(String m_id, int b_id);

	// (m_id 1개 당) 현재 대출가능한 도서 개수
	int cntUserLo(String m_id);

	// 도서 대출 신청(db insert)
	String checkout(String m_id, int b_id);

	// 가장 빠른 반납예정일
	String fastRetdate(int b_id);
}
