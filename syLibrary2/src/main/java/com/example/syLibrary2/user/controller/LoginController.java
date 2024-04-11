package com.example.syLibrary2.user.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

	@Autowired
	PasswordEncoder pwdEncoder;

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
		String chkPasswd = loginDao.chkPasswd(mId);
		String mName = loginDao.loginChk(mId, mPasswd);
		if (pwdEncoder.matches(mPasswd, chkPasswd)) { // 로그인 성공
			mName = loginDao.loginChk(mId, chkPasswd);
			session.setAttribute("mId", mId);
			session.setAttribute("mName", mName);
			mav.setViewName("redirect:/");
		} else if (mName != "" && mName != null) {
			session.setAttribute("mId", mId);
			session.setAttribute("mName", mName);
			session.setAttribute("mPasswd", mPasswd);
			mav.setViewName("redirect:/");
		} else {
			mav.addObject("message", "로그인실패");
		}
		return mav;
	}

	// 아이디 찾기 페이지
	@GetMapping("searchId")
	public String searchId() {
		return "user/login/searchId";
	}

	@PostMapping("searchId.do")
	@ResponseBody
	public Map<String, Object> searchIdForm(@RequestParam("mEmail") String mEmail, @RequestParam("mTel") String mTel,
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

	@PostMapping("searchPwd.do")
	@ResponseBody
	public Map<String, Object> searchPwForm(@RequestParam("mEmail") String mEmail, @RequestParam("mTel") String mTel,
			@RequestParam(name = "mId") String mId, @RequestParam("mName") String mName,
			@RequestParam("mBirthDate") String mBirthDate) {
		LoginDTO dto = new LoginDTO();
		dto.setM_email(mEmail);
		dto.setM_tel(mTel);
		dto.setM_id(mId);
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
		String mPw = "";
		int status = 0;
		if (dto.getM_email() != null && !dto.getM_email().equals("") && !dto.getM_email().equals("null")) {
			mPw = loginDao.searchPwEmail(mEmail, mId, mName, mBirthDate);
			if (mPw != null) {
				status = 1;
			} else {
				status = 2;
			}
		} else {
			mPw = loginDao.searchPwTel(mTel, mId, mName, mBirthDate);
			if (mPw != null) {
				status = 1;
			} else {
				status = 2;
			}
		}
		Map<String, Object> result = new HashMap<>();
		result.put("mPw", mPw);
		result.put("status", status);
		return result;
	}

	// 로그아웃
	@GetMapping("logout")
	public ModelAndView logout(HttpSession session, ModelAndView mav) {
		session.invalidate();
		mav.setViewName("redirect:/");
		return mav;
	}
}
