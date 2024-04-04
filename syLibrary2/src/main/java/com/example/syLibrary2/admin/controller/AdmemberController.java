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
import com.example.syLibrary2.user.model.dto.MemberDTO;
import com.example.syLibrary2.util.PageUtil;

@Controller
@RequestMapping("admin/admember/*")
public class AdmemberController {
	
	@Autowired
	AdmemberDAO dao;
	
//	@Autowired
//	LoanDAO dao1;
	
	@GetMapping("list.do")
	public ModelAndView list(@RequestParam(name="curPage", defaultValue="1") int curPage) {
		int count = dao.count(); //레코드 개수
		PageUtil page = new PageUtil(count, curPage);
		int start = page.getPageBegin();
		int end = page.getPageEnd();
		List<MemberDTO> list =dao.list(start, end); //게시물 리스트
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/member/admin_m_list");
		Map<String,Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		map.put("page", page);
		mav.addObject("map", map);
		System.out.println("리스트:"+list);
		return mav;
	}
	
	@RequestMapping("search.do")
	public ModelAndView search(@RequestParam(name="curPage", defaultValue="1") int curPage, @RequestParam(name="search_option", defaultValue="all") String search_option, @RequestParam(name="search", defaultValue="") String search) {
		int count = dao.search_count(search_option, search); //레코드 개수
		PageUtil page = new PageUtil(count, curPage);
		int start = page.getPageBegin();
		int end = page.getPageEnd();
//		if (search_option == "" || search == "") {
//			count = dao.count();
//		} else {
//			count = dao.search_count(search_option, search);
//		}
		
//		List<MemberDTO> list = null;
//		Map<String,Object> map = new HashMap<>();
//    	if (search_option == "" || search == "") {
//    		list = dao.list(start, end);
//		} else {
//			list = dao.ad_list_search(search_option,search,start,end);
//			map.put("search_option", search_option);
//			map.put("search", search);
//		}
    	
		//list=dao.list_search(search_option, search,start, end); //게시물 리스트
		List<MemberDTO> list = dao.ad_list_search(search_option,search,start,end);
		Map<String,Object> map = new HashMap<>();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/member/admin_m_search");
		map.put("search_option", search_option);
		map.put("search", search);
		map.put("list", list);
		map.put("count", count);
		map.put("page", page);
		mav.addObject("map", map);
		System.out.println("검색결과:"+list);
		return mav;
	}
	
	@GetMapping("ad_list_detail.do")
	public ModelAndView detail(@RequestParam(name="m_no") int m_no ) {
		
		//List<LoanDTO> mem_list = dao1.lo_memlist(m_no);
		MemberDTO de_list = dao.ad_list_detail(m_no);
		
//		LoanDTO loan_y = dao1.loan_y(m_no);
//		LoanDTO reser_y = dao1.reser_y(m_no);
		Map<String,Object> map = new HashMap<>();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/member/mem_detail");
		//mav.addObject("mem_list", mem_list); //회원상세 리스트
		mav.addObject("de_list", de_list); //대출현황 리스트
		//mav.addObject("loan_y", loan_y); //대출현황 카운트
		//mav.addObject("reser_y", reser_y); //예약현황 카운트
		return mav;
	}
}
