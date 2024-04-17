package com.example.syLibrary2.user.controller;

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

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("user/book/*")
public class ResBookController {

	@Autowired
	private ResbookDAO resbookDao;

	// 페이지로드
	@GetMapping("/{mId}")
	public ModelAndView myReBook(@PathVariable(name = "mId") String mId, ModelAndView mav) {
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
		Map<String, Object> map = new HashMap<>();
		map.put("r_bookid", r_bookid);
		map.put("m_id", m_id);
		int reCnt = resbookDao.recheck_book(map);
		int cntRes = resbookDao.recheck_level(map);
		int status = 0;

		if (cntRes == 5) {
			int dupCnt = resbookDao.recheck_duplicate(map);
			if (dupCnt == 0) {
				if (reCnt < 5) {
					status = 3;
					resbookDao.insert_book(map);
				} else {
					status = 1;
				}
			} else if (dupCnt == 1) {
				status = 2;
			}
		} else if (cntRes == 4) {
			int dupCnt = resbookDao.recheck_duplicate(map);
			if (dupCnt == 0) {
				if (reCnt < 4) {
					status = 3;
					resbookDao.insert_book(map);
				} else {
					status = 1;
				}
			} else if (dupCnt == 1) {
				status = 2;
			}
		} else if (cntRes == 3) {
			int dupCnt = resbookDao.recheck_duplicate(map);
			if (dupCnt == 0) {
				if (reCnt < 3) {
					status = 3;
					resbookDao.insert_book(map);
				} else {
					status = 1;
				}
			} else if (dupCnt == 1) {
				status = 2;
			}
		}
		return status;
	}

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

	// 대출 성공시 예약목록에서 삭제
	@PostMapping("myres_delete")
	@ResponseBody
	public String myResDelete(HttpSession session, @RequestParam("b_id") int b_id) {
		String m_id = (String) session.getAttribute("mId");
		Map<String, Object> map = new HashMap<>();
		map.put("m_id", m_id);
		map.put("b_id", b_id);
		resbookDao.myResDelete(map);
		return "신청완료";
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