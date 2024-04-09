package com.example.syLibrary2.user.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.syLibrary2.user.model.dao.MemberDAO;
import com.example.syLibrary2.user.model.dto.MemberDTO;

import jakarta.servlet.http.HttpServletRequest;


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
	public ModelAndView join(HttpServletRequest request, @RequestParam("mImg") MultipartFile mImgFile)
			throws IOException {
	  String mName = request.getParameter("mName");
	  String mId = request.getParameter("mId"); 
	  String mPasswd = request.getParameter("mPasswd"); 
	  String mTel = request.getParameter("mTel");
	  String mAddress = request.getParameter("mAddress"); 
	  String mEmail =  request.getParameter("mEmail"); 
	  String mZipNo =  request.getParameter("mZipNo"); 
	  String birthdate =  request.getParameter("mBirthDate"); 
	  String mDetailAddress =  request.getParameter("mDetailAddress");
	  
	  // 회원 이미지 db 저장 
	  String mImg = "image_no.png"; 
	  if (!mImgFile.isEmpty()) { 
		  try	{ 
			  String mImgPath = "/resources/images/member/"; 
			  String realPath = request.getSession().getServletContext().getRealPath(mImgPath); 
			  mImg = mImgFile.getOriginalFilename();
			  mImgFile.transferTo(new File(realPath +  mImg)
					  ); 
			  } catch (IOException e) { 
				  e.printStackTrace(); 
			  } 
		  }
	  
	  MemberDTO dto = new MemberDTO(); 
	  dto.setM_name(mName); 
	  dto.setM_id(mId);
	  dto.setM_passwd(mPasswd); 
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
	  return  mav;
 }
	    
	@GetMapping("detail_memberInfo")
	public String detail_memberInfo(HttpServletRequest request) {
		String m_Id = request.getParameter("m_Id");
		return m_Id;
	}

	@GetMapping("edit_memberInfo")
	public String edit_memberInfo(HttpServletRequest request) {
		return null;
	}
		  
	
	/*
	 * @PostMapping("id_check") public @ResponseBody int idCheck(String mId) { int
	 * result = memberDao.id_check(mId); return result; }
	 */
}	 

