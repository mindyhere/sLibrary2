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

import com.example.syLibrary2.admin.model.dao.ReBookDAO;
import com.example.syLibrary2.admin.model.dto.ReBookDTO;
import com.example.syLibrary2.util.PageUtil;

@Controller
@RequestMapping("admin/rebook/*")
public class ReBookController {

	@Autowired
	ReBookDAO dao;
	
	@GetMapping("search.do")
	public ModelAndView search(@RequestParam(name="curPage", defaultValue="1") int curPage, @RequestParam(name="search_option", defaultValue="") String search_option, @RequestParam(name="search", defaultValue="") String search) {
		int count = dao.count(search_option, search); //레코드 개수
		PageUtil page = new PageUtil(count, curPage);
		int start = page.getPageBegin();
		int end = page.getPageEnd();
		if (search_option == "" || search == "") {
			count = dao.count();
		} else {
			count = dao.count(search_option, search);
		}
		List<ReBookDTO> list = null;
		Map<String,Object> map = new HashMap<>();
    	if (search_option == "" || search == "") {
    		list = dao.list(start, end);
		} else {
			list = dao.list_search(search_option,search,start,end);
			map.put("search_option", search_option);
			map.put("search", search);
		}
		//list=dao.list_search(search_option, search,start, end); //게시물 리스트
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/l_book/re_search");
		map.put("list", list);
		map.put("count", count);
		map.put("search_option", search_option);
		map.put("search", search);
		map.put("page", page);
		mav.addObject("map", map);
		return mav;
	}
	
	@GetMapping("list.do")
	public ModelAndView list(@RequestParam(name="curPage", defaultValue="1") int curPage) {
		int count = dao.count(); //레코드 개수
		PageUtil page = new PageUtil(count, curPage);
		int start = page.getPageBegin();
		int end = page.getPageEnd();
		List<ReBookDTO> list =dao.list(start, end); //게시물 리스트
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/l_book/re_list");
		Map<String,Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		map.put("page", page);
		mav.addObject("map", map);
		return mav;
	} 
}
