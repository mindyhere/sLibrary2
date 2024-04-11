package com.example.syLibrary2.user.model.dao;

import com.example.syLibrary2.user.model.dto.MemberDTO;

public interface MemberDAO {
	// 회원가입
	void insert_join(MemberDTO dto);

	// 회원정보 상세페이지
	MemberDTO detailMember(String mId);

	// 회원정보 수정
	void edit_memberInfo(MemberDTO dto);

	// 회원아이디 중복 체크
	int id_check(String m_id);
} 
