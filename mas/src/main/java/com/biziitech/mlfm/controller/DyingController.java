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
import com.biziitech.mlfm.custom.model.ModelDyingCustom;
import com.biziitech.mlfm.custom.model.ModelUOMCustom;
import com.biziitech.mlfm.dao.DaoDying;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoItemImp;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerTypeImp;
import com.biziitech.mlfm.daoimpl.DaoUOMImp;
import com.biziitech.mlfm.model.ModelDying;
import com.biziitech.mlfm.model.ModelDyingPlan;
import com.biziitech.mlfm.model.ModelItem;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelOrderOwnerType;
import com.biziitech.mlfm.model.ModelProduction;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.repository.DyingRepository;

@Controller
public class DyingController {
	
	@Autowired
	private DaoDying daoDying;
	
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
	private DyingRepository dyingRepository;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


private Long systemUserId;
	
	
	@RequestMapping(path="/dyingcontroller/init/{userId}")
	public String DyingCreate(@PathVariable Long userId,Model model) {
		
		
		
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
		
		
		
		ModelDying modelDying = new ModelDying();
		model.addAttribute("modelDying", modelDying);
		
		
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
		
		
		return "Dying";
		}
	
	/*
	 * 
	 * Below code for sending buyerName through buyerTypeId 
	 * 
	 * */
	
	
	
	@RequestMapping(path="/dyingcontroller/findowners")
	@ResponseBody
	public List<ModelOrderOwner> findOwners(@RequestParam("buyerType")Long typeId){
		
		System.out.println("" + typeId);
		
		return daoOrderOwnerImp.getOwnerByTypeInOrder(typeId);
	}
	
