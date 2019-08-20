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
import com.biziitech.mlfm.custom.model.ModelUOMCustom;
import com.biziitech.mlfm.custom.model.ModelWashingCustom;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoItemImp;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerTypeImp;
import com.biziitech.mlfm.daoimpl.DaoUOMImp;
import com.biziitech.mlfm.daoimpl.DaoWashingImp;
import com.biziitech.mlfm.model.ModelItem;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelOrderOwnerType;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.model.ModelWashing;
import com.biziitech.mlfm.model.ModelWashingPlan;
import com.biziitech.mlfm.repository.WashingRepository;

@Controller
public class WashingController {
	
	@Autowired
	private DaoWashingImp daoWashingImp;
	
	@Autowired
	private DaoOrderOwnerImp daoOrderOwnerImp;

	@Autowired
	private DaoItemImp daoItemImp;
	
	@Autowired
	private DaoOrderOwnerTypeImp daoOrderOwnerTypeImp;
	
	@Autowired
	private DaoUOMImp daoUOMImp;
	
	@Autowired
	private DaoUserImp daoUserImp;
	
	@Autowired
	private WashingRepository washingRepository;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


private Long systemUserId;
	
	@RequestMapping(path="/washingcontroller/init/{userId}")
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
		
		ModelWashing modelWashing = new ModelWashing();
		model.addAttribute("modelWashing", modelWashing);
		
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
		
