package com.example.syLibrary2.admin.model.dao;

import java.util.List;

import com.example.syLibrary2.admin.model.dto.HoBookDTO;

public interface HoBookDAO {
	int count(String search_option, String keyword);
	List<HoBookDTO> list(int start, int end, String search_option, String keyword);
	HoBookDTO detail(int h_idx);
	void state_update(HoBookDTO dto);
	void cancel_reason(HoBookDTO dto);
}
