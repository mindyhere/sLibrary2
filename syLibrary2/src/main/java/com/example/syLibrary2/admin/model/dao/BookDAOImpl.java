package com.example.syLibrary2.admin.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.syLibrary2.admin.model.dto.BookDTO;
import com.example.syLibrary2.admin.model.dto.CtBookDTO;

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
		String result = "";
		System.out.println("daoimpl"+dto);
		try {
			sqlSession.insert("book.insert", dto);
			result = "success";
		} catch (Exception e) {
			result = "fail";
		}
		return result;
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
		sqlSession.delete("book.delete", b_id);
	}

	@Override
	public String url_cate(int b_id) {
		return sqlSession.selectOne("book.url_cate", b_id);
	}
	
	@Override
	public int ins_ct(String word) {
		return sqlSession.selectOne("book.ins_ct", word);
	}
	
	@Override
	public int ctct(String word) {
		return sqlSession.selectOne("book.ctct", word);
	}
	
	@Override
	public int ct_count() {
		return sqlSession.selectOne("book.ct_count");
	}
	
	@Override
	public List<CtBookDTO> ct_list(int start, int end) {
		Map<String,Object> map=new HashMap<>();
		map.put("start",start);
		map.put("end",end);
		return sqlSession.selectList("book.ct_list",map);
	}
	
	@Override
	public String ins_ct(CtBookDTO dto) {
		String result = "";
		try {
			int check = sqlSession.selectOne("book.check_ct", dto);
			if (check == 0) {
				sqlSession.insert("book.insert_ct", dto);
				result="success";
			} else {
				result="over";
			}
		} catch (Exception e) {
			result="fail";
		}
		return result;
	}
	
	@Override
	public String del_ct(int ct_number) {
		String result = "";
		try {
			sqlSession.delete("book.del_ct", ct_number);
			result="성공";
		} catch (Exception e) {
			result="실패";
		}
		return result;
	}
}
