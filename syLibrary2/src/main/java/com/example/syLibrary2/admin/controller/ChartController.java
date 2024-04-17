package com.example.syLibrary2.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.syLibrary2.admin.model.dao.ChartDAO;

@Controller
@RequestMapping("admin/chart/*")
public class ChartController {
	
	@Autowired
	ChartDAO dao;
	
	@GetMapping("ct.do")
	public String ct_chart(){
		return "admin/chart/ct_chart";
	}
	
	@RequestMapping("ct_chart.do")
	@ResponseBody
	public List<Map<String, Object>> chcht() {
		List<Map<String, Object>> ctchart = dao.ct_chart();
		return ctchart;
	}
	
	@GetMapping("chart.do")
	public String chart(){
		return "admin/chart/loan_chart";
	}
	
	
	@RequestMapping("loan_chart.do")
	@ResponseBody
	public List<Map<String, Object>> loChart() {
		List<Map<String, Object>> chart = dao.loChart();
		return chart;
	}
		
}
