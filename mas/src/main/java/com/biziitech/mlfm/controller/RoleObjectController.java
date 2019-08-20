package com.biziitech.mlfm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class RoleObjectController {
	
	
	@RequestMapping(path="/role/object")
	public String roleObject(Model model) {
	
	
		
		return "role_object";
	
	}
	

}
