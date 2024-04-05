package com.example.syLibrary2.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.syLibrary2.user.model.dao.ReviewDAO;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("user/search/bookinfo/*")
public class ReviewController {
	@Autowired
	ReviewDAO reviewDao;

	@GetMapping("totalList/")
	public ModelAndView totalList(ModelAndView mav) {
		System.out.println("111");
		mav.setViewName("user/search/totalReviews");
		mav.addObject("reviews", reviewDao.totalList());
		return mav;
	}

	@GetMapping("getReviews/")
	public ModelAndView getReviews(@RequestParam(name = "b_id") int b_id, ModelAndView mav) {
		System.out.println("222 : " + b_id);
//		Map<String, Object> map= new HashMap<>();
		if (b_id != 0) {
			mav.setViewName("user/search/reviews");
		} else {
			mav.setViewName("user/search/totalReviews");
		}
		mav.addObject("reviews", reviewDao.getReviews(b_id));
		return mav;
	}

	@ResponseBody
	@RequestMapping("insert")
	public String insert(@RequestParam(name = "b_id") int b_id, @RequestParam(name = "m_id") String m_id,
			@RequestParam(name = "contents") String contents) {
		Map<String, Object> map = new HashMap<>();
		map.put("m_id", m_id);
		map.put("b_id", b_id);
		String contents1 = "";
		if (contents != "") {
			contents1 = contents.replace("<", "&lt;");
			contents1 = contents.replace(">", "&gt;");
			contents1 = contents.replace("  ", "&nbsp;&nbsp;");
			map.put("contents", contents1);
		}
		return reviewDao.insert(map);
	}

	@Transactional
	@ResponseBody
	@GetMapping("delete/{option}")
	public void delete(@PathVariable(name = "option") String option, @RequestParam(name = "arr") String arr,
			HttpSession session) {
		String[] values = arr.split(",");
		switch (option) {
		case "user":
			String m_id = (String) session.getAttribute("mId");
			System.out.println(m_id);
			for (int i = 0; i < values.length; i++) {
				Map<String, Object> map = new HashMap<>();
				int idx = Integer.parseInt(values[i]);
				map.put("m_id", m_id);
				map.put("idx", idx);
				reviewDao.delete(map);
			}
			break;
		case "admin":
			for (int i = 0; i < values.length; i++) {
				reviewDao.delete(Integer.parseInt(values[i]));
			}
			break;
		}
	}

	@GetMapping("search")
	public ModelAndView search(@RequestParam(name = "keyword", defaultValue = "") String keyword,
			@RequestParam(name = "searchOpt", defaultValue = "all") String searchOpt, ModelAndView mav) {
		mav.setViewName("user/search/totalReviews");
		mav.addObject("list", reviewDao.search(searchOpt, keyword));
		return mav;
	}

}
