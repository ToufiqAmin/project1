package com.biziitech.mlfm.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biziitech.mlfm.bg.daoimp.DaoBranchImp;
import com.biziitech.mlfm.bg.daoimp.DaoBranchUnitImp;
import com.biziitech.mlfm.bg.daoimp.DaoBranchUnitLocImp;

import com.biziitech.mlfm.bg.daoimp.DaoOrgGroupImp;
import com.biziitech.mlfm.bg.daoimp.DaoOrgImp;
import com.biziitech.mlfm.bg.model.ModelBranch;
import com.biziitech.mlfm.bg.model.ModelBranchUnit;
import com.biziitech.mlfm.bg.model.ModelBranchUnitLoc;
import com.biziitech.mlfm.bg.model.ModelOrg;

import com.biziitech.mlfm.bg.model.ModelOrgGroup;
import com.biziitech.mlfm.repository.BgBranchUnitRepository;
import com.biziitech.mlfm.repository.BgBranchUnitLocRepository;

@Controller
public class BranchUnitController {
	
	@Autowired
	private DaoBranchUnitImp daoBranchUnitImp;
	@Autowired
	private DaoOrgGroupImp daoOrgGroupImp;
	@Autowired
	private DaoOrgImp daoOrgImp;
	@Autowired
	private DaoBranchImp daoBranchImp;

	@Autowired
	private DaoBranchUnitLocImp daoBranchUnitLocImp;
	@Autowired
	private BgBranchUnitRepository BgBranchUnitRepository;
	
	@Autowired
	private BgBranchUnitLocRepository BgBranchUnitLocRepository;
	
	
	
