package com.example.syLibrary2.admin.model;

import java.util.List;

public interface BookDAO {
	int count(String search_option, String keyword);
	int count();
	List<BookDTO> list_search(String search_option, String keyword, int start, int end);
	List<BookDTO> select_cg();
	String insert(BookDTO dto);
	BookDTO edit(int b_id);
	String update(BookDTO dto, String dto_category);
	void delete(int b_id);
	List<BookDTO> list(int pageStart, int pageEnd);
	String url_cate(int b_id);
}
