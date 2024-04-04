package com.example.syLibrary2.user.controller;



import java.io.File;
import java.io.IOException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;

import com.example.syLibrary2.user.model.dao.MemberDAO;
import com.example.syLibrary2.user.model.dto.MemberDTO;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("user/member/*")
public class MemberController { 

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	MemberDAO memberDao;
	
		    @GetMapping("join.do")
		    public String join(
		            @RequestParam("mname") String m_name,
		            @RequestParam("mId") String m_Id,
		            @RequestParam("mPasswd") String m_Passwd,
		            @RequestParam("mTel") String m_Tel,
		            @RequestParam("mAddress") String m_Address,
		            @RequestParam("mEmail") String m_Email,
		            @RequestParam("mZipNo") String m_ZipNo,
		            @RequestParam("mBirthDate") String m_BirthDate,
		            @RequestParam("mDetailAddress") String m_DetailAddress,
		            @RequestParam(value = "mImg", required = false) MultipartFile m_Img,
		            HttpServletRequest request,  Model model )    {
		    	
		    	
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
		            		            
		           System.out.println("mname:"+  m_name);
		           System.out.println("mId:"+  m_Id);
		           System.out.println("mPasswd:"+  m_Passwd);
		           System.out.println("mTel:"+  m_Tel);

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
		


@GetMapping("detail_memberInfo.do")
public String detail_memberInfo(HttpServletRequest request) {
	String m_Id = request.getParameter("m_Id");
	return m_Id;
}

@GetMapping("edit_memberInfo.do")
public String edit_memberInfo(HttpServletRequest request) {
	return null;

}


	
}
	
	