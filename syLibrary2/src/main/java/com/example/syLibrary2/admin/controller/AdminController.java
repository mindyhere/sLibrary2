package com.example.syLibrary2.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.syLibrary2.admin.model.dao.AdminDAO;
import com.example.syLibrary2.admin.model.dto.AdminDTO;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin_login/*")
public class AdminController {
	
	@Autowired
	AdminDAO adminDao;
	
	//관리자 로그인화면
	@GetMapping("admin.do")
	public String main(){
		return "admin/admin_login";
	}
	
	@PostMapping("adlogin.do")
	public ModelAndView login(AdminDTO dto, HttpSession session, ModelAndView mav) {
		String a_name = adminDao.login(dto);
		if (a_name != null) { //로그인 성공시
			session.setAttribute("a_id", dto.getA_id());
			session.setAttribute("a_name", a_name);
			session.setAttribute("result", a_name+"님 로그인중");
			mav.setViewName("admin/admin_main");
		} //로그인 실패시 알람창
		return mav;
	}
	
	//기능추가시 ==> 로그인체크 기능
	//인터셉터 사용
	
	@GetMapping("adlogout.do")
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:/admin/admin_login";
	}
	
}
