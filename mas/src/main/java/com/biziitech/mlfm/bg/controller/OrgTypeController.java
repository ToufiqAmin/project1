package com.biziitech.mlfm.bg.controller;


import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.biziitech.mlfm.bg.daoimp.DaoOrgTypeImp;
import com.biziitech.mlfm.bg.model.ModelOrgType;
import com.biziitech.mlfm.repository.bgOrgTypeRepository;


@Controller
public class OrgTypeController {
			
	
		@Autowired
		private DaoOrgTypeImp daoOrgType;
		
		@Autowired
		private bgOrgTypeRepository bgOrgTypeRepository;

	@RequestMapping(path="/bgOrgType/init")
	public String orgTypeEntry(Model model) {
		 ModelOrgType modelOrgType = new  ModelOrgType();	
		model.addAttribute("bgOrgType",modelOrgType);
		modelOrgType.setActive(true);
		 return "bgOrgType_upload";
	}
	

	@RequestMapping(path="/bgOrgType/save", method =RequestMethod.POST)
	
	public String saveOrgType(ModelOrgType modelOrgType, Model model) {
		
		
		if(modelOrgType.getOrgTypeId()== null) {
			
		
			java.util.Date date = new java.util.Date();
			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
			modelOrgType.setEnteredBy(1);
			modelOrgType.setEntryTimestamp(entryTime);
			daoOrgType.saveOrgType(modelOrgType);
			
			
		
		}else {
			
			Optional<ModelOrgType> existsModelOrgType=bgOrgTypeRepository.findById(modelOrgType.getOrgTypeId());
			
			java.util.Date date = new java.util.Date();
			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
			modelOrgType.setEnteredBy(existsModelOrgType.get().getEnteredBy());
			modelOrgType.setEntryTimestamp(existsModelOrgType.get().getEntryTimestamp());
			modelOrgType.setUpdatedBy(2);
			modelOrgType.setUpdateTimestap(date);
			daoOrgType.saveOrgType(modelOrgType);
			
		}
		
//		daoOrgType.saveOrgType(modelOrgType);
		
		model.addAttribute("bgOrgType",modelOrgType);
		
		return "bgOrgType_upload";
	}
	
	 @RequestMapping(path = "/bgOrgType/update/{id}", method = RequestMethod.GET)
	    public String updateTest(@PathVariable("id") Long orgTypeId, Model model) {
	    	
	    	 Optional<ModelOrgType> bgOrgTypeById=bgOrgTypeRepository.findById(orgTypeId);
		 
	         model.addAttribute("bgOrgType", bgOrgTypeById);
	           
	         return "bgOrgType_upload";

	    } 	
	


}
