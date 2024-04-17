package com.example.syLibrary2.admin.model.dao;

import java.util.List;

import com.example.syLibrary2.admin.model.dto.LoanDTO;
import com.example.syLibrary2.user.model.dto.MemberDTO;

public interface AdmemberDAO {
	MemberDTO ad_list_detail(int m_no);
	List<MemberDTO> list_search(String search_option, String search, int start, int end);
	int count(String search_option, String search);
	//-----회원대출상세
	List<LoanDTO> lo_memlist(int m_no);
	LoanDTO loan(int m_no);
}
