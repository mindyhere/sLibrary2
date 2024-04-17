package com.example.syLibrary2.admin.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.syLibrary2.admin.model.dao.EmailService;
import com.example.syLibrary2.admin.model.dto.EmailDTO;

@Controller
@RequestMapping("/email/*")
public class EmailController {
	@Autowired
	EmailService emailService;

	@ResponseBody
	@RequestMapping("send.do")
	public String send(@RequestParam (name="receiveMail") String receiveMail, @RequestParam (name="subject") String subject, @RequestParam (name="message") String message) {
		EmailDTO dto = new EmailDTO();	
		dto.setSubject(subject);
		dto.setMessage(message);
		dto.setReceiveMail(receiveMail);
		dto.setSenderName("SY Library");
		dto.setSenderMail("hjahee@gmail.com");
		System.out.println(dto);
		String result = "";
		try {
			emailService.sendMail(dto);
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			result = "fail";
		}
		System.out.println(result);
		return result;
	}

}
