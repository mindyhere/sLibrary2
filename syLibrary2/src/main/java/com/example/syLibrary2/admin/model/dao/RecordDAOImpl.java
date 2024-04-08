package com.example.syLibrary2.admin.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.syLibrary2.admin.model.dto.RecordDTO;

@Repository
public class RecordDAOImpl implements RecordDAO {
	@Autowired 
	SqlSession sqlSession;
	
	@Override
	public int count(String search_option, String keyword) {
		Map<String,Object> map=new HashMap<>();
		map.put("search_option",search_option);
		map.put("keyword",keyword);
		return sqlSession.selectOne("record.count",map);
	}

	@Override
	public List<RecordDTO> list(int start, int end, String search_option, String keyword) {
		Map<String,Object> map=new HashMap<>();
		map.put("search_option",search_option);
		map.put("keyword",keyword);
		map.put("start",start);
		map.put("end",end);
		return sqlSession.selectList("record.list",map);
	}

	@Override
	public List<RecordDTO> order(int start, int end, String search_option, String keyword) {
		Map<String,Object> map=new HashMap<>();
		map.put("search_option",search_option);
		map.put("keyword",keyword);
		map.put("start",start);
		map.put("end",end);
		return sqlSession.selectList("record.order",map);
	}

	@Override
	public String member_email(int l_num) {
		return sqlSession.selectOne("record.email", l_num);
	}

}
