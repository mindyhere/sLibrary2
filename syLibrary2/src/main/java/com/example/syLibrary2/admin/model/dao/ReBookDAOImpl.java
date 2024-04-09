package com.example.syLibrary2.admin.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.syLibrary2.admin.model.dto.ReBookDTO;

@Repository
public class ReBookDAOImpl implements ReBookDAO {
	
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public List<ReBookDTO> list(String search_option, String search, int start, int end) {
		Map<String,Object> map=new HashMap<>();
		map.put("search_option",search_option);
		map.put("search",search);
		map.put("start",start);
		map.put("end",end);
		return sqlSession.selectList("rebook.list",map);
	}
	
	@Override
	public int count(String search_option, String search) {
		Map<String, Object> map = new HashMap<>();
		map.put("search_option", search_option);
		map.put("search", search);
		return sqlSession.selectOne("rebook.count",map);
	}
}