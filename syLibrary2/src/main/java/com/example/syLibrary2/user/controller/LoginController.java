package com.example.syLibrary2.user.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.syLibrary2.user.model.dao.LoginDAO;
import com.example.syLibrary2.user.model.dto.LoginDTO;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("user/login/*")
public class LoginController {

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
		session.setAttribute("mId", mId);
		session.setAttribute("mName", mName);
		mav.setViewName("redirect:/");
		return mav;
	}

	// 아이디 찾기 페이지
	@GetMapping("searchId")
	public String searchId() {
		return "user/login/searchId";
	}

	@PostMapping("searchId.do")
	@ResponseBody
	public Object searchIdForm(@RequestParam("mEmail") String mEmail, @RequestParam("mTel") String mTel,
			@RequestParam("mName") String mName, @RequestParam("mBirthDate") String mBirthDate) {
		LoginDTO dto = new LoginDTO();
		dto.setM_email(mEmail);
		dto.setM_tel(mTel);
		dto.setM_name(mName);
		String birthdate = mBirthDate;
		// String → Date
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date mBirthDate2 = format.parse(birthdate);
			dto.setM_birth_date(mBirthDate2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String mId = "";
		int status = 0;
		if (dto.getM_email() != null && !dto.getM_email().equals("") && !dto.getM_email().equals("null")) {
			mId = loginDao.searchIdEmail(mEmail, mName, mBirthDate);
			if (mId != null) {
				status = 1;
			} else {
				status = 2;
			}
		} else {
			mId = loginDao.searchIdTel(mTel, mName, mBirthDate);
			if (mId != null) {
				status = 1;
			} else {
				status = 2;
			}
		}
		Map<String, Object> result = new HashMap<>();
		result.put("mId", mId);
		result.put("status", status);
		return result;
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
