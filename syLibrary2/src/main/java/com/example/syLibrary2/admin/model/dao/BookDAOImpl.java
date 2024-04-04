package com.example.syLibrary2.admin.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.syLibrary2.admin.model.dto.BookDTO;

@Repository
public class BookDAOImpl implements BookDAO {
	@Autowired 
	SqlSession sqlSession;
	
	@Override
	public int count(String search_option, String keyword) {
		Map<String,Object> map=new HashMap<>();
		map.put("search_option",search_option);
		map.put("keyword",keyword);
		return sqlSession.selectOne("book.count",map);
	}

	/*
	 * @Override public int count() { return sqlSession.selectOne("book.count"); }
	 */

	@Override
	public List<BookDTO> list(int start, int end, String search_option, String keyword) {
		Map<String,Object> map=new HashMap<>();
		map.put("search_option",search_option);
		map.put("keyword",keyword);
		map.put("start",start);
		map.put("end",end);
		return sqlSession.selectList("book.list",map);
	}

	@Override
	public List<String> select_cg() {
		return sqlSession.selectList("book.select_cg");
	}

	@Override
	public String insert(BookDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BookDTO edit(int b_id) {
		return sqlSession.selectOne("book.edit", b_id);
	}

	@Override
	public String update(BookDTO dto) {
		String result = "";
		try {
			sqlSession.update("book.update", dto);
			result = "success";
		} catch (Exception e) {
			result = "fail";
		}
		return result;
	}

	@Override
	public void delete(int b_id) {
		// TODO Auto-generated method stub

	}

	/*
	 * @Override public List<BookDTO> list(int start, int end) { Map<String,Object>
	 * map=new HashMap<>(); map.put("start",start); map.put("end",end);
	 * List<BookDTO> list = sqlSession.selectList("book.list", map); return list; }
	 */

	@Override
	public String url_cate(int b_id) {
		return sqlSession.selectOne("book.url_cate", b_id);
	}

}
