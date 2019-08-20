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
import com.biziitech.mlfm.custom.model.ModelQCPlanCustom;
import com.biziitech.mlfm.custom.model.ModelUOMCustom;
import com.biziitech.mlfm.dao.DaoQCPlan;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerTypeImp;
import com.biziitech.mlfm.daoimpl.DaoUOMImp;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelOrderOwnerType;
import com.biziitech.mlfm.model.ModelPOMst;
import com.biziitech.mlfm.model.ModelQCPlan;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.repository.QCPlanRepository;

@Controller
public class QCPlanController {
	
	@Autowired
	private QCPlanRepository qCPlanRepository;
	
	@Autowired
	private DaoQCPlan daoQCPlan;
	
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
	
	@RequestMapping(path="/qcplancontroller/init/{userId}")
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
		
		return "QC_Plan";
	
	}
	
	@RequestMapping(path="/qcplancontroller/findowners")
	@ResponseBody
	public List<ModelOrderOwner> findOwners(@RequestParam("buyerType")Long typeId){
		
		System.out.println("" + typeId);
		
		return daoOrderOwnerImp.getOwnerByType(typeId);
	}
	
	
	
	
	@ResponseBody
	@RequestMapping(path="/qcplancontroller/saveqcplan", method = RequestMethod.POST)
	public List<ModelQCPlanCustom> saveQCPlan(ModelQCPlan modelQCPlan, Model model, ModelPOMst modelPOMst,ModelUOM modelUOM,
			@RequestParam("qCPlanPOId") Long qCPlanPOId,
			@RequestParam("qCPlanDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date qCPlanDate,
			@RequestParam("qCPlanQty") Double qCPlanQty,
			@RequestParam("uomId") Long uomId,
			@RequestParam("saveQCPlanActive") int saveQCPlanActive,
			@RequestParam("qCPlanRemarks") String qCPlanRemarks
			
			
			) {
		
		
		
		System.out.println("IN QCPlan Controller's saveQCPlan Method");
		
		try {
		
		System.out.println("qCPlanPOId :" + qCPlanPOId + "\n qCPlanDate: "+ qCPlanDate +"\n qCPlanQty: " + qCPlanQty +"\n uomId: "+uomId+ "\n saveQCPlanActive " + saveQCPlanActive + "\n qCPlanRemarks " + qCPlanRemarks);
		
		
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		
		modelQCPlan.setqCPlanDate(qCPlanDate);
		//modelQCPlan.setEnteredBy(1);
		modelPOMst.setpOMstId(qCPlanPOId);
		modelQCPlan.setModelPOMst(modelPOMst);
		modelQCPlan.setqCPlanQty(qCPlanQty);
		modelUOM.setUomId(uomId);
		modelQCPlan.setModelUOM(modelUOM);
		modelQCPlan.setqCPlanRemarks(qCPlanRemarks);
		
		
		Long logonUserId=systemUserId;
		ModelUser modelUser1 = new ModelUser();
		modelUser1.setUserId(logonUserId);
		
		modelQCPlan.setEnteredBy(modelUser1);
		modelQCPlan.setEntryTimeStamp(entryTime);
		
		
		if(saveQCPlanActive==1) 
		{
			modelQCPlan.setActiveStatus(1);
			modelQCPlan.setsActive("Active");
			modelQCPlan.setActive(true);
			
		}else {
			
			modelQCPlan.setActiveStatus(0);
			modelQCPlan.setsActive("InActive");
			modelQCPlan.setActive(false);
		}
		
		this.daoQCPlan.saveQCPlan(modelQCPlan);
		
		System.out.println("QCPlan Id : " + modelQCPlan.getqCPlanId());
		
		
		List<ModelQCPlanCustom> modelQCPlanList = daoQCPlan.getQCPlanByPOId(qCPlanPOId);
		System.out.println("Size : " + modelQCPlanList.size());

		return modelQCPlanList;
		
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}	
		
	}
	
	
	
	@ResponseBody
	@RequestMapping(path="/qcplancontroller/deleteqcplan", method = RequestMethod.POST)
	public List<ModelQCPlanCustom> deleteQCPlan(ModelQCPlan modelQCPlan, Model model,
			@RequestParam("qCPlanId") Long qCPlanId,
			@RequestParam("pOId") Long pOId
			
			
			
			) {
		
		
		
		System.out.println("IN QCPlan Controller's deleteQCPlan Method");
		
		try {
		
		System.out.println("qCPlanId :" + qCPlanId + "\n pOId: " +pOId);
		
		
		
		
		
		this.daoQCPlan.deleteQCPlan(qCPlanId);;
		
		System.out.println("QCPlan Id : " + modelQCPlan.getqCPlanId());
		
		
		List<ModelQCPlanCustom> modelQCPlanList = daoQCPlan.getQCPlanByPOId(pOId);
		System.out.println("Size : " + modelQCPlanList.size());

		return modelQCPlanList;
		
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}	
		
	}
	
	
	
	
	@ResponseBody
    @RequestMapping(path="/qcplancontroller/qcplanupdate", method=RequestMethod.POST)
	    public List<ModelQCPlanCustom> QCPlanUpdate(Model model,ModelQCPlan modelQCPlan,ModelUOM modelUOM,
	    		
			@RequestParam("qCPlanId") Long qCPlanId,
			@RequestParam("pOId") Long pOId,
			@RequestParam("uomId") Long uomId,
	    	@RequestParam("qCPlanQTY") Double qCPlanQTY,
	    	@RequestParam("qCPlanRemarks") String qCPlanRemarks,
	    	@RequestParam("qCPlanDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date qCPlanDate,
	    	@RequestParam("activeStatus") int activeStatus
	 ) {
		         
		
		try { 
		    System.out.println("QCPlanId: "+qCPlanId+"\n uomId: "+uomId+"\n qCPlanDate: "+qCPlanDate+"\n QCPlanQTY: "+qCPlanQTY+"\n QCPlanRemarks: "+qCPlanRemarks+"\n activeStatus: "+activeStatus);
			System.out.println("pOId: "+pOId);
			
			
			Optional<ModelQCPlan> modelQCPlanOpt = qCPlanRepository.findQCPlanById(qCPlanId);
			
			
		    //System.out.println("QCPlanId:"+QCPlanQTY);
			//Optional<ModelQCPlan> modelQCPlanOpt = qCPlanRepository.findById(QCPlanId);
			
			 
			modelQCPlan=modelQCPlanOpt.get();
			
			modelQCPlan.setqCPlanQty(qCPlanQTY);
			modelQCPlan.setActiveStatus(activeStatus);
			modelQCPlan.setqCPlanRemarks(qCPlanRemarks);
			modelQCPlan.setqCPlanDate(qCPlanDate);
			modelUOM.setUomId(uomId);
			modelQCPlan.setModelUOM(modelUOM);
			
			
			
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp updatedTime = new java.sql.Timestamp(date.getTime());
		modelQCPlan.setUpdateTimeStamp(updatedTime);
		
		Long logonUserId=systemUserId;
		ModelUser modelUser1 = new ModelUser();
		modelUser1.setUserId(logonUserId);
		
		modelQCPlan.setUpdatedBy(modelUser1);
		
		
		
		this.daoQCPlan.saveQCPlan(modelQCPlan);	
		
		List<ModelQCPlanCustom> modelQCPlanList= daoQCPlan.getQCPlanByPOId(pOId);
		
			return modelQCPlanList;
			
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}	
			
	              }
	
	
	
	
	@ResponseBody
	@RequestMapping(path="/qcplancontroller/pendingqcplanposearch",method = RequestMethod.GET)
	public List<ModelQCPlanCustom> pendingQCPlanPOSearch(Model model,
				@RequestParam("pOId")Long pOId,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate
				) {

		System.out.println("I am in QC-Plan controller's Pending QCplanSearch method");
	
		try {
			
		System.out.println("POId :" + pOId + "\n buyerId: "+buyerId+"\n startDate " + startDate + "\n endDate " + endDate);
		List<ModelQCPlanCustom> modelQCPlanCustomList = new ArrayList<ModelQCPlanCustom>();	
		modelQCPlanCustomList=daoQCPlan.getPendingQCPlanPODetails(pOId,buyerId,startDate,endDate);
		System.out.println("Size:"+modelQCPlanCustomList.size());
		
		
		return modelQCPlanCustomList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	
	
	@ResponseBody
	@RequestMapping(path="/qcplancontroller/completedqcplanposearch",method = RequestMethod.GET)
	public List<ModelQCPlanCustom> completedQCPlanPOSearch(Model model,
				@RequestParam("pOId")Long pOId,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate
				) {

		System.out.println("I am in QC-Plan controller's Completed QCplanSearch method");
	
		try {
			
		System.out.println("POId :" + pOId + "\n startDate " + startDate + "\n endDate " + endDate);
		List<ModelQCPlanCustom> modelQCPlanCustomList = new ArrayList<ModelQCPlanCustom>();	
		modelQCPlanCustomList=daoQCPlan.getCompletedQCPlanPODetails(pOId,buyerId,startDate,endDate);
		System.out.println("Size:"+modelQCPlanCustomList.size());
		
		
		return modelQCPlanCustomList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	@ResponseBody
	@RequestMapping(path="/qcplancontroller/qcplansearch",method = RequestMethod.GET)
	public List<ModelQCPlanCustom> qCPlanSearch(Model model,@RequestParam("id")Long id
				
				) {

		System.out.println("I am in QC-Plan controller's qCPlanSearch method");
	
		try {
			
		System.out.println("POId :" + id);
		List<ModelQCPlanCustom> modelQCPlanCustomList = new ArrayList<ModelQCPlanCustom>();	
		modelQCPlanCustomList=daoQCPlan.getQCPlanByPOId(id);
		System.out.println("Size:"+modelQCPlanCustomList.size());
		
		
		return modelQCPlanCustomList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	@ResponseBody
	@RequestMapping(path="/qcplancontroller/saveqcplansearch",method = RequestMethod.GET)
	public List<ModelQCPlanCustom> saveqCPlanSearch(Model model,@RequestParam("id")Long id
				
				) {

		System.out.println("I am in QC-Plan controller's saveQCPlanSearch method");
	
		try {
			
		System.out.println("QCPlanId :" + id);
		
		List<ModelQCPlanCustom> modelQCPlanList= daoQCPlan.getQCPlanByQCPlanId(id);
		
		System.out.println("Size:"+modelQCPlanList.size());
		
		
		return modelQCPlanList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	

}
