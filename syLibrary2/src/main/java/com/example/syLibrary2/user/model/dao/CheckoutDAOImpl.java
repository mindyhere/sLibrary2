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
	public void checkMloan(Map<String, Object> param) {
		sqlSession.selectOne("checkout.check_mLoan", param);
	}

	@Override
	public int duplicate(Map<String, Object> map) {
		return sqlSession.selectOne("checkout.check_duplicate", map);
	}

	@Override
	public void insert(Map<String, Object> map) {
		sqlSession.insert("checkout.insert_Lobook", map); 
		sqlSession.update("checkout.call_checkLevel", map.get("m_id")); 
	}
}
