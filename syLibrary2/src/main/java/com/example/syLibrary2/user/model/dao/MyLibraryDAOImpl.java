package com.example.syLibrary2.user.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.syLibrary2.user.model.dto.MyLibraryDTO;

@Repository
public class MyLibraryDAOImpl implements MyLibraryDAO {

	@Autowired
	SqlSession sqlSession;

	// 나의 서재 페이지
	@Override
	public MyLibraryDTO myLibrary(String mId) {
		MyLibraryDTO myLibrary = sqlSession.selectOne("myLibrary.myLibrary_list", mId);
		return myLibrary;
	}

	// 대출 중인 도서
	@Override
	public List<MyLibraryDTO> myLoanBook(int mNo) {
		return sqlSession.selectList("myLibrary.myLoanBook", mNo);
	}

	// 도서 통계-카테고리별
	@Override
	public List<Map<String, Object>> cateChart(int mNo) {
		List<Map<String, Object>> cateChartList = sqlSession.selectList("myLibrary.cateChart", mNo);
		return cateChartList;
	}

	// 도서 통계-반기별
	@Override
	public List<Map<String, Object>> monthChart(int mNo) {
		List<Map<String, Object>> monthChart = sqlSession.selectList("myLibrary.monthChart", mNo);
		return monthChart;
	}

	// 도서 통계-연도별
	@Override
	public List<Map<String, Object>> yearChart(int mNo) {
		List<Map<String, Object>> yearChart = sqlSession.selectList("myLibrary.yearChart", mNo);
		return yearChart;
	}

	// 대출 이력
	@Override
	public List<MyLibraryDTO> myHistory(int mNo) {
		return sqlSession.selectList("myLibrary.myHistory", mNo);
	}

	// 예약 여부 확인 (연장신청)
	@Override
	public int checkReservation(int bId) {
		return sqlSession.selectOne("myLibrary.checkReservation", bId);
	}

	// 동일도서 연장여부 확인(연장신청)
	@Override
	public String checkRenewYn(Map<String, Object> map) {
		String renewYn = sqlSession.selectOne("myLibrary.checkRenewYn", map);
		return renewYn;
	}

	// 연장신청
	@Override
	public void updateReturn(Map<String, Object> map) {
		sqlSession.update("myLibrary.updateReturn", map);
	}

	// 예약 중인 도서

}
