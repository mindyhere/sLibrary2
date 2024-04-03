package com.example.syLibrary2.admin.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.syLibrary2.admin.model.dto.ReBookDTO;

@Repository
public class ReBookDAOImpl {
	
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public
	
	List<ReBookDTO> list_search(String search_option, String search, int start, int end);
	int count(String search_option, String search);
	List<ReBookDTO> list(int start, int end);
	int count();
}