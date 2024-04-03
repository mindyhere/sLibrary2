package com.example.syLibrary2.user.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.syLibrary2.admin.model.dto.BookDTO;


@Repository
public class SearchDAOImpl implements SearchDAO {

	@Autowired // 의존관계주입. 스프링 객체생성&연결
	SqlSession sqlSession;
	
	@Autowired
	CheckoutDAO checkoutDao;

	@Override
	public List<BookDTO> totSearch(String keyword, int start, int end) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", keyword);
		map.put("start", start);
		map.put("end", end);
		List<BookDTO> list= sqlSession.selectList("search.totSearch", map);
		for (BookDTO dto : list) {
			String b_name = dto.getB_name();
			String b_author = dto.getB_author();
			String b_pub = dto.getB_pub();
			// 키워드 강조(폰트 컬러)
			b_name = b_name.replace(keyword, "<span style='color:crimson'>" + keyword + "</span>");
			b_author = b_author.replace(keyword, "<span style='color:crimson'>" + keyword + "</span>");
			b_pub = b_pub.replace(keyword, "<span style='color:crimson'>" + keyword + "</span>");
			dto.setB_name(b_name);
			dto.setB_author(b_author);
			dto.setB_pub(b_pub);
		}
		return list;
	}

	@Override
	public List<BookDTO> totSearch(String keyword) {
		List<BookDTO> list = sqlSession.selectList("search.simpleSearch", keyword);

		for (BookDTO dto : list) {
			String b_name = dto.getB_name();
			String b_author = dto.getB_author();
			String b_pub = dto.getB_pub();
			// 키워드 강조(폰트 컬러)
			b_name = b_name.replace(keyword, "<span style='color:crimson; font-weight:bold;'>" + keyword + "</span>");
			b_author = b_author.replace(keyword,
					"<span style='color:crimson; font-weight:bold;'>" + keyword + "</span>");
			b_pub = b_pub.replace(keyword, "<span style='color:crimson; font-weight:bold;'>" + keyword + "</span>");
			dto.setB_name(b_name);
			dto.setB_author(b_author);
			dto.setB_pub(b_pub);
		}
		return list;
	}

	@Override
	public List<BookDTO> detailSearch(String b_name, String b_author, String b_pub, int start, int end) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("b_name", b_name);
		map.put("b_author", b_author);
		map.put("b_pub", b_pub);
		map.put("start", start);
		map.put("end", end);
		List<BookDTO> list = sqlSession.selectList("search.detailSearch", map);
		for (BookDTO dto : list) {
			String name = dto.getB_name();
			String author = dto.getB_author();
			String pub = dto.getB_pub();
			// 키워드 강조(폰트 컬러)
			if (b_name != "") {
				name = name.replace(b_name, "<span style='color:crimson'>" + b_name + "</span>");
			}
			dto.setB_name(name);

			if (b_author != "") {
				author = author.replace(b_author, "<span style='color:crimson'>" + b_author + "</span>");
			}
			dto.setB_author(author);

			if (b_pub != "") {
				pub = pub.replace(b_pub, "<span style='color:crimson'>" + b_pub + "</span>");
			}
			dto.setB_pub(pub);
		}
		return list;
	}

	@Override
	public Map<String, Object> countRecords(String keyword) {
		Map<String, Object> map = new HashMap<String, Object>();

		String b_name = keyword;
		int cntName = (sqlSession.selectOne("search.cntName", b_name) != null)
				? sqlSession.selectOne("search.cntName", b_name)
				: 0;

		String b_author = keyword;
		int cntAuthor = (sqlSession.selectOne("search.cntAuthor", b_author) != null)
				? sqlSession.selectOne("search.cntAuthor", b_name)
				: 0;

		String b_pub = keyword;
		int cntPub = (sqlSession.selectOne("search.cntPub", b_pub) != null)
				? sqlSession.selectOne("search.cntPub", b_pub)
				: 0;

		map.put("cntName", cntName);
		map.put("cntAuthor", cntAuthor);
		map.put("cntPub", cntPub);
		return map;
	}

	@Override
	public Map<String, Object> countRecords(String b_name, String b_author, String b_pub) {
		int cntName = 0;
		int cntAuthor = 0;
		int cntPub = 0;
		Map<String, Object> map = new HashMap<String, Object>();

		if (b_name != "") {
			cntName = sqlSession.selectOne("search.cntName", b_name);
		}
		map.put("cntName", cntName);
		if (b_author != "") {
			cntAuthor = sqlSession.selectOne("search.cntAuthor", b_author);
		}
		map.put("cntAuthor", cntAuthor);
		if (b_pub != "") {
			cntPub = sqlSession.selectOne("search.cntPub", b_pub);
		}
		map.put("cntPub", cntPub);
		return map;
	}

	@Override
	public int resultCount(String option, String b_name, String b_author, String b_pub) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("option", option);
		map.put("b_name", b_name);
		map.put("b_author", b_author);
		map.put("b_pub", b_pub);
		return sqlSession.selectOne("search.resultCount", map);

	}

	@Override
	public int resultCount(String option, String keyword) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("option", option);
		map.put("keyword", keyword);
		return sqlSession.selectOne("search.resultCount", map);
	}

	@Override
	public BookDTO showDetails(int b_id) {
		return sqlSession.selectOne("book.edit", b_id);
	}
	
	@Override
	public List<Map<String, Object>> listState(List<BookDTO> list) {
		List<Map<String, Object>> stateinfo = new ArrayList<>();
		for (BookDTO dto : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			String state = checkoutDao.isAvailable(dto.getB_id());
			map.put("b_id", dto.getB_id());
			map.put("state", state);
			stateinfo.add(map);
		}
		return stateinfo;
	}
	
	@Override
	public String fastRetdate(int b_id) {
		String state = "";
		String result = "";
		state = checkoutDao.isAvailable(b_id);
		if (state.equals("n")) {
			result = sqlSession.selectOne("checkout.retDate", b_id);
		}
		return result;
	}

}
