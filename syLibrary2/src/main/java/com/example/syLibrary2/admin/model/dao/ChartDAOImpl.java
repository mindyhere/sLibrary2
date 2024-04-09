package com.example.syLibrary2.admin.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ChartDAOImpl implements ChartDAO {
	
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public List<Map<String, Object>> ct_chart() {
		Map<String, Object> map = new HashMap<>();
		return sqlSession.selectList("chart.ct_Chart");
	}
}
