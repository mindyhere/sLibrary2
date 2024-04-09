package com.example.syLibrary2.user.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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

	@GetMapping("join")
	public String join() {
		return "user/member/join";
	} 

	@PostMapping("join")
	public String join(@RequestParam("mname") String m_name, @RequestParam("mId") String m_Id,
			@RequestParam("mPasswd") String m_Passwd, @RequestParam("mTel") String m_Tel,
			@RequestParam("mAddress") String m_Address, @RequestParam("mEmail") String m_Email,
			@RequestParam("mZipNo") String m_ZipNo, @RequestParam("mBirthDate") String m_BirthDate,
			@RequestParam("mDetailAddress") String m_DetailAddress,
			@RequestParam(value = "mImg", required = false) MultipartFile m_Img, HttpServletRequest request,
			Model model) {

		ServletContext application = request.getSession().getServletContext();
		String mImgPath = application.getRealPath("/webapp/resources/images/member/");
		String fileName = m_Img != null ? m_Img.getOriginalFilename() : null;

		try {
			if (fileName != null && !fileName.trim().isEmpty()) {
				File file = new File(mImgPath, fileName);
				m_Img.transferTo(file);
			} else {
				fileName = "image_no.png";
			}

			System.out.println("mname:" + m_name);
			System.out.println("mId:" + m_Id);
			System.out.println("mPasswd:" + m_Passwd);
			System.out.println("mTel:" + m_Tel);

			MemberDTO dto = new MemberDTO();
			dto.setM_name(m_name);
			dto.setM_id(m_Id);
			dto.setM_passwd(m_Passwd);
			dto.setM_tel(m_Tel);
			dto.setM_address(m_Address);
			dto.setM_email(m_Email);
			dto.setM_zip_no(m_ZipNo);
			dto.setM_birth_date(m_BirthDate);
			dto.setM_detail_address(m_DetailAddress);
			dto.setM_img(fileName);

			memberDao.insert_join(dto);
			model.addAttribute("message", "join");

			return "redirect:user/main";
		} catch (IOException e) {
			e.printStackTrace();
			model.addAttribute("error");
			return "error";
		}
	}

	// 회원정보 상세페이지
	@GetMapping("detail_memberInfo")
	public ModelAndView detail_memberInfo(HttpSession session, ModelAndView mav) {
		String mId = (String) session.getAttribute("mId");
		MemberDTO memberInfo = memberDao.detailMember(mId);
		mav.setViewName("user/member/detail_memberInfo");
		mav.addObject("memberInfo", memberInfo);
		return mav;
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
		dto.setM_passwd(m_passwd);
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