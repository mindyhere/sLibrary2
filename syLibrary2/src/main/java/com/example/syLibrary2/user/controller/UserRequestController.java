package com.example.syLibrary2.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.syLibrary2.admin.model.dto.HoBookDTO;
import com.example.syLibrary2.user.model.dao.UserRequestDAO;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("user/request/*")
public class UserRequestController {
	@Autowired
	UserRequestDAO hoBookDao;

	@GetMapping("/")
	public String form() {
		return "/user/book/bookRequest";
	}

	@Transactional
	@ResponseBody
	@PostMapping("insert")
	public String insert(@RequestParam Map<String, Object> params, HttpSession session) {
		System.out.println("111 컨트롤러: " + session + ", " + params);
		String h_name=(String) session.getAttribute("mName");
		String h_memid=(String) session.getAttribute("mId");
		
		HoBookDTO dto=new HoBookDTO();
		dto.setH_name("h_name");
		
//		dto.setH_url((String) params.get("h_url"));
//		dto.setH_author((String) params.get("h_author"));
//		dto.setH_pub((String) params.get("h_pub"));
//		dto.setH_isbn((String) params.get("h_isbn"));
//		dto.setH_description((String) params.get("h_description"));
//		dto.setH_year((Integer) params.get("h_year"));
//		dto.setH_category((String) params.get("h_category"));
//		dto.setH_memid("h_memid");
//		dto.setH_link((String) params.get("h_link"));
		return hoBookDao.insert(dto);
	}

}