	/*
	 * 
	 * Below code for saving dying data 
	 * 
	 * */
	@ResponseBody
	@RequestMapping(path="/dyingcontroller/savedyingentry", method = RequestMethod.POST)
	public ModelDying saveDyingEntry(ModelDying modelDying, Model model,
			ModelUOM modelUOM,
			ModelDyingPlan modelDyingPlan,
			@RequestParam("dyingPlanId") Long dyingPlanId,
			@RequestParam("dyingDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date dyingDate,
			@RequestParam("activeStatus") int activeStatus,
			@RequestParam("uomId") Long uomId,
			@RequestParam("dyingQty") Double dyingQty,
			@RequestParam("dyingRemarks") String dyingRemarks
			
			) {
		
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		modelDying.setEntryTimeStamp(entryTime);
		modelDying.setDyingDate(dyingDate);
		
		
		Long logonUserId=systemUserId;
		ModelUser modelUser1 = new ModelUser();
		modelUser1.setUserId(logonUserId);
		
		modelDying.setEnteredBy(modelUser1);
		if(activeStatus==1) 
		{
			modelDying.setActiveStatus(1);		
		}
		else
		{
			modelDying.setActiveStatus(0);
		}
		
		modelDyingPlan.setDyingPlanId(dyingPlanId);
		modelDying.setModelDyingPlan(modelDyingPlan);
		modelDying.setDyingQty(dyingQty);
		modelDying.setDyingRemarks(dyingRemarks);
		modelUOM.setUomId(uomId);
		modelDying.setModelUOM(modelUOM);
		this.daoDying.saveDying(modelDying);
		
		model.addAttribute("modelDying",modelDying);	
		//return "redirect:/dyingcontroller/init";
		return modelDying;
		
	}
	
	@ResponseBody
    @RequestMapping(path="/dyingcontroller/dyingupdate", method=RequestMethod.POST)
	    public String dyingUpdate(Model model,ModelDying modelDying,	    		
		@RequestParam("dyingId") Long dyingId,
	    @RequestParam("dyingQty") Double dyingQty,
	    @RequestParam("dyingRemarks") String dyingRemarks
	 ) {
		         		
		    System.out.println("Dying dyingId : "+dyingId);
		    System.out.println("Dying dyingQty : "+dyingQty);
		   
			Optional<ModelDying> modelDyingOpt = dyingRepository.findById(dyingId);
			modelDying=modelDyingOpt.get();
		
			modelDying.setDyingQty(dyingQty);
			modelDying.setDyingRemarks(dyingRemarks);
					
			modelDying.setActiveStatus(1);
						
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		modelDying.setUpdateTimeStamp(entryTime);
		
		Long logonUserId=systemUserId;
		ModelUser modelUser1 = new ModelUser();
		modelUser1.setUserId(logonUserId);
		
		modelDying.setUpdatedBy(modelUser1);
				
		daoDying.saveDying(modelDying);	
		
		return "redirect:/dyingcontroller/init";
			
	              }
		
	
	/*
	 * below code for showing dying Qty
	 * 
	 * */
	
	
	@ResponseBody
	@RequestMapping(path = "/dyingcontroller/getdyingbyid", method = RequestMethod.GET)
	 public List<ModelDyingCustom> getDyingById(@RequestParam("id")Long id, Model model){
		 System.out.println("I am in dyingQty Controller & id is " +id);
			List<ModelDyingCustom> modelDyingList =  daoDying.getDyingById(id);
			System.out.println("size: "+modelDyingList.size());
			model.addAttribute("modelDyingList", modelDyingList);	 
		 return modelDyingList;		 
	 }
	
	/*
	 * below code for showing dying Done Qty
	 * 
	 * */
	
	
	@ResponseBody
	@RequestMapping(path = "/dyingDoneQuantity/", method = RequestMethod.GET)
	 public List<ModelDyingCustom> getDyingByIdDate(Model model,
			 @RequestParam("id")Long id
			 ){
		 System.out.println("I am in dyingQty Controller & id is " +id);
			List<ModelDyingCustom> modelDyingList =  daoDying.getDyingDoneById(id);
			model.addAttribute("modelDyingList", modelDyingList);	 
		 return modelDyingList;		 
	 }	
	
	/*
	 * 
	 * Below code for pending inquiry search 
	 * 
	 * */
	
	@ResponseBody
	@RequestMapping(path="/dyingcontroller/getpendingdyingordersearch",method = RequestMethod.GET)
	public List<ModelDyingCustom> getPendingDyingOrderSearch(Model model,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("pOId")Long pOId , 				 
				@RequestParam("dyingById")Long dyingById,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,
				@RequestParam("itemId")Long itemId
				) {

		System.out.println("I am in dyingController's pendingOrderSearch method");
	
		try {
			
		System.out.println("pOId :" + pOId + "\n BuyerId: "+ buyerId +"\n dyingById: " + dyingById + "\n startDate " + startDate + "\n endDate " + endDate+ "\n itemId: "+itemId);
		List<ModelDyingCustom> modelDyingCustomList = new ArrayList<ModelDyingCustom>();	
		modelDyingCustomList=daoDying.getPendingDyingOrderDetails(buyerId,pOId,dyingById,startDate,endDate,itemId);
		System.out.println("Size:"+modelDyingCustomList.size());
		 
		return modelDyingCustomList;
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
	@RequestMapping(path="/dyingController/completedOrderSearch",method = RequestMethod.GET)
	public List<ModelDyingCustom> getCompletedDyingOrderSearch(Model model,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("pOId")Long pOId , 				 
				@RequestParam("dyingById")Long dyingById,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,
				@RequestParam("itemId")Long itemId
				) {	
		System.out.println("I am in dyingController's completedOrderSearch method");
		try {
			
		System.out.println("\n pOId :" + pOId + "\n BuyerId: "+ buyerId +"\n dyingById: " + dyingById + "\n startDate " + startDate + "\n endDate " + endDate +"\n itemId: "+itemId);
		
		List<ModelDyingCustom> modelDyingCustomList = new ArrayList<ModelDyingCustom>();		
		modelDyingCustomList=daoDying.getCompletedDyingOrderDetails(buyerId, pOId, dyingById, startDate, endDate,itemId);
		System.out.println("Size:"+modelDyingCustomList.size());
		
		return modelDyingCustomList;
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
	@RequestMapping(path="/dyingController/pendingWOSearch",method = RequestMethod.GET)
	public List<ModelDyingCustom> getPendingDyingWOSearch(Model model,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("pOId")Long pOId , 				 
				@RequestParam("dyingById")Long dyingById,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,
				@RequestParam("itemId")Long itemId
				) {
	
		System.out.println("I am in ajax Pending Dying WOSearch");

		try {
			
		System.out.println("pOId :" + pOId + "\n BuyerId :"+ buyerId +"\n dyingById: " + dyingById + "\n startDate " + startDate + "\n endDate " + endDate+"\n itemId:"+itemId);

		List<ModelDyingCustom> modelDyingCustomList = new ArrayList<ModelDyingCustom>();
		modelDyingCustomList= daoDying.getPendingDyingWODetails(buyerId,pOId,dyingById,startDate,endDate,itemId);
		System.out.println("Size:"+modelDyingCustomList.size()); 
			
		return modelDyingCustomList;	
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
	@RequestMapping(path="/dyingController/completedWOSearch",method = RequestMethod.GET)
	public List<ModelDyingCustom> getCompletedWOSearch(Model model,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("pOId")Long pOId , 				 
				@RequestParam("dyingById")Long dyingById,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,
				@RequestParam("itemId")Long itemId
				) {	
		System.out.println("I am in ajax Completed WOSearch");
		try {			
		System.out.println("pOId :" + pOId + "\n BuyerId :"+ buyerId +"\n dyingById: " + dyingById + "\n startDate " + startDate + "\n endDate " + endDate+"\n itemId:"+itemId);
		List<ModelDyingCustom> modelDyingCustomList = new ArrayList<ModelDyingCustom>();
		modelDyingCustomList=daoDying.getCompletedDyingWODetails(buyerId, pOId, dyingById, startDate, endDate, itemId);		
		System.out.println("Size:"+modelDyingCustomList.size()); 
		
		return modelDyingCustomList;
		}
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	}
	

}
