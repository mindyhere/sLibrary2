package com.example.syLibrary2.admin.model.dao;

import com.example.syLibrary2.admin.model.dto.EmailDTO;

public interface EmailService {
	void sendMail(EmailDTO dto);

	//void sendMailPw(EmailDTO emailPw);
}
