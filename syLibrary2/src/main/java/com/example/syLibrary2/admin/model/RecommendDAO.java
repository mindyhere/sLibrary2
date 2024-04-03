package com.example.syLibrary2.admin.model;

import java.util.List;
import java.util.Map;

public interface RecommendDAO {
	// 사용자 탭 선택 → 목록 가져오기
	List<BookDTO> getList(String option);
	
	// 관리자, edit탭 → 추천도서 목록 가져오기		
	List<Map<String, Object>> getList();
	
	String insert(String a_id, int b_id);
	
	// 전체 레코드 삭제
	String delete(String option);

	// 선택 레코드 삭제
	String delete(String option, int idx);
}
