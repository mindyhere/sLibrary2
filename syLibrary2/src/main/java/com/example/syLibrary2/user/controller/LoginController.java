package com.example.syLibrary2.user.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.syLibrary2.admin.model.dao.EmailService;
import com.example.syLibrary2.admin.model.dto.EmailDTO;
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

	@Autowired
	EmailService emailService;

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
		String sendMail = "";
		if (dto.getM_email() != null && !dto.getM_email().equals("") && !dto.getM_email().equals("null")) {
			mPw = loginDao.searchPwEmail(mEmail, mId, mName, mBirthDate);
			if (mPw != null) {
				status = 1;
				String randomPw = getTempPassword();
				loginDao.setRandomPw(randomPw, mId, mEmail, mTel);
				EmailDTO emailPw = new EmailDTO();
				emailPw.setSubject("[3월 도서관] 임시 비밀번호 안내");
				emailPw.setMessage("안녕하세요. 3월 도서관 임시비밀번호 안내 관련 이메일 입니다." + " 회원님의 임시 비밀번호는 " + randomPw + " 입니다."
						+ "로그인 후에 비밀번호를 변경을 해주세요");
				emailPw.setReceiveMail(mEmail);
				emailPw.setSenderName("SY Library");
				emailPw.setSenderMail("SYLibrary@gmail.com");
				emailService.sendMail(emailPw);
				sendMail = "success";
			} else {
				status = 2;
				sendMail = "fail";
			}
		} else {
			mPw = loginDao.searchPwTel(mTel, mId, mName, mBirthDate);
			if (mPw != null) {
				status = 1;
				String randomPw = getTempPassword();
				loginDao.setRandomPw(randomPw, mId, mEmail, mTel);
				String getMail =  loginDao.getEmail(mTel, mId, mName, mBirthDate);
				EmailDTO emailPw = new EmailDTO();
				emailPw.setSubject("[3월 도서관] 임시 비밀번호 안내");
				emailPw.setMessage("안녕하세요. 3월 도서관 임시비밀번호 안내 관련 이메일 입니다." + " 회원님의 임시 비밀번호는 " + randomPw + " 입니다."
						+ "로그인 후에 비밀번호를 변경을 해주세요");
				emailPw.setReceiveMail(getMail);
				emailPw.setSenderName("SY Library");
				emailPw.setSenderMail("SYLibrary@gmail.com");
				emailService.sendMail(emailPw);
				sendMail = "success";
			} else {
				status = 2;
				sendMail = "fail";
			}
		}
		Map<String, Object> result = new HashMap<>();
		result.put("mPw", mPw);
		result.put("status", status);
		result.put("sendMail", sendMail);
		return result;
	}

	// 랜덤함수로 임시비밀번호 구문 만들기
	public String getTempPassword() {
		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
				'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

		String str = "";
		int idx = 0;
		for (int i = 0; i < 10; i++) {
			idx = (int) (charSet.length * Math.random());
			str += charSet[idx];
		}
		return str;
	}

	// 로그아웃
	@GetMapping("logout")
	public ModelAndView logout(HttpSession session, ModelAndView mav) {
		session.invalidate();
		mav.setViewName("redirect:/");
		return mav;
	}
}
