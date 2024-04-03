package com.example.syLibrary2.user.model.dao;

import java.util.List;
import java.util.Map;

import com.example.syLibrary2.admin.model.BookDTO;

public interface SearchDAO {
	// 키워드로 통합검색(도서명, 작가, 출판사)
	List<BookDTO> totSearch(String keyword, int start, int end);

	// 추천도서 편집페이지에서 검색
	List<BookDTO> totSearch(String keyword);

	// 상세검색(도서명, 작가, 출판사)
	List<BookDTO> detailSearch(String b_name, String b_author, String b_pub, int start, int end);

	// 항목별 레코드 개수 구하기
	Map<String, Object> countRecords(String keyword);

	Map<String, Object> countRecords(String b_name, String b_author, String b_pub);

	int resultCount(String option, String b_name, String b_author, String b_pub);

	// resultCount함수 오버로딩 : 검색 결과 레코드 수 계산
	int resultCount(String option, String keyword);

	// 도서 검색 → 도서별 대여가능여부 목록
	List<Map<String, Object>> listState(List<BookDTO> list);

	// 도서 상세정보 가져오기
	BookDTO showDetails(int b_id);

	// 가장 빠른 반납예정일
	String fastRetdate(int b_id);
}
