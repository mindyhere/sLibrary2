package com.example.syLibrary2.admin.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.syLibrary2.user.model.dto.MemberDTO;

@Repository
public class AdmemberDAOImpl implements AdmemberDAO {
	
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public List<MemberDTO> ad_list_search(String search_option, String search, int start, int end) {
		Map<String,Object> map=new HashMap<>();
		map.put("search_option",search_option);
		map.put("search",search);
		map.put("start",start);
		map.put("end",end);
		return sqlSession.selectList("admember.ad_search_list",map);
	}

	@Override
	public MemberDTO ad_list_detail(int m_no) {
		return sqlSession.selectOne("admember.ad_detail",m_no);
	}

	@Override
	public int count() {
		return sqlSession.selectOne("admember.ad_p_count");
	}

	@Override
	public List<MemberDTO> list() {
		return sqlSession.selectList("admember.ad_p_list");
	}

	@Override
	public List<MemberDTO> list(int pageStart, int pageEnd) {
		Map<String, Object> map = new HashMap<>();
		map.put("start", pageStart);
		map.put("end", pageEnd);
		return sqlSession.selectList("admember.ad_p_list", map);
	}

	@Override
	public int search_count(String search_option, String search) {
		Map<String, Object> map = new HashMap<>();
		map.put("search_option", search_option);
		map.put("search", search);
		return sqlSession.selectOne("admember.ad_search_count");
	}

}
