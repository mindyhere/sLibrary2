package com.example.syLibrary2.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.syLibrary2.admin.model.dao.HoBookDAO;
import com.example.syLibrary2.admin.model.dto.HoBookDTO;
import com.example.syLibrary2.util.PageUtil2;

@Controller
@RequestMapping("/admin/hope/*")
public class HoBookController {
	@Autowired
	HoBookDAO dao;
	
	@RequestMapping("list.do")
	public ModelAndView list(@RequestParam(name = "cur_page", defaultValue = "1") int curPage,
			@RequestParam(name = "search_option", defaultValue = "none") String search_option,
			@RequestParam(name = "keyword", defaultValue = "") String keyword) {
		int count = dao.count(search_option, keyword);
		PageUtil2 page = new PageUtil2(count, curPage);
		int start = page.getPageBegin();
		int end = page.getPageEnd();
		List<HoBookDTO> dto = dao.list(start, end, search_option, keyword);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/hope/hope_list");
		Map<String, Object> map = new HashMap<>();
		map.put("dto", dto);
		map.put("count", count);
		map.put("search_option", search_option);
		map.put("keyword", keyword);
		map.put("page", page);
		mav.addObject("map", map);
		return mav; 
	}
	
	@GetMapping("detail.do")
	public ModelAndView detail(@RequestParam(name = "h_idx") int h_idx) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/book/hope_detail");
		mav.addObject("dto", dao.detail(h_idx));
		return mav;
	}
}