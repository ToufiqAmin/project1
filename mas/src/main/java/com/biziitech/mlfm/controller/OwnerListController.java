package com.biziitech.mlfm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biziitech.mlfm.bg.daoimp.DaoCountryImp;
import com.biziitech.mlfm.bg.daoimp.DaoPhoneImp;
import com.biziitech.mlfm.bg.model.ModelCountry;
import com.biziitech.mlfm.bg.model.ModelPhone;
import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoBusinessTypeImp;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerImp;
import com.biziitech.mlfm.model.ModelBusinessType;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.repository.OrderOwnerRepository;
import com.biziitech.mlfm.repository.OrderOwnerZoneRepository;
import com.biziitech.mlfm.repository.PhoneRepository;

@Controller
public class OwnerListController {

	
	@Autowired
	private DaoCountryImp country;
	
	@Autowired
	private DaoBusinessTypeImp businessType;
	
	@Autowired
	private DaoPhoneImp phone;
	@Autowired 
	private OrderOwnerRepository ownerRepository;
	@Autowired
	private DaoOrderOwnerImp owner;
	@Autowired
	private PhoneRepository phoneRepository;
	
	
	//created by sohel on 17/03/2019
	
	@Autowired 
	private OrderOwnerZoneRepository orderOwnerZoneRepository;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


private Long systemUserId;
	
	 @RequestMapping(path = "/owners/{userId}", method = RequestMethod.GET)
	    public String getAllOwner(@PathVariable Long userId,Model model) {
		 
		 System.out.println("user ID :" +userId);
		 ModelUser modelUser=new ModelUser();
		 modelUser.setUserId(userId);
		 this.systemUserId=userId;
		 ModelUser logonUser=daoLogonImp.getLogonUserName(userId);
		 String userName=logonUser.getUserName();
		 System.out.println("logon user name is :" + userName);
		 String name=userName;
		 model.addAttribute("name",name );	  
			
		 // left panel list
		        //  for setup
				List<ModelUserObject> listModelUserObjectSetup= new ArrayList<ModelUserObject>();
				listModelUserObjectSetup=daoUserObject.getUserObjectByObjectGroup(userId,"S");
				System.out.println("listModelUserObjectSetup : " + listModelUserObjectSetup);
				System.out.println("size " + listModelUserObjectSetup.size() );
				model.addAttribute("listModelUserObjectSetup", listModelUserObjectSetup);
				
				//for Transactions
				List<ModelUserObject> listModelUserObjectTransaction= new ArrayList<ModelUserObject>();
				listModelUserObjectTransaction=daoUserObject.getUserObjectByObjectGroup(userId,"T");
				System.out.println("listModelUserObjectTransaction : " + listModelUserObjectTransaction);
				System.out.println("size " + listModelUserObjectTransaction.size() );
				model.addAttribute("listModelUserObjectTransaction", listModelUserObjectTransaction);
				
		        //for Tools
				List<ModelUserObject> listModelUserObjectTool= new ArrayList<ModelUserObject>();
				listModelUserObjectTool=daoUserObject.getUserObjectByObjectGroup(userId,"U");
				System.out.println("listModelUserObjectTool : " + listModelUserObjectTool);
				System.out.println("listModelUserObjectTool size " + listModelUserObjectTool.size() );
				model.addAttribute("listModelUserObjectTool", listModelUserObjectTool);
				
			    //for reports
				List<ModelUserObject> listModelUserObjectReport= new ArrayList<ModelUserObject>();
				listModelUserObjectReport=daoUserObject.getUserObjectByObjectGroup(userId,"R");
				System.out.println("listModelUserObjectReport : " + listModelUserObjectReport);
				System.out.println("listModelUserObjectReport size " + listModelUserObjectReport.size());
				model.addAttribute("listModelUserObjectReport", listModelUserObjectReport);
			    
				//left panel list
				
		 
		 
		 List <ModelCountry> countryList= country.getCountryName()	;        
	        model.addAttribute("countryList",countryList);
	        List <ModelBusinessType> typeList=  businessType.getTypeName();
	        model.addAttribute("typeList",typeList);
	        
	    // model.addAttribute("owner", newOwner);
		 model.addAttribute("ownerList",new ArrayList<ModelOrderOwner>());
	//	 System.out.println("ALL LIST"+ownerRepository.findAll());
		 
		 
		//created by sohel on 17/03/2019
		 
		 model.addAttribute("zoneLists",orderOwnerZoneRepository.findOrderOwnerZone());
		 
	        return "buyer_data_list";
	}
	 
	
	
