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
import com.biziitech.mlfm.custom.model.ModelDyingPlanCustom;
import com.biziitech.mlfm.custom.model.ModelQCPlanCustom;
import com.biziitech.mlfm.custom.model.ModelUOMCustom;
import com.biziitech.mlfm.dao.DaoDyingPlan;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerTypeImp;
import com.biziitech.mlfm.daoimpl.DaoUOMImp;
import com.biziitech.mlfm.model.ModelDyingPlan;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelOrderOwnerType;
import com.biziitech.mlfm.model.ModelPOMst;
import com.biziitech.mlfm.model.ModelQCPlan;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.repository.DyingPlanRepository;

@Controller
public class DyingPlanController {
	
	
	@Autowired
	private DyingPlanRepository dyingPlanRepository;
	
	@Autowired
	private DaoDyingPlan daoDyingPlan;
	
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
	
	@RequestMapping(path="/dyingplancontroller/init/{userId}")
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
		
		return "dyingPlan";
	
	}
	
	@RequestMapping(path="/dyingplancontroller/findowners")
	@ResponseBody
	public List<ModelOrderOwner> findOwners(@RequestParam("buyerType")Long typeId){
		
		System.out.println("" + typeId);
		
		return daoOrderOwnerImp.getOwnerByType(typeId);
	}
	
	@ResponseBody
	@RequestMapping(path="/dyingplancontroller/pendingdyingplanposearch",method = RequestMethod.GET)
	public List<ModelDyingPlanCustom> pendingDyingPlanPOSearch(Model model,
				@RequestParam("pOId")Long pOId,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate
				) {

		System.out.println("I am in DyingPlan controller's Pending DyingplanSearch method");
	
		try {
			
		System.out.println("POId :" + pOId + "\n buyerId: "+buyerId+"\n startDate " + startDate + "\n endDate " + endDate);
		List<ModelDyingPlanCustom> modelDyingPlanCustomList = new ArrayList<ModelDyingPlanCustom>();	
		modelDyingPlanCustomList=daoDyingPlan.getPendingDyingPlanPODetails(pOId, buyerId, startDate, endDate);
		System.out.println("Size:"+modelDyingPlanCustomList.size());
		
		
		return modelDyingPlanCustomList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	
	@ResponseBody
	@RequestMapping(path="/dyingplancontroller/completeddyingplanposearch",method = RequestMethod.GET)
	public List<ModelDyingPlanCustom> completedDyingPlanPOSearch(Model model,
				@RequestParam("pOId")Long pOId,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate
				) {

		System.out.println("I am in DyingPlan controller's Completed DyingplanSearch method");
	
		try {
			
		System.out.println("POId :" + pOId + "\n startDate " + startDate + "\n endDate " + endDate);
		List<ModelDyingPlanCustom> modelDyingPlanCustomList = new ArrayList<ModelDyingPlanCustom>();	
		modelDyingPlanCustomList=daoDyingPlan.getCompletedDyingPlanPODetails(pOId, buyerId, startDate, endDate);
		System.out.println("Size:"+modelDyingPlanCustomList.size());
		
		
		return modelDyingPlanCustomList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	@ResponseBody
	@RequestMapping(path="/dyingplancontroller/dyingplansearch",method = RequestMethod.GET)
	public List<ModelDyingPlanCustom> dyingPlanSearch(Model model,@RequestParam("pOId")Long pOId
				
				) {

		System.out.println("I am in DyingPlan controller's dyingPlanSearch method");
	
		try {
			
		System.out.println("POId :" + pOId);
		List<ModelDyingPlanCustom> modelDyingPlanCustomList = new ArrayList<ModelDyingPlanCustom>();	
		modelDyingPlanCustomList=daoDyingPlan.getDyingPlanByPOId(pOId);
		System.out.println("Size:"+modelDyingPlanCustomList.size());
		
		
		return modelDyingPlanCustomList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	@ResponseBody
	@RequestMapping(path="/dyingplancontroller/savedyingplansearch",method = RequestMethod.GET)
	public List<ModelDyingPlanCustom> saveDyingPlanSearch(Model model,@RequestParam("dyingPlanId")Long dyingPlanId
				
				) {

		System.out.println("I am in DyingPlan controller's saveDyingPlanSearch method");
	
		try {
			
		System.out.println("dyingPlanId :" + dyingPlanId);
		
		List<ModelDyingPlanCustom> modelDyingPlanList= daoDyingPlan.getDyingPlanByDyingPlanId(dyingPlanId);
		
		System.out.println("Size:"+modelDyingPlanList.size());
		
		
		return modelDyingPlanList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	@ResponseBody
	@RequestMapping(path="/dyingplancontroller/savedyingplan", method = RequestMethod.POST)
	public List<ModelDyingPlanCustom> saveDyingPlan(ModelDyingPlan modelDyingPlan, Model model, 
			ModelPOMst modelPOMst,
			ModelUOM modelUOM,
			@RequestParam("dyingPlanPOId") Long dyingPlanPOId,
			@RequestParam("uomId") Long uomId,
			@RequestParam("dyingPlanDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date dyingPlanDate,
			@RequestParam("dyingPlanQty") Double dyingPlanQty,
			@RequestParam("activeStatus") int activeStatus,
			@RequestParam("dyingPlanRemarks") String dyingPlanRemarks
			
			
			) {
		
		
		
		System.out.println("IN DyingPlan Controller's saveDyingPlan Method");
		
		try {
		
		System.out.println("dyingPlanPOId :" + dyingPlanPOId +"\n uomId: "+uomId+ "\n dyingPlanDate: "+ dyingPlanDate +"\n dyingPlanQty: " + dyingPlanQty +"\n activeStatus " + activeStatus + "\n dyingPlanRemarks " + dyingPlanRemarks);
		
		
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		modelDyingPlan.setEntryTimeStamp(entryTime);
		modelDyingPlan.setDyingPlanDate(dyingPlanDate);
		
		
		Long logonUserId=systemUserId;
		ModelUser modelUser1 = new ModelUser();
		modelUser1.setUserId(logonUserId);
		
		modelDyingPlan.setEnteredBy(modelUser1);
		modelPOMst.setpOMstId(dyingPlanPOId);
		modelUOM.setUomId(uomId);
		modelDyingPlan.setModelUOM(modelUOM);
		modelDyingPlan.setModelPOMst(modelPOMst);
		modelDyingPlan.setDyingPlanQty(dyingPlanQty);
		modelDyingPlan.setDyingPlanRemarks(dyingPlanRemarks);
		if(activeStatus==1) 
		{
			modelDyingPlan.setActiveStatus(1);
			modelDyingPlan.setsActive("Active");
			modelDyingPlan.setActive(true);
			
		}else {
			
			modelDyingPlan.setActiveStatus(0);
			modelDyingPlan.setsActive("InActive");
			modelDyingPlan.setActive(false);
		}
		
		this.daoDyingPlan.saveDyingPlan(modelDyingPlan);
		
		System.out.println("DyingPlan Id : " + modelDyingPlan.getDyingPlanId());
		
		
		List<ModelDyingPlanCustom> modelDyingPlanList = daoDyingPlan.getDyingPlanByPOId(dyingPlanPOId);
		System.out.println("Size : " + modelDyingPlanList.size());

		return modelDyingPlanList;
		
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}	
		
	}
	
	@ResponseBody
    @RequestMapping(path="/dyingplancontroller/dyingplanupdate", method=RequestMethod.POST)
	    public List<ModelDyingPlanCustom> DyingPlanUpdate(Model model,
	    		ModelDyingPlan modelDyingPlan,
	    		ModelUOM modelUOM,
	    		
			@RequestParam("dyingPlanId") Long dyingPlanId,
			@RequestParam("pOId") Long pOId,
			@RequestParam("uomId") Long uomId,
	    	@RequestParam("dyingPlanQTY") Double dyingPlanQTY,
	    	@RequestParam("dyingPlanRemarks") String dyingPlanRemarks,
	    	@RequestParam("dyingPlanDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date dyingPlanDate,
	    	@RequestParam("activeStatus") int activeStatus
	 ) {
		         
		
		try { 
		    System.out.println("dyingPlanId: "+dyingPlanId+"\n uomId: "+uomId+"\n dyingPlanDate: "+dyingPlanDate+"\n dyingPlanQTY: "+dyingPlanQTY+"\n dyingPlanRemarks: "+dyingPlanRemarks+"\n activeStatus: "+activeStatus);
			System.out.println("pOId: "+pOId);
			
			
			Optional<ModelDyingPlan> modelDyingPlanOpt = dyingPlanRepository.findById(dyingPlanId);
			
			
		    //System.out.println("QCPlanId:"+QCPlanQTY);
			//Optional<ModelQCPlan> modelQCPlanOpt = qCPlanRepository.findById(QCPlanId);
			
			 
			modelDyingPlan=modelDyingPlanOpt.get();
			
			modelDyingPlan.setDyingPlanQty(dyingPlanQTY);
			modelDyingPlan.setActiveStatus(activeStatus);
			modelDyingPlan.setDyingPlanRemarks(dyingPlanRemarks);
			modelDyingPlan.setDyingPlanDate(dyingPlanDate);
			modelUOM.setUomId(uomId);
			modelDyingPlan.setModelUOM(modelUOM);
			
			
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		modelDyingPlan.setUpdateTimeStamp(entryTime);
		
		Long logonUserId=systemUserId;
		ModelUser modelUser1 = new ModelUser();
		modelUser1.setUserId(logonUserId);
		
		modelDyingPlan.setUpdatedBy(modelUser1);
		
		
		
		this.daoDyingPlan.saveDyingPlan(modelDyingPlan);	
		
		List<ModelDyingPlanCustom> modelDyingPlanList= daoDyingPlan.getDyingPlanByPOId(pOId);
		
			return modelDyingPlanList;
			
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}	
			
	  }
	
	
	

}
