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
		BookDTO dto = dao.edit(b_id);
		String b_url = dto.getB_url();
		int check = 0;
		if (b_url.contains("https")) {
			check = 1;
		} else {
			check = 0;
		}
		map.put("dto", dto);
		map.put("list", dao.select_cg());
		map.put("check", check);
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
		String b_category = dto.getB_category();
		int ct_number = 0;
		if (b_category.contains("판타지")) {
			ct_number = 100;
		} else if(b_category.contains("에세이")) {
			ct_number = 110;
		} else if(b_category.contains("소설")) {
			ct_number = 120;
		} else if(b_category.contains("동화")) {
			ct_number = 130;
		} else if(b_category.contains("SF")) {
			ct_number = 140;
		} else if(b_category.contains("추리")) {
			ct_number = 150;
		} else if(b_category.contains("만화")) {
			ct_number = 160;
		} else if(b_category.contains("청소년")) {
			ct_number = 170;
		} else if(b_category.contains("자기계발")) {
			ct_number = 180;
		} else if(b_category.contains("역사")) {
			ct_number = 190;
		} else if(b_category.contains("과학")) {
			ct_number = 200;
		} else {
			ct_number = 500;
		}
		dto.setCt_number(ct_number);
		result = dao.update(dto);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/book/edit");
		mav.addObject("result", result);
		// System.out.println(mav);
		return mav;
	}

	@PostMapping("delete.do")
	public String delete(@RequestParam(name = "b_id") int b_id) {
		dao.delete(b_id);
		return "redirect:/book/list_all.do";
	}
	
	@GetMapping("select_cg.do")
	public ModelAndView select_cg(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/book/insert");
		mav.addObject("list", dao.select_cg());
		return mav;
	}
	
	@PostMapping("insert.do")
	public ModelAndView insert(BookDTO dto, HttpServletRequest request) {
		String filename = "-";
		String result = "";
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
		dto.setB_url(filename);
		String b_category = dto.getB_category();
		int ct_number = 0;
		if (b_category.contains("판타지")) {
			ct_number = 100;
		} else if(b_category.contains("에세이")) {
			ct_number = 110;
		} else if(b_category.contains("소설")) {
			ct_number = 120;
		} else if(b_category.contains("동화")) {
			ct_number = 130;
		} else if(b_category.contains("SF")) {
			ct_number = 140;
		} else if(b_category.contains("추리")) {
			ct_number = 150;
		} else if(b_category.contains("만화")) {
			ct_number = 160;
		} else if(b_category.contains("청소년")) {
			ct_number = 170;
		} else if(b_category.contains("자기계발")) {
			ct_number = 180;
		} else if(b_category.contains("역사")) {
			ct_number = 190;
		} else if(b_category.contains("과학")) {
			ct_number = 200;
		} else {
			ct_number = 500;
		}
		dto.setCt_number(ct_number);
		// System.out.println(dto);
		result = dao.insert(dto);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/book/insert");
		mav.addObject("result", result);
		// System.out.println(mav);
		return mav;
	}
}
