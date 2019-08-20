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
import com.biziitech.mlfm.custom.model.ModelUOMCustom;
import com.biziitech.mlfm.custom.model.ModelWashingPlanCustom;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.dao.DaoWashingPlan;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerTypeImp;
import com.biziitech.mlfm.daoimpl.DaoUOMImp;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelOrderOwnerType;
import com.biziitech.mlfm.model.ModelPOMst;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.model.ModelWashingPlan;
import com.biziitech.mlfm.repository.WashingPlanRepository;

@Controller
public class WashingPlanController {
	
	@Autowired
	private WashingPlanRepository washingPlanRepository;
	
	@Autowired
	private DaoWashingPlan daoWashingPlan;
	
	@Autowired
	private DaoOrderOwnerImp daoOrderOwnerImp;
	
	@Autowired
	private DaoUOMImp daoUOMImp;
	
	@Autowired
	private DaoOrderOwnerTypeImp daoOrderOwnerTypeImp;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


private Long systemUserId;
	
	
	@RequestMapping(path="/washingplancontroller/init/{userId}")
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
		
		
		return "washingPlan";
	
	}
	
	@ResponseBody
	@RequestMapping(path="/washingplancontroller/findowners")
	public List<ModelOrderOwner> findOwners(@RequestParam("buyerType")Long typeId){
		
		System.out.println("" + typeId);
		
		return daoOrderOwnerImp.getOwnerByType(typeId);
	}
	
	
	@ResponseBody
	@RequestMapping(path="/washingplancontroller/savewashingplan", method = RequestMethod.POST)
	public List<ModelWashingPlanCustom> saveWashingPlan(ModelWashingPlan modelWashingPlan, Model model,
			ModelPOMst modelPOMst, ModelUOM modelUOM,
			@RequestParam("pOId") Long pOId,
			@RequestParam("uomId") Long uomId,
			@RequestParam("washingPlanDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date washingPlanDate,
			@RequestParam("washingPlanQty") Double washingPlanQty,
			@RequestParam("activeStatus") int activeStatus,
			@RequestParam("washingPlanRemarks") String washingPlanRemarks
			
			
			) {
		
		
		
		System.out.println("IN WashingPlan Controller's saveWashingPlan Method");
		
		try {
		
		System.out.println("pOId :" + pOId +"\n uomId: "+uomId+"\n washingPlanDate: " + washingPlanDate +"\n washingPlanDate: "+washingPlanDate+ "\n activeStatus " + activeStatus + "\n washingPlanRemarks " + washingPlanRemarks);
		
		
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		modelWashingPlan.setEntryTimeStamp(entryTime);
		modelWashingPlan.setWashingPlanDate(washingPlanDate);
		
		
		Long logonUserId=systemUserId;
		ModelUser modelUser1 = new ModelUser();
		modelUser1.setUserId(logonUserId);
		
		
		
		modelWashingPlan.setEnteredBy(modelUser1);
		modelPOMst.setpOMstId(pOId);
		modelUOM.setUomId(uomId);
		modelWashingPlan.setModelUOM(modelUOM);
		modelWashingPlan.setModelPOMst(modelPOMst);
		modelWashingPlan.setWashingPlanQty(washingPlanQty);

		modelWashingPlan.setWashingPlanRemarks(washingPlanRemarks);
		if(activeStatus==1) 
		{
			modelWashingPlan.setActiveStatus(1);
			modelWashingPlan.setsActive("Active");
			modelWashingPlan.setActive(true);
			
		}else {
			
			modelWashingPlan.setActiveStatus(0);
			modelWashingPlan.setsActive("InActive");
			modelWashingPlan.setActive(false);
		}
		
		this.daoWashingPlan.saveWashingPlan(modelWashingPlan);
		
		System.out.println("MendingPlan Id : " + modelWashingPlan.getWashingPlanId());
		
		
		List<ModelWashingPlanCustom> modelMendingPlanList = daoWashingPlan.getWashingPlanBy(pOId);
		System.out.println("Size : " + modelMendingPlanList.size());

		return modelMendingPlanList;
		
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}	
		
	}
	
	@ResponseBody
    @RequestMapping(path="/washingplancontroller/washingplanupdate", method = RequestMethod.POST)
	    public List<ModelWashingPlanCustom> washingPlanUpdate(Model model,ModelWashingPlan modelWashingPlan,
	    		ModelUOM modelUOM,
			@RequestParam("washingPlanId") Long washingPlanId,
			@RequestParam("pOId") Long pOId,
			@RequestParam("uomId") Long uomId,
	    	@RequestParam("washingPlanQty") Double washingPlanQty,
	    	@RequestParam("washingPlanRemarks") String washingPlanRemarks,
	    	@RequestParam("washingPlanDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date washingPlanDate,
	    	@RequestParam("activeStatus") int activeStatus
	 ) {
		         
		
		try { 
		    System.out.println("washingPlanId: "+washingPlanId+"\n pOId: "+pOId+"\n uomId: "+uomId+"\n washingPlanQty: "+washingPlanQty+"\n washingPlanRemarks: "+washingPlanRemarks+"\n washingPlanDate: "+washingPlanDate+"\n activeStatus: "+activeStatus);
			
			
			Optional<ModelWashingPlan> modelWashingPlanOpt = washingPlanRepository.findById(washingPlanId);
			
			
		    //System.out.println("QCPlanId:"+QCPlanQTY);
			//Optional<ModelQCPlan> modelQCPlanOpt = qCPlanRepository.findById(QCPlanId);
			
			 
			modelWashingPlan=modelWashingPlanOpt.get();
			
			modelWashingPlan.setWashingPlanQty(washingPlanQty);
			modelWashingPlan.setActiveStatus(activeStatus);
			modelWashingPlan.setWashingPlanRemarks(washingPlanRemarks);
			modelWashingPlan.setWashingPlanDate(washingPlanDate);
			modelUOM.setUomId(uomId);
			modelWashingPlan.setModelUOM(modelUOM);
			
			
			
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		modelWashingPlan.setUpdateTimeStamp(entryTime);
		
		
		Long logonUserId=systemUserId;
		ModelUser modelUser1 = new ModelUser();
		modelUser1.setUserId(logonUserId);
		
		
		modelWashingPlan.setUpdatedBy(modelUser1);
		
		
		
		this.daoWashingPlan.saveWashingPlan(modelWashingPlan);	
		
		List<ModelWashingPlanCustom> modelWashingPlanList= daoWashingPlan.getWashingPlanBy(pOId);
		
			return modelWashingPlanList;
			
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}	
			
	   }
	
	@ResponseBody
	@RequestMapping(path="/washingplancontroller/deletewashingplan", method = RequestMethod.POST)
	public List<ModelWashingPlanCustom> deleteWashingPlan(ModelWashingPlan modelWashingPlan, Model model,
			@RequestParam("washingPlanId") Long washingPlanId,
			@RequestParam("pOId") Long pOId
			
			
			
			) {
		
		
		
		System.out.println("IN WashingPlan Controller's deleteWashingPlan Method");
		
		try {
		
		System.out.println("pOId: " +pOId);
		
		
		
		
		
		this.daoWashingPlan.deleteWashingPlan(washingPlanId);
		
		System.out.println("mendingPlanId : " + modelWashingPlan.getWashingPlanId());
		
		
		List<ModelWashingPlanCustom> modelWashingPlanList = daoWashingPlan.getWashingPlanBy(pOId);
		System.out.println("Size : " + modelWashingPlanList.size());

		return modelWashingPlanList;
		
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}	
		
	}
	
	
	@ResponseBody
	@RequestMapping(path="/washingplancontroller/pendingwashingplansearch",method = RequestMethod.GET)
	public List<ModelWashingPlanCustom> pendingWashingPlanSearch(Model model,
				@RequestParam("pOId")Long pOId,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate
				) {

		System.out.println("I am in Washing Plan controller's pendingWashingPlanSearch method");
	
		try {
			
		System.out.println("POId :" + pOId + "\n buyerId: "+buyerId+"\n startDate " + startDate + "\n endDate " + endDate);
		List<ModelWashingPlanCustom> modelWashingPlanCustomList = new ArrayList<ModelWashingPlanCustom>();	
		modelWashingPlanCustomList=daoWashingPlan.getPendingWashingPlanDetails(pOId, buyerId, startDate, endDate);
		System.out.println("Size:"+modelWashingPlanCustomList.size());
		
		
		return modelWashingPlanCustomList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	@ResponseBody
	@RequestMapping(path="/washingplancontroller/completedwashingplansearch",method = RequestMethod.GET)
	public List<ModelWashingPlanCustom> completedWashingPlanSearch(Model model,
				@RequestParam("pOId")Long pOId,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate
				) {

		System.out.println("I am in WashingPlan controller's Completed WashingPlanSearch method");
	
		try {
			
		System.out.println("POId :" + pOId + "\n startDate " + startDate + "\n endDate " + endDate);
		List<ModelWashingPlanCustom> modelWashingPlanCustomList = new ArrayList<ModelWashingPlanCustom>();	
		modelWashingPlanCustomList=daoWashingPlan.getCompletedWashingPlanDetails(pOId, buyerId, startDate, endDate);
		System.out.println("Size:"+modelWashingPlanCustomList.size());
		
		
		return modelWashingPlanCustomList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	
	
	
	
	@ResponseBody
	@RequestMapping(path="/washingplancontroller/savewashingplansearch",method = RequestMethod.GET)
	public List<ModelWashingPlanCustom> saveWashingPlanSearch(Model model,
			@RequestParam("pOId") Long pOId
				
				) {

		System.out.println("I am in Washing controller's saveWashingPlanSearch method");
	
		try {
			
		System.out.println("pOId: " + pOId);
		
		List<ModelWashingPlanCustom> modelWashingPlanList= daoWashingPlan.getWashingPlanBy(pOId);
		
		System.out.println("Size:"+modelWashingPlanList.size());
		
		
		return modelWashingPlanList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	@ResponseBody
	@RequestMapping(path="/washingplancontroller/showwashingplandata",method = RequestMethod.GET)
	public List<ModelWashingPlanCustom> showWashingPlanData(Model model,
			@RequestParam("washingPlanId") Long washingPlanId
				
				) {

		System.out.println("I am in Washing controller's showWashingPlanData method");
	
		try {
			
		System.out.println("washingPlanId: " + washingPlanId);
		
		List<ModelWashingPlanCustom> modelWashingPlanList= daoWashingPlan.getWashingPlanById(washingPlanId);
		
		System.out.println("Size:"+modelWashingPlanList.size());
		
		
		return modelWashingPlanList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}

}
