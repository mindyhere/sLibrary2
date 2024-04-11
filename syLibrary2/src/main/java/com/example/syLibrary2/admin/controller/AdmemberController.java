package com.example.syLibrary2.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.syLibrary2.admin.model.dao.AdmemberDAO;
import com.example.syLibrary2.admin.model.dto.LoanDTO;
import com.example.syLibrary2.user.model.dto.MemberDTO;
import com.example.syLibrary2.util.PageUtil;

@Controller
@RequestMapping("admin/admember/*")
public class AdmemberController {
	
	@Autowired
	AdmemberDAO dao;
	
	@RequestMapping("list.do")
	public ModelAndView list(@RequestParam(name="curPage", defaultValue="1") int curPage, @RequestParam(name="search_option", defaultValue="all") String search_option, @RequestParam(name="search", defaultValue="") String search) {
		int count = dao.count(search_option, search); //레코드 개수
		PageUtil page = new PageUtil(count, curPage);
		int start = page.getPageBegin();
		int end = page.getPageEnd();
		List<MemberDTO> list = dao.list_search(search_option,search,start,end);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/member/admin_m_list");
		Map<String,Object> map = new HashMap<>();
		map.put("search_option", search_option);
		map.put("search", search);
		map.put("list", list);
		map.put("count", count);
		map.put("page", page);
		mav.addObject("map", map);
		return mav;
	}
	
	@GetMapping("ad_list_detail.do")
	public ModelAndView detail(@RequestParam(name="m_no") int m_no ) {
		
		List<LoanDTO> mem_list = dao.lo_memlist(m_no);
		MemberDTO de_list = dao.ad_list_detail(m_no);
		LoanDTO over = dao.loan(m_no);
		
		ModelAndView mav = new ModelAndView();
		Map<String,Object> map = new HashMap<>();
		mav.setViewName("admin/member/mem_detail");
		map.put("de_list", de_list); //회원상세 리스트
		map.put("mem_list", mem_list); //대출현황 리스트
		map.put("over", over); //대출예약현황, 연체확인
		
		mav.addObject("map", map);
		return mav;
	}
}
