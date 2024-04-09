package com.example.syLibrary2.user.model.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.syLibrary2.admin.model.dto.ReBookDTO;


@Repository
public class ResbookDAOImpl implements ResbookDAO {
	

	@Autowired
	SqlSession sqlSession;

	@Override  //예약신청
	public void insert_book(Map<String, Object> map) {
		sqlSession.insert("resbook.insert_book", map);
		sqlSession.commit();

	}
	
	@Override
	  public List<ReBookDTO> myReBook(@RequestParam("mId") String r_memno) { 
		return sqlSession.selectList("resbook.myReBook", r_memno);
	 }


	@Override //예약도서 권수 표시
	public int recheck_book(Map<String, Object> map) {
		 return sqlSession.selectOne("resbook.recheck_book", map);
	
	}

	@Override //중복예약 확인 
	public int rechech_duplicate(Map<String, Object> map) {
		return sqlSession.selectOne("resbook.recheck_duplicate", map);
		
	}

	@Override //예약취소
	public void res_delete(Map<String, Object> map) {
		sqlSession.delete("resbook.res_delete", map);
	}

	@Override //예약 차례로 대출 후 예약목록에서 삭제 
	public void myres_delete(Map<String, Object> map) {
		sqlSession.delete("resbook.myres_delete", map);

	}

	

}
