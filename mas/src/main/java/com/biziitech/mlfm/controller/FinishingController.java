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
import com.biziitech.mlfm.custom.model.ModelFinishingCustom;
import com.biziitech.mlfm.custom.model.ModelUOMCustom;
import com.biziitech.mlfm.dao.DaoFinishing;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoItemImp;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerTypeImp;
import com.biziitech.mlfm.daoimpl.DaoUOMImp;
import com.biziitech.mlfm.model.ModelFinishing;
import com.biziitech.mlfm.model.ModelFinishingPlan;
import com.biziitech.mlfm.model.ModelItem;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelOrderOwnerType;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.repository.FinishingRepository;

@Controller
public class FinishingController {
	
	@Autowired
	private DaoFinishing daoFinishing;
	
	
	@Autowired
	private DaoOrderOwnerImp daoOrderOwnerImp;

	@Autowired
	private DaoItemImp daoItemImp;
	
	@Autowired
	private DaoOrderOwnerTypeImp daoOrderOwnerTypeImp;
	
	@Autowired
	private DaoUserImp daoUserImp;
	
	@Autowired
	private FinishingRepository finishingRepository;
	
	@Autowired
	private DaoUOMImp daoUOMImp;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


    private Long systemUserId;
	
	
	@RequestMapping(path="/finishingcontroller/init/{userId}")
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
				
