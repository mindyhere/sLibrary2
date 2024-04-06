package com.example.syLibrary2.user.model.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CheckoutDAOImpl implements CheckoutDAO {
	@Autowired
	SqlSession sqlSession;

	@Override
	public String isAvailable(int b_id) {
		String state = "";
		int b_amount = 3;
		int loCount = sqlSession.selectOne("checkout.loCount", b_id);
		int reCount = sqlSession.selectOne("checkout.reCount", b_id);
		if (b_amount > (loCount + reCount)) {
			return state = "y";
		} else {
			return state = "n";
		}
	}

	@Override
	public int checkMloan(String m_id) {
		// 회원의 대출가능여부: 1(대출가능) or 0(대출불가=패널티 or 대출가능한 책 수=0)
		return sqlSession.selectOne("checkout.check_mLoan", m_id);
	}

	@Override
	public int duplicate(Map<String, Object> map) {
		return sqlSession.selectOne("checkout.check_duplicate", map);
	}

//	@Override
//	public int cntUserLo(String m_id) {
//		int m_level = Integer.parseInt(sqlSession.selectOne("checkout.mlevel_check", m_id));
//		int cnt = 0; // 1인당 대출 가능 도서 권수
//		switch (m_level) {
//		case 1:
//			cnt = 3;
//			break;
//		case 2:
//			cnt = 5;
//			break;
//		case 3:
//			cnt = 7;
//			break;
//		}
//		return cnt - Integer.parseInt(sqlSession.selectOne("checkout.cntUserLo", m_id));
//	}

	@Override
	public void insert(Map<String, Object> map) {
		sqlSession.insert("checkout.insert_Lobook", map); // 대출 Lo_book insert
		sqlSession.update("checkout.call_CheckLoan", map.get("m_id"));
	}

}
