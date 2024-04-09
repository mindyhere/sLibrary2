package com.example.syLibrary2.admin.model.dao;

import java.util.List;

import com.example.syLibrary2.admin.model.dto.LoanDTO;

public interface LoanDAO {
	int count(String search_option, String keyword);
	List<LoanDTO> list(String search_option, String keyword, int start, int end);
	String re_book(int l_num);
}
