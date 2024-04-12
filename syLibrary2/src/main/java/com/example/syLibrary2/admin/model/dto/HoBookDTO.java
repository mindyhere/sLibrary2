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
	private int h_id;
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
	private int h_state;
	private String h_chkdate;
}
