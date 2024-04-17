package com.example.syLibrary2.admin.model.dao;

import java.util.List;
import java.util.Map;

import com.example.syLibrary2.admin.model.dto.BookDTO;


public interface RecommendDAO {
	List<BookDTO> getList(String option);
	
	List<Map<String, Object>> getList();
	
	String insert(String a_id, int b_id);
	
	void delete(String option);

	void delete(String option, int idx);
}
