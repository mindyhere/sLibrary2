package com.example.syLibrary2.admin.model.dao;

import java.util.List;

import com.example.syLibrary2.admin.model.dto.LoanDTO;
import com.example.syLibrary2.user.model.dto.MemberDTO;

public interface AdmemberDAO {
	//List<MemberDTO> ad_list_search(String search_option, String search, int start, int end);
	MemberDTO ad_list_detail(int m_no);
	//int count();
	//List<MemberDTO> list();
	//List<MemberDTO> list(int pageStart, int pageEnd);
	//int search_count(String search_option, String search);
	
	List<MemberDTO> list_search(String search_option, String search, int start, int end);
	int count(String search_option, String search);
	
	//-----회원대출상세
	List<LoanDTO> lo_memlist(int m_no);
	
	LoanDTO loan(int m_no);
}
