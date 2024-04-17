package com.example.syLibrary2.admin.model.dao;

import java.util.List;

import com.example.syLibrary2.admin.model.dto.BookDTO;
import com.example.syLibrary2.admin.model.dto.CtBookDTO;

public interface BookDAO {
	int count(String search_option, String keyword);
	List<BookDTO> list(int start, int end, String search_option, String keyword);
	List<String> select_cg();
	String insert(BookDTO dto);
	BookDTO edit(int b_id);
	String update(BookDTO dto);
	void delete(int b_id);
	String url_cate(int b_id);
	int ins_ct(String word);
	int ct_count();
	List<CtBookDTO> ct_list(int start, int end);
	String ins_ct(CtBookDTO dto);
	String del_ct(int ct_number);
	int ctct(String h_category);
}
