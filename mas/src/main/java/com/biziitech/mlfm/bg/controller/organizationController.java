package com.biziitech.mlfm.bg.controller;






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
import com.biziitech.mlfm.bg.daoimp.DaoCountryImp;
import com.biziitech.mlfm.bg.daoimp.DaoOrgGroupImp;
import com.biziitech.mlfm.bg.daoimp.DaoOrgImp;
import com.biziitech.mlfm.bg.daoimp.DaoOrgTypeImp;
import com.biziitech.mlfm.bg.dao.DaoBranch;
import com.biziitech.mlfm.bg.dao.DaoOrg;
import com.biziitech.mlfm.bg.dao.DaoOrgGroup;
import com.biziitech.mlfm.bg.model.ModelBranch;
import com.biziitech.mlfm.bg.model.ModelCountry;
import com.biziitech.mlfm.bg.model.ModelOrg;
import com.biziitech.mlfm.bg.model.ModelOrgGroup;
import com.biziitech.mlfm.bg.model.ModelOrgType;
import com.biziitech.mlfm.repository.BgOrgRepository;
import com.biziitech.mlfm.repository.BgBranchRepository;
import com.biziitech.mlfm.repository.BgOrgGroupRepository;




@Controller
public class organizationController {
	
	@Autowired
	private DaoOrgGroup daoOrgGroup;
	
	@Autowired
	private DaoOrgTypeImp daoOrgTypeImp;
	
	@Autowired
	private DaoOrg daoOrg;
	
	@Autowired
	private DaoOrgImp daoOrgImp;
	
	@Autowired
	private DaoBranch daoBranch;
	
	@Autowired
	private DaoBranchImp daoBranchImp;
	
	@Autowired
	private DaoOrgGroupImp daoOrgGroupImp;
	
	@Autowired
	private DaoCountryImp daoCountryImp;
	
	@Autowired
	private BgOrgGroupRepository bgOrgGroupRepository;
	
	@Autowired
	private BgBranchRepository BgBranchRepository;
	
	
	@Autowired
	private BgOrgRepository BgOrgRepository;
	
	@RequestMapping(path="/organization/init")
	public String orgGroupEntry(Model model) {
		ModelOrgGroup modelOrgGroup= new ModelOrgGroup();
		modelOrgGroup.setActive(true);			
		model.addAttribute("orgGroup",modelOrgGroup);
		
		ModelOrg modelOrg= new ModelOrg();
		modelOrg.setActive(true);
		model.addAttribute("org",modelOrg);
		
		ModelBranch modelBranch= new ModelBranch();
		modelBranch.setActive(true);
		model.addAttribute("branch", modelBranch);
		
		
		List<ModelCountry> countryList= daoCountryImp.getCountryName();
		model.addAttribute("countryList",countryList);
		
		 return "organization";
	}
	
	
	@RequestMapping(path="/orgGroup/save", method = RequestMethod.POST)

