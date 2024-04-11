package com.example.syLibrary2.user.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.syLibrary2.admin.model.dto.ReBookDTO;
import com.example.syLibrary2.user.model.dao.ResbookDAO;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("user/book/*")
public class ResBookController {

	@Autowired
	private ResbookDAO resbookDao;

	// 페이지로드
	@GetMapping("/{mId}")
	public ModelAndView myReBook(@PathVariable(name="mId") String mId, ModelAndView mav) {
		List<ReBookDTO> myReBook = resbookDao.myReBook(mId);
		mav.setViewName("user/book/myReBook"); 
		mav.addObject("myReBook", myReBook);
		return mav;  
	}
	
	// 예약 도서 리스트 확인창 관련 코드
	@ResponseBody
	@GetMapping("rebookAlert")
	public List<ReBookDTO> rebookAlert(HttpSession session, ModelAndView mav) {
		String r_memno = (String) session.getAttribute("mId");
		List<ReBookDTO> myReBook = resbookDao.myReBook(r_memno);
		System.out.println(myReBook);
		return myReBook;  
	}

	// 예약 가능여부 확인
	@GetMapping("recheck_book.do")
	@ResponseBody
	public int recheck_book(@RequestParam("b_id") int r_bookid, @RequestParam("m_id") String m_id) {
		System.out.println("000 확인=" + r_bookid + ", " + m_id);
		Map<String, Object> map = new HashMap<>();
		map.put("r_bookid", r_bookid); // 예약된 도서 ID
		map.put("m_id", m_id); // 회원 ID
		// 해당 도서의 예약 횟수 확인
		int reCnt = resbookDao.recheck_book(map);
		// 초기 상태는 0 (예약 가능)으로 설정
		int status = 0;
		// 예약 가능 여부 확인
		if (reCnt < 3) { // 최대 예약 횟수가 3회 미만인 경우
			// 중복 예약 여부 확인
			int dupCnt = resbookDao.recheck_duplicate(map);
			if (dupCnt == 0) { // 중복 예약이 없는 경우
				status = 3; // 예약 가능
				// 예약 정보를 데이터베이스에 추가
				System.out.println("111 확인=" + r_bookid + ", " + m_id+", status="+status);
				resbookDao.insert_book(map);
			} else if (dupCnt == 1) { // 중복 예약이 있는 경우
				status = 2; // 중복 예약
				System.out.println("222 확인=" + r_bookid + ", " + m_id+", status="+status);
				
			}
		} else if (reCnt >= 3) { // 최대 예약 횟수가 3회 이상인 경우
			status = 1; // 예약 횟수 초과
			System.out.println("333 확인=" + r_bookid + ", " + m_id+", status="+status);
			
		}
		System.out.println("**결과="+status);
		
		return status;
	}

	/*
	 * @ResponseBody
	 * 
	 * @GetMapping("recheck_book") public int recheck_book(@RequestParam("b_id") int
	 * r_bookid, @RequestParam("m_id") String mId) { Map<String, Object> map = new
	 * HashMap<>(); map.put("b_id", r_bookid); map.put("m_id", mId);
	 * 
	 * int reCnt = resbookDao.recheck_book(map); int dupCnt =
	 * resbookDao.rechech_duplicate(map);
	 * 
	 * int status = 0; if (reCnt < 3) { if (dupCnt == 0) { status = 3;
	 * resbookDao.insert_book(map); } else if (dupCnt == 1) { status = 2; } } else
	 * if (reCnt >= 3) { status = 1; } return status; }
	 */
	// 도서 예약 삭제
	@ResponseBody
	@PostMapping("res_delete")
	public String reDelete(@RequestParam("m_id") String m_id, @RequestParam("r_bookid") int r_bookid) {
		Map<String, Object> map = new HashMap<>();
		map.put("m_id", m_id);
		map.put("r_bookid", r_bookid);
		resbookDao.res_delete(map);
		return m_id;
	}

	// 예약 순서일때 대출하기
	@GetMapping("reservation.do")
	/* @ResponseBody */
	public ModelAndView reservation(@RequestParam("mId") String r_memno, HttpServletResponse response)
			throws IOException {

		List<ReBookDTO> myReBook = resbookDao.myReBook(r_memno);

		List<Map<String, Object>> resultList = new ArrayList<>();
		for (int i = 0; i < myReBook.size(); i++) {
			Map<String, Object> map = new HashMap<>();
			map.put("r_reservation", ((ReBookDTO) myReBook).getR_reservation());
			map.put("b_id", ((ReBookDTO) myReBook).getB_id());
			map.put("b_name", ((ReBookDTO) myReBook).getB_name());
			resultList.add(map);
		}

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(new ObjectMapper().writeValueAsString(myReBook));

		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:user/book/myReBook");
		return mav;
	}

	// 대출 성공시 예약목록에서 삭제
	@PostMapping("myres_delete.do")
	@ResponseBody
	public String myResDelete(@RequestParam("m_id") String m_id, @RequestParam("b_id") int b_id) {
		Map<String, Object> map = new HashMap<>();
		map.put("m_id", m_id);
		map.put("b_id", b_id);
		resbookDao.myResDelete(map);
		return m_id;
	}

	// 예약 신청 기능
	@ResponseBody
	@PostMapping("res_book.do")
	public String resBook(@RequestParam("b_id") int r_bookid, @RequestParam("m_id") String r_memno) {
		System.out.println("페이지 넘어가는지");
		Map<String, Object> map = new HashMap<>();
		map.put("r_bookid", r_bookid);
		map.put("m_id", r_memno);
		resbookDao.insert_book(map);
		return "redirect: user/book/myReBook";
	}
}