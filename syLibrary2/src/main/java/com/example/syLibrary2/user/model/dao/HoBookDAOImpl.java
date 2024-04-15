package com.example.syLibrary2.user.model.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.syLibrary2.admin.model.dto.HoBookDTO;

@Repository
public class HoBookDAOImpl implements HoBookDAO {
	@Autowired
	SqlSession sqlSession;

	// 희망도서 신청(HoBook 테이블 insert)
	public String insert(HoBookDTO dto) {
		String result = "";
		System.out.println("222페이지 링크 확인=HoBookDAOImpl");
		try {
			sqlSession.insert("request.insert", dto);
			result = "등록되었습니다.";
		} catch (Exception e) {
			e.printStackTrace();
			result = "Not possible";
		}
		System.out.println("333 result=" + result);
		return result;
	}
}