	 @RequestMapping(path = "/branchUnit/init", method = RequestMethod.GET)
	    public String createBranchUnit(Model model) {
		 
		 ModelBranchUnit modelBranchUnit= new ModelBranchUnit();
		 modelBranchUnit.setActive(true);
		 model.addAttribute("branchUnit", modelBranchUnit);
		 
		 List<ModelOrgGroup> orgGroupList= daoOrgGroupImp.getOrgGroupName();
		 model.addAttribute("orgGroupList", orgGroupList);
		 
		 List<ModelOrg> orgList= daoOrgImp.getOrgName();
		 model.addAttribute("orgList", orgList);
		 
		 List<ModelBranch> branchList= daoBranchImp.getBranchList();
		 model.addAttribute("branchList", branchList);
		 
//		 List<ModelOrgBranch> orgBranchList = daoOrgBranchImp.getOrgBranchList();
//		 model.addAttribute("orgBranchList", orgBranchList);
		 
		 ModelBranchUnitLoc modelBranchUnitLoc= new ModelBranchUnitLoc();
		 modelBranchUnitLoc.setActive(true);
		 model.addAttribute("branchUnitLoc", modelBranchUnitLoc);
		 
		 List<ModelBranchUnit> branchUnitList2= daoBranchUnitImp.getBranchUnitList();
		 model.addAttribute("branchUnitList2", branchUnitList2);
		 
	        return "branchUnit";
	}
	 
	 
	 @RequestMapping(path="/branchUnit/save", method = RequestMethod.POST)
	 public String saveBranchUnitInquery(ModelBranchUnit modelBranchUnit, Model model) 
	 {
		 if(modelBranchUnit.getBranchUnitId()==null) {
		 java.util.Date date = new java.util.Date();
		 java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		 modelBranchUnit.setEntryTimeStamp(entryTime);
		 modelBranchUnit.setEnteredBy(1);
	 	 daoBranchUnitImp.saveBranchUnit(modelBranchUnit);
	 	 }
		 else {
			 
			 	Optional<ModelBranchUnit> existBranchUnit= BgBranchUnitRepository.findById(modelBranchUnit.getBranchUnitId());
			 	java.util.Date date = new java.util.Date();
				java.sql.Timestamp updateTime = new java.sql.Timestamp(date.getTime());
				modelBranchUnit.setUpdateTimeStamp(updateTime);
				modelBranchUnit.setEntryTimeStamp(existBranchUnit.get().getEntryTimeStamp());
				modelBranchUnit.setEnteredBy(existBranchUnit.get().getEnteredBy());
				existBranchUnit.get().setUpdateTimeStamp(updateTime);
				daoBranchUnitImp.saveBranchUnit(modelBranchUnit);
			 
		 }
	 
	 	 model.addAttribute("branchUnit",modelBranchUnit);
	 
		 return "branchUnit";
	 }
	 
	 
	 @RequestMapping(path="/branchUnitLoc/save", method = RequestMethod.POST)
	 public String saveBranchUnitLocInquery(ModelBranchUnitLoc modelBranchUnitLoc, Model model) 
	 	 {
	 		 if(modelBranchUnitLoc.getBranchUnitLocId()==null) {
	 		 java.util.Date date = new java.util.Date();
	 		 java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
	 		 modelBranchUnitLoc.setEntryTimeStamp(entryTime);
	 		 modelBranchUnitLoc.setEnteredBy(1);
	 	 	 daoBranchUnitLocImp.saveBranchUnitLoc(modelBranchUnitLoc);
	 	 	 }
	 		 else {
	 			 
	 			 	Optional<ModelBranchUnitLoc> existBranchUnitLoc= BgBranchUnitLocRepository.findById(modelBranchUnitLoc.getBranchUnitLocId());
	 			 	java.util.Date date = new java.util.Date();
	 				java.sql.Timestamp updateTime = new java.sql.Timestamp(date.getTime());
	 				modelBranchUnitLoc.setUpdateTimeStamp(updateTime);
	 				modelBranchUnitLoc.setEntryTimeStamp(existBranchUnitLoc.get().getEntryTimeStamp());
	 				modelBranchUnitLoc.setEnteredBy(existBranchUnitLoc.get().getEnteredBy());
	 				existBranchUnitLoc.get().setUpdateTimeStamp(updateTime);
	 				daoBranchUnitLocImp.saveBranchUnitLoc(modelBranchUnitLoc);
	 			 
	 		 }
	 	 
	 	 	 model.addAttribute("branchUnitLoc",modelBranchUnitLoc);
	 	 	 
	 	 	 ModelBranchUnit modelBranchUnit= new ModelBranchUnit();
			 modelBranchUnit.setActive(true);
			 model.addAttribute("branchUnit", modelBranchUnit);
	 	 
	 		 return "branchUnit";
	 	 }
	 
	 
	 
	 
	 
	 
	 