		return "Finishing";
		}
	
	/*
	 * 
	 * Below code for sending buyerName through buyerTypeId 
	 * 
	 * */	
	
	@RequestMapping(path="/finishingcontroller/findowners")
	@ResponseBody
	public List<ModelOrderOwner> findOwners(@RequestParam("buyerType")Long typeId){
		
		System.out.println("" + typeId);
		
		return daoOrderOwnerImp.getOwnerByTypeInOrder(typeId);
	}
	
	/*
	 * 
	 * Below code for saving finishing data 
	 * 
	 * */
	@ResponseBody
	@RequestMapping(path="/finishingcontroller/savefinishingentry", method = RequestMethod.POST)
	public ModelFinishing saveFinishingEntry(ModelFinishing modelFinishing, Model model,ModelFinishingPlan modelFinishingPlan,
			@RequestParam("finshingPlanId") Long finshingPlanId,
			@RequestParam("finishedQty") Double finishedQty,
			@RequestParam("finishingDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date finishingDate,
			@RequestParam("finishingRemarks") String finishingRemarks,
			@RequestParam("activeStatus") int activeStatus
			
			) {
		
		try {
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		modelFinishing.setEntryTimeStamp(entryTime);
		modelFinishing.setFinishingDate(finishingDate);
		
		Long logonUserId=systemUserId;
		ModelUser modelUser1 = new ModelUser();
		modelUser1.setUserId(logonUserId);
		
		modelFinishing.setEnteredBy(modelUser1);
		modelFinishing.setActiveStatus(1);
		modelFinishingPlan.setFinishingPlanId(finshingPlanId);
		modelFinishing.setModelFinishingPlan(modelFinishingPlan);
		modelFinishing.setFinishedQty(finishedQty);
		modelFinishing.setFinishedRemarks(finishingRemarks);
		if(activeStatus==1) 
		{
			modelFinishing.setActiveStatus(1);
			
		}
		else 
		{
			modelFinishing.setActiveStatus(0);
			
		}
		
		this.daoFinishing.saveFinishing(modelFinishing);
		
		//model.addAttribute("modelFinishing",modelFinishing);	
	//	return "redirect:/finishingcontroller/init";
		return modelFinishing;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
		
	}
	
	@ResponseBody
    @RequestMapping(path="/finishingcontroller/dyingupdate", method=RequestMethod.POST)
	    public String dyingUpdate(Model model,ModelFinishing modelFinishing,
	    		
		@RequestParam("finishingId") Long finishingId,
	    	@RequestParam("finishedQty") Double finishedQty,
	    	@RequestParam("finishedRemarks") String finishedRemarks
	 ) {
		         
		try {	
		
		    System.out.println("Dying finishingId : "+finishingId);
		    System.out.println("Dying finishedQty : "+finishedQty);
		   
			Optional<ModelFinishing> modelFinishingOpt = finishingRepository.findById(finishingId);
			modelFinishing=modelFinishingOpt.get();
		
			modelFinishing.setFinishedQty(finishedQty);
			modelFinishing.setFinishedRemarks(finishedRemarks);
									
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		modelFinishing.setUpdateTimeStamp(entryTime);
		
		Long logonUserId=systemUserId;
		ModelUser modelUser1 = new ModelUser();
		modelUser1.setUserId(logonUserId);
		
		modelFinishing.setUpdatedBy(modelUser1);
				
		daoFinishing.saveFinishing(modelFinishing);	
		
		return "/finishingcontroller/init";
			
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}	
			
}
	
	
	/*
	 * below code for showing finishing Qty
	 * 
	 * */
	
	
	@ResponseBody
	@RequestMapping(path = "/finishingcontroller/getfinishingbyid", method = RequestMethod.GET)
	 public List<ModelFinishingCustom> getFinishingById(@RequestParam("finishingPlanId")Long finishingPlanId, Model model){
		 System.out.println("I am in finishingQty Controller & finishingPlanId is " +finishingPlanId);
			List<ModelFinishingCustom> modelFinishingList =  daoFinishing.getFinishingById(finishingPlanId);
			model.addAttribute("modelFinishingList", modelFinishingList);	 
		 return modelFinishingList;		 
	 }
	
	/*
	 * below code for showing finished Qty
	 * 
	 * */
	
	
	@ResponseBody
	@RequestMapping(path = "/finishingcontroller/getfinisheddonebyid", method = RequestMethod.GET)
	 public List<ModelFinishingCustom> getFinishedDoneById(Model model,
			 @RequestParam("finishingId")Long finishingId 

			 ){
		 System.out.println("I am in finishedQty Controller & finishingId is " +finishingId);
			List<ModelFinishingCustom> modelFinishingList =  daoFinishing.getFinishingDoneById(finishingId);
			model.addAttribute("modelFinishingList", modelFinishingList);	 
		 return modelFinishingList;		 
	 }
	
	/*
	 * 
	 * Below code for pending inquiry search 
	 * 
	 * */
	
	@ResponseBody
	@RequestMapping(path="/finishingcontroller/getpendingfinishingordersearch",method = RequestMethod.GET)
	public List<ModelFinishingCustom> getPendingFinishingOrderSearch(Model model,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("pOId")Long pOId , 				 
				@RequestParam("finishedById")Long finishedById,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,
				@RequestParam("itemId")Long itemId
				) {

		System.out.println("I am in finishingController's pendingOrderSearch method");
	
		try {
			
		System.out.println("pOId :" + pOId + "\n BuyerId: "+ buyerId +"\n finishedById: " + finishedById + "\n startDate " + startDate + "\n endDate " + endDate+ "\n itemId: "+itemId);
		List<ModelFinishingCustom> modelFinishingCustomList = new ArrayList<ModelFinishingCustom>();	
		modelFinishingCustomList=daoFinishing.getPendingFinishingOrderDetails(buyerId,pOId,finishedById,startDate,endDate,itemId);
		System.out.println("Size:"+modelFinishingCustomList.size());
		 
		return modelFinishingCustomList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	/*
	 * 
	 * Below code for Completed inquiry search 
	 * 
	 * */
	
	@ResponseBody
	@RequestMapping(path="/finishingcontroller/getcompletedfinishingordersearch",method = RequestMethod.GET)
	public List<ModelFinishingCustom> getCompletedFinishingOrderSearch(Model model,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("pOId")Long pOId , 				 
				@RequestParam("finishedById")Long finishedById,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,
				@RequestParam("itemId")Long itemId
				) {

		System.out.println("I am in finishingController's completedOrderSearch method");
	
		try {
			
		System.out.println("pOId :" + pOId + "\n BuyerId: "+ buyerId +"\n finishedById: " + finishedById + "\n startDate " + startDate + "\n endDate " + endDate+ "\n itemId: "+itemId);
		List<ModelFinishingCustom> modelFinishingCustomList = new ArrayList<ModelFinishingCustom>();	
		modelFinishingCustomList=daoFinishing.getCompletedFinishingOrderDetails(buyerId,pOId,finishedById,startDate,endDate,itemId);
		System.out.println("Size:"+modelFinishingCustomList.size());
		 
		return modelFinishingCustomList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	/*
	 * 
	 * Below code for pendingWO inquiry search 
	 * 
	 * */
	
	@ResponseBody
	@RequestMapping(path="/finishingcontroller/getpendingfinishingwosearch",method = RequestMethod.GET)
	public List<ModelFinishingCustom> getPendingFinishingWOSearch(Model model,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("pOId")Long pOId , 				 
				@RequestParam("finishedById")Long finishedById,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,
				@RequestParam("itemId")Long itemId
				) {

		System.out.println("I am in finishingController's pendingWOSearch method");
	
		try {
			
		System.out.println("pOId :" + pOId + "\n BuyerId: "+ buyerId +"\n finishedById: " + finishedById + "\n startDate " + startDate + "\n endDate " + endDate+ "\n itemId: "+itemId);
		List<ModelFinishingCustom> modelFinishingCustomList = new ArrayList<ModelFinishingCustom>();	
		modelFinishingCustomList=daoFinishing.getPendingFinishingWODetails(buyerId,pOId,finishedById,startDate,endDate,itemId);
		System.out.println("Size:"+modelFinishingCustomList.size());
		 
		return modelFinishingCustomList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	/*
	 * 
	 * Below code for completedWO inquiry search 
	 * 
	 * */
	
	@ResponseBody
	@RequestMapping(path="/finishingcontroller/getcompletedfinishingwosearch",method = RequestMethod.GET)
	public List<ModelFinishingCustom> getCompletedFinishingWOSearch(Model model,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("pOId")Long pOId , 				 
				@RequestParam("finishedById")Long finishedById,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,
				@RequestParam("itemId")Long itemId
				) {

		System.out.println("I am in finishingController's completedWOSearch method");
	
		try {
			
		System.out.println("pOId :" + pOId + "\n BuyerId: "+ buyerId +"\n finishedById: " + finishedById + "\n startDate " + startDate + "\n endDate " + endDate+ "\n itemId: "+itemId);
		List<ModelFinishingCustom> modelFinishingCustomList = new ArrayList<ModelFinishingCustom>();	
		modelFinishingCustomList=daoFinishing.getCompletedFinishingWODetails(buyerId,pOId,finishedById,startDate,endDate,itemId);
		System.out.println("Size:"+modelFinishingCustomList.size());
		 
		return modelFinishingCustomList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}	

}
