package com.example.syLibrary2.user.model.dao;

import java.util.List;
import java.util.Map;

public interface ReviewDAO {
	// 도서별 리뷰 목록 조회
	List<Map<String, Object>> getReviews(int b_id);

	// 리뷰 테이블 전체 목록 조회
	//List<Map<String, Object>> totalList();

	String insert(Map<String, Object> map);

	// 회원이 삭제할 경우
	void delete(Map<String, Object> map);

	// 관리자가 삭제할경우
	void delete(int idx);

	List<Map<String, Object>> search(Map<String, Object> map);
	List<Map<String, Object>> searchAll(String keyword);
}