	 @RequestMapping(path="/branchUnit/search")
	 public String searchBranchUnitInquery(Model model, @RequestParam("branchId")Long branchId) {

		 if(branchId==0) {
			 List<ModelBranchUnit> BranchUnitList = daoBranchUnitImp.getBranchUnitList();
			 model.addAttribute("branchUnitList", BranchUnitList);
		 }
		 else {
		 List<ModelBranchUnit> branchUnitList= daoBranchUnitImp.getBranchListByCraiteria(branchId);
		 model.addAttribute("branchUnitList", branchUnitList);
		 }
		 

		 
		 
		 
		 ModelBranchUnit modelBranchUnit= new ModelBranchUnit();
		 modelBranchUnit.setActive(true);
		 model.addAttribute("branchUnit", modelBranchUnit);
		 
//		 List<ModelOrgBranch> orgBranchList = daoOrgBranchImp.getOrgBranchList();
//		 model.addAttribute("orgBranchList", orgBranchList);
		 
		 ModelBranchUnitLoc modelBranchUnitLoc= new ModelBranchUnitLoc();
		 modelBranchUnitLoc.setActive(true);
		 model.addAttribute("branchUnitLoc", modelBranchUnitLoc);
		 
		 List<ModelOrgGroup> orgGroupList= daoOrgGroupImp.getOrgGroupName();
		 model.addAttribute("orgGroupList", orgGroupList);
		 
		 List<ModelOrg> orgList= daoOrgImp.getOrgName();
		 model.addAttribute("orgList", orgList);
		 
		 List<ModelBranch> branchList= daoBranchImp.getBranchList();
		 model.addAttribute("branchList", branchList);
		 
		 List<ModelBranchUnit> branchUnitList2= daoBranchUnitImp.getBranchUnitList();
		 model.addAttribute("branchUnitList2", branchUnitList2);
		 
		 
		 return "branchUnit";
		}
	 
	 
	 @RequestMapping(path="/branchUnit/branchUnitLoc/search/{id}")
	 public String searchBranchUnitLocInquery(@PathVariable("id")Long id,Model model) {
		
		 model.addAttribute("branchUnitLocList", daoBranchUnitLocImp.getBranchUnitLocListByBranchUnitId(id));
		 
//		 List<ModelOrgBranch> orgBranchList = daoOrgBranchImp.getOrgBranchList();
//		 model.addAttribute("orgBranchList", orgBranchList);
		 
		 List<ModelBranch> branchList= daoBranchImp.getBranchList();
		 model.addAttribute("branchList", branchList);
		 
		 ModelBranchUnit modelBranchUnit= new ModelBranchUnit();
		 modelBranchUnit.setActive(true);
		 model.addAttribute("branchUnit", modelBranchUnit);
		 
		 
		 List<ModelBranchUnit> branchUnitList= daoBranchUnitImp.getBranchUnitList();
		 model.addAttribute("branchUnitList", branchUnitList);
		 
		 ModelBranchUnitLoc modelBranchUnitLoc= new ModelBranchUnitLoc();
		 modelBranchUnitLoc.setActive(true);
		 model.addAttribute("branchUnitLoc", modelBranchUnitLoc);
		 
		 
		 List<ModelOrgGroup> orgGroupList= daoOrgGroupImp.getOrgGroupName();
		 model.addAttribute("orgGroupList", orgGroupList);
		 
		 List<ModelOrg> orgList= daoOrgImp.getOrgName();
		 model.addAttribute("orgList", orgList);
		 
		 List<ModelBranchUnit> branchUnitList2= daoBranchUnitImp.getBranchUnitList();
		 model.addAttribute("branchUnitList2", branchUnitList2);
		 
		 
		 return "branchUnit";
		}
	 
	 
	 @ResponseBody
	 @RequestMapping(path = "/unit/edit/{id}", method = RequestMethod.GET)
	 	 public Optional<ModelBranchUnit> getBranchUnitById(@PathVariable("id")Long id){
	 		 

	 	 
	 		 return this.daoBranchUnitImp.getBranchUnitById(id);
	 		 
	 	 }
	 
	 
	 @ResponseBody
	 @RequestMapping(path = "/unitLoc/edit/{id}", method = RequestMethod.GET)
	 	 	 public Optional<ModelBranchUnitLoc> getBranchUnitLocById(@PathVariable("id")Long id){
	 	 		 

	 	 	 
	 	 		 return this.daoBranchUnitLocImp.getBranchUnitLocById(id);
	 	 		 
	 	 	 }
	 
	 
	 
	 
	 @ResponseBody
	 @RequestMapping(path="/branchUnit/new/org")
	 public List<ModelOrg> findOrg(@RequestParam("orgGroup")Long orgGroupId){
	 			
	 return daoBranchUnitImp.getOrgByOrgGroup(orgGroupId);
	 }
	 
	 
	 @ResponseBody
	 @RequestMapping(path="/branchUnit/new/branch")
	 public List<ModelBranch> findBranch(@RequestParam("org")Long orgId){
	 			
	 return daoBranchUnitImp.getBranchByOrg(orgId);
	 }
	 
	 
	 

}
