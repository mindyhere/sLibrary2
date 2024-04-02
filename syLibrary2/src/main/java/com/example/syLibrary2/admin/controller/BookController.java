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

import com.example.syLibrary2.admin.model.BookDAO;
import com.example.syLibrary2.admin.model.BookDTO;
import com.example.syLibrary2.util.PageUtil;

@Controller
@RequestMapping("/admin/*")
public class BookController {
	@Autowired
	BookDAO dao;
	
	@GetMapping("list_all.do")
	public ModelAndView list(@RequestParam(name="curPage", defaultValue="1")int curPage) {
		int count = dao.count();
		PageUtil page = new PageUtil(count, curPage);
		int start = page.getPageBegin();
		int end = page.getPageEnd();
		List<BookDTO> dto = dao.list(start, end);
		ModelAndView mav=new ModelAndView();
		mav.setViewName("admin/book/list");
		Map<String,Object> map=new HashMap<>();
		map.put("dto", dto);
		map.put("count",count);
		map.put("page",page);
		mav.addObject("map",map);
		System.out.println(mav);
		return mav;
	}
}

