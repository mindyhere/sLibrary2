package com.example.syLibrary2.user.model.dao;

import java.util.Map;

public interface CheckoutDAO {
	String isAvailable(int b_id);

	void checkMloan(Map<String, Object> param);

	int duplicate(Map<String, Object> map);

	void insert(Map<String, Object> map);
}
