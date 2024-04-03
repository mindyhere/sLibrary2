package com.example.syLibrary2.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.syLibrary2.admin.model.BookDTO;
import com.example.syLibrary2.admin.model.RecommendDAO;

@Controller
public class RecommendController {

	@Autowired
	RecommendDAO recommendDao;

	@RequestMapping("/")
	public String index() {
		System.out.println("index > list로 redirect");
		return "redirect:index/";
	}
	
	@GetMapping("index/")
	public ModelAndView list(@RequestParam(name = "opt", defaultValue = "") String option, ModelAndView mav) {
		//String resultPage = "";
		if (option.equals("")) {
			List<BookDTO> list = recommendDao.getList("opt1");
			mav.setViewName("user/main");
			mav.addObject("list", list);
			System.out.println("list 확인/추천목록 getList =>" + list);
		} else if (!option.equals("")) {
			List<BookDTO> list = recommendDao.getList(option);
			System.out.println("옵션선택 option=" + option + "/ list=>" + list);
			mav.setViewName("user/book/recommend");
			mav.addObject("list", list);
		}
		return mav;
	}

//	@GetMapping("index/")
//	public String list(@RequestParam(name = "opt", defaultValue = "") String option, Model model) {
//		String resultPage = "";
//		if (option.equals("")) {
//			List<BookDTO> list = recommendDao.getList("opt1");
//			model.addAttribute("list", list);
//			System.out.println("list 확인/추천목록 getList =>" + list);
//			resultPage = "user/main";
//		} else if (!option.equals("")) {
//			List<BookDTO> list = recommendDao.getList(option);
//			System.out.println("옵션선택 option=" + option + "/ list=>" + list);
//			model.addAttribute("list", list);
//			resultPage = "user/book/recommend";
//		}
//		return resultPage;
//	}

}
