package com.example.syLibrary2.admin.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookDAOImpl implements BookDAO {
	@Autowired 
	SqlSession sqlSession;
	
	@Override
	public int count(String search_option, String keyword) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int count() {
		return sqlSession.selectOne("book.count");	
	}

	@Override
	public List<BookDTO> list_search(String search_option, String keyword, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookDTO> select_cg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insert(BookDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BookDTO edit(int b_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(BookDTO dto, String dto_category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(int b_id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<BookDTO> list(int start, int end) {
		Map<String,Object> map=new HashMap<>();
		map.put("start",start);
		map.put("end",end);
		List<BookDTO> list = sqlSession.selectList("book.list", map);
		return list;
	}

	@Override
	public String url_cate(int b_id) {
		// TODO Auto-generated method stub
		return null;
	}

}
