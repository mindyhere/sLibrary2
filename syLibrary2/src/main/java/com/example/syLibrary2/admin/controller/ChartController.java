package com.example.syLibrary2.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.syLibrary2.admin.model.dao.ChartDAO;

@Controller
@RequestMapping("admin/chart/*")
public class ChartController {
	
	@Autowired
	ChartDAO dao;
	
	@RequestMapping("ct_chart.do")
	@ResponseBody
	public ModelAndView chcht() {
		List<Map<String, Object>> ctchart = dao.ct_chart();
		//List<Map<String, Object>> chart1 = null;
		JSONArray json = new JSONArray();
//		for(int j=0; j < ct_Chart.size(); j++) {
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("cnt", ct_Chart.get(j).get("CNT"));
//			jsonObject.put("category", ct_Chart.get(j).get("CATEGORY"));
//			json.add(jsonObject);
//		}
		
		for (int j = 0; j < ctchart.size(); j++) {
			Map<String, Object> chart = new HashMap<>();
			chart.put("category", ctchart.get(j).get("CATEGORY"));
			chart.put("cnt", ctchart.get(j).get("CNT"));
			json.add(chart);
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/chart/ct_chart");
		mav.addObject("ct_Chart", json);
		//mav.addObject("ct_Chart", chart);
		System.out.println(json);
		return mav;
	}
	
}
