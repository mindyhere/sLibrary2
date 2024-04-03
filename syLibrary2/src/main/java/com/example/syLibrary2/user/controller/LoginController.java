package com.example.syLibrary2.user.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.syLibrary2.user.model.dao.LoginDAO;
import com.example.syLibrary2.user.model.dto.LoginDTO;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("user/login/*")
public class LoginController {
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	LoginDAO loginDao;

	// 로그인 페이지
	@GetMapping("login")
	public String login() {
		return "user/login/login";
	}

	// 로그인
	@PostMapping("login.do")
	public ModelAndView loginCheck(@RequestParam(name = "mId", defaultValue = "") String mId,
			@RequestParam(name = "mPasswd", defaultValue = "") String mPasswd, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String mName = loginDao.loginChk(mId, mPasswd);
		// Map<String, Object> map = new HashMap<>();
		session.setAttribute("mId", mId);
		session.setAttribute("mName", mName);
		System.out.println("mId : " + mId);
		System.out.println("mName : " + mName);
		mav.setViewName("redirect:/");
//		mav.addObject("map", map);
		return mav;
	}

	// 아이디 찾기 페이지
	@GetMapping("searchId")
	public String searchId() {
		return "user/login/searchId";
	}

	@PostMapping("searchId.do")
	public ModelAndView searchIdForm(@ModelAttribute LoginDTO dto) {
		ModelAndView mav = new ModelAndView();
		String mId = "";
		System.out.println("입력값 확인 : ");
		if (dto.getM_email() != null) {
			mId = loginDao.searchIdEmail(dto);
		} else {
			mId = loginDao.searchIdTel(dto);
		}
		mav.setViewName("user/login/searchId");
		mav.addObject("mId", mId);
		return mav;
	}

	// 비밀번호 찾기 페이지
	@GetMapping("searchPasswd")
	public String searchPasswd() {
		return "user/login/searchPasswd";
	}

	@PostMapping("searchPw.do")
	public ModelAndView searchPwForm(@RequestParam(name = "mId", defaultValue = "") String mId,
			@RequestParam(name = "mPasswd", defaultValue = "") String mPasswd, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/main/index");
		// mav.setViewName("user/login/searchId");
		String mName = loginDao.loginChk(mId, mPasswd);
		Map<String, Object> map = new HashMap<>();
		session.setAttribute("mId", mId);
		session.setAttribute("mName", mName);
		mav.addObject("map", map);
		return mav;
	}
	
	// 로그아웃
	@GetMapping("logout")
	public ModelAndView logout(HttpSession session, ModelAndView mav) {
		session.invalidate();
		mav.setViewName("redirect:/");
		return mav;
	}
}
