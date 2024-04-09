package com.example.syLibrary2.admin.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.syLibrary2.admin.model.dto.LoanDTO;

@Repository
public class LoanDAOImpl implements LoanDAO {
	@Autowired 
	SqlSession sqlSession;
	
	@Override
	public int count(String search_option, String keyword) {
		Map<String,Object> map=new HashMap<>();
		map.put("search_option",search_option);
		map.put("keyword",keyword);
		System.out.println(map);
		return sqlSession.selectOne("loanBook.count",map);
	}

	@Override
	public List<LoanDTO> list(String search_option, String keyword, int start, int end) {
		Map<String,Object> map=new HashMap<>();
		map.put("search_option",search_option);
		map.put("keyword",keyword);
		map.put("start",start);
		map.put("end",end);
		return sqlSession.selectList("loanBook.list",map);
	}

	@Override
	public String re_book(int l_num) {
		String result = "success";
		try {
			sqlSession.update("loanBook.re_book", l_num);
			sqlSession.update("loanBook.re_book_check", l_num);
		} catch (Exception e) {
			e.printStackTrace();
			result = "fail";
		}
		return result;
	}

}
