package com.example.syLibrary2.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.syLibrary2.admin.model.dao.BookDAO;
import com.example.syLibrary2.admin.model.dto.BookDTO;
import com.example.syLibrary2.util.PageUtil2;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

@Controller
@RequestMapping("/book/*")
public class BookController {
	@Autowired
	BookDAO dao;

	@RequestMapping("list_all.do")
	public ModelAndView list(@RequestParam(name = "cur_page", defaultValue = "1") int curPage,
			@RequestParam(name = "search_option", defaultValue = "none") String search_option,
			@RequestParam(name = "keyword", defaultValue = "") String keyword) {
		// System.out.println(curPage);
		int count = dao.count(search_option, keyword);
		PageUtil2 page = new PageUtil2(count, curPage);
		int start = page.getPageBegin();
		int end = page.getPageEnd();
		// System.out.println(count+" "+start + " " + end);
		List<BookDTO> dto = dao.list(start, end, search_option, keyword);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/book/list");
		Map<String, Object> map = new HashMap<>();
		map.put("dto", dto);
		map.put("count", count);
		map.put("search_option", search_option);
		map.put("keyword", keyword);
		map.put("page", page);
		mav.addObject("map", map);
		// System.out.println(search_option);
		return mav;
	}

	@GetMapping("edit.do")
	public ModelAndView edit(@RequestParam(name = "b_id") int b_id) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/book/edit");
		Map<String, Object> map = new HashMap<>();
		map.put("dto", dao.edit(b_id));
		map.put("list", dao.select_cg());
		mav.addObject("map", map);
		// System.out.println(map);
		return mav;
	}

	@PostMapping("update.do")
	public ModelAndView update(@RequestParam Map<String, Object> map, @RequestParam(name = "b_id") int b_id,
			HttpServletRequest request) {
		String filename = "-";
		String result = "";
		if (ObjectUtils.isEmpty(map.get("b_category"))) {
			String b_category = map.get("category").toString();
			map.put("b_category", b_category);
		}
		ObjectMapper mapper = new ObjectMapper();
		BookDTO dto = mapper.convertValue(map, BookDTO.class);
		try {
			ServletContext application = request.getSession().getServletContext();
			String path = application.getRealPath("/resources/images/book/");
			for (Part part : request.getParts()) {
				filename = part.getSubmittedFileName();
				if (filename != null && !filename.equals("null") && !filename.equals("")) {
					part.write(path + filename);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (filename == null || filename.trim().equals("")) {
			String b_url = dao.url_cate(b_id);
			dto.setB_url(b_url);
		} else {
			dto.setB_url(filename);
		}
		result = dao.update(dto);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/book/edit");
		mav.addObject("result", result);
		System.out.println(mav);
		return mav;
	}
}
