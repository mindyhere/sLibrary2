package com.example.syLibrary2.user.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewDAOImpl implements ReviewDAO {
	@Autowired // 의존관계주입. 스프링 객체생성&연결
	SqlSession sqlSession;

	@Override
	public List<Map<String, Object>> getReviews(int b_id) {
		return sqlSession.selectList("review.getReviews", b_id);
	}

	@Override
	public List<Map<String, Object>> totalList() {
		List<Map<String, Object>> reviews = sqlSession.selectList("review.searchAll", "");
		return reviews;
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
	public List<Map<String, Object>> search(String searchOpt, String keyword) {
		List<Map<String, Object>> list = null;
		if (searchOpt.equals("all")) {
			list = sqlSession.selectList("review.searchAll", keyword);
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("searchOpt", searchOpt);
			map.put("keyword", keyword);
			list = sqlSession.selectList("review.search", map);
		}
		return list;
	}

}
