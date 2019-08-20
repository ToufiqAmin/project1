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

import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.custom.model.ModelFinishingPlanCustom;
import com.biziitech.mlfm.custom.model.ModelUOMCustom;
import com.biziitech.mlfm.dao.DaoFinishingPlan;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerTypeImp;
import com.biziitech.mlfm.daoimpl.DaoUOMImp;
import com.biziitech.mlfm.model.ModelFinishingPlan;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelOrderOwnerType;
import com.biziitech.mlfm.model.ModelPOMst;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.repository.FinishingPlanRepository;

@Controller
public class FinishingPlanController {
	
	@Autowired
	private FinishingPlanRepository finishingPlanRepository;
	
	@Autowired
	private DaoFinishingPlan daoFinishingPlan;
	
	@Autowired
	private DaoOrderOwnerImp daoOrderOwnerImp;
	
	@Autowired
	private DaoOrderOwnerTypeImp daoOrderOwnerTypeImp;
	
	@Autowired
	private DaoUOMImp daoUOMImp;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


private Long systemUserId;
	
	
	@RequestMapping(path="/finishingplancontroller/init/{userId}")
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
		
		
		List<ModelOrderOwnerType> modelOrderOwnerTypeList= daoOrderOwnerTypeImp.getTypeName();
		model.addAttribute("modelOrderOwnerTypeList", modelOrderOwnerTypeList);
		
