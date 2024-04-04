package com.example.syLibrary2.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.syLibrary2.admin.model.dao.ReBookDAO;

@Controller
public class ReBookController {
	
	@Autowired
	ReBookDAO dao;
	
	
}
