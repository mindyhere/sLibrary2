package com.example.syLibrary2.admin.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HoBookDTO {
	private int h_idx;
	private String h_name;
	private String h_url;
	private String h_author;
	private String h_pub;
	private String h_isbn;
	private String h_description;
	private int h_year;
	private String h_category;
	private String h_memid;
	private String h_regdate;
	private String h_state; // 신청완료 1 > 접수취소 2 > 신청취소 3 > 처리중 4 > 이용가능 5
	private String h_chkdate;
	private String h_link;
	private String ho_book_cnt; // 희망도서 신청건수
	private String h_cancel;
}
