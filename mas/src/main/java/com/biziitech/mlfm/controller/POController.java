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
import com.biziitech.mlfm.custom.model.ModelInquiryList;
import com.biziitech.mlfm.custom.model.ModelPIMstCustom;
import com.biziitech.mlfm.custom.model.ModelPOCustom;
import com.biziitech.mlfm.custom.model.ModelProductionCustom;
import com.biziitech.mlfm.custom.model.ModelWOChdListCustom;
import com.biziitech.mlfm.dao.DaoDesignConsumItem;
import com.biziitech.mlfm.dao.DaoItem;
import com.biziitech.mlfm.dao.DaoPO;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerTypeImp;
import com.biziitech.mlfm.model.ModelDesign;
import com.biziitech.mlfm.model.ModelDesignConsumItem;
import com.biziitech.mlfm.model.ModelItem;
import com.biziitech.mlfm.model.ModelMachineShift;
import com.biziitech.mlfm.model.ModelMachineType;
import com.biziitech.mlfm.model.ModelOrderItemQty;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelOrderOwnerType;
import com.biziitech.mlfm.model.ModelPO;
import com.biziitech.mlfm.model.ModelPOChd;
import com.biziitech.mlfm.model.ModelPOMst;
import com.biziitech.mlfm.model.ModelProduction;
import com.biziitech.mlfm.model.ModelProductionPlanChd;
import com.biziitech.mlfm.model.ModelProductionPlanMst;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.repository.PORepository;
import com.ibm.icu.text.SimpleDateFormat;

@Controller
public class POController {
	
	@Autowired
	private DaoOrderOwnerImp daoOrderOwnerImp;
	
	@Autowired
	private DaoOrderOwnerTypeImp daoOrderOwnerTypeImp;
	
	@Autowired
	private DaoItem daoItem;
	
	@Autowired
	private DaoPO daoPO;
	
	@Autowired
	private PORepository pORepository;
	
	@Autowired
	private DaoDesignConsumItem daoDesignConsumItem;
	
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


    private Long systemUserId;
	
	SimpleDateFormat sd= new SimpleDateFormat("dd/mm/yyyy");
	
	// for new po
	@RequestMapping(path ="/pocontroller/init/{userId}")
	private String init(@PathVariable Long userId,Model model) {
		
		try {
			
			
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
			
			
			
			
			
		ModelPOMst modelPOMst=new ModelPOMst();
		model.addAttribute("pOMst",modelPOMst);
		
		List<ModelOrderOwnerType> ownerTypeList= daoOrderOwnerTypeImp.getTypeName();			
        model.addAttribute("ownerTypeList",ownerTypeList);
        
        List<ModelItem> modelItemList= daoItem.getItemListActive();
		model.addAttribute("modelItemList", modelItemList);
		return "po";
		
		}
		
		catch(Exception e) {
			
			  System.out.println(e.getMessage());
              return null;
		}
	}
	
	
	
	@RequestMapping(path ="/pocontroller/initold")
	private String initold(Model model) {
		
		try {
		ModelPOMst modelPOMst=new ModelPOMst();
		model.addAttribute("pOMst",modelPOMst);
		
		List<ModelOrderOwnerType> ownerTypeList= daoOrderOwnerTypeImp.getTypeName();			
        model.addAttribute("ownerTypeList",ownerTypeList);
        
        List<ModelItem> modelItemList= daoItem.getItemListActive();
		model.addAttribute("modelItemList", modelItemList);
		return "production_order";
		
		}
		
		catch(Exception e) {
			
			  System.out.println(e.getMessage());
              return null;
		}
	}
	
	
	// cak 190214

	@RequestMapping(path="/pocontroller/findowners")
	@ResponseBody
	public List<ModelOrderOwner> findOwners(@RequestParam("buyerType")Long typeId){
		
		System.out.println("" + typeId);
		
		return daoOrderOwnerImp.getOwnerByType(typeId);
	}
	
