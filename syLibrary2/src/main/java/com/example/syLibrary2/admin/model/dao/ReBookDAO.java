package com.example.syLibrary2.admin.model.dao;

import java.util.List;

import com.example.syLibrary2.admin.model.dto.ReBookDTO;

public interface ReBookDAO {
	List<ReBookDTO> list_search(String search_option, String search, int start, int end);
	int count(String search_option, String search);
	List<ReBookDTO> list(int start, int end);
	int count();
}
