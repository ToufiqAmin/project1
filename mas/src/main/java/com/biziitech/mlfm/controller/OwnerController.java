package com.biziitech.mlfm.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import com.biziitech.mlfm.bg.daoimp.DaoCountryImp;
import com.biziitech.mlfm.bg.daoimp.DaoPhoneImp;
import com.biziitech.mlfm.bg.model.ModelCountry;
import com.biziitech.mlfm.bg.model.ModelPhone;
import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.dao.DaoBusinessType;
import com.biziitech.mlfm.dao.DaoItemType;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoBusinessTypeImp;
import com.biziitech.mlfm.daoimpl.DaoBuyerImp;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerTypeImp;
import com.biziitech.mlfm.model.ModelBusinessType;
import com.biziitech.mlfm.model.ModelBuyer;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelOrderOwnerType;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.repository.BusinessTypeRepository;
import com.biziitech.mlfm.repository.BuyerRepository;
import com.biziitech.mlfm.repository.OrderOwnerRepository;
import com.biziitech.mlfm.repository.OrderOwnerZoneRepository;
import com.biziitech.mlfm.repository.PhoneRepository;


@Controller
public class OwnerController {

	
	@Autowired
	private DaoPhoneImp phone;
	
	@Autowired
	private DaoCountryImp country;
	
	@Autowired
	private DaoBusinessTypeImp businessType;
	

	
	@Autowired
	private DaoOrderOwnerImp orderOwner;
	
	@Autowired
	private DaoOrderOwnerTypeImp ownerType;
	
	@Autowired 
	private OrderOwnerRepository ownerRepository;
	
	@Autowired 
	private OrderOwnerZoneRepository orderOwnerZoneRepository;
	
	@Autowired
	private DaoUserObject daoUserObject;
	

	@Autowired
	private DaoItemType daoItemType;
	
	

	@Autowired
	private DaoLogonImp daoLogonImp;


private Long systemUserId;
	

	@RequestMapping(path = "/")
    public String index() {
        return "index";
}
	
	 @RequestMapping(path = "/owners/add/{userId}", method = RequestMethod.GET)
	    public String createBuyer(@PathVariable Long userId,Model model) {
		 
		 
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
		 
		 
		 
	
		 ModelOrderOwner newOwner= new ModelOrderOwner();
		 ModelCountry defaultCountry= new ModelCountry();
		 defaultCountry.setCountryId(1l);
		 
		 ModelBusinessType defaultType = new ModelBusinessType();
		 defaultType.setTypeId(1l);
		 
		 ModelOrderOwnerType defaultOwnerType= new ModelOrderOwnerType();
		 defaultOwnerType.setOrderOwnerTypeId(1l);
		 
		 newOwner.setOwnerCountry(defaultCountry);
		 newOwner.setModelBusinessType(defaultType);
		 newOwner.setOrderOwnerType(defaultOwnerType);
	        model.addAttribute("owner", newOwner);
	        String msg=" ";
	        model.addAttribute("message", " ");
	        List <ModelCountry> countryList= country.getCountryName();
	        
	        model.addAttribute("countryList",countryList);

	        List <ModelBusinessType> typeList=  businessType.getTypeName();
	        model.addAttribute("typeList",typeList);
	        
	       List<ModelOrderOwnerType> ownerTypeList= ownerType.getTypeName();
	        model.addAttribute("ownerTypeList",ownerTypeList);
	        
	        model.addAttribute("zoneList",orderOwnerZoneRepository.findOrderOwnerZone());
	        
	        
	        ModelPhone phone= new ModelPhone();
	        
	        
	        
	        ModelCountry defaultC= new ModelCountry();
            defaultC.setCountryId(1l);
			phone.setModelCountry(defaultC);
			
	        model.addAttribute("phone",phone);
	        
	        List<ModelPhone> numberList= new ArrayList<>();
	        
	        model.addAttribute("phoneList",numberList);
	        
	        return "buyer_data_upload";
	}
	
