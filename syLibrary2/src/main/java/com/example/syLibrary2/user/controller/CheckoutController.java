package com.example.syLibrary2.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.syLibrary2.user.model.dao.CheckoutDAO;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/checkout/*")
public class CheckoutController {
	@Autowired
	CheckoutDAO checkoutDao;

	@GetMapping("{b_id}")
	public String checkMloan(@PathVariable(name = "b_id") int b_id, HttpSession session) {
		String m_id = (String) session.getAttribute("mId");
		System.out.println("checkMloan확인 : " + b_id + ", " + m_id);
		int m_loan = checkoutDao.checkMloan(m_id);
		String resultPage = "";
		switch (m_loan) {
		case 1:// 도서대출 중복신청 여부 확인
			Map<String, Object> map = new HashMap<>();
			map.put("m_id", m_id);
			map.put("b_id", b_id);
			int result = checkoutDao.duplicate(map) > 0 ? 0 : 1; // 0(대출불가-중복신청) or 1(대출가능)
			if (checkoutDao.duplicate(map) > 0) {
//				resultPage = "fail";
			} else {
				resultPage = "redirect:" + b_id + "/insert";
			}
			break;
		case 0:
//			resultPage = "fail";
			break;
		}
		System.out.println("이동확인 : " + resultPage);
		return resultPage;
	}

	@Transactional
	@ResponseBody
	@GetMapping("{b_id}/insert")
	public String insert(@PathVariable(name = "b_id") int b_id, HttpSession session) {
		String m_id = (String) session.getAttribute("mId");
		System.out.println("insert 확인 : " + b_id + ", " + m_id);
		Map<String, Object> map = new HashMap<>();
		map.put("m_id", m_id);
		map.put("b_id", b_id);
		checkoutDao.insert(map);
		String result = "신청이 완료되었습니다";
		System.out.println("결과 확인 : " + result);
		return result;
	}
}
