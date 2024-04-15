package com.example.syLibrary2.user.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.syLibrary2.user.model.dao.LoginDAO;
import com.example.syLibrary2.user.model.dao.MemberDAO;
import com.example.syLibrary2.user.model.dto.MemberDTO;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@Controller
@RequestMapping("user/member/*")
public class MemberController {
	@Autowired
	MemberDAO memberDao;

	@Autowired
	LoginDAO loginDao;

	@Autowired
	PasswordEncoder pwdEncoder;
 
	@GetMapping("join")
	public String join() {
		return "user/member/join";
	}

	@PostMapping("join.do")
	@ResponseBody
	public ModelAndView join(HttpServletRequest request, @RequestParam("mImg") MultipartFile mImgFile)
			throws IOException {
		String mName = request.getParameter("mName");
		String mId = request.getParameter("mId");
		String mPasswd = request.getParameter("mPasswd");
		String mTel = request.getParameter("mTel");
		String mAddress = request.getParameter("mAddress");
		String mEmail = request.getParameter("mEmail");
		String mZipNo = request.getParameter("mZipNo");
		String birthdate = request.getParameter("mBirthDate");
		String mDetailAddress = request.getParameter("mDetailAddress");

		String mImg = "image_no.png";
		if (!mImgFile.isEmpty()) {
			try {
				String mImgPath = "/resources/images/member/";
				String realPath = request.getSession().getServletContext().getRealPath(mImgPath);
				mImg = mImgFile.getOriginalFilename();
				mImgFile.transferTo(new File(realPath + mImg));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		MemberDTO dto = new MemberDTO();
		dto.setM_name(mName);
		dto.setM_id(mId);
		dto.setM_passwd(pwdEncoder.encode(mPasswd));
		dto.setM_tel(mTel);
		dto.setM_address(mAddress);
		dto.setM_email(mEmail);
		dto.setM_zip_no(mZipNo);
		dto.setM_birth_date(birthdate);
		dto.setM_detail_address(mDetailAddress);
		dto.setM_img(mImg);

		memberDao.insert_join(dto);

		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/");
		return mav;

	}

	// 아이디 중복체크
	@PostMapping("id_check.do")
	@ResponseBody
	public int id_check(@RequestParam("mId") String mId) {
		int checkId = memberDao.id_check(mId);
		return checkId;
	}

	// 회원정보 상세페이지
	@GetMapping("detail_memberInfo")
	public ModelAndView detail_memberInfo(HttpSession session, ModelAndView mav) {
		String mId = (String) session.getAttribute("mId");
		String mPasswd = (String) session.getAttribute("mPasswd");
		MemberDTO memberInfo = memberDao.detailMember(mId);
		mav.setViewName("user/member/detail_memberInfo");
		mav.addObject("memberInfo", memberInfo);
		return mav;
	}

	// 비밀번호 복호화 체크
	@PostMapping("chkPasswd.do")
	@ResponseBody
	public int chkPasswd(@RequestParam("mPasswd") String mPasswd, HttpServletRequest request, HttpSession session) {
		String mId = (String) session.getAttribute("mId");
		System.out.println(" ! 아이디 mId" + mId);
		System.out.println(" ! 입력 비밀번호 mPasswd" + mPasswd);
		String chkPasswd = loginDao.chkPasswd(mId);
		System.out.println(" 복호화 비밀번호 chkPasswd" + chkPasswd);
		String beforePasswd = loginDao.loginChk(mId, mPasswd);
		System.out.println(" 이전 비밀번호 beforePasswd" + beforePasswd);
		int status = 0; 
		if (pwdEncoder.matches(mPasswd, chkPasswd)) { // 비밀번호 일치
			status = 1;
			System.out.println(" 복호화 비밀번호 chkPasswd 성공");
		} else if (beforePasswd != "" && beforePasswd != null) {
			status = 1;
			System.out.println(" 이전 비밀번호 beforePasswd 성공");
		} else {
			status = 2;
			System.out.println("비밀번호 불일치");
		}
		return status;
	}

	// 회원정보 수정
	@PostMapping("edit_memberInfo")
	public ModelAndView edit_memberInfo(@RequestParam("mPasswd") String mPasswd, @RequestParam("mTel") String mTel,
			@RequestParam("mEmail") String mEmail, @RequestParam("mZipNo") String mZipNo,
			@RequestParam("mAddress") String mAddress, @RequestParam("mDetailAddress") String mDetailAddress,
			@RequestParam(value = "mImg", required = false) MultipartFile m_Img, HttpServletRequest request,
			HttpSession session) {
		String mId = (String) session.getAttribute("mId");
		String m_passwd = request.getParameter("mPasswd");
		String m_tel = request.getParameter("mTel");
		String m_email = request.getParameter("mEmail");
		String m_zip_no = request.getParameter("mZipNo");
		String m_address = request.getParameter("mAddress");
		String m_detail_address = request.getParameter("mDetailAddress");
		ServletContext application = request.getSession().getServletContext();
		String mImg_path = application.getRealPath("/resources/images/member/");
		String mImg = m_Img != null ? m_Img.getOriginalFilename() : null;

		try {
			for (Part part : request.getParts()) {
				mImg = part.getSubmittedFileName();
				if (mImg != null && !mImg.trim().equals("")) {
					part.write(mImg_path + mImg);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		MemberDTO dto = new MemberDTO();
		dto.setM_passwd(pwdEncoder.encode(m_passwd));
		dto.setM_tel(m_tel);
		dto.setM_email(m_email);
		dto.setM_zip_no(m_zip_no);
		dto.setM_img(mImg);
		dto.setM_address(m_address);
		dto.setM_detail_address(m_detail_address);
		dto.setM_id(mId);

		if (mImg == null || mImg.trim().equals("") || mImg.equals("null")) {
			MemberDTO dto2 = memberDao.detailMember(mId);
			mImg = dto2.getM_img();
			dto.setM_img(mImg);
		} else {
			dto.setM_img(mImg);
		}
		memberDao.edit_memberInfo(dto);

		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/user/member/detail_memberInfo");
		return mav;
	}
}