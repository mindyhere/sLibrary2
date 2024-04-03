package com.example.syLibrary2.admin.model.dao;

import java.util.List;

import com.example.syLibrary2.admin.model.dto.MemoDTO;

public interface MemoDAO {
	List<MemoDTO> list(int start, int end);
	void insert(MemoDTO dto);
	void delete(int me_rownum);
	void update(MemoDTO dto);
	int count();
	MemoDTO search(int me_rownum);
}
