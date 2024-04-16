package com.example.syLibrary2.user.model.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.syLibrary2.admin.model.dto.HoBookDTO;

@Repository
public class UserRequestDAOImpl implements UserRequestDAO {
	@Autowired
	SqlSession sqlSession;

	// 희망도서 신청(HoBook 테이블 insert)
	public String insert(HoBookDTO dto) {
		String result = "";
		System.out.println(dto);
		try {
			sqlSession.insert("request.insert", dto);
			result = "신청완료";
		} catch (Exception e) {
			e.printStackTrace();
			result = "Not possible";
		}
		System.out.println("333 result=" + result);
		return result;
	}
}
