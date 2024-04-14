package com.example.syLibrary2.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.syLibrary2.admin.model.dao.RecommendDAO;
import com.example.syLibrary2.admin.model.dto.BookDTO;
import com.example.syLibrary2.user.model.dao.SearchDAO;

@Controller
public class RecommendController {

	@Autowired
	RecommendDAO recommendDao;

	@Autowired
	SearchDAO searchDao;

	@GetMapping("/")
	public String index() {
		return "redirect:index/";
	}

	@GetMapping("index/")
	public ModelAndView list(@RequestParam(name = "opt", defaultValue = "") String option, ModelAndView mav) {
		List<BookDTO> list = null;
		if (option.equals("")) {
			list = recommendDao.getList("opt1");
			mav.setViewName("user/main");
		} else if (!option.equals("")) {
			list = recommendDao.getList(option);
			mav.setViewName("user/book/recommend");
		}
		mav.addObject("list", list);
		mav.addObject("opt", option);
		return mav;
	}

	@GetMapping("index/recommendList")
	public ModelAndView recommendList(ModelAndView mav) {
		List<Map<String, Object>> list = recommendDao.getList();
		mav.setViewName("user/book/modal1");
		mav.addObject("list", list);
		return mav;
	}

	@GetMapping("index/search")
	public ModelAndView search(@RequestParam(name = "keyword", defaultValue = "") String keyword, ModelAndView mav) {
		// 관리자, 추천도서 edit → 도서 검색
		List<BookDTO> list = searchDao.totSearch(keyword);
		mav.setViewName("user/book/autocomplete");
		mav.addObject("list", list);
		return mav;
	}

	@Transactional
	@ResponseBody
	@GetMapping("index/insert")
	public String insert(@RequestParam(name = "a_id") String a_id, @RequestParam(name = "b_id") int b_id) {
		return recommendDao.insert(a_id, b_id);
	}

	@Transactional
	@ResponseBody
	@GetMapping("index/delete")
	public Map<String, Object> delete(@RequestParam(name = "cnt") int cnt, @RequestParam(name = "arr") String arr) {
		Map<String, Object> map = new HashMap<>();
		String option = "";
		if (cnt == 5) {
			option = "all"; // 전체삭제
			recommendDao.delete(option);
		} else if (cnt < 5) {
			option = "each"; // 개별삭제
			String[] values = arr.split(",");
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					recommendDao.delete(option, Integer.parseInt(values[i]));
				}
			}
		}
		String result = "success";
		map.put("result", result);
		return map;
	}

}