package com.example.syLibrary2.admin.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.syLibrary2.admin.model.dto.HoBookDTO;

@Repository
public class HoBookDAOImpl implements HoBookDAO {
	@Autowired 
	SqlSession sqlSession;
	
	@Override
	public int count(String search_option, String keyword) {
		Map<String,Object> map=new HashMap<>();
		map.put("search_option",search_option);
		map.put("keyword",keyword);
		return sqlSession.selectOne("hope.count",map);
	}

	@Override
	public List<HoBookDTO> list(int start, int end, String search_option, String keyword) {
		Map<String,Object> map=new HashMap<>();
		map.put("search_option",search_option);
		map.put("keyword",keyword);
		map.put("start",start);
		map.put("end",end);
		return sqlSession.selectList("hope.list",map);
	}

	@Override
	public HoBookDTO detail(int h_idx) {
		return sqlSession.selectOne("hope.detail", h_idx);
	}
	
	@Override
	public void state_update(HoBookDTO dto) {
		sqlSession.update("hope.state_update", dto);
	}
	
	@Override
	public void cancel_reason(HoBookDTO dto) {
		sqlSession.update("hope.cancel_reason", dto);
	}

}