	 @RequestMapping(value = "/owners", method = RequestMethod.POST)
	    public String saveOwner(ModelOrderOwner owner){
		 
		    Long userId=systemUserId;
			 
			
		 
		 
		if(owner.getOrderOwnerId()==null )
		{
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		    
		    owner.setModelBusinessType(null);
			owner.setEntryTimestamp(entryTime);
			
			
			 ModelUser user=new ModelUser();
			 user.setUserId(userId);
			owner.setEnteredBy(user);
			orderOwner.saveOwner(owner);
		}
		else
		{
			Optional<ModelOrderOwner> existOwner= ownerRepository.findById(owner.getOrderOwnerId());
			java.util.Date date = new java.util.Date();
			java.sql.Timestamp updateTime = new java.sql.Timestamp(date.getTime());
		
			
			owner.setEnteredBy(existOwner.get().getEnteredBy());
			owner.setEntryTimestamp(existOwner.get().getEntryTimestamp());
			
			 ModelUser user=new ModelUser();
			 user.setUserId(userId);
			
			owner.setUpdatedBy(user);
			owner.setUpdateTimestap(updateTime);
			orderOwner.saveOwner(owner);
		}
	    

	        return "redirect:/ownerscontroller/editOwner/"+owner.getOrderOwnerId();
	}
	 
	 

	
	 @RequestMapping(path = "/ownerscontroller/editOwner/{id}", method = RequestMethod.GET)
	    public String editOwner(@PathVariable Long id, Model model) {
		 
		 
		 System.out.println("system user :" + systemUserId);
		    Long userId=systemUserId;
		    
		     ModelUser logonUser=daoLogonImp.getLogonUserName(userId);
			 String userName=logonUser.getUserName();
			 System.out.println("logon user name is :" + userName);
			 String name=userName;
			 model.addAttribute("name",name );
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
		 
		 
		 Optional<ModelOrderOwner> ownerById;
		 ownerById= ownerRepository.findById(id);
		if( ownerById.get().getActiveStatus()==1)
			ownerById.get().setActive(true);
			else
				ownerById.get().setActive(false);
	        model.addAttribute("owner", ownerById);
	        if(id!=0) {
	        	String msg="Successfully Saved";
	        	 model.addAttribute("message",msg );
	        }
	        else
	        {
	        	String msg="Some Error Occured";
	        	 model.addAttribute("message",msg);
	        }
	        List <ModelCountry> countryList= country.getCountryName();
	        model.addAttribute("zoneList",orderOwnerZoneRepository.findOrderOwnerZone());
	        
	        model.addAttribute("countryList",countryList);

	        List <ModelBusinessType> typeList=  businessType.getTypeName();
	        model.addAttribute("typeList",typeList);
	        
	        List<ModelOrderOwnerType> ownerTypeList= ownerType.getTypeName();
	        model.addAttribute("ownerTypeList",ownerTypeList);
	        
	        ModelOrderOwner  orderOwner= new ModelOrderOwner();
	        orderOwner.setOrderOwnerId(id);
	        
	        ModelPhone phone= new ModelPhone();
	        phone.setOwner(orderOwner);
	        ModelCountry defaultCountry= new ModelCountry();
			defaultCountry.setCountryId(1l);
			phone.setModelCountry(defaultCountry);
	        model.addAttribute("phone",phone);
	        
	        List<ModelPhone> numberList= this.phone.getUserPhoneData(phone.getOwner().getOrderOwnerId());
	        
	        model.addAttribute("phoneList",numberList);
	        
	        return "buyer_data_upload";
	}
	 
	 
	 @RequestMapping(path = "/owner/phone", method = RequestMethod.POST)
	    public String savePhone(ModelPhone phone) {
		 java.util.Date date = new java.util.Date();
			java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
			if(phone.getPhoneId()==null )
			{
				phone.setEntryTimestap(sqlDate);
				phone.setEntered_By(1);
				 this.phone.savePhone(phone);
			}
			
	      //  return "redirect:/owners/add/"+phone.getOwner().getOrderOwnerId();
			
		    return "redirect:/ownerscontroller/editOwner/"+phone.getOwner().getOrderOwnerId();
	}
	
	 @ResponseBody
	    @RequestMapping(value = "/owner/phone/{id}", method = RequestMethod.GET)
	    public Optional<ModelPhone> loadEntity(@PathVariable("id") Long id) {
	//	 System.out.println(phone.getPhoneById(id));
	        return phone.getPhoneById(id);
	}
	 
	 
	
	    @RequestMapping(value = "/owner/phone/update", method = RequestMethod.POST)
	    public String updatePhone(@ModelAttribute("phone") ModelPhone phone) {
	    	java.util.Date date = new java.util.Date();
			java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
			Optional<ModelPhone> exitsPhone= this.phone.getPhoneById(phone.getPhoneId());
			
			phone.setUpdatedBy(2);
			phone.setUpdateTimestap(sqlDate);
	    	phone.setEntered_By(exitsPhone.get().getEntered_By());
	    	phone.setEntryTimestap(exitsPhone.get().getEntryTimestap());
		 this.phone.savePhone(phone);
		 
	//	 return "redirect:/owners/add/"+phone.getOwner().getOrderOwnerId();
		 
		 return "redirect:/ownerscontroller/editOwner/"+phone.getOwner().getOrderOwnerId();
	}
	  
	  
	 @ResponseBody
	  @RequestMapping(path = "/owner/loadphone/{id}", method = RequestMethod.GET)
	    public List<ModelPhone> loadOwnerPhone(@PathVariable Long id, Model model) {
		
	        List<ModelPhone> numberList= this.phone.getUserPhoneData(id);
	        
	     return  numberList;
	        
	       
	}
	 
	 @RequestMapping(method = RequestMethod.POST , value = "/GenerateReport")
     public @ResponseBody void generatePdfReport(ModelAndView modelAndView,HttpServletRequest request,HttpServletResponse response) throws SQLException{
 
         WebApplicationContext context =WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
         
         //StudentDAO dao=(StudentDAO)context.getBean("studentDAO");
       
         //BasicDataSource dataSource = (BasicDataSource)context.getBean("myDataSource");
        // String contextPath = request.getServletContext().getRealPath("D:\\office\\software_dev\\mas\\mas\\src\\main\\resources\\reports\\ClientListRpt.jrxml");
         String contextPath = "D:\\office\\software_dev\\mas\\mas\\src\\main\\resources\\reports\\ClientListRpt.jrxml";
         System.out.println("contextpath " + contextPath);
         daoItemType.generatePdfReport(contextPath);
         
       
      }
	
}
