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
import com.biziitech.mlfm.custom.model.ModelQCCustom;
import com.biziitech.mlfm.custom.model.ModelUOMCustom;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoItemImp;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerTypeImp;
import com.biziitech.mlfm.daoimpl.DaoQCImp;
import com.biziitech.mlfm.daoimpl.DaoUOMImp;
import com.biziitech.mlfm.model.ModelItem;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelOrderOwnerType;
import com.biziitech.mlfm.model.ModelProduction;
import com.biziitech.mlfm.model.ModelQC;
import com.biziitech.mlfm.model.ModelQCPlan;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.repository.QCRepository;


@Controller
public class QCController {
	
	@Autowired
	private DaoQCImp daoQCImp;
	
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
	private QCRepository qCRepository;
	
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


private Long systemUserId;
	
	
	@RequestMapping(path="/qccontroller/init/{userId}")
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
		
		ModelQC modelQC = new ModelQC();
		model.addAttribute("modelQC", modelQC);
		
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
		
		return "QC";
		}
	
	@RequestMapping(path="/qccontroller/findowners")
	@ResponseBody
	public List<ModelOrderOwner> findOwners(@RequestParam("buyerType")Long typeId){
		
		System.out.println("" + typeId);
		
		return daoOrderOwnerImp.getOwnerByType(typeId);
	}
	
	@ResponseBody
	@RequestMapping(path="/qccontroller/saveqcentry", method = RequestMethod.POST)
	public ModelQC saveQCEntry(ModelQC modelQC, Model model,ModelQCPlan modelQCPlan,
			ModelUOM modelUOM,
			ModelProduction modelProduction,
			@RequestParam("bedNo") Double bedNo,
			@RequestParam("mendingRequiredQty") Double mendingRequiredQty,
			@RequestParam("qCDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date qCDate,
			@RequestParam("qCPlanId") Long qCPlanId,
			@RequestParam("uomId") Long uomId,
			@RequestParam("qCDoneQty") Double qCDoneQty,
			@RequestParam("activeStatus") int activeStatus,
			@RequestParam("qCRemarks") String qCRemarks
			
			
			) {
		System.out.println("IN QCController's save Method");
		System.out.println("bedNo: "+bedNo+"\n mendingRequiredQty: "+mendingRequiredQty+"\n qCDate"+qCDate+"\n qCPlanId"+qCPlanId+"\n uomId: "+uomId+"\n qCDoneQty"+qCDoneQty+"\n activeStatus"+activeStatus+"\n qCRemarks"+qCRemarks);
		
		try {
		
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		modelQC.setEntryTimeStamp(entryTime);
		modelQC.setqCDate(qCDate);
		
		Long logonUserId=systemUserId;
		 ModelUser modelUser1 = new ModelUser();
	        modelUser1.setUserId(logonUserId);
		
		
		modelQC.setEnteredBy(modelUser1);
//		modelProduction.setProductionId(qCProductionId);
//		modelQC.setModelProduction(modelProduction);
		modelUOM.setUomId(uomId);
		modelQC.setModelUOM(modelUOM);
		modelQC.setqCQty(qCDoneQty);
		modelQC.setqCRemarks(qCRemarks);
		modelQC.setActiveStatus(activeStatus);
		modelQCPlan.setqCPlanId(qCPlanId);
		modelQC.setModelQCPlan(modelQCPlan);
		this.daoQCImp.saveQC(modelQC);
		
		model.addAttribute("modelQC",modelQC);	
		return modelQC;
		
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}	
		
	}
	
	@ResponseBody
	@RequestMapping(path="/qccontroller/deleteqcentry", method = RequestMethod.POST)
	public List<ModelQCCustom> deleteQCEntry(ModelQC modelQC, Model model,ModelQCPlan modelQCPlan,
			ModelProduction modelProduction,
			@RequestParam("qCId") Long qCId
			
			
			) {
		System.out.println("IN QCController's Update Method");
		System.out.println("qCId: "+qCId);
		
		try {
		
		
		this.daoQCImp.deleteQC(qCId);
		
		
		return null;
		
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}	
		
	}
	
	
	
	@ResponseBody
    @RequestMapping(path="/qccontroller/qcupdate", method=RequestMethod.POST)
	    public List<ModelQCCustom> QCUpdate(Model model,ModelQC modelQC,
	    		
			@RequestParam("qCId") Long qCId,
	    	@RequestParam("qCQty") Double qCQty,
	    	@RequestParam("mendingRequiredQty") Double mendingRequiredQty,
	    	@RequestParam("bedNo") Double bedNo,
	    	@RequestParam("qCRemarks") String qCRemarks
	 ) {
		         
		try {
		
		    System.out.println("QC qCId : "+qCId);
		   
			Optional<ModelQC> modelQCOpt = qCRepository.findById(qCId);
			modelQC=modelQCOpt.get();
		
			modelQC.setqCQty(qCQty);
			modelQC.setBedNo(bedNo);
			modelQC.setMendingRequiredQty(mendingRequiredQty);
			//modelQC.setActive(active);
			modelQC.setqCRemarks(qCRemarks);
			
	
			modelQC.setActiveStatus(1);
	
			
			
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		modelQC.setUpdateTimeStamp(entryTime);
		
		
		 Long logonUserId=systemUserId;
		 ModelUser modelUser1 = new ModelUser();
	     modelUser1.setUserId(logonUserId);
		
		
		modelQC.setUpdatedBy(modelUser1);
		
		
		
		daoQCImp.saveQC(modelQC);
		List<ModelQCCustom> modelQCList =  daoQCImp.getQCDoneById(qCId);
		
		return modelQCList;
		
		
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}

			
	     }
	
	@ResponseBody
	@RequestMapping(path = "/qccontroller/getqcbyid", method = RequestMethod.GET)
	 public List<ModelQCCustom> getQCById(@RequestParam("id")Long id, Model model){
		 System.out.println("I am in qCQty Controller & id is " +id);
			List<ModelQCCustom> modelQCList =  daoQCImp.getQCById(id);
			model.addAttribute("modelQCList", modelQCList);	 
		 return modelQCList;		 
	 }

	
	/*
	@ResponseBody
	@RequestMapping(path = "/qCDoneQuantity/getqcdonebyId", method = RequestMethod.GET)
	 public List<ModelQCCustom> getQCDoneById(Model model,
			 @RequestParam("id")Long id,
			 @RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
			 @RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate){
		
		
		 System.out.println("I am in qCDoneQty Controller & id is " +id);
			List<ModelQCCustom> modelQCList =  daoQCImp.getQCDoneById(id);
			model.addAttribute("modelQCList", modelQCList);	 
		 return modelQCList;		 
	 }
	
*/	
	
	
	
	
/*
 * 
 * search pending Order 
 * */	
	
	@ResponseBody
	@RequestMapping(path="/qccontroller/getpendingordersearch",method = RequestMethod.GET)
	public List<ModelQCCustom> getPendingQCSearch(Model model,@RequestParam("buyerId")Long buyerId,
				@RequestParam("inquiryId")Long inquiryId , 				 
				@RequestParam("qCById")Long qCById,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,
				@RequestParam("itemId")Long itemId
				) {

		System.out.println("I am in controller's pendingOrderSearch method");
	
		try {
			
		System.out.println("InquiryId :" + inquiryId + "\n BuyerId: "+ buyerId +"\n qCById: " + qCById + "\n startDate " + startDate + "\n endDate " + endDate+ "\n itemId: "+itemId);
		List<ModelQCCustom> modelqCCustomList = new ArrayList<ModelQCCustom>();	
		modelqCCustomList=daoQCImp.getPendingQCOrderDetails(buyerId,inquiryId,qCById,startDate,endDate,itemId);
		System.out.println("Size:"+modelqCCustomList.size());
		
		
		return modelqCCustomList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	/*
	 * 
	 * search completed Order 
	 * */	
	
	@ResponseBody
	@RequestMapping(path="/qccontroller/getcompletedordersearch",method = RequestMethod.GET)
	public List<ModelQCCustom> getCompletedQCSearch(Model model,@RequestParam("buyerId")Long buyerId,
				@RequestParam("inquiryId")Long inquiryId , 				 
				@RequestParam("qCById")Long qCById,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,
				@RequestParam("itemId")Long itemId
				) {	
		System.out.println("I am in controller's completedOrderSearch method");
		try {
			
		System.out.println("InquiryId :" + inquiryId + "\n BuyerId: "+ buyerId +"\n qCById: " + qCById + "\n startDate " + startDate + "\n endDate " + endDate +"\n itemId: "+itemId);
		
		List<ModelQCCustom> modelqCCustomList = new ArrayList<ModelQCCustom>();		
		modelqCCustomList=daoQCImp.getCompletedQCOrderDetails(buyerId, inquiryId, qCById, startDate, endDate,itemId);
		System.out.println("Size:"+modelqCCustomList.size());
		 int id=modelqCCustomList.size();
			if(id==0) {
				System.out.println("test size");
	        	String msg="No Records are Found";
	        	 model.addAttribute("message",msg );
	        }
		return modelqCCustomList;
		}		
		catch(Exception e) {		
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
		
	}
	
	/*
	 * 
	 * search pending Work Order 
	 * */
	

	@ResponseBody
	@RequestMapping(path="/qccontroller/getpendingwosearch",method = RequestMethod.GET)
	public List<ModelQCCustom> getPendingWOSearch(Model model,@RequestParam("buyerId")Long buyerId,
				@RequestParam("inquiryId")Long inquiryId , 				 
				@RequestParam("qCById")Long qCById,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,
				@RequestParam("itemId")Long itemId
				) {
	
		System.out.println("I am in ajax Pending WOSearch");

		try {
			
		System.out.println("InquiryId :" + inquiryId + "\n BuyerId :"+ buyerId +"\n qCById: " + qCById + "\n startDate " + startDate + "\n endDate " + endDate+"\n itemId:"+itemId);

		List<ModelQCCustom> modelqCCustomList = new ArrayList<ModelQCCustom>();
		modelqCCustomList= daoQCImp.getPendingQCWODetails(buyerId,inquiryId,qCById,startDate,endDate,itemId);
		 int id=modelqCCustomList.size();
			if(id==0) {
				System.out.println("test size");
	        	String msg="No Records are Found";
	        	 model.addAttribute("message",msg );
	        }
		return modelqCCustomList;	
		}	
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}		
	}
	
	/*
	 * 
	 * search completed Work Order 
	 * */
	
	
	@ResponseBody
	@RequestMapping(path="/qccontroller/getcompletedwosearch",method = RequestMethod.GET)
	public List<ModelQCCustom> getCompletedWOSearch(Model model,@RequestParam("buyerId")Long buyerId,
				@RequestParam("inquiryId")Long inquiryId , 				 
				@RequestParam("qCById")Long qCById,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,
				@RequestParam("itemId")Long itemId
				) {	
		System.out.println("I am in ajax Completed WOSearch");
		try {			
		System.out.println("InquiryId :" + inquiryId + "\n BuyerId :"+ buyerId +"\n qCById: " + qCById + "\n startDate " + startDate + "\n endDate " + endDate+"\n itemId:"+itemId);
					List<ModelQCCustom> modelqCCustomList = new ArrayList<ModelQCCustom>();
					modelqCCustomList=daoQCImp.getCompletedQCWODetails(buyerId, inquiryId, qCById, startDate, endDate, itemId);		
					 int id=modelqCCustomList.size();
						if(id==0) {
							System.out.println("test size");
				        	String msg="No Records are Found";
				        	 model.addAttribute("message",msg );
				        }
					return modelqCCustomList;
		}
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	}
	

	

			

		

		
		
}