		return "Washing";
		}
	/*
	 * 
	 * Below code for saving washing data 
	 * 
	 * */
	@ResponseBody
	@RequestMapping(path="/washingcontroller/savewashingentry", method = RequestMethod.POST)
	public ModelWashing saveWashingEntry(ModelWashing modelWashing, Model model,
			ModelWashingPlan modelWashingPlan,
			ModelUOM modelUOM,
			@RequestParam("washingPlanId") Long washingPlanId,
			@RequestParam("uomId") Long uomId,
			@RequestParam("washingDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date washingDate,
			@RequestParam("washedQty") Double washedQty,
			@RequestParam("washingRemarks") String washingRemarks,
			@RequestParam("activeStatus") int activeStatus
			
			) {
		
		try {
		
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		modelWashing.setEntryTimeStamp(entryTime);
		modelWashing.setWashingDate(washingDate);
		
		
		Long logonUserId=systemUserId;
		ModelUser modelUser1 = new ModelUser();
		modelUser1.setUserId(logonUserId);
		
		modelWashing.setEnteredBy(modelUser1);
		modelWashing.setActiveStatus(1);
		modelWashingPlan.setWashingPlanId(washingPlanId);
		modelWashing.setModelWashingPlan(modelWashingPlan);
		modelUOM.setUomId(uomId);
		modelWashing.setModelUOM(modelUOM);
		modelWashing.setWashedQty(washedQty);
		modelWashing.setWashedRemarks(washingRemarks);
		this.daoWashingImp.saveWashing(modelWashing);
		
		model.addAttribute("modelWashing",modelWashing);	
		return modelWashing;
		
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}	
		
	}
	
	
	@ResponseBody
    @RequestMapping(path="/washingController/washingupdate", method=RequestMethod.POST)
	    public String washingUpdate(Model model,ModelWashing modelWashing,
		@RequestParam("washingId") Long washingId,
	    @RequestParam("washedQty") Double washedQty,
	    @RequestParam("washedRemarks") String washedRemarks
	 ) {
		         
		try {
		
		    System.out.println("Washing washingId : "+washingId);
		    System.out.println("Washing washedQty : "+washedQty);
		   
			Optional<ModelWashing> modelWashingOpt = washingRepository.findById(washingId);
			modelWashing=modelWashingOpt.get();
		
			modelWashing.setWashedQty(washedQty);
			modelWashing.setWashedRemarks(washedRemarks);
			
			modelWashing.setActiveStatus(1);
			
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		modelWashing.setUpdateTimeStamp(entryTime);
		

		Long logonUserId=systemUserId;
		ModelUser modelUser1 = new ModelUser();
		modelUser1.setUserId(logonUserId);
		
		modelWashing.setUpdatedBy(modelUser1);
				
		daoWashingImp.saveWashing(modelWashing);	
		
		return null;
			
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}	
			
}
		
	
	
	/*
	 * 
	 * Below code for sending buyerName through buyerTypeId 
	 * 
	 * */
	
	
	
	@RequestMapping(path="/washingController/findowners")
	@ResponseBody
	public List<ModelOrderOwner> findOwners(@RequestParam("buyerType")Long typeId){
		
		System.out.println("" + typeId);
		
		return daoOrderOwnerImp.getOwnerByTypeInOrder(typeId);
	}
	
	/*
	 * below code for showing washing Qty
	 * 
	 * */
	
	@ResponseBody
	@RequestMapping(path = "/washingController/getwashingbyid", method = RequestMethod.GET)
	 public List<ModelWashingCustom> getWashingById( Model model,
			 @RequestParam("washingPlanId")Long washingPlanId
			
			 ){
		 System.out.println("I am in washingController Controller's getWashingById method & washingPlanId is " +washingPlanId);
			List<ModelWashingCustom> modelWashingList =  daoWashingImp.getWashingById(washingPlanId);
			model.addAttribute("modelWashingList", modelWashingList);	 
		 return modelWashingList;		 
	 }
	
	@ResponseBody
	@RequestMapping(path = "/washedQuantity/", method = RequestMethod.GET)
	 public List<ModelWashingCustom> getWashedById( Model model,
			 @RequestParam("id")Long id,
			 @RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
			 @RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate
			 ){
		 System.out.println("I am in washingQty Controller & id is " +id);
			List<ModelWashingCustom> modelWashingList =  daoWashingImp.getWashedById(id,startDate,endDate);
			model.addAttribute("modelWashingList", modelWashingList);	 
		 return modelWashingList;		 
	 }
	
	
	/*
	 * 
	 * Below code for pending inquiry search 
	 * 
	 * */
	
	@ResponseBody
	@RequestMapping(path="/washingController/pendingOrderSearch",method = RequestMethod.GET)
	public List<ModelWashingCustom> getPendingMendingOrderSearch(Model model,

				@RequestParam("buyerId")Long buyerId,
				@RequestParam("inquiryId")Long inquiryId , 				 
				@RequestParam("washedById")Long washedById,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,
				@RequestParam("itemId")Long itemId
				) {

		System.out.println("I am in washingController's pendingOrderSearch method");
	
		try {
			
		System.out.println("InquiryId :" + inquiryId + "\n BuyerId: "+ buyerId +"\n washedById: " + washedById + "\n startDate " + startDate + "\n endDate " + endDate+ "\n itemId: "+itemId);
		List<ModelWashingCustom> modelWashingCustomList = new ArrayList<ModelWashingCustom>();	
		modelWashingCustomList=daoWashingImp.getPendingWashingOrderDetails(buyerId,inquiryId,washedById,startDate,endDate,itemId);
		System.out.println("Size:"+modelWashingCustomList.size());
		 
		return modelWashingCustomList;
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
	@RequestMapping(path="/washingController/completedOrderSearch",method = RequestMethod.GET)
	public List<ModelWashingCustom> getCompletedWashingOrderSearch(Model model,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("inquiryId")Long inquiryId , 				 
				@RequestParam("washedById")Long washedById,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,
				@RequestParam("itemId")Long itemId
				) {	
		System.out.println("I am in washingController's completedOrderSearch method");
		try {
			
		System.out.println("InquiryId :" + inquiryId + "\n BuyerId: "+ buyerId +"\n washedById: " + washedById + "\n startDate " + startDate + "\n endDate " + endDate +"\n itemId: "+itemId);
		
		List<ModelWashingCustom> modelWashingCustomList = new ArrayList<ModelWashingCustom>();		
		modelWashingCustomList=daoWashingImp.getCompletedWashingOrderDetails(buyerId, inquiryId, washedById, startDate, endDate,itemId);
		System.out.println("Size:"+modelWashingCustomList.size());
		
		return modelWashingCustomList;
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
	@RequestMapping(path="/washingcontroller/pendingwosearch",method = RequestMethod.GET)
	public List<ModelWashingCustom> getPendingWashingWOSearch(Model model,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("pOId")Long pOId , 				 
				@RequestParam("washedById")Long washedById,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,
				@RequestParam("itemId")Long itemId
				) {
	
		System.out.println("I am in ajax Pending washing WOSearch");

		try {
			
		System.out.println("pOId :" + pOId + "\n BuyerId :"+ buyerId +"\n washedById: " + washedById + "\n startDate " + startDate + "\n endDate " + endDate+"\n itemId:"+itemId);

		List<ModelWashingCustom> modelWashingCustomList = new ArrayList<ModelWashingCustom>();
		modelWashingCustomList= daoWashingImp.getPendingWashingWODetails(buyerId,pOId,washedById,startDate,endDate,itemId);
		 
			
		return modelWashingCustomList;	
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
	@RequestMapping(path="/washingcontroller/completedwosearch",method = RequestMethod.GET)
	public List<ModelWashingCustom> getCompletedWOSearch(Model model,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("pOId")Long pOId , 				 
				@RequestParam("washedById")Long washedById,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,
				@RequestParam("itemId")Long itemId
				) {	
		System.out.println("I am in ajax Completed WOSearch");
		try {			
		System.out.println("pOId :" + pOId + "\n BuyerId :"+ buyerId +"\n washedById: " + washedById + "\n startDate " + startDate + "\n endDate " + endDate+"\n itemId:"+itemId);
		List<ModelWashingCustom> modelWashingCustomList = new ArrayList<ModelWashingCustom>();
		modelWashingCustomList=daoWashingImp.getCompletedWashingWODetails(buyerId, pOId, washedById, startDate, endDate, itemId);		
					 
		return modelWashingCustomList;
		}
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	}
	
	


}
