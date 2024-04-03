package com.example.syLibrary2.user.model.dao;

import java.util.HashMap;
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
	public int checkMloan(String m_id, int b_id) {
		int check = 1; // 초기화. 1(대출가능) or 0(대출불가)
		try {
			// 회원의 대출가능여부: 1(대출가능) or 0(대출불가=패널티 or 대출가능한 책 수=0)
			int m_loan = sqlSession.selectOne("checkout.check_mLoan", m_id);
			switch (m_loan) {
			case 1: // 대출 중복신청 여부 확인
				Map<String, Object> map = new HashMap<>();
				map.put("m_id", m_id);
				map.put("b_id", b_id);
				int duplicate = sqlSession.selectOne("checkout.check_duplicate", map);
				check = duplicate > 0 ? 0 : 1;
				break;
			case 0:
				check = 0;
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			check = 0;
		}
		return check;
	}

	@Override
	public int cntUserLo(String m_id) {
		int cnt = 5;
		return cnt - Integer.parseInt(sqlSession.selectOne("checkout.cntUserLo", m_id));
	}

	@Override
	public String checkout(String m_id, int b_id) {
		// TODO Auto-generated method stub
		String result = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("m_id", m_id);
			map.put("b_id", b_id);
			sqlSession.insert("checkout.insert_Lobook", map); // 대출 Lo_book insert
			sqlSession.update("checkout.call_CheckLoan", m_id);
			sqlSession.commit();
			result = "신청이 완료되었습니다";
		} catch (Exception e) {
			e.printStackTrace();
			result = "Not possible";
		}
		return null;
	}

}
