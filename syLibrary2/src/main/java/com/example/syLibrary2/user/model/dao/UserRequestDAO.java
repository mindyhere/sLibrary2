package com.example.syLibrary2.user.model.dao;

import com.example.syLibrary2.admin.model.dto.HoBookDTO;

public interface UserRequestDAO {
	// 희망도서 신청(HoBook 테이블 insert)
	String insert(HoBookDTO dto);
}
