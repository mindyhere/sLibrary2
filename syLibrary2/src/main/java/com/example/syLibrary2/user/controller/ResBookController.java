package com.example.syLibrary2.user.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
	    ResbookDAO resbookDao;

		/*
		 * @GetMapping("myReBook") public String myReBook(HttpSession session, Model
		 * model) { String r_memno = (String) session.getAttribute("mId");
		 * List<ReBookDTO> myReBook = resbookDao.myReBook(r_memno);
		 * model.addAttribute("myReBook", myReBook); return "user/book/myReBook"; }
		 */
	 
	    @GetMapping("myReBook")
		  public ModelAndView myReBook(HttpSession session,  ModelAndView mav) { 
			  String r_memno = (String) session.getAttribute("mId");
		  List<ReBookDTO> myReBook = resbookDao.myReBook(r_memno);
		  mav.setViewName("user/book/myReBook"); 
		  mav.addObject("myReBook", myReBook);
		  return mav; 
		  }
		 
		 
		  
		  
		    @ResponseBody
		    @GetMapping("recheck_book")
		    public int recheckBook(@RequestParam("b_id") int bId, @RequestParam("m_id") String mId) {
		        Map<String, Object> map = new HashMap<>();
		        map.put("r_bookid", bId);
		        map.put("m_id", mId);

		        int reCnt = resbookDao.recheck_book(map);
		        int dupCnt = resbookDao.rechech_duplicate(map);

		        int status = 0;
		        if (reCnt < 3) {
		            if (dupCnt == 0) {
		                status = 3;
		                resbookDao.insert_book(map);
		            } else if (dupCnt == 1) {
		                status = 2;
		            }
		        } else if (reCnt >= 3) {
		            status = 1;
		        }
		        return status;
		    }
		 

		    @ResponseBody
		    @PostMapping("re_delete")
		    public String reDelete(@RequestParam("m_id") String mId, @RequestParam("r_bookid") int rBookId) {
		        Map<String, Object> map = new HashMap<>();
		        map.put("m_id", mId);
		        map.put("r_bookid", rBookId);
		        resbookDao.res_delete(map);
		        return mId;
		    }
		    
		    
		    
			@GetMapping("reservation")	  
		    @ResponseBody
		    public ModelAndView reservation(@RequestParam("mId") String r_memno, HttpServletResponse response) throws IOException {
		        List<ReBookDTO> myReBook = resbookDao.myReBook(r_memno);
		        List<Map<String, Object>> resultList = new ArrayList<>();
		        
		        for (int i = 0; i < myReBook.size(); i++) {
		            Map<String, Object> map = new HashMap<>();
		            map.put("r_reservation", ((ReBookDTO) myReBook).getR_reservation());
		            map.put("b_id", ((ReBookDTO) myReBook).getB_id());
		            map.put("b_name", ((ReBookDTO) myReBook).getB_name());
		            resultList.add(map);
		        }

		        response.setContentType("application/json");
		        PrintWriter out = response.getWriter();
		        out.print(new ObjectMapper().writeValueAsString(resultList));

		        ModelAndView mav = new ModelAndView();
		        mav.setViewName("redirect:/user/book/myReBook");
		        return mav;
		    }
	

		    @ResponseBody
		    @PostMapping("res_delete")
		    public String res_delete(@RequestParam("m_id") String mId, @RequestParam("b_id") int bId) {
		        Map<String, Object> map = new HashMap<>();
		        map.put("m_id", mId);
		        map.put("b_id", bId);
		        resbookDao.myres_delete(map);
		        return mId;
		    }

		    @PostMapping("res_book")
		    public String resBook(@RequestParam("b_id") int bId, @RequestParam("m_id") String mId) {
		        Map<String, Object> map = new HashMap<>();
		        map.put("r_bookid", bId);
		        map.put("m_id", mId);
		        resbookDao.insert_book(map);
		        return "redirect:/user/book/myReBook";
		    }
		}