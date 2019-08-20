package com.biziitech.mlfm.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biziitech.mlfm.bg.daoimp.DaoUserImp;
import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.custom.model.ModelMendingCustom;
import com.biziitech.mlfm.custom.model.ModelUOMCustom;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoItemImp;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.daoimpl.DaoMendingImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerTypeImp;
import com.biziitech.mlfm.daoimpl.DaoUOMImp;
import com.biziitech.mlfm.model.ModelItem;
import com.biziitech.mlfm.model.ModelMending;
import com.biziitech.mlfm.model.ModelMendingPlan;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelOrderOwnerType;
import com.biziitech.mlfm.model.ModelProduction;
import com.biziitech.mlfm.model.ModelQC;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.repository.MendingRepository;

@Controller
public class MendingController {
	
	@Autowired
	private DaoMendingImp daoMendingImp;
	
	@Autowired
	private DaoOrderOwnerImp daoOrderOwnerImp;

	@Autowired
	private DaoItemImp daoItemImp;
	
	@Autowired
	private DaoOrderOwnerTypeImp daoOrderOwnerTypeImp;
	
	@Autowired
	private DaoUserImp daoUserImp;
	
	@Autowired
	private DaoUOMImp daoUOMImp;
	
	@Autowired
	private MendingRepository mendingRepository;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


private Long systemUserId;
	
	
	
	@RequestMapping(path="/mendingController/init/{userId}")
	public String Init(@PathVariable Long userId,Model model) {
		
		
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
		
		ModelMending modelMending= new ModelMending();
		model.addAttribute("modelMending", modelMending);
		
		List<ModelOrderOwner> modelOrderOwnerList = daoOrderOwnerImp.getAllOwner();
		model.addAttribute("modelOrderOwnerList", modelOrderOwnerList);

		
		List<ModelItem> modelItemList= daoItemImp.getItemListActive();
		model.addAttribute("modelItemList", modelItemList);
		
		List<ModelOrderOwnerType> modelOrderOwnerTypeList= daoOrderOwnerTypeImp.getTypeName();
		model.addAttribute("modelOrderOwnerTypeList", modelOrderOwnerTypeList);
		
		List<ModelUser> userList= daoUserImp.getAllUSerNameInOrder();
		model.addAttribute("userList",userList);
		
		List<ModelUOMCustom> modelUOMList = daoUOMImp.getUOMListCustom();
		model.addAttribute("modelUOMList", modelUOMList);
		
		 String msg=" ";
	     model.addAttribute("message", msg);
		
		
		return "Mending";
		
	}
	
