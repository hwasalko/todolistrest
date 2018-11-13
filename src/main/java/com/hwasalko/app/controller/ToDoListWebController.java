package com.hwasalko.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author DongJin Lee
 * @since 2018.11.13
 * 
 * ToDo 리스트 화면(View)을 위한 웹 컨트롤러
 * (REST API 컨트롤러와 구분하여 사용)
 */

@Controller
public class ToDoListWebController {

	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("name", "TODO 리스트");
		return "index";
	}
	
}