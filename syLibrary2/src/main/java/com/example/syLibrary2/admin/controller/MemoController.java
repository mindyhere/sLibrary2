package com.example.syLibrary2.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.syLibrary2.admin.model.dao.AdminDAO;
import com.example.syLibrary2.admin.model.dao.MemoDAO;
import com.example.syLibrary2.admin.model.dto.MemoDTO;
import com.example.syLibrary2.util.PageUtil2;

import jakarta.servlet.http.HttpSession;

//json객체 => RestController
@Controller
@RequestMapping("admin/memo/*")
public class MemoController {
	
	@Autowired
	MemoDAO memoDao;
	
	@Autowired
	AdminDAO adminDao;
	
	@GetMapping("list.do")
	public ModelAndView list(@RequestParam(name="curPage", defaultValue="1") int curPage) {
		int count = memoDao.count(); //레코드 개수
		PageUtil2 page_info = new PageUtil2(count, curPage);
		int start = page_info.getPageBegin();
		int end = page_info.getPageEnd();
		List<MemoDTO> list =memoDao.list(start, end); //게시물 리스트
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/main/memo_list");
		Map<String,Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		map.put("page_info", page_info);
		mav.addObject("map", map);
		//mav.setViewName("admin/admin_main");
		return mav;
	}
	
	@PostMapping("insert.do")
	public String insert(MemoDTO dto, HttpSession session) {
		String me_a_id = (String) session.getAttribute("me_a_id");
		String me_memo = (String) session.getAttribute("me_memo");
		dto.setMe_a_id(me_a_id);
		dto.setMe_memo(me_memo);
		memoDao.insert(dto);
		return "redirect:/admin/list.do";
		//return "redirect:/admin/list.do";
	}
	
	@PostMapping("delete.do")
	public String delete(@RequestParam(name="me_rownum")int me_rownum) {
		memoDao.delete(me_rownum);
		return "redirect:/admin/list.do";
	}
	
	@PostMapping("update.do")
	public String update(MemoDTO dto){
//		int me_rownum = Integer.parseInt(request.getParameter("me_rownum"));
//		String me_a_id = request.getParameter("me_a_id");
//		String me_memo = request.getParameter("me_memo");
//		dto.setMe_a_id(me_a_id);
//		dto.setMe_rownum(me_rownum);
//		dto.setMe_memo(me_memo);
		memoDao.update(dto);
		return "redirect:/admin/list.do";
	}
	
	//메모상세 (확인요망)
	@RequestMapping("/search/{me_rownum}")
	public ResponseEntity<String> detail(@PathVariable(name="me_rownum") int me_rownum, @RequestBody MemoDTO dto) {
		ResponseEntity<String> entity = null;
		try{
			//dto.setMe_rownum(me_rownum);
			memoDao.search(me_rownum);
			entity = new ResponseEntity<String>("success",HttpStatus.OK);
		} catch (Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return entity;
		
//		memoDao.search(me_rownum);
//	    JsonObject jso = new JsonObject();
//		
//		
//		ModelAndView mav = new ModelAndView();
//		mav.setViewName("board/view");
//		mav.addObject("dto", boardService.detail(idx));
//		mav.addObject("count", replyDao.count(idx));
//		mav.addObject("cur_page", cur_page);
//		mav.addObject("search_option", search_option);
//		mav.addObject("keyword", keyword);
//		return mav;
	}
	
}
