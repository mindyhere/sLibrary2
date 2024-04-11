package com.example.syLibrary2.admin.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.syLibrary2.admin.model.dto.LoanDTO;
import com.example.syLibrary2.user.model.dto.MemberDTO;

@Repository
public class AdmemberDAOImpl implements AdmemberDAO {
	
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public List<MemberDTO> list_search(String search_option, String search, int start, int end) {
		Map<String, Object> map = new HashMap<>();
		map.put("search_option", search_option);
		map.put("search", search);
		map.put("start", start);
		map.put("end", end);
		return sqlSession.selectList("admember.list",map);
	}
	
	@Override
	public int count(String search_option, String search) {
		Map<String, Object> map = new HashMap<>();
		map.put("search_option", search_option);
		map.put("search", search);
		return sqlSession.selectOne("admember.count", map);
	}

	@Override
	public MemberDTO ad_list_detail(int m_no) {
		return sqlSession.selectOne("admember.ad_detail",m_no);
	}
	
	//-------회원대출상세
	@Override
	public List<LoanDTO> lo_memlist(int m_no) {
		Map<String, Object> map = new HashMap<>();
		map.put("m_no",m_no);
		return sqlSession.selectList("admember.lo_memlist", map);
	}
	
	@Override
	public LoanDTO loan(int m_no) {
		LoanDTO loan = new LoanDTO();
		loan.setL_y(sqlSession.selectOne("admember.loan_y", m_no)); //대출현황 카운트
		loan.setR_y(sqlSession.selectOne("admember.reser_y", m_no)); //예약현황 카운트
		loan.setL_o(sqlSession.selectOne("admember.over", m_no)); //연체확인
		return loan;
	}

}
