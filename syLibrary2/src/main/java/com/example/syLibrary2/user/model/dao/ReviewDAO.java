package com.example.syLibrary2.user.model.dao;

import java.util.List;
import java.util.Map;

public interface ReviewDAO {
	List<Map<String, Object>> getReviews(int b_id);

	String insert(Map<String, Object> map);

	void delete(Map<String, Object> map);

	void delete(int idx);

	List<Map<String, Object>> search(Map<String, Object> map);
	List<Map<String, Object>> searchAll(String keyword);
}