	@RequestMapping(path="/owners/search")
	 public String getAllOwner(Model model,@RequestParam("owner_name") String name,@RequestParam("shortCode") String code,@RequestParam("contactPerson") String contactPerson,
			 
			 				@RequestParam("phoneNumber") String phoneNumber,@RequestParam("countryName") Long countryId,
			 				@RequestParam(value = "active", required = false) String active, @RequestParam("zoneName") Long zoneId
			 ) {
		
		
		  System.out.println("system user :" + systemUserId);
		    Long userId=systemUserId;
		    
		     ModelUser logonUser=daoLogonImp.getLogonUserName(userId);
			 String userName=logonUser.getUserName();
			 System.out.println("logon user name is :" + userName);
			 String name1=userName;
			 model.addAttribute("name",name1 );
			 model.addAttribute("userId",userId);
		    // left panel list
		   
			        //  for setup
				    List<ModelUserObject> listModelUserObjectSetup= new ArrayList<ModelUserObject>();
					listModelUserObjectSetup=daoUserObject.getUserObjectByObjectGroup(userId,"S");
					System.out.println("listModelUserObjectSetup : " + listModelUserObjectSetup);
					System.out.println("size " + listModelUserObjectSetup.size() );
					model.addAttribute("listModelUserObjectSetup", listModelUserObjectSetup);
					
					//for Transactions
					List<ModelUserObject> listModelUserObjectTransaction= new ArrayList<ModelUserObject>();
					listModelUserObjectTransaction=daoUserObject.getUserObjectByObjectGroup(userId,"T");
					System.out.println("listModelUserObjectTransaction : " + listModelUserObjectTransaction);
					System.out.println("size " + listModelUserObjectTransaction.size() );
					model.addAttribute("listModelUserObjectTransaction", listModelUserObjectTransaction);
					
			        //for Tools
					List<ModelUserObject> listModelUserObjectTool= new ArrayList<ModelUserObject>();
					listModelUserObjectTool=daoUserObject.getUserObjectByObjectGroup(userId,"U");
					System.out.println("listModelUserObjectTool : " + listModelUserObjectTool);
					System.out.println("listModelUserObjectTool size " + listModelUserObjectTool.size() );
					model.addAttribute("listModelUserObjectTool", listModelUserObjectTool);
					
					//for reports
					List<ModelUserObject> listModelUserObjectReport= new ArrayList<ModelUserObject>();
					listModelUserObjectReport=daoUserObject.getUserObjectByObjectGroup(userId,"R");
					System.out.println("listModelUserObjectReport : " + listModelUserObjectReport);
					System.out.println("listModelUserObjectReport size " + listModelUserObjectReport.size());
					model.addAttribute("listModelUserObjectReport", listModelUserObjectReport);
				//left panel list
		
		
		
		int status=1;
		// the code needs to be modified. cak-Nov 4, 2018
		if(active!=null)
			status=1;
		else
			status=0;
		
	
	        List <ModelCountry> countryList= country.getCountryName();
	        
	        model.addAttribute("countryList",countryList);

	        List <ModelBusinessType> typeList=  businessType.getTypeName();
	        model.addAttribute("typeList",typeList);
	        
	        model.addAttribute("zoneLists",orderOwnerZoneRepository.findOrderOwnerZone());
	        
	       // System.out.println(phoneNumber+" "+active);
	        
	        
	        
	        System.out.println("zone id :" + zoneId);
		
		
		 // model.addAttribute("ownerList",owner.getAllOwner(countryId, name, code, status));
	        
	        model.addAttribute("ownerList",ownerRepository.findOwnerDetailsNew(status, countryId, name));
	        
	      
		 
	        return "buyer_data_list";
	}
	
	@ResponseBody
	@RequestMapping(path="/owners/search/phone/{id}")
	public List<ModelPhone> findOwnerPhone(Model  model, @PathVariable("id")Long id) {
		model.addAttribute("phoneList",phone.getUserPhoneData(id));
		return phone.getUserPhoneData(id);
		
	}
	
}
