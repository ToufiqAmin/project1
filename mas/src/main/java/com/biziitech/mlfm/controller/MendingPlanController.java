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
import com.biziitech.mlfm.custom.model.ModelMendingPlanCustom;
import com.biziitech.mlfm.custom.model.ModelUOMCustom;
import com.biziitech.mlfm.dao.DaoMendingPlan;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerTypeImp;
import com.biziitech.mlfm.daoimpl.DaoUOMImp;
import com.biziitech.mlfm.model.ModelMendingPlan;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelOrderOwnerType;

import com.biziitech.mlfm.model.ModelPOMst;
import com.biziitech.mlfm.model.ModelQC;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.repository.MendingPlanRepository;

@Controller
public class MendingPlanController {
	
	@Autowired
	private DaoMendingPlan daoMending;
	
	@Autowired
	private DaoOrderOwnerImp daoOrderOwnerImp;
	
	@Autowired
	private DaoOrderOwnerTypeImp daoOrderOwnerTypeImp;
	
	@Autowired
	private DaoUOMImp daoUOMImp;
	
	@Autowired
	private MendingPlanRepository mendingPlanRepository;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


private Long systemUserId;
	
	
	@RequestMapping(path="/mendingplancontroller/init/{userId}")
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
		
		
		return "mendingPlan";
	
	}
	
	
	@ResponseBody
	@RequestMapping(path="/mendingplancontroller/findowners")
	public List<ModelOrderOwner> findOwners(@RequestParam("buyerType")Long typeId){
		
		System.out.println("" + typeId);
		
		return daoOrderOwnerImp.getOwnerByType(typeId);
	}
	
	
	@ResponseBody
	@RequestMapping(path="/mendingplancontroller/savemendingplan", method = RequestMethod.POST)
	public List<ModelMendingPlanCustom> saveMendingPlan(ModelMendingPlan modelMendingPlan, Model model, 
			ModelUOM modelUOM,
			ModelPOMst modelPOMst,
			ModelQC modelQC,
			@RequestParam("pOId") Long pOId,
			@RequestParam("qCId") Long qCId,
			@RequestParam("uomId") Long uomId,
			@RequestParam("mendingPlanDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date mendingPlanDate,
			@RequestParam("mendingPlanQty") Double mendingPlanQty,
			@RequestParam("activeStatus") int activeStatus,
			@RequestParam("mendingPlanRemarks") String mendingPlanRemarks
			
			
			) {
		
		
		
		System.out.println("IN MendingPlan Controller's saveMendingPlan Method");
		
		try {
		
		System.out.println("pOId :" + pOId + "\n qCId: "+ qCId +"\n uomId; "+uomId+"\n mendingPlanDate: " + mendingPlanDate +"\n mendingPlanQty: "+mendingPlanQty+ "\n activeStatus " + activeStatus + "\n mendingPlanRemarks " + mendingPlanRemarks);
		
		
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		modelMendingPlan.setEntryTimeStamp(entryTime);
		modelMendingPlan.setMendingPlanDate(mendingPlanDate);
		//  modelMendingPlan.setEnteredBy(1);
		modelPOMst.setpOMstId(pOId);
		modelMendingPlan.setModelPOMst(modelPOMst);
		modelQC.setqCId(qCId);
		modelMendingPlan.setModelQC(modelQC);
		modelMendingPlan.setMendingPlanQty(mendingPlanQty);
		modelUOM.setUomId(uomId);
		modelMendingPlan.setModelUOM(modelUOM);

		modelMendingPlan.setMendingPlanRemarks(mendingPlanRemarks);
		
		Long logonUserId=systemUserId;
		ModelUser modelUser1 = new ModelUser();
		modelUser1.setUserId(logonUserId);
		modelMendingPlan.setEnteredBy(modelUser1);
		
		if(activeStatus==1) 
		{
			modelMendingPlan.setActiveStatus(1);
			modelMendingPlan.setsActive("Active");
			modelMendingPlan.setActive(true);
			
		}else {
			
			modelMendingPlan.setActiveStatus(0);
			modelMendingPlan.setsActive("InActive");
			modelMendingPlan.setActive(false);
		}
		
		this.daoMending.saveMendingPlan(modelMendingPlan);
		
		System.out.println("MendingPlan Id : " + modelMendingPlan.getMendingPlanId());
		
		
		List<ModelMendingPlanCustom> modelMendingPlanList = daoMending.getMendingPlanBy(pOId, qCId);
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
    @RequestMapping(path="/mendingplancontroller/mendingplanupdate", method=RequestMethod.POST)
	    public List<ModelMendingPlanCustom> mendingPlanUpdate(Model model,ModelMendingPlan modelMendingPlan,
	    		ModelUOM modelUOM,	
	    		
			@RequestParam("mendingPlanId") Long mendingPlanId,
			@RequestParam("pOId") Long pOId,
			@RequestParam("qCId") Long qCId,
			@RequestParam("uomId") Long uomId,
	    	@RequestParam("mendingPlanQty") Double mendingPlanQty,
	    	@RequestParam("mendingPlanRemarks") String mendingPlanRemarks,
	    	@RequestParam("mendingPlanDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date mendingPlanDate,
	    	@RequestParam("activeStatus") int activeStatus
	 ) {
		         
		
		try { 
		    System.out.println("mendingPlanId: "+mendingPlanId+"\n pOId: "+pOId+"\n uomId"+uomId+"\n mendingPlanQty: "+mendingPlanQty+"\n mendingPlanRemarks: "+mendingPlanRemarks+"\n mendingPlanDate: "+mendingPlanDate+"\n activeStatus: "+activeStatus);
			
			
			Optional<ModelMendingPlan> modelMendingPlanOpt = mendingPlanRepository.findById(mendingPlanId);
			
			
		    //System.out.println("QCPlanId:"+QCPlanQTY);
			//Optional<ModelQCPlan> modelQCPlanOpt = qCPlanRepository.findById(QCPlanId);
			
			 
			modelMendingPlan=modelMendingPlanOpt.get();
			
			modelMendingPlan.setMendingPlanQty(mendingPlanQty);
			modelMendingPlan.setActiveStatus(activeStatus);
			modelMendingPlan.setMendingPlanRemarks(mendingPlanRemarks);
			modelMendingPlan.setMendingPlanDate(mendingPlanDate);
			modelUOM.setUomId(uomId);
			modelMendingPlan.setModelUOM(modelUOM);
			
			
			
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		modelMendingPlan.setUpdateTimeStamp(entryTime);
		
		
		Long logonUserId=systemUserId;
		ModelUser modelUser1 = new ModelUser();
		modelUser1.setUserId(logonUserId);
		modelMendingPlan.setUpdatedBy(modelUser1);
		
		
		
		this.daoMending.saveMendingPlan(modelMendingPlan);	
		
		List<ModelMendingPlanCustom> modelMendingPlanList= daoMending.getMendingPlanBy(pOId, qCId);
		
			return modelMendingPlanList;
			
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}	
			
	   }
	
	
	@ResponseBody
	@RequestMapping(path="/mendingplancontroller/deletemendingplan", method = RequestMethod.POST)
	public List<ModelMendingPlanCustom> deleteMendingPlan(ModelMendingPlan modelMendingPlan, Model model,
			@RequestParam("mendingPlanId") Long mendingPlanId,
			@RequestParam("qCId") Long qCId,
			@RequestParam("pOId") Long pOId
			
			
			
			) {
		
		
		
		System.out.println("IN MendingPlan Controller's deleteMendingPlan Method");
		
		try {
		
		System.out.println("qCId :" + qCId + "\n pOId: " +pOId);
		
		
		
		
		
		this.daoMending.deleteMendingPlan(mendingPlanId);
		
		System.out.println("mendingPlanId : " + modelMendingPlan.getMendingPlanId());
		
		
		List<ModelMendingPlanCustom> modelMendingPlanList = daoMending.getMendingPlanBy(pOId, qCId);
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
	@RequestMapping(path="/mendingplancontroller/pendingmendingplansearch",method = RequestMethod.GET)
	public List<ModelMendingPlanCustom> pendingMendingPlanSearch(Model model,
				@RequestParam("pOId")Long pOId,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate
				) {

		System.out.println("I am in Mending Plan controller's Pending MendingPlanSearch method");
	
		try {
			
		System.out.println("POId :" + pOId + "\n buyerId: "+buyerId+"\n startDate " + startDate + "\n endDate " + endDate);
		List<ModelMendingPlanCustom> modelMendingPlanCustomList = new ArrayList<ModelMendingPlanCustom>();	
		modelMendingPlanCustomList=daoMending.getPendingMendingPlanDetails(pOId, buyerId, startDate, endDate);
		System.out.println("Size:"+modelMendingPlanCustomList.size());
		
		
		return modelMendingPlanCustomList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	
	@ResponseBody
	@RequestMapping(path="/mendingplancontroller/completedmendingplansearch",method = RequestMethod.GET)
	public List<ModelMendingPlanCustom> completedMendingPlanSearch(Model model,
				@RequestParam("pOId")Long pOId,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate
				) {

		System.out.println("I am in MendingPlan controller's Completed MendingPlanSearch method");
	
		try {
			
		System.out.println("POId :" + pOId + "\n startDate " + startDate + "\n endDate " + endDate);
		List<ModelMendingPlanCustom> modelMendingPlanCustomList = new ArrayList<ModelMendingPlanCustom>();	
		modelMendingPlanCustomList=daoMending.getCompletedMendingPlanDetails(pOId, buyerId, startDate, endDate);
		System.out.println("Size:"+modelMendingPlanCustomList.size());
		
		
		return modelMendingPlanCustomList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	
	@ResponseBody
	@RequestMapping(path="/mendingplancontroller/qcplansearch",method = RequestMethod.GET)
	public List<ModelMendingPlanCustom> qCPlanSearch(Model model,
			@RequestParam("pOId") Long pOId
				
				) {

		System.out.println("I am in MendingPlan controller's qCPlanSearch method");
	
		try {
			
		System.out.println("pOId :" + pOId);
		
		List<ModelMendingPlanCustom> modelMendingPlanList= daoMending.getQCPlanByPOId(pOId);
		
		System.out.println("Size:"+modelMendingPlanList.size());
		
		
		return modelMendingPlanList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	
	@ResponseBody
	@RequestMapping(path="/mendingplancontroller/qcsearch",method = RequestMethod.GET)
	public List<ModelMendingPlanCustom> qCSearch(Model model,@RequestParam("pOId")Long pOId
				
				) {

		System.out.println("I am in MendingPlan controller's qCSearch method");
	
		try {
			
		System.out.println("pOId :" + pOId);
		
		List<ModelMendingPlanCustom> modelMendingPlanList= daoMending.getQCByPOId(pOId);
		
		System.out.println("Size:"+modelMendingPlanList.size());
		
		
		return modelMendingPlanList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	@ResponseBody
	@RequestMapping(path="/mendingplancontroller/savemendingplansearch",method = RequestMethod.GET)
	public List<ModelMendingPlanCustom> saveMendingPlanSearch(Model model,
			@RequestParam("pOId") Long pOId,
			@RequestParam("qCId") Long qCId
				
				) {

		System.out.println("I am in Mending controller's saveMendingPlanSearch method");
	
		try {
			
		System.out.println("pOId: " + pOId+"\n qCId: "+qCId);
		
		List<ModelMendingPlanCustom> modelMendingPlanList= daoMending.getMendingPlanBy(pOId, qCId);
		
		System.out.println("Size:"+modelMendingPlanList.size());
		
		
		return modelMendingPlanList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	@ResponseBody
	@RequestMapping(path="/mendingplancontroller/showmendingplandata",method = RequestMethod.GET)
	public List<ModelMendingPlanCustom> showMendingPlanData(Model model,
			@RequestParam("mendingPlanId") Long mendingPlanId
				
				) {

		System.out.println("I am in Mending controller's showMendingPlanData method");
	
		try {
			
		System.out.println("mendingPlanId: " + mendingPlanId);
		
		List<ModelMendingPlanCustom> modelMendingPlanList= daoMending.getMendingPlanById(mendingPlanId);
		
		System.out.println("Size:"+modelMendingPlanList.size());
		
		
		return modelMendingPlanList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	

}
