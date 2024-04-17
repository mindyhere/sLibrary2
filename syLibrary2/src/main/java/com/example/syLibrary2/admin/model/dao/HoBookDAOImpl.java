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
	public void state_update(int h_idx, String h_state) {
		Map<String,Object> map=new HashMap<>();
		map.put("h_idx",h_idx);
		map.put("h_state",h_state);
		sqlSession.update("hope.state_update", map);
	}
	
	@Override
	public void cancel_reason(int h_idx,String h_cancel) {
		Map<String,Object> map=new HashMap<>();
		map.put("h_idx",h_idx);
		map.put("h_cancel",h_cancel);
		sqlSession.update("hope.cancel_reason", map);
	}
	
	
	@Override
	public String ins_book(HoBookDTO dto, int ct_num) {
		String result = "";
		Map<String,Object> map=new HashMap<>();
		map.put("dto", dto);
		map.put("ct_num", ct_num);
		try {
			sqlSession.insert("hope.book_insert", map);
			result = "success";
		} catch (Exception e) {
			result = "fail";
		}
		return result;
	}

}
