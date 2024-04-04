package com.example.syLibrary2.user.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {
	private int m_no;
	private String m_id;
	private String m_passwd;
	private String m_name;
	private String m_tel;
	private String m_zip_no;
	private String m_address;
	private String m_detail_address;
	private String m_email;
	private String m_birth_date;
	private String m_img;
	private String m_year;
	private int m_loan;
}
