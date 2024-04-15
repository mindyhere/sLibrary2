package com.example.syLibrary2.user.model.dao;

import java.util.List;
import java.util.Map;

import com.example.syLibrary2.admin.model.dto.HoBookDTO;
import com.example.syLibrary2.user.model.dto.MyLibraryDTO;

public interface MyLibraryDAO {

	// 나의 서재
	MyLibraryDTO myLibrary(String mId);

	// 도서 통계-카테고리별
	List<Map<String, Object>> cateChart(int mNo);

	// 도서 통계-반기별
	List<Map<String, Object>> monthChart(int mNo);

	// 도서 통계-연도별
	List<Map<String, Object>> yearChart(int mNo);

	// 대출 중인 도서
	List<MyLibraryDTO> myLoanBook(int mNo);

	// 예약 여부 확인
	int checkReservation(int bId);
 
	// 연장 여부 확인
	String checkRenewYn(Map<String, Object> map);

	// 연체 여부 확인
	int checkOverReturn(int mNo);

	// 연장 신청
	void updateReturn(Map<String, Object> map);

	// 예약 중인 도서

	// 대출이력
	List<MyLibraryDTO> myHistory(int mNo);
	
	// 희망도서 신청 내역
	List<HoBookDTO> myHopeBook(String mId);
	
	// 희망도서 신청 취소
	void cancelHopeBook(Map<String, Object> map);

}
