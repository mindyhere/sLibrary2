package com.example.syLibrary2.admin.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.syLibrary2.admin.model.dto.BookDTO;

@Repository
public class RecommendDAOImpl implements RecommendDAO {

	@Autowired
	SqlSession sqlSession;

	@Override
	public List<BookDTO> getList(String option) {
		List<BookDTO> list = new ArrayList<>();
		switch (option) {
		case "opt1":
			list = sqlSession.selectList("recommend.opt1");
			break;

		case "opt2":
			list = sqlSession.selectList("recommend.opt2");

			break;
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getList() {
		return sqlSession.selectList("recommend.opt3");
	}

	@Override
	public String insert(String a_id, int b_id) {
		String result = "";
		try {
			int preCount = sqlSession.selectOne("recommend.pre_count");
			int preCheck = sqlSession.selectOne("recommend.pre_check", b_id);
			if (preCount < 5 && preCheck == 0) {
				Map<String, Object> map = new HashMap<>();
				map.put("a_id", a_id);
				map.put("b_id", b_id);
				sqlSession.insert("recommend.recmd_insert", map);
				result = "등록되었습니다.";
			} else {
				result = "Not possible";
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "Error";
		}
		return result;
	}

	@Override
	public String delete(String option) {
		String result = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("option", option);
			sqlSession.delete("recommend.delete", map);
			sqlSession.commit();
			result = "삭제되었습니다.";
		} catch (Exception e) {
			result = "Error";
			e.printStackTrace();
		} 
		return result;
	}

	@Override
	public String delete(String option, int idx) {
		String result = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("option", option);
			map.put("idx", idx);
			sqlSession.delete("recommend.delete", map);
			result = "삭제되었습니다.";
		} catch (Exception e) {
			e.printStackTrace();
			result = "Error";
		}
		return result;
	}

}
