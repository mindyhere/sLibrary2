package com.example.syLibrary2.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.syLibrary2.user.model.dao.ResbookDAO;

@Controller
@RequestMapping("/")
public class ResBookController {


	    @Autowired
	    private ResbookDAO dao;
}
