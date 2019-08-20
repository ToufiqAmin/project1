package com.biziitech.mlfm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class HomePageController {
	
	@RequestMapping(path="/homepagecontroller/init")
	public String init(Model model) {
		//ModelUser modelUser = new  ModelUser();
		//model.addAttribute("user",modelUser);
		
		return "home_page";
	}
	
	
	
	
	
	
	
	

}
