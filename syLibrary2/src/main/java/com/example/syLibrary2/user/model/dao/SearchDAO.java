package com.example.syLibrary2.user.model.dao;

import java.util.List;
import java.util.Map;

import com.example.syLibrary2.admin.model.dto.BookDTO;

public interface SearchDAO {
	List<BookDTO> totSearch(String keyword, int start, int end);

	List<BookDTO> totSearch(String keyword);

	List<BookDTO> detailSearch(String b_name, String b_author, String b_pub, int start, int end);

	Map<String, Object> countRecords(String b_name, String b_author, String b_pub);

	int resultCount(String option, String b_name, String b_author, String b_pub);

	int resultCount(String option, String keyword);

	List<Map<String, Object>> listState(List<BookDTO> list);

	Map<String, Object> showDetails(int b_id);

	String fastRetdate(int b_id);

	List<Map<String, Object>> autocomplete(Map<String, Object> params);
}
