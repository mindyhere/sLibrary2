package com.example.syLibrary2.user.controller;

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

import com.example.syLibrary2.user.model.dao.MyLibraryDAO;
import com.example.syLibrary2.user.model.dto.MyLibraryDTO;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("user/book/*")
public class MyLibraryController {

	@Autowired
	MyLibraryDAO myLibraryDao;

	// 나의 서재 페이지
	@GetMapping("myLibrary/{mId}")
	public ModelAndView myLibrary(HttpSession session, ModelAndView mav) {
		String mId = (String) session.getAttribute("mId");
		MyLibraryDTO myLibrary = myLibraryDao.myLibrary(mId);
		mav.setViewName("user/book/myLibrary");
		mav.addObject("myLibrary", myLibrary);
		return mav;
	}

	// 도서 통계
	@PostMapping("createChart")
	@ResponseBody
	public List<Map<String, Object>> myChart(@RequestParam(name = "m_no") int m_no,
			@RequestParam(name = "type") String type) {
		int mNo = m_no;
		String title = "";
		List<Map<String, Object>> categoryChart = null;
		if (type.equals("cate")) { // 카테고리별 통계
			categoryChart = myLibraryDao.cateChart(mNo);
			title = "카테고리별 통계";
		} else if (type.equals("month")) { // 반기별 통계
			categoryChart = myLibraryDao.monthChart(mNo);
			title = "반기별 통계";
		} else if (type.equals("year")) { // 연도별 통계
			categoryChart = myLibraryDao.yearChart(mNo);
			title = "연도별 통계";
		}

		List<Map<String, Object>> chartResult = new ArrayList<>();
		for (int i = 0; i < categoryChart.size(); i++) {
			Map<String, Object> result = new HashMap<>();
			result.put("category", categoryChart.get(i).get("CATEGORY"));
			result.put("cnt", categoryChart.get(i).get("TOTAL_CNT"));
			result.put("title", title);
			chartResult.add(result);
		}
		return chartResult;
	}

	// 대출 중인 도서
	@GetMapping("myLoanBook/{m_no}")
	public ModelAndView myLoanBook(@PathVariable(name = "m_no") int m_no, ModelAndView mav) {
		int mNo = m_no;
		List<MyLibraryDTO> myLoanBook = myLibraryDao.myLoanBook(mNo);
		mav.setViewName("user/book/myLoanBook");
		mav.addObject("myLoanBook", myLoanBook);
		return mav;
	}

	// 도서 연장신청
	@PostMapping("renew")
	@ResponseBody
	public int renewBook(@RequestParam(name = "m_no") int m_no, @RequestParam(name = "b_id") int b_id) {
		Map<String, Object> map = new HashMap<>();
		map.put("mNo", m_no);
		map.put("bId", b_id);

		// 예약여부 확인절차
		int reCnt = myLibraryDao.checkReservation(b_id);
		// 연장여부 확인절차
		String renewYn = myLibraryDao.checkRenewYn(map);
		// 연체상태 확인절차
		int overReturnYn = myLibraryDao.checkOverReturn(m_no);
		// 상태코드
		int status = 0;
		if (reCnt == 0) {
			if (renewYn.equals("N")) {
				if (overReturnYn < 0) {
					myLibraryDao.updateReturn(map);
					status = 0;
				} else { // 연체상태
					status = 1;
				}
			} else if (renewYn.equals("Y")) {
				status = 2;
			}
		} else if (reCnt > 0) {
			status = 3;
		}
		return status;
	}
	// 예약 중인 도서

	// 대출 이력
	@GetMapping("myHistory/{m_no}")
	public ModelAndView myHistory(@PathVariable(name = "m_no") int m_no, ModelAndView mav) {
		int mNo = m_no;
		List<MyLibraryDTO> myHistory = myLibraryDao.myHistory(mNo);
		mav.setViewName("user/book/myHistory");
		mav.addObject("myHistory", myHistory);
		return mav;
	}
}
