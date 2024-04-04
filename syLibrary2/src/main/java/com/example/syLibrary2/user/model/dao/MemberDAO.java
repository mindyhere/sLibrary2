package com.example.syLibrary2.user.model.dao;

import com.example.syLibrary2.user.model.dto.MemberDTO;

public interface MemberDAO {
     void insert_join(MemberDTO dto); //회원가입 
	 MemberDTO detailMember(String m_Id); // 회원조회
	 void edit_memberInfo(MemberDTO dto); // 회원정보 수정
	 int checkId(String m_id);// 회원아이디 중복 체크
	}  


