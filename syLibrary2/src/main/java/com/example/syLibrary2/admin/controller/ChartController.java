package com.example.syLibrary2.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public List<Map<String, Object>> chcht() {
		List<Map<String, Object>> ct_Chart = dao.ct_chart();
//		JSONArray ct_Chart = dao.ct_chart();
//		for(int j=0; j < ct_Chart.size(); j++) {
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("cnt", ct_Chart.get(j).get("CNT"));
//			jsonObject.put("category", ct_Chart.get(j).get("CATEGORY"));
//			json.add(jsonObject);
//		}
		
		for (int j = 0; j < ct_Chart.size(); j++) {
			Map<String, Object> chart = new HashMap<>();
			chart.put("category", ct_Chart.get(j).get("CATEGORY"));
			chart.put("cnt", ct_Chart.get(j).get("CNT"));
			ct_Chart.add(chart);
		}
		
		//ModelAndView mav = new ModelAndView();
		//mav.setViewName("admin/chart/ct_chart");
		//mav.addObject("ct_Chart", ct_Chart);
		System.out.println(ct_Chart);
		return ct_Chart;
	}
	
}
