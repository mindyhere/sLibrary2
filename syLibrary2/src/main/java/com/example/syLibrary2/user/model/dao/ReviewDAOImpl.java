package com.example.syLibrary2.user.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewDAOImpl implements ReviewDAO {
	@Autowired
	SqlSession sqlSession;

	@Override
	public List<Map<String, Object>> getReviews(int b_id) {
		return sqlSession.selectList("review.getReviews", b_id);
	}

	@Override
	public String insert(Map<String, Object> map) {
		String result = "";
		try {
			sqlSession.insert("review.review_insert", map);
			result = "등록되었습니다.";
		} catch (Exception e) {
			e.printStackTrace();
			result = "Not possible";
		}
		return result;
	}

	@Override
	public void delete(Map<String, Object> map) {
		sqlSession.delete("review.user_delete", map);
	}

	@Override
	public void delete(int idx) {
		sqlSession.delete("review.admin_delete", idx);
	}

	@Override
	public List<Map<String, Object>> search(Map<String, Object> map) {
		return sqlSession.selectList("review.search", map);
	}

	@Override
	public List<Map<String, Object>> searchAll(String keyword) {
		return sqlSession.selectList("review.searchAll", keyword);
	}

}
