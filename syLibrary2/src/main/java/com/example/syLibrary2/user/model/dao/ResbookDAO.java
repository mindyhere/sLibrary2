package com.example.syLibrary2.user.model.dao;



import java.util.List;
import java.util.Map;

import com.example.syLibrary2.admin.model.dto.ReBookDTO;

public interface ResbookDAO {
	void insert_book(Map<String, Object> map);
	int recheck_book(Map<String, Object> map);
	int recheck_level(Map<String, Object> map);
	int recheck_duplicate(Map<String, Object> map);
	void res_delete(Map<String, Object> map);
	void myResDelete(Map<String, Object> map);
	List<ReBookDTO> myReBook(String r_memno);
}