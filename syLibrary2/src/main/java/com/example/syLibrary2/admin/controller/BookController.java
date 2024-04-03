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

import com.example.syLibrary2.admin.model.dao.BookDAO;
import com.example.syLibrary2.admin.model.dto.BookDTO;
import com.example.syLibrary2.util.PageUtil;

@Controller
@RequestMapping("/admin/*")
public class BookController {
	@Autowired
	BookDAO dao;
	
	@GetMapping("list_all.do")
	public ModelAndView list(@RequestParam(name="curPage", defaultValue="1")int curPage, 
			@RequestParam(name = "search_option", defaultValue="none") String search_option,
			@RequestParam(name = "keyword", defaultValue = "") String keyword) {
		int count = dao.count(search_option, keyword);
		PageUtil page = new PageUtil(count, curPage);
		int start = page.getPageBegin();
		int end = page.getPageEnd();
		List<BookDTO> dto = dao.list(start, end, search_option, keyword);
		ModelAndView mav=new ModelAndView();
		mav.setViewName("admin/book/list");
		Map<String,Object> map=new HashMap<>();
		map.put("dto", dto);
		map.put("count",count);
		map.put("search_option", search_option);
		map.put("keyword", keyword);
		map.put("page",page);
		mav.addObject("map",map);
		System.out.println(mav);
		return mav;
	}
	
	@GetMapping("list.do")
	public String list() {
		System.out.println("확인");
		return "admin/test";
	}
	
}

