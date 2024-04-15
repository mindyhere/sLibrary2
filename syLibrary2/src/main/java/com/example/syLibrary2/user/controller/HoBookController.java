package com.example.syLibrary2.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.syLibrary2.user.model.dao.HoBookDAO;

@Controller
@RequestMapping("user/request/*")
public class HoBookController {
	@Autowired
	HoBookDAO hoBookDao;

	@GetMapping("/")
	public String form() {
		return "/user/book/bookRequest";
	}

	@ResponseBody
	@PostMapping("insert")
	public Map<String, Object> insert(@RequestParam Map<String, Object> params) {
		System.out.println("111페이지 링크 확인=컨트롤러");
		return null;
	}

}
