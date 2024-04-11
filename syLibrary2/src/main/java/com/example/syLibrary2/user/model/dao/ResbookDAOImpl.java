package com.example.syLibrary2.user.model.dao;

import java.util.HashMap;
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
	private SqlSession sqlSession;

	@Override // 예약신청
	public void insert_book(Map<String, Object> map) {
		System.out.println("map 확인=" + map);
		sqlSession.insert("resbook.insert_book", map);
	}

	@Override // 예약목록
	public List<ReBookDTO> myReBook(@RequestParam("mId") String r_memno) {
		return sqlSession.selectList("resbook.myReBook", r_memno);
	}

	@Override // 예약도서 권수 표시
	public int recheck_book(Map<String, Object> map) {
		return sqlSession.selectOne("resbook.recheck_book", map);
	}

	@Override // 중복예약 확인
	public int recheck_duplicate(Map<String, Object> map) {
		int dupCnt; // 파라미터 초기화
		
		// 같은 도서를 중복으로 예약신청 했는지 확인
		int dupCnt1 = sqlSession.selectOne("resbook.recheck_duplicate", map);

		// 현재 대출 중인 책을 예약신청 했는지 확인
		Map<String, Object> params = new HashMap<>();
		params.put("m_id", map.get("m_id"));
		params.put("b_id", map.get("r_bookid"));
		int dupCnt2 = sqlSession.selectOne("checkout.check_duplicate", params);
		if (dupCnt1 == 1 || dupCnt2 == 1) {
			dupCnt = 1; // 예약 또는 대출에서 중복신청이 있는 경우
		} else {
			dupCnt = 0; // 예약 또는 대출에서 중복신청이 없는 경우
		}
		return dupCnt;
	}

	@Override // 예약취소
	public void res_delete(Map<String, Object> map) {
		sqlSession.delete("resbook.res_delete", map);
	}

	@Override // 예약 차례로 대출 후 예약목록에서 삭제
	public void myResDelete(Map<String, Object> map) {
		sqlSession.delete("resbook.myres_delete", map);

	}
}
