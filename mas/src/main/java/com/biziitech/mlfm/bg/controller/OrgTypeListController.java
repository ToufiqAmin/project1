package com.biziitech.mlfm.bg.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.biziitech.mlfm.bg.dao.DaoOrgType;
import com.biziitech.mlfm.bg.daoimp.DaoOrgTypeImp;
import com.biziitech.mlfm.repository.bgOrgTypeRepository;



@Controller
public class OrgTypeListController {
	
	
	@Autowired
	private DaoOrgTypeImp OrgType;
	
	
	@Autowired 
	private bgOrgTypeRepository bgOrgTypeRepository;
	
	@Autowired 
	private DaoOrgType daoOrgType;
	
	@RequestMapping(path = "/bgOrgType/list", method = RequestMethod.GET)
    public String getOrgTypeList(Model model) {
	 
	   
        return "bgOrgType_list";
		}	
	
	
	@RequestMapping(path="/bgOrgType/search")
	public String getOrgTypeList(Model model,@RequestParam("orgTypeName")String orgTypeName,@RequestParam("shortCode")String shortCode
			,@RequestParam("orgTypeRemarks")String orgTypeRemarks, @RequestParam(value = "active", required = false) String active
	) {
		
        int status=1;
		
		if(active==null)
			status=0;
		
//		orgTypeName.trim();
		model.addAttribute("orgTypeList",daoOrgType.getOrgTypeListByCraiteria(orgTypeName.trim(), shortCode.trim(), orgTypeRemarks.trim(), status));	
		return "bgOrgType_list";
	}

}
