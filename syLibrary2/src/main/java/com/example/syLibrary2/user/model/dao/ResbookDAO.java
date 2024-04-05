package com.example.syLibrary2.user.model.dao;

import java.util.Map;

public interface ResbookDAO {
	void insert_resbook(Map<String, Object> map);
	void myReBook(String r_memno);
	int recheck_book(Map<String, Object> map);
	int recheck_duplicate(Map<String, Object> map);
	void res_delete(Map<String, Object> map);
	void  myResDelete(Map<String, Object> map);



}
