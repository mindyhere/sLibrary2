package com.example.syLibrary2.user.model.dao;

import com.example.syLibrary2.user.model.dto.LoginDTO;

public interface LoginDAO {

	// 로그인
	String loginChk(String mId, String mpasswd);

	// 아이디-이메일 찾기
	String searchIdEmail(LoginDTO dto);

	// 아이디-전화번호 찾기
	String searchIdTel(LoginDTO dto);

	// 비밀번호-이메일 찾기
	String searchPwEmail(LoginDTO dto);

	// 비밀번호-전화번호 찾기
	String searchPwTel(LoginDTO dto);
}
