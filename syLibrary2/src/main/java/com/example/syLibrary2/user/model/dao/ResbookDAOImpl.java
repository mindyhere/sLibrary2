package com.example.syLibrary2.user.model.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ResbookDAOImpl implements ResbookDAO {
	

	@Autowired
	SqlSession sqlSession;

	@Override
	public void insert_resbook(Map<String, Object> map) {
		// TODO Auto-generated method stub

	}

	@Override
	public void myReBook(String r_memno) {
		// TODO Auto-generated method stub

	}

	@Override
	public int recheck_book(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int recheck_duplicate(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void res_delete(Map<String, Object> map) {
		// TODO Auto-generated method stub

	}

	@Override
	public void myResDelete(Map<String, Object> map) {
		// TODO Auto-generated method stub

	}

}