	@ResponseBody
	@RequestMapping(path="/pocontroller/getsearchdatapopending")
	private List<ModelPOCustom> getSearchDataPOPending(
			@RequestParam("orderOwnerTypeId")Long orderOwnerTypeId,
			@RequestParam("orderOwnerId")Long orderOwnerId,
			@RequestParam("wOMstId")Long wOMstId,
			@RequestParam("itemId")Long itemId,
			@RequestParam("designId")Long designId,
			//@RequestParam("userDesignNo")String userDesignNo,
			@RequestParam("planStartDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date planStartDate,
			@RequestParam("planEndDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date planEndDate){
		
		System.out.println("orderOwnerTypeId " + orderOwnerTypeId);
		System.out.println("orderOwnerId " + orderOwnerId);
		System.out.println("wOMstId " + wOMstId);
		System.out.println("itemId " + itemId);
		System.out.println("designId " + designId);
		//System.out.println("userDesignNo " + userDesignNo);
		System.out.println("planStartDate " + planStartDate);
		System.out.println("planEndDate " + planEndDate);
		
		
		List<ModelPOCustom> pONotDoneList= new ArrayList<ModelPOCustom>();
		//,itemId,designId
		pONotDoneList= daoPO.getProductionPlanDataPONotDone(orderOwnerTypeId, orderOwnerId, wOMstId,itemId,designId,planStartDate, planEndDate);
		//pONotDoneList= daoPO.getProductionPlanDataPONotDone(orderOwnerTypeId, orderOwnerId, wOMstId,itemId,designId,userDesignNo,planStartDate, planEndDate);
		System.out.println("size " + pONotDoneList.size());
		
		return pONotDoneList;
		
	}
	
	
	@ResponseBody
	@RequestMapping(path="/pocontroller/getsearchdatapodone")
	private List<ModelPOCustom> getSearchDataPODone(
			
			@RequestParam("orderOwnerTypeId")Long orderOwnerTypeId,
			@RequestParam("orderOwnerId")Long orderOwnerId,
			@RequestParam("wOMstId")Long wOMstId,
			@RequestParam("itemId")Long itemId,
			@RequestParam("designId")Long designId,
			//@RequestParam("userDesignNo")String userDesignNo,
			@RequestParam("pODoneFrom")@DateTimeFormat(pattern="yyyy-MM-dd")Date pODoneFrom,
			@RequestParam("pODoneTo")@DateTimeFormat(pattern="yyyy-MM-dd")Date pODoneTo) {
		
		List<ModelPOCustom> pODoneList= new ArrayList<ModelPOCustom>();
		
		pODoneList= daoPO.getProductionPlanDataPODone(orderOwnerTypeId, orderOwnerId, wOMstId,itemId,designId,pODoneFrom, pODoneTo);
		System.out.println("pODoneList size " + pODoneList.size());
		
		return pODoneList;
		
	}
	
	
	
	@RequestMapping(path="/pocontroller/savepomst",method=RequestMethod.POST)
	@ResponseBody
	private List<ModelPOCustom> savePOMst(ModelPOMst modelPOMst,ModelDesign modelDesign,
			//@RequestParam("pOMstDate")@DateTimeFormat(pattern="yyyy-MM-DD") Date pOMstDate,
			@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("pOMstDate") Date pOMstDate,
			@RequestParam("designId") Long designId,
			@RequestParam("userPONo") String userPONo,
			@RequestParam("remarks") String remarks,
			@RequestParam("active") int active) {
		
		      System.out.println(" po date " + pOMstDate);
		      System.out.println(" designId " + designId);
		      System.out.println(" userPONo " + userPONo);
		      System.out.println(" remarks " + remarks);
		      // System.out.println(" po date " + pOMstDate);
		      
		      java.util.Date date = new java.util.Date();
			  java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		      
		      modelDesign.setDesignId(designId);
			  modelPOMst.setModelDesign(modelDesign);
			  modelPOMst.setpOMstDate(pOMstDate);
			  modelPOMst.setUserPONo(userPONo);
			  modelPOMst.setRemarks(remarks);
			  modelPOMst.setActiveStatus(active);
			  
			  Long logonUserId=systemUserId;
			  ModelUser modelUser1 = new ModelUser();
		      modelUser1.setUserId(logonUserId);
		      
		      modelPOMst.setEnteredBy(modelUser1);
		      modelPOMst.setEntryTimeStamp(entryTime);
			  
			  daoPO.savePOMst(modelPOMst);
			  System.out.println(" after save modelPOMst : " + modelPOMst);
			  
			  
			  System.out.println(" controller : poMstId : " + modelPOMst.getpOMstId());
			  
			  List<ModelPOCustom> pOMstList= new ArrayList<ModelPOCustom>();
			  
			  
			  
			  pOMstList = daoPO.getPOMstData(modelPOMst.getpOMstId());
			  
			  System.out.println(" pOMstList : " + pOMstList);
		
		      
		      return pOMstList;
		
	          }
	
	
	
	@RequestMapping(path="/pocontroller/saveeditpomst",method=RequestMethod.POST)
	@ResponseBody
	private List<ModelPOCustom> saveEditPOMst(ModelPOMst modelPOMst,ModelDesign modelDesign,
			@RequestParam("pOMstId") Long pOMstId,
			@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("pOMstDate") Date pOMstDate,
			@RequestParam("userPONo") String userPONo,
			@RequestParam("remarks") String remarks,
			@RequestParam("active") int active) {
		
		      System.out.println(" po date " + pOMstDate);
		      System.out.println(" userPONo " + userPONo);
		      System.out.println(" remarks " + remarks);
		      System.out.println(" active " + active);
		     
		     
		      
		      java.util.Date date = new java.util.Date();
			  java.sql.Timestamp updateTime = new java.sql.Timestamp(date.getTime());
			  
			  
			  
			  Optional <ModelPOMst> POMstDataByOpt = daoPO.findPOMstById(pOMstId);
			  System.out.println("POMstDataByOpt (optional type Object) : "+POMstDataByOpt);
			  
			  modelPOMst=POMstDataByOpt.get();
			  
			  
			  modelPOMst.setpOMstDate(pOMstDate);
			  modelPOMst.setUserPONo(userPONo);
			  modelPOMst.setRemarks(remarks);
			  modelPOMst.setActiveStatus(active);
			  
			  Long logonUserId=systemUserId;
		      ModelUser modelUser1 = new ModelUser();
			  modelUser1.setUserId(logonUserId);
			  
			  modelPOMst.setUpdatedBy(modelUser1);
			  modelPOMst.setUpdateTimeStamp(updateTime);
			  
			  daoPO.savePOMst(modelPOMst);
			  
			  System.out.println("After Edit Save POMstData : "+modelPOMst);
		     
		      
              System.out.println(" controller : poMstId : " + modelPOMst.getpOMstId());
			  List<ModelPOCustom> pOMstList= new ArrayList<ModelPOCustom>();
			  pOMstList = daoPO.getPOMstData(modelPOMst.getpOMstId());
			  System.out.println(" pOMstList : " + pOMstList);
		
		      
		      return pOMstList;
		
	          }
	
	
	
	
	
	@ResponseBody
	@RequestMapping(path="/pocontroller/productionplanchddata")
	private List<ModelPOCustom> getProductionPlanChdData(
			@RequestParam("pPlanMstId") Long pPlanMstId
			) {
		
		
		System.out.println("pPlanMstId : " +pPlanMstId);
		
        List<ModelPOCustom> pPlanChdList= new ArrayList<ModelPOCustom>();
		
        pPlanChdList= daoPO.getProductionPlanChdData(pPlanMstId);
	    
	    System.out.println("productionPlanChdList : " + pPlanChdList);
		System.out.println("productionPlanChdList size : " + pPlanChdList.size());
		
		return pPlanChdList;
	
		
	//	return null;
		
		
	}
	
	
	@ResponseBody
	@RequestMapping(path="/pocontroller/getpomstdonedata")
	private List<ModelPOCustom> getPOMstDoneData(
			@RequestParam("pPChdId") Long pPChdId) {
		
		
		System.out.println("pPChdId : " +pPChdId);
		
        List<ModelPOCustom> pOMstDoneList= new ArrayList<ModelPOCustom>();
		
        pOMstDoneList= daoPO.getPOMstDoneData(pPChdId);
	    
	    System.out.println("pOMstDoneList : " + pOMstDoneList);
		System.out.println("pOMstDoneList size : " + pOMstDoneList.size());
		
		return pOMstDoneList;
	
		
	//	return null;
		
		
	}
	
	@ResponseBody
	@RequestMapping(path="/pocontroller/getpochddonedata")
	private List<ModelPOCustom> getPOChdDoneData(
			@RequestParam("pOMstId") Long pOMstId) {
		
		
		System.out.println("pOMstId : " +pOMstId);
		
        List<ModelPOCustom> pOChdDoneList= new ArrayList<ModelPOCustom>();
        pOChdDoneList= daoPO.getPOChdDoneData(pOMstId);
	    System.out.println("pOChdDoneList : " + pOChdDoneList);
		System.out.println("pOChdDoneList size : " + pOChdDoneList.size());
		
	//	return pOMstDoneList;
	
		
		return pOChdDoneList;
		
		
	}
	
	
	
	@ResponseBody
	@RequestMapping(path = "/pocontroller/savepochd", method = RequestMethod.POST) 
	 public  List<ModelPOCustom> savePOChd(Model model,ModelPOMst modelPOMst,ModelPOChd modelPOChd,
			 ModelProductionPlanMst modelProductionPlanMst,ModelProductionPlanChd modelProductionPlanChd,
			 ModelMachineShift modelMachineShift,
			 @RequestParam("pOMstId") Long pOMstId,
			 @RequestParam("productionPlanMstId") Long productionPlanMstId,
			 @RequestParam("productionPlanChdId") Long productionPlanChdId,
			 @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("pOChdDate") Date pOChdDate,
			 @RequestParam("pOQty") Double pOQty,
			 @RequestParam("machineShiftId") Long machineShiftId){
		
		System.out.println("pOMstId : "+ pOMstId);
		System.out.println("productionPlanMstId : "+ productionPlanMstId);
		System.out.println("productionPlanChdId : "+ productionPlanChdId);
		System.out.println("productionPlanMstId : "+ pOChdDate);
		System.out.println("productionPlanChdId : "+ pOQty);
		System.out.println("machineShiftId : "+ machineShiftId);
	
		try {
			
			  java.util.Date date = new java.util.Date();
			  java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
			
			
			modelPOMst.setpOMstId(pOMstId);
			modelProductionPlanMst.setProductionPlanMstId(productionPlanMstId);
			modelProductionPlanChd.setProductionPlanChdId(productionPlanChdId);
			
			modelPOChd.setModelPOMst(modelPOMst);
			modelPOChd.setModelProductionPlanChd(modelProductionPlanChd);
			
			modelMachineShift.setMachineShiftId(machineShiftId);
			modelPOChd.setModelMachineShift(modelMachineShift);
			
			modelPOChd.setpOChdDate(pOChdDate);
			
			modelPOChd.setpOQty(pOQty);
			
			modelPOChd.setActiveStatus(1);
			
			Long logonUserId=systemUserId;
			ModelUser modelUser1 = new ModelUser();
		    modelUser1.setUserId(logonUserId);
		    modelPOChd.setEnteredBy(modelUser1);
		      
		    modelPOChd.setEntryTimeStamp(entryTime);
			
		    daoPO.savePOChd(modelPOChd);
			System.out.println(" after save modelPOMst : " + modelPOChd);
			System.out.println(" controller : poChdId : " + modelPOChd.getpOChdId());
			
			List<ModelPOCustom> pOChdList= new ArrayList<ModelPOCustom>();
			pOChdList = daoPO.getPOChdData(modelPOChd.getpOChdId());
			
			System.out.println("pOChdList : " + pOChdList);
			System.out.println("pOChdList size : " + pOChdList.size());
		
		
		return pOChdList;
		
		}catch(Exception e) {
			
			e.printStackTrace();
		    System.out.println("Problem occurred when Production Plan Chd Save");
			return null;
		}
	}
	
	
	// for po previous
	@RequestMapping(path ="/ProductionOrderController/Init")
	private String Init(Model model) {
		
		try {
		     
			List<ModelOrderOwnerType> ownerTypeList= daoOrderOwnerTypeImp.getTypeName();			
	        model.addAttribute("ownerTypeList",ownerTypeList);
	        
	        List<ModelItem> modelItemList= daoItem.getItemListActive();
			model.addAttribute("modelItemList", modelItemList);
			
			return "production_order";
		    } //try
	
	catch(Exception e) {
		
		                System.out.println(e.getMessage());
		                 return null;
	       }  //catch
		
	}  //Init()
	
	
	
	/*
	   * order Type : Work Order(Drop down selected option ) Unchecked : PO Done 
	   * 
	   * creator : sas
	   * 
	   * */
		@ResponseBody
		@RequestMapping(path="/ProductionOrderController/getWorkOrderData")
		public List<ModelPOCustom> getWorkOrderDataUnchecked(Model model,
				@RequestParam("wOMstId") Long wOMstId,
				@RequestParam("orderOwnerTypeId") Long orderOwnerTypeId,
				 @RequestParam("ownerName") String ownerName,
				 @RequestParam("itemId") Long itemId,
				 @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("pPStratDate") Date pPStratDate,
				  @DateTimeFormat(pattern="yyyy-MM-dd") @RequestParam("pPEndDate") Date pPEndDate
				 ){
			try {  
				System.out.println(" Item Id : "+itemId);
				System.out.println(" wO Mst Id : "+wOMstId);
				System.out.println(" order Owner Type Id : "+orderOwnerTypeId);
				List<ModelPOCustom> wOList = new ArrayList<ModelPOCustom>();
				wOList = daoPO.getWorkOrderDataUnchecked(wOMstId,orderOwnerTypeId,ownerName,itemId,pPStratDate,pPEndDate);
				
				
				System.out.println(" WO List size : "+wOList.size());
				System.out.println(" WO List : "+wOList);

				return wOList;
			
			    }
				catch(Exception e) {
					
	                System.out.println(e.getMessage());
	                 return null;
       }  //catch
			
		}  // end getWorkOrderDataUnchecked
		
		
		
		
		/*
		   * order Type : Work Order(Drop down selected option ) Checked : PO Done 
		   * 
		   * creator : sas
		   * 
		   * */
			@ResponseBody
			@RequestMapping(path="/ProductionOrderController/getWorkOrderDataChecked")
			public List<ModelPOCustom> getWorkOrderDataChecked(Model model,
					 @RequestParam("ownerName") String ownerName,
					 @RequestParam("wOMstId") Long wOMstId,
						@RequestParam("orderOwnerTypeId") Long orderOwnerTypeId,
						@RequestParam("itemId") Long itemId,
					@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("pOStratDate") Date pOStratDate,
					  @DateTimeFormat(pattern="yyyy-MM-dd") @RequestParam("pOEndDate") Date pOEndDate
					 ){
				
				try {  
					System.out.println(" PO DONE ");
					
					System.out.println(" ownerName : "+ownerName);
					System.out.println(" wOMstId : "+wOMstId);
					System.out.println(" orderOwnerTypeId : "+orderOwnerTypeId);
					System.out.println(" itemId : "+itemId);
					System.out.println(" pOStratDate : "+pOStratDate);
					System.out.println(" pOEndDate : "+pOEndDate);
					
					
					List<ModelPOCustom> wOList = new ArrayList<ModelPOCustom>();
					wOList = daoPO.getWorkOrderDataChecked(wOMstId,orderOwnerTypeId,ownerName,itemId,pOStratDate,pOEndDate);
					
					System.out.println(" WO List size : "+wOList.size());
					System.out.println(" WO List : "+wOList);
					
					return wOList;
					
			    }
				catch(Exception e) {
					
	                System.out.println(e.getMessage());
	                 return null;
       }  //catch
			
		}  // end getWorkOrderDatachecked
			
			
		
		@RequestMapping(path="/ProductionOrderController/saveModalpO", method = RequestMethod.POST)
		@ResponseBody
			public List<ModelPO> saveModalpO(Model model,ModelPO modelPO,ModelProductionPlanChd modelProductionPlanChd,ModelMachineShift modelMachineShift,
					ModelMachineType modelMachineType,ModelDesign modelDesign, 
					@RequestParam("productionPlanChdId") Long productionPlanChdId,
					@RequestParam("machineShiftId") Long machineShiftId,
					@RequestParam("machineTypeId") Long machineTypeId,
					@RequestParam("designId") Long designId,
					@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("pODate") Date pODate,
			        @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("deliveryDate") Date deliveryDate,
			        @RequestParam("pOQty") Double pOQty,
					@RequestParam("nOOfBed") Integer nOOfBed,
					@RequestParam("patch") String patch,
					@RequestParam("dTM") String dTM,
					// @RequestParam("fabricTypeId") int fabricTypeId,
					@RequestParam("activeStatus") int activeStatus,
					@RequestParam("remarks") String remarks)
					
		{
			
			try {
			
			java.util.Date date = new java.util.Date();
			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
				
			modelProductionPlanChd.setProductionPlanChdId(productionPlanChdId);
			modelPO.setModelProductionPlanChd(modelProductionPlanChd);
			
			modelMachineShift.setMachineShiftId(machineShiftId);
			modelPO.setModelMachineShift(modelMachineShift);
			
			modelMachineType.setMachineTypeId(machineTypeId);
			modelPO.setModelMachineType(modelMachineType);
			
			modelDesign.setDesignId(designId);
			modelPO.setModelDesign(modelDesign);
			
			modelPO.setpODate(pODate);
			modelPO.setDeliveryDate(deliveryDate);
			modelPO.setpOQty(pOQty);
			modelPO.setnOOfBed(nOOfBed);
			modelPO.setPatch(patch);
			modelPO.setdTM(dTM);
			//modelPO.setFabricTypeId(fabricTypeId);
			modelPO.setRemarks(remarks);
			modelPO.setActiveStatus(activeStatus);
			
			modelPO.setEntryTimeStamp(entryTime);
			modelPO.setEnteredBy(1L);
			System.out.println("Before saved modelPO : " + modelPO);
			
			daoPO.savePO(modelPO);
			
			System.out.println("PO Id : " + modelPO.getpOId());
			
		//Optional<ModelPO> modelpOList= daoPO.getPOList(modelPO.getpOId());
			ModelPO modelpOList= daoPO.getPOList(modelPO.getpOId());
			//System.out.println("Find By ID modelpOList size : " + modelpOList.size());
			System.out.println("Find By ID modelpOList : " + modelpOList);
			
			System.out.println("Machine Type Name " + modelpOList.getModelMachineType().getMachineTypeName());
			//modelPO=modelpOList.get();
			
			//modelPO.setpODate(sd.parse(sd.format(modelPO.getpODate())));
			
			//System.out.println("po date " + modelPO.getpODate());
			
			//modelpOList = pORepository.findById(modelPO.getpOId());
			
			List<ModelPO> modelPOList= new ArrayList<ModelPO>();
			
			modelPOList.add(modelPO);
			return modelPOList;
			
			

		    }
			catch(Exception e) {
				
                System.out.println(e.getMessage());
                 return null;
   }  //catch
		
		}
		
		
		
		@RequestMapping(path="/productionordercontroller/savemodaleditpo", method = RequestMethod.POST)
		@ResponseBody
			//public List<ModelPO> saveModalEditpO(Model model,ModelPO modelPO,
					public String saveModalEditpO(Model model,ModelPO modelPO, 
					@RequestParam("pOId") Long pOId,
					@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("pODate") Date pODate,
			        @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("deliveryDate") Date deliveryDate,
			        @RequestParam("pOQty") Double pOQty,
					@RequestParam("noOfBed") Integer noOfBed,
					@RequestParam("patch") String patch,
					@RequestParam("dTM") String dTM,
					//@RequestParam("fabricTypeId") int fabricTypeId,
					@RequestParam("activeStatus") int activeStatus,
					@RequestParam("remarks") String remarks)
					
		{
			
			System.out.println("Po ID  : " + pOId);
			System.out.println("pODate : " + pODate);
			System.out.println("deliveryDate  : " + deliveryDate);
			System.out.println("noOfBed  : " + noOfBed);
			System.out.println("patch  : " + patch);
			System.out.println("dTM  : " + dTM);
			//System.out.println("fabricTypeId  : " + fabricTypeId);
			System.out.println("activeStatus  : " + activeStatus);
			System.out.println("remarks  : " + remarks);
			
			try {
				
				//ModelPO modelPOEdit= daoPO.getPOList(pOId);
			 //   System.out.println("Find By ID modelpO  For Edit  : " + modelPOEdit);
				
//				modelPOEdit.setpODate(pODate);
//				modelPOEdit.setDeliveryDate(deliveryDate);
//				modelPOEdit.setpOQty(pOQty);
//				modelPOEdit.setnOOfBed(noOfBed);
//				modelPOEdit.setdTM(dTM);
//				modelPOEdit.setActiveStatus(activeStatus);
//				modelPOEdit.setRemarks(remarks);
//				
//				daoPO.savePO(modelPOEdit);
//				
//				System.out.println("PO Id : " + modelPO.getpOId());
//				ModelPO POEdit= daoPO.getPOList(modelPO.getpOId());
//				List<ModelPO> modelPOEditList= new ArrayList<ModelPO>();
//				modelPOEditList.add(POEdit);
//				return modelPOEditList;
				
				

				
				
				//				Optional<ModelPO>  modelPOOpt = pORepository.findById(pOId);
//				System.out.println("Find By ID modelpO  For Edit  : " + modelPOOpt);
//				modelPO=modelPOOpt.get();
//				
//				modelPO.setpODate(pODate);
//				modelPO.setDeliveryDate(deliveryDate);
//				modelPO.setpOQty(pOQty);
//				modelPO.setnOOfBed(noOfBed);
//				modelPO.setdTM(dTM);
//				modelPO.setActiveStatus(activeStatus);
//				modelPO.setRemarks(remarks);
				
			//	List<ModelPO> modelPOEditList= new ArrayList<ModelPO>();
				
				//modelPOEditList.add(modelPO);
				
				
				
		//		return modelPOEditList;
				
				return "";
			
		    }
				catch(Exception e) {
					
	                System.out.println(e.getMessage());
	                 return null;
				} //catch
	   }  
		
		
		
		@ResponseBody
	    @RequestMapping(path="/productionordercontroller/getpodonelist", method=RequestMethod.GET)
		    public List<ModelPO> getPODoneList(Model model,@RequestParam("designId") Long designId
		    		) {
			
			try {
				
				System.out.println("Design ID  : " + designId);
				
				//Optional<ModelPO>  modelPOOpt = daoPO.getPOByDesignId(designId);
				ModelPO  modelPO = daoPO.getPOByDesignId(designId);
				System.out.println("Find By ID modelpO  For Edit  : " + modelPO);
				
	            List<ModelPO> modelPOList= new ArrayList<ModelPO>();
				
	            modelPOList.add(modelPO);
				
			return modelPOList;
			
		 }
		catch(Exception e) {
			
            System.out.println(e.getMessage());
             return null;
		} //catch
		}
		
		
		
		@ResponseBody
	    @RequestMapping(path="/productionordercontroller/getdesignconsumlist", method=RequestMethod.GET)
		    public List<ModelDesignConsumItem> getDesignConsumList(Model model,@RequestParam("designId") Long designId
		    		) {
			
			try {
				
				System.out.println("Design ID  : " + designId);
				
			    List<ModelDesignConsumItem> modelDesignConsumItemList= new ArrayList<ModelDesignConsumItem>();
	          
	            modelDesignConsumItemList=daoDesignConsumItem.getDesignConsumItemList(designId);
	            System.out.println("Design Consum item List  : " + modelDesignConsumItemList);
	            
	          
				
			return modelDesignConsumItemList;
			
		 }
		catch(Exception e) {
			
            System.out.println(e.getMessage());
             return null;
		} //catch
		}
		
		
		@RequestMapping(path="/ProductionOrderController/OwnerName")
		@ResponseBody
		public List<ModelOrderOwner> findOwner(@RequestParam("ownerType")Long typeId){
			return daoOrderOwnerImp.getOwnerByTypeInOrder(typeId);
		}
		
	/*	
		
		  @ResponseBody
		    @RequestMapping(value = "/ProductionOrderController/getPOData/edit", method=RequestMethod.GET)
		    public List<ModelPO> getPOData(ModelPO modelPO,@RequestParam("pOId") Long pOId) {
			   
	        	System.out.println("PO Id : " + pOId);
	        	
	        	Optional<ModelPO> POData=daoPO.getPOList(pOId);
	        	modelPO=POData.get();
	        	
	        	List<ModelPO> modelEditPOList= new ArrayList<ModelPO>();
	        	modelEditPOList.add(modelPO);
			   
		        return modelEditPOList;
			}
	*/
	

	
	
} //POController