	public String saveOrgGroupInquery(ModelOrgGroup modelOrgGroup, Model model) {
		

		if(modelOrgGroup.getOrgGroupId()==null) {
			
			java.util.Date date = new java.util.Date();
			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
			modelOrgGroup.setEntryTimestamp(entryTime);
			modelOrgGroup.setEnteredBy(1);
			
			
		
			this.daoOrgGroupImp.saveOrgGroup(modelOrgGroup);
			
		}
		else
		{
			Optional<ModelOrgGroup> existOrgGroup= bgOrgGroupRepository.findById(modelOrgGroup.getOrgGroupId());
			java.util.Date date = new java.util.Date();
			java.sql.Timestamp updateTime = new java.sql.Timestamp(date.getTime());
			
			modelOrgGroup.setUpdateTimestap(updateTime);
			modelOrgGroup.setEntryTimestamp(existOrgGroup.get().getEntryTimestamp());

			modelOrgGroup.setEnteredBy(existOrgGroup.get().getEnteredBy());
			existOrgGroup.get().setUpdateTimestap(updateTime);
			
			this.daoOrgGroupImp.saveOrgGroup(modelOrgGroup);
		}
		
		
					
		model.addAttribute("orgGroup",modelOrgGroup);
		
		

		 return "organization";
	}
	
	
	@RequestMapping(path="/orgGroup/search")
	/*
	 * Date: Dec 22, 2018 KTA
	 * the following search cannot return data while country id is blank in org. group table. 
	 * We should solve this problem later.
	 * 
	 */
    public String getOrgGroup(Model model,@RequestParam("groupName")String groupName,
    		@RequestParam("groupCode")String groupCode,@RequestParam("orgGroupRemarks")String orgGroupRemarks,
    		@RequestParam(value = "active", required = false)String active ) {
		
			int status=1;
		
		if(active!=null)
			status=1;
		else
			status=0;
		
		System.out.println("group name " + groupName + " group code " + groupCode + " group remarks " + orgGroupRemarks + "Status " + status);
		model.addAttribute("orgGroupList",daoOrgGroupImp.getOrgGroupListByCraiteria(groupName,groupCode,orgGroupRemarks,status));
    	
		
		
		
		ModelOrgGroup modelOrgGroup= new ModelOrgGroup();
		modelOrgGroup.setActive(true);			
		model.addAttribute("orgGroup",modelOrgGroup);
		
		ModelOrg modelOrg= new ModelOrg();
		modelOrg.setActive(true);
		model.addAttribute("org",modelOrg);
		
		ModelBranch modelBranch= new ModelBranch();
		modelBranch.setActive(true);
		model.addAttribute("branch", modelBranch);
		
		
		List<ModelCountry> countryList= daoCountryImp.getCountryName();
		model.addAttribute("countryList",countryList);
		
		List<ModelOrgType> orgTypeList= daoOrgTypeImp.getOrgTypeName();
		model.addAttribute("orgTypeList",orgTypeList);
		
//		List<ModelOrgGroup> orgGroupList= daoOrgGroupImp.findOrgGroup();
//		model.addAttribute("orgGroupList", orgGroupList);
		
		
		
		
		 return "organization";

		
    			
    }
	
	 @ResponseBody
	 @RequestMapping(path = "/orgGroup/edit/{id}", method = RequestMethod.GET)
	 public Optional<ModelOrgGroup> getGroupById(@PathVariable("id")Long id, Model model){
		 

	 
		 return this.daoOrgGroupImp.getGroupById(id);
		 
	 }
	 
	 
	 @ResponseBody
	 @RequestMapping(path = "/org/edit/{id}", method = RequestMethod.GET)
	 public Optional<ModelOrg> getOrgById(@PathVariable("id")Long id){
		 

	 
		 return this.daoOrgImp.getOrgById(id);
		 
	 }
	 
	 
	 @ResponseBody
	 @RequestMapping(path = "/branch/edit/{id}", method = RequestMethod.GET)
	 public Optional<ModelBranch> getBranchById(@PathVariable("id")Long id){
		 

	 
		 return this.daoBranchImp.getBranchById(id);
		 
	 }
	 
	 

	 @RequestMapping(path = "/orgGroup/org/save", method = RequestMethod.POST)
	 public String saveOrg(ModelOrg modelOrg, Model model){
		 if(modelOrg.getOrgId()==null) {
				
				java.util.Date date = new java.util.Date();
				java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
				modelOrg.setEntryTimestamp(entryTime);
				modelOrg.setEnteredBy(1);
			
				
			
				this.daoOrg.saveOrg(modelOrg);
				
			}else
			{
				Optional<ModelOrg> existOrg= BgOrgRepository.findById(modelOrg.getOrgId());
				java.util.Date date = new java.util.Date();
				java.sql.Timestamp updateTime = new java.sql.Timestamp(date.getTime());
				
				modelOrg.setUpdateTimestap(updateTime);
				modelOrg.setEntryTimestamp(existOrg.get().getEntryTimestamp());

				modelOrg.setEnteredBy(existOrg.get().getEnteredBy());
				existOrg.get().setUpdateTimestap(updateTime);
				
				this.daoOrgImp.saveOrg(modelOrg);
			}
			
			
			
						
			model.addAttribute("org",modelOrg);
			
			ModelOrgGroup modelOrgGroup= new ModelOrgGroup();
			modelOrgGroup.setActive(true);			
			model.addAttribute("orgGroup",modelOrgGroup);
	 
			return "organization";
		 
	 }
	 
	 
	 @RequestMapping(path="/branch/save", method = RequestMethod.POST)

