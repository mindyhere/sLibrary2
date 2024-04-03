package com.example.syLibrary2.admin.model.dao;

import java.util.List;

import com.example.syLibrary2.admin.model.dto.BookDTO;

public interface BookDAO {
	int count(String search_option, String keyword);
	int count();
	List<BookDTO> list(int start, int end, String search_option, String keyword);
	List<BookDTO> select_cg();
	String insert(BookDTO dto);
	BookDTO edit(int b_id);
	String update(BookDTO dto, String dto_category);
	void delete(int b_id);
	// List<BookDTO> list(int pageStart, int pageEnd);
	String url_cate(int b_id);
}