	/*
	 * 
	 * Below code for saving mending data 
	 * 
	 * */
	@ResponseBody
	@RequestMapping(path="/mendingcontroller/savemendingentry", method = RequestMethod.POST)
	public ModelMending saveMendingEntry(ModelMending modelMending, Model model,ModelUOM modelUOM,
			ModelMendingPlan modelMendingPlan,
			ModelQC modelQC,
			ModelProduction modelProduction,
			@RequestParam("mendingPlanId") Long mendingPlanId,
			@RequestParam("mendedQty") Double mendedQty,
			@RequestParam("qCId") Long qCId,
			@RequestParam("uomId") Long uomId,
			@RequestParam("mendingDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date mendingDate,
			@RequestParam("activeStatus") int activeStatus,
			@RequestParam("mendingRemarks") String mendingRemarks
			
			) {
		
		System.out.println("I am in mendingcontroller's savemendingentry method");
		
		try {
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		modelMending.setEntryTimeStamp(entryTime);
		modelMending.setMendingDate(mendingDate);
		
		Long logonUserId=systemUserId;
		ModelUser modelUser1 = new ModelUser();
		modelUser1.setUserId(logonUserId);
		modelMending.setEnteredBy(modelUser1);
		
		//modelProduction.setProductionId(productionId);
		//modelMending.setModelProduction(modelProduction);
		modelMendingPlan.setMendingPlanId(mendingPlanId);
		modelMending.setModelMendingPlan(modelMendingPlan);
		modelQC.setqCId(qCId);
		modelMending.setModelQC(modelQC);
		modelUOM.setUomId(uomId);
		modelMending.setModelUOM(modelUOM);
		modelMending.setMendedQty(mendedQty);
		modelMending.setMendedRemarks(mendingRemarks);
		modelMending.setActiveStatus(activeStatus);
		this.daoMendingImp.saveMending(modelMending);
		
		model.addAttribute("modelMending",modelMending);	
		
		return modelMending;
		
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
		 
		 return null;
			
		}
		
	}
	
	
	@ResponseBody
    @RequestMapping(path="/mendingcontroller/mendingupdate", method=RequestMethod.POST)
	    public void mendingUpdate(Model model,ModelMending modelMending,
	    		
		@RequestParam("mendingId") Long mendingId,
	    	@RequestParam("mendedQty") Double mendedQty,
	    	@RequestParam("mendedRemarks") String mendedRemarks
	 ) {
		    System.out.println("Mending mendingId : "+mendingId);
		    System.out.println("Mending mendedQty : "+mendedQty);
		    
		    try {
		   
			Optional<ModelMending> modelMendingOpt = mendingRepository.findById(mendingId);
			modelMending=modelMendingOpt.get();		
			modelMending.setMendedQty(mendedQty);
			modelMending.setMendedRemarks(mendedRemarks);		
			modelMending.setActiveStatus(1);
				
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		modelMending.setUpdateTimeStamp(entryTime);
		
		
		Long logonUserId=systemUserId;
		ModelUser modelUser1 = new ModelUser();
		modelUser1.setUserId(logonUserId);
		
		modelMending.setUpdatedBy(modelUser1);
				
		daoMendingImp.saveMending(modelMending);	
		    }		
			catch(Exception e) {			
			 e.printStackTrace();
			 System.out.println("error");
				
			}
					
	       }
	
	/*
	 * 
	 * Below code for sending buyerName through buyerTypeId 
	 * 
	 * */
	
	
	@RequestMapping(path="/mendingcontroller/findowners")
	@ResponseBody
	public List<ModelOrderOwner> findOwners(@RequestParam("buyerType")Long typeId){
		
		System.out.println("" + typeId);
		
		return daoOrderOwnerImp.getOwnerByTypeInOrder(typeId);
	}
	
	/*
	 * below code for showing mending Qty
	 * 
	 * */
	
	
	@ResponseBody
	@RequestMapping(path = "/mendingcontroller/getmendingbymendingplanid", method = RequestMethod.GET)
	 public List<ModelMendingCustom> getMendingByMendingPlanId(@RequestParam("mendingPlanId")Long mendingPlanId,
			 Model model){
		 System.out.println("I am in mendingQty Controller & id is " +mendingPlanId);
		 try {
			List<ModelMendingCustom> modelMendingList =  daoMendingImp.getMendingByMendingPlanId(mendingPlanId);
			model.addAttribute("modelMendingList", modelMendingList);	 
		 return modelMendingList;
		 }		
			catch(Exception e) {			
			 e.printStackTrace();
			 System.out.println("error");
				return null;
			}	
	 }
	
	@ResponseBody
	@RequestMapping(path = "/mendedQuantity/", method = RequestMethod.GET)
	 public List<ModelMendingCustom> getMendedById(Model model,
			 @RequestParam("id")Long id,
			 @RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
			 @RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate
			 ){
		 System.out.println("I am in mendedQty Controller & id is " +id);
			List<ModelMendingCustom> modelMendingList =  daoMendingImp.getMendedById(id,startDate,endDate);
			model.addAttribute("modelMendingList", modelMendingList);	 
		 return modelMendingList;		 
	 }
	
	/*
	 * 
	 * Below code for pending inquiry search 
	 * 
	 * */
	
	@ResponseBody
	@RequestMapping(path="/mendingController/pendingOrderSearch",method = RequestMethod.GET)
	public List<ModelMendingCustom> getPendingMendingOrderSearch(Model model,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("inquiryId")Long inquiryId , 				 
				@RequestParam("mendedById")Long mendedById,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,
				@RequestParam("itemId")Long itemId
				) {

		System.out.println("I am in mendingController's pendingOrderSearch method");
	
		try {
			
		System.out.println("InquiryId :" + inquiryId + "\n BuyerId: "+ buyerId +"\n mendedById: " + mendedById + "\n startDate " + startDate + "\n endDate " + endDate+ "\n itemId: "+itemId);
		List<ModelMendingCustom> modelMendingCustomList = new ArrayList<ModelMendingCustom>();	
		modelMendingCustomList=daoMendingImp.getPendingMendingOrderDetails(buyerId,inquiryId,mendedById,startDate,endDate,itemId);
		System.out.println("Size:"+modelMendingCustomList.size());
		 
		return modelMendingCustomList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	/*
	 * 
	 * Below code for completed inquiry search 
	 * 
	 * */
	
	@ResponseBody
	@RequestMapping(path="/mendingController/completedOrderSearch",method = RequestMethod.GET)
	public List<ModelMendingCustom> getCompletedMendingOrderSearch(Model model,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("inquiryId")Long inquiryId , 				 
				@RequestParam("mendedById")Long mendedById,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,
				@RequestParam("itemId")Long itemId
				) {	
		System.out.println("I am in mendingController's completedOrderSearch method");
		try {
			
		System.out.println("InquiryId :" + inquiryId + "\n BuyerId: "+ buyerId +"\n mendedById: " + mendedById + "\n startDate " + startDate + "\n endDate " + endDate +"\n itemId: "+itemId);
		
		List<ModelMendingCustom> modelMendingCustomList = new ArrayList<ModelMendingCustom>();		
		modelMendingCustomList=daoMendingImp.getCompletedMendingOrderDetails(buyerId, inquiryId, mendedById, startDate, endDate,itemId);
		System.out.println("Size:"+modelMendingCustomList.size());
		 
		return modelMendingCustomList;
		}		
		catch(Exception e) {		
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
		
	}
	
	/*
	 * 
	 * Below code for pending workOrder search 
	 * 
	 * */
	
	@ResponseBody
	@RequestMapping(path="/mendingcontroller/getpendingmendingwosearch",method = RequestMethod.GET)
	public List<ModelMendingCustom> getPendingMendingWOSearch(Model model,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("inquiryId")Long inquiryId , 				 
				@RequestParam("mendedById")Long mendedById,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,
				@RequestParam("itemId")Long itemId
				) {
	
		System.out.println("I am in mendingcontroller getPendingMendingWOSearch Method");

		try {
			
		System.out.println("InquiryId :" + inquiryId + "\n BuyerId :"+ buyerId +"\n mendedById: " + mendedById + "\n startDate " + startDate + "\n endDate " + endDate+"\n itemId:"+itemId);

		List<ModelMendingCustom> modelMendingCustomList = new ArrayList<ModelMendingCustom>();
		modelMendingCustomList= daoMendingImp.getPendingMendingWODetails(buyerId,inquiryId,mendedById,startDate,endDate,itemId);
		System.out.println("Size: "+modelMendingCustomList.size());
			
		return modelMendingCustomList;	
		}	
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}		
	}
	
	/*
	 * 
	 * Below code for completed workOrder search 
	 * 
	 * */
	
	@ResponseBody
	@RequestMapping(path="/mendingController/completedWOSearch",method = RequestMethod.GET)
	public List<ModelMendingCustom> getCompletedWOSearch(Model model,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("inquiryId")Long inquiryId , 				 
				@RequestParam("mendedById")Long mendedById,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,
				@RequestParam("itemId")Long itemId
				) {	
		System.out.println("I am in ajax Completed WOSearch");
		try {			
		System.out.println("InquiryId :" + inquiryId + "\n BuyerId :"+ buyerId +"\n mendedById: " + mendedById + "\n startDate " + startDate + "\n endDate " + endDate+"\n itemId:"+itemId);
		List<ModelMendingCustom> modelMendingCustomList = new ArrayList<ModelMendingCustom>();
		modelMendingCustomList=daoMendingImp.getCompletedMendingWODetails(buyerId, inquiryId, mendedById, startDate, endDate, itemId);		
					 
		return modelMendingCustomList;
		}
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	}
	
	

	
	

}