		List<ModelUOMCustom> modelUOMList = daoUOMImp.getUOMListCustom();
		model.addAttribute("modelUOMList", modelUOMList);
		
		
		return "finishingPlan";
	
	}
	
	@RequestMapping(path="/finishingplancontroller/findowners")
	@ResponseBody
	public List<ModelOrderOwner> findOwners(@RequestParam("buyerType")Long typeId){
		
		System.out.println("" + typeId);
		
		return daoOrderOwnerImp.getOwnerByType(typeId);
	}
	
	@ResponseBody
	@RequestMapping(path="/finishingplancontroller/pendingfinishingplanposearch",method = RequestMethod.GET)
	public List<ModelFinishingPlanCustom> pendingFinishingPlanPOSearch(Model model,
				@RequestParam("pOId")Long pOId,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate
				) {

		System.out.println("I am in FinishingPlan controller's Pending FinishingplanSearch method");
	
		try {
			
		System.out.println("POId :" + pOId + "\n buyerId: "+buyerId+"\n startDate " + startDate + "\n endDate " + endDate);
		List<ModelFinishingPlanCustom> modelFinishingPlanCustomList = new ArrayList<ModelFinishingPlanCustom>();	
		modelFinishingPlanCustomList=daoFinishingPlan.getPendingFinisingPlanPODetails(pOId, buyerId, startDate, endDate);
		System.out.println("Size:"+modelFinishingPlanCustomList.size());
				
		return modelFinishingPlanCustomList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	@ResponseBody
	@RequestMapping(path="/finishingplancontroller/completedfinishingplanposearch",method = RequestMethod.GET)
	public List<ModelFinishingPlanCustom> completedFinishingPlanPOSearch(Model model,
				@RequestParam("pOId")Long pOId,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate
				) {

		System.out.println("I am in FinishingPlan controller's Completed FinshingplanSearch method");
	
		try {
			
		System.out.println("POId :" + pOId + "\n startDate " + startDate + "\n endDate " + endDate);
		List<ModelFinishingPlanCustom> modelFinishingPlanCustomList = new ArrayList<ModelFinishingPlanCustom>();	
		modelFinishingPlanCustomList=daoFinishingPlan.getCompletedFinishingPlanPODetails(pOId, buyerId, startDate, endDate);
		System.out.println("Size:"+modelFinishingPlanCustomList.size());
				
		return modelFinishingPlanCustomList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	@ResponseBody
	@RequestMapping(path="/finishingplancontroller/finishingplansearch",method = RequestMethod.GET)
	public List<ModelFinishingPlanCustom> finishingPlanSearch(Model model,@RequestParam("pOId")Long pOId
				
				) {

		System.out.println("I am in FinishingPlan controller's finishingPlanSearch method");
	
		try {
			
		System.out.println("POId :" + pOId);
		List<ModelFinishingPlanCustom> modelFinishingPlanCustomList = new ArrayList<ModelFinishingPlanCustom>();	
		modelFinishingPlanCustomList=daoFinishingPlan.getFinishingPlanByPOId(pOId);
		System.out.println("Size:"+modelFinishingPlanCustomList.size());
		
		
		return modelFinishingPlanCustomList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	@ResponseBody
	@RequestMapping(path="/finishingplancontroller/savefinishingplansearch",method = RequestMethod.GET)
	public List<ModelFinishingPlanCustom> saveFinishingPlanSearch(Model model,
			@RequestParam("finishingPlanId")Long finishingPlanId
				
				) {

		System.out.println("I am in FinishingPlan controller's saveFinishingPlanSearch method");
	
		try {
			
		System.out.println("finishingPlanId :" + finishingPlanId);
		
		List<ModelFinishingPlanCustom> modelFinishingPlanList= daoFinishingPlan.getFinishingPlanByFinishingPlanId(finishingPlanId);
		
		System.out.println("Size:"+modelFinishingPlanList.size());
		
		
		return modelFinishingPlanList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	@ResponseBody
	@RequestMapping(path="/finishingplancontroller/savefinishingplan", method = RequestMethod.POST)
	public List<ModelFinishingPlanCustom> saveFinishingPlan(ModelFinishingPlan modelFinishingPlan, Model model, 
			ModelPOMst modelPOMst,
			ModelUOM modelUOM,
			@RequestParam("finishingPlanPOId") Long finishingPlanPOId,
			@RequestParam("uomId") Long uomId,
			@RequestParam("finishingPlanDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date finishingPlanDate,
			@RequestParam("finishingPlanQty") Double finishingPlanQty,
			@RequestParam("activeStatus") int activeStatus,
			@RequestParam("finishingPlanRemarks") String finishingPlanRemarks
						
			) {
						
		System.out.println("IN FinishingPlan Controller's saveFinishingPlan Method");
		
		try {
		
		System.out.println("finishingPlanPOId :" + finishingPlanPOId +"\n uomId: "+uomId+ "\n finishingPlanDate: "+ finishingPlanDate +"\n finishingPlanQty: " + finishingPlanQty +"\n activeStatus " + activeStatus + "\n finishingPlanRemarks " + finishingPlanRemarks);
		
		
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		modelFinishingPlan.setEntryTimeStamp(entryTime);
		modelFinishingPlan.setFinishingPlanDate(finishingPlanDate);
		
		Long logonUserId=systemUserId;
		ModelUser modelUser1 = new ModelUser();
		modelUser1.setUserId(logonUserId);
		
		modelFinishingPlan.setEnteredBy(modelUser1);
		modelPOMst.setpOMstId(finishingPlanPOId);
		modelUOM.setUomId(uomId);
		modelFinishingPlan.setModelUOM(modelUOM);
		modelFinishingPlan.setModelPOMst(modelPOMst);
		modelFinishingPlan.setFinishingPlanQty(finishingPlanQty);
		modelFinishingPlan.setFinishingPlanRemarks(finishingPlanRemarks);
		if(activeStatus==1) 
		{
			modelFinishingPlan.setActiveStatus(1);
			modelFinishingPlan.setsActive("Active");
			modelFinishingPlan.setActive(true);
			
		}else {
			
			modelFinishingPlan.setActiveStatus(0);
			modelFinishingPlan.setsActive("InActive");
			modelFinishingPlan.setActive(false);
		}
		
		this.daoFinishingPlan.saveFinishingPlan(modelFinishingPlan);
		
		System.out.println("FinishingPlan Id : " + modelFinishingPlan.getFinishingPlanId());
		
		
		List<ModelFinishingPlanCustom> modelFinishingPlanList = daoFinishingPlan.getFinishingPlanByPOId(finishingPlanPOId);
		System.out.println("Size : " + modelFinishingPlanList.size());

		return modelFinishingPlanList;
		
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}	
		
	}
	
	@ResponseBody
    @RequestMapping(path="/finishingplancontroller/finishingplanupdate", method=RequestMethod.POST)
	    public List<ModelFinishingPlanCustom> FinishingPlanUpdate(Model model,
	    		ModelFinishingPlan modelFinishingPlan,
	    		ModelUOM modelUOM,
	    		
			@RequestParam("finishingPlanId") Long finishingPlanId,
			@RequestParam("pOId") Long pOId,
			@RequestParam("uomId") Long uomId,
	    	@RequestParam("finishingPlanQTY") Double finishingPlanQTY,
	    	@RequestParam("finishingPlanRemarks") String finishingPlanRemarks,
	    	@RequestParam("finishingPlanDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date finishingPlanDate,
	    	@RequestParam("activeStatus") int activeStatus
	 ) {
		         		
		try { 
		    System.out.println("finishingPlanId: "+finishingPlanId+"\n uomId: "+uomId+"\n finishingPlanDate: "+finishingPlanDate+"\n finishingPlanQTY: "+finishingPlanQTY+"\n finishingPlanRemarks: "+finishingPlanRemarks+"\n activeStatus: "+activeStatus);
			System.out.println("pOId: "+pOId);
						
			Optional<ModelFinishingPlan> modelFinishingPlanOpt = finishingPlanRepository.findById(finishingPlanId);
						 
			modelFinishingPlan=modelFinishingPlanOpt.get();
			
			modelFinishingPlan.setFinishingPlanQty(finishingPlanQTY);
			modelFinishingPlan.setActiveStatus(activeStatus);
			modelFinishingPlan.setFinishingPlanRemarks(finishingPlanRemarks);
			modelFinishingPlan.setFinishingPlanDate(finishingPlanDate);
			modelUOM.setUomId(uomId);
			modelFinishingPlan.setModelUOM(modelUOM);
			
			
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		modelFinishingPlan.setUpdateTimeStamp(entryTime);
		
		Long logonUserId=systemUserId;
		ModelUser modelUser1 = new ModelUser();
		modelUser1.setUserId(logonUserId);
		
		modelFinishingPlan.setUpdatedBy(modelUser1);
		
		
		
		this.daoFinishingPlan.saveFinishingPlan(modelFinishingPlan);	
		
		List<ModelFinishingPlanCustom> modelFinishingPlanList= daoFinishingPlan.getFinishingPlanByPOId(pOId);
		
			return modelFinishingPlanList;
			
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}	
			
	  }

}
