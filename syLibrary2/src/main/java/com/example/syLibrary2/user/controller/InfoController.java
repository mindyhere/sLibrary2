package com.example.syLibrary2.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user/info/*")
public class InfoController {

	@GetMapping("guide")
	public String guide() {
		return "user/info/guide";
	}

	@GetMapping("map")
	public String map() {
		return "user/info/map";
	}

	@GetMapping("sitemap")
	public String sitemap() {
		return "user/info/sitemap";
	}
}