		public String saveBranchInquery(ModelBranch modelBranch, Model model) {

	if(modelBranch.getBranchId()==null) {
				
				java.util.Date date = new java.util.Date();
				java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
				modelBranch.setEntryTimeStamp(entryTime);
				modelBranch.setEnteredBy(1);
				
				
			
				this.daoBranchImp.saveBranch(modelBranch);
				
			}
			else
			{
				Optional<ModelBranch> existBranch= BgBranchRepository.findById(modelBranch.getBranchId());
				java.util.Date date = new java.util.Date();
				java.sql.Timestamp updateTime = new java.sql.Timestamp(date.getTime());
				
				modelBranch.setUpdateTimeStamp(updateTime);
				modelBranch.setEntryTimeStamp(existBranch.get().getEntryTimeStamp());

				modelBranch.setEnteredBy(existBranch.get().getEnteredBy());
				existBranch.get().setUpdateTimeStamp(updateTime);
				
				this.daoBranchImp.saveBranch(modelBranch);
			}
			
			
						
			model.addAttribute("branch",modelBranch);
			ModelOrgGroup modelOrgGroup= new ModelOrgGroup();
			modelOrgGroup.setActive(true);			
			model.addAttribute("orgGroup",modelOrgGroup);
			
			return "organization";

	}
	 
	 

	
	 @RequestMapping(path="/orgGroup/org/search/{id}", method=RequestMethod.GET)
	    public String getOrg(@PathVariable("id")Long id,Model model ) {
		 
		 			 
			
		 	ModelOrgGroup modelOrgGroup= new ModelOrgGroup();
			modelOrgGroup.setActive(true);			
			model.addAttribute("orgGroup",modelOrgGroup);
			
			ModelOrg modelOrg= new ModelOrg();
			modelOrg.setActive(true);
			model.addAttribute("org",modelOrg);
			
			ModelBranch modelBranch= new ModelBranch();
			modelBranch.setActive(true);
			model.addAttribute("branch", modelBranch);
			
			
			List<ModelCountry> countryList= daoCountryImp.getCountryName();
			model.addAttribute("countryList",countryList);
			System.out.println("test2: ");
			
			List<ModelOrgType> orgTypeList= daoOrgTypeImp.getOrgTypeName();
			model.addAttribute("orgTypeList",orgTypeList);
			System.out.println("test3: "+orgTypeList);
			
			List<ModelOrgGroup> groupList= daoOrgGroupImp.findOrgGroup();
			model.addAttribute("orgGroupList", groupList);
			//model.addAttribute("orgGroup",modelOrgGroup);
			
//			List<ModelOrg> orgList= daoOrgImp.getOrgList();
			List<ModelOrg> orgList= daoOrgImp.getOrgByOrgGroupId(id);
			model.addAttribute("orgList", orgList);
			

			
			
			return "organization";
	    }
	 
	 @RequestMapping(path="/orgGroup/branch/search/{id}", method=RequestMethod.GET)
	    public String getOrgBranch(@PathVariable("id")Long id,Model model ) {
			
		 	model.addAttribute("orgGroupList", daoOrgGroupImp.findOrgGroup());
	
		 	model.addAttribute("branchList", daoBranch.getBranchByOrgId(id));
		 	
		 	List<ModelOrgType> orgTypeList= daoOrgTypeImp.getOrgTypeName();
			model.addAttribute("orgTypeList",orgTypeList);
		 	
		 	List<ModelOrg> orgList= daoOrgImp.getOrgList();
			model.addAttribute("orgList", orgList);
		 	
		 	List<ModelCountry> countryList= daoCountryImp.getCountryName();
			model.addAttribute("countryList",countryList);
			System.out.println("test2: ");
		 
			
		 	ModelOrgGroup modelOrgGroup= new ModelOrgGroup();
			modelOrgGroup.setActive(true);			
			model.addAttribute("orgGroup",modelOrgGroup);
			
			ModelOrg modelOrg= new ModelOrg();
			modelOrg.setActive(true);
			model.addAttribute("org", modelOrg);
			
			
			ModelBranch modelBranch= new ModelBranch();
			modelBranch.setActive(true);
			model.addAttribute("branch", modelBranch);
			
			
			return "organization";
	    }
	 
	 
	 
	 
	 
	 
	 
	
	
	

}
