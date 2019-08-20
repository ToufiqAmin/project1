package com.biziitech.mlfm.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
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
import com.biziitech.mlfm.bg.model.ModelCurrency;
import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.custom.model.ModelInquiryList;
import com.biziitech.mlfm.custom.model.ModelMachineScheduleData;
import com.biziitech.mlfm.custom.model.ModelProductionCustom;
import com.biziitech.mlfm.dao.DaoDesign;
import com.biziitech.mlfm.dao.DaoItem;
import com.biziitech.mlfm.dao.DaoMachineShift;
import com.biziitech.mlfm.dao.DaoProductionPlan;
import com.biziitech.mlfm.dao.DaoProductionPlanChd;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoDesignImp;
import com.biziitech.mlfm.daoimpl.DaoItemImp;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.daoimpl.DaoMachineImp;
import com.biziitech.mlfm.daoimpl.DaoMachineShiftImp;
import com.biziitech.mlfm.daoimpl.DaoMachineTypeImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerTypeImp;
import com.biziitech.mlfm.model.ModelDesign;
import com.biziitech.mlfm.model.ModelItem;
import com.biziitech.mlfm.model.ModelMachine;
import com.biziitech.mlfm.model.ModelMachineShift;
import com.biziitech.mlfm.model.ModelMachineType;
import com.biziitech.mlfm.model.ModelOrderItemQty;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelOrderOwnerType;
import com.biziitech.mlfm.model.ModelProductionPlanChd;
import com.biziitech.mlfm.model.ModelProductionPlanMst;
import com.biziitech.mlfm.model.ModelUserCluster;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.model.ModelWOChd;
import com.biziitech.mlfm.model.ModelWOMst;
import com.biziitech.mlfm.repository.ProductionPlanChdRepository;
import com.biziitech.mlfm.repository.UserClusterRepository;
import com.ibm.icu.text.SimpleDateFormat;

@Controller
public class ProductionPlanController {
	
	@Autowired
	private DaoProductionPlan daoProductionPlan;
	
	@Autowired
	private DaoProductionPlanChd daoProductionPlanChd;
	
	@Autowired
	private DaoUserImp daoUserImp;
	
	@Autowired
	private DaoDesignImp daoDesignImp;
	
	@Autowired
	private DaoMachineTypeImp daoMachineTypeImp;
	
	@Autowired
	private DaoMachineImp daoMachineImp;
	
	@Autowired
	private DaoItem daoItem;
	
	@Autowired
	private DaoMachineShiftImp DaoMachineShift;
	
	@Autowired
	private DaoMachineShift daoMachineShift;
	
	@Autowired
	private DaoOrderOwnerImp daoOrderOwnerImp;
	
	@Autowired
	private UserClusterRepository userClusterRepository;
	
	@Autowired
	private DaoDesign daoDesign;
	
	@Autowired
	private DaoOrderOwnerTypeImp ownerType;
	
	@Autowired
	private DaoOrderOwnerImp orderOwner;
	
	@Autowired
	private ProductionPlanChdRepository productionPlanChdRepository;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


    private Long systemUserId;
	
	
	@RequestMapping(path="/production/plan/{userId}")
	public String init(@PathVariable Long userId,Model model) {
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
			
			
			
			
			
			
		
			ModelProductionPlanMst modelProductionPlanMst= new ModelProductionPlanMst();
			
		List<ModelUser> userList= daoUserImp.getAllUSerName();
		model.addAttribute("userList",userList);
		
	    List<ModelOrderOwnerType> ownerTypeList= ownerType.getTypeName();			
        model.addAttribute("ownerTypeList",ownerTypeList);
		
		List<ModelOrderOwner> ownerList= daoOrderOwnerImp.getAllOwner();
		model.addAttribute("ownerList",ownerList);
		
		List<ModelItem> modelItemList= daoItem.getItemListActive();
		model.addAttribute("modelItemList", modelItemList);
		
		List<ModelDesign> modelDesignList= daoDesign.getDesignListActive();
		model.addAttribute("modelDesignList", modelDesignList);
		
		List<ModelUserCluster> clusterList= userClusterRepository.findAll();						
		model.addAttribute("clusterList",clusterList);
		
	    model.addAttribute("productionPlanMst", modelProductionPlanMst);
		model.addAttribute("userList",daoProductionPlan.getUserList());
		
		//popup list
		List<ModelMachineType> machineTypeList= daoMachineTypeImp.getMachineName();
		//System.out.println("machine id "+machineTypeList);
		model.addAttribute("machineTypeList",machineTypeList);
		
		model.addAttribute("shiftList",daoMachineShift.getShiftList());
		
		
		model.addAttribute("machineShiftList",DaoMachineShift.getMachineShiftList());
		
		System.out.println("test");
		return "production_plan";
		}
		
		catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		
		
	
	}
	
	@RequestMapping(path="/productionplancontroller/productionplanmodalsave",method=RequestMethod.POST)
	@ResponseBody
	public List<ModelProductionCustom> productionPlanModalSave(Model model,ModelProductionPlanMst modelProductionPlanMst,
			   ModelOrderItemQty modelOrderItemQty,ModelWOChd modelWOChd,ModelUser modelUser,
			  @RequestParam("orderItemQtyId") Long orderItemQtyId,
	          @RequestParam("wOChdId") Long wOChdId,
	          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("planDate") Date planDate,
	          @RequestParam("userId") Long userId,
	          @RequestParam("refNo") String refNo,
	          @RequestParam("remarks") String remarks,
	          @RequestParam("activeStatus") int active) {
		
		System.out.println("Save Production Plan Mst ");
		
		System.out.println("orderItemQtyId :"+orderItemQtyId);
		System.out.println("wOChdId :"+wOChdId);
		System.out.println("planDate : "+planDate);
		System.out.println("userId :"+userId);
		System.out.println("remarks : "+remarks);
		System.out.println("activeStatus : "+active);
		
		
		
		java.util.Date date = new java.util.Date();
		    java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		    
		    modelOrderItemQty.setOrderItemQtyId(orderItemQtyId);
		    modelProductionPlanMst.setModelOrderItemQty(modelOrderItemQty);
		    
		    modelWOChd.setWoChdId(wOChdId);
		    modelProductionPlanMst.setModelWOChd(modelWOChd);
		    
		    modelUser.setUserId(userId);
		    modelProductionPlanMst.setModelUser(modelUser);
		    
		    modelProductionPlanMst.setPlanDate(planDate);
		    modelProductionPlanMst.setRefNo(refNo);
		    modelProductionPlanMst.setRemarks(remarks);
		    modelProductionPlanMst.setActiveStatus(active);
		    
		    modelProductionPlanMst.setEntryTimestamp(entryTime);
		    
		    Long logonUserId=systemUserId;
		    
		    ModelUser modelUser1 = new ModelUser();
	        modelUser1.setUserId(logonUserId);
	        
		    modelProductionPlanMst.setEnteredBy(modelUser1);
		    
		    daoProductionPlan.saveProductionPlan(modelProductionPlanMst);
		    
		    System.out.println(" controller : Model Production plan Mst  find by Id : " + modelProductionPlanMst.getProductionPlanMstId());
		    System.out.println(" controller : Model Production plan Mst  ActiveStatus : " + modelProductionPlanMst.getActiveStatus());
		    List<ModelProductionCustom> productionPlanMstList= new ArrayList<ModelProductionCustom>();
		    productionPlanMstList = daoProductionPlan.getProductionPlanMstById(modelProductionPlanMst.getProductionPlanMstId());
		    System.out.println(" controller : Model Production plan Mst  find by Id : " + productionPlanMstList);
		    
		    System.out.println(" controller : Model Production plan Mst size : " + productionPlanMstList.size());
		
		
		return productionPlanMstList;
		
		
		//return null;
	}
	
	
	@RequestMapping(path="/productionplancontroller/productionplanmodaleditsave",method=RequestMethod.POST)
	@ResponseBody
	public List<ModelProductionCustom> productionPlanModalEditSave(Model model,ModelProductionPlanMst modelProductionPlanMst,
			   ModelUser modelUser,
			  @RequestParam("productionPlanMstId") Long productionPlanMstId,
			  @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("planDate") Date planDate,
	          @RequestParam("userId") Long userId,
	          @RequestParam("refNo") String refNo,
	          @RequestParam("remarks") String remarks,
	          @RequestParam("activeStatus") int activeStatus) {
		
		System.out.println("Edit Production Plan Mst then Save ");
		
		System.out.println("productionPlanMstId : "+productionPlanMstId);
		System.out.println("planDate : "+planDate);
		System.out.println("userId :"+userId);
		System.out.println("remarks : "+remarks);
		System.out.println("activeStatus : "+activeStatus);
		

		java.util.Date date = new java.util.Date();
		    java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		
	//	List <ModelProductionPlanMst> modelProductionPlanMstList= new ArrayList<ModelProductionPlanMst>();
		
		
		//List<ModelProductionCustom> productionPlanMstListForEdit= new ArrayList<ModelProductionCustom>();
		//productionPlanMstListForEdit = daoProductionPlan.getProductionPlanMstById(modelProductionPlanMst.getProductionPlanMstId());
		//System.out.println(" controller : Model Production plan Mst  find by Id : " + productionPlanMstListForEdit);
		
		
		Optional <ModelProductionPlanMst>  ProductionPlanMstDataByOpt=daoProductionPlan.findProductionplanMstById(productionPlanMstId);
		System.out.println("ProductionPlanMstDataByOpt (optional type Object) : "+ProductionPlanMstDataByOpt);
		modelProductionPlanMst=ProductionPlanMstDataByOpt.get();
		
		modelProductionPlanMst.setPlanDate(planDate);
		
		modelUser.setUserId(userId);
		modelProductionPlanMst.setModelUser(modelUser);
		
		modelProductionPlanMst.setRefNo(refNo);
		
        modelProductionPlanMst.setRemarks(remarks);
		modelProductionPlanMst.setActiveStatus(activeStatus);
		
		Long logonUserId=systemUserId;
		 ModelUser modelUser1 = new ModelUser();
	        modelUser1.setUserId(logonUserId);
	        
	       
		
		modelProductionPlanMst.setUpdatedBy(modelUser1);
		modelProductionPlanMst.setUpdateTimestap(entryTime);
		
		
		daoProductionPlan.saveProductionPlan(modelProductionPlanMst);
		System.out.println("Production Plan Mst: "+modelProductionPlanMst);
		
		List<ModelProductionCustom> productionPlanMstList= new ArrayList<ModelProductionCustom>();
	    productionPlanMstList = daoProductionPlan.getProductionPlanMstById(modelProductionPlanMst.getProductionPlanMstId());
	    System.out.println(" controller : Model Production plan Mst  find by Id : " + productionPlanMstList);
		
		
		
		//modelProductionPlanMstList.add(modelProductionPlanMst);
		//System.out.println("modelProductionPlanMstList size " + modelProductionPlanMstList.size());
		return productionPlanMstList;
	}
	
	
	@RequestMapping(path="/productionplancontroller/getavailablemachine",method=RequestMethod.GET)
	@ResponseBody
	public List<ModelMachineScheduleData> getAvailableMachine(Model model,
			@RequestParam("machineTypeId") Long machineTypeId){
		 System.out.println("1. controller : method name : getAvailableMachine :  machineTypeId :" + machineTypeId);
		 
		 
		 List<ModelMachineScheduleData> availableMachineList= new ArrayList<ModelMachineScheduleData>();
		 availableMachineList=daoProductionPlan.getAvailableMachine(machineTypeId);
		 System.out.println("2. controller : method name : getAvailableMachine : availableMachineList :" + availableMachineList);
		
		return availableMachineList;
	}
	
	
	
	@RequestMapping(path="/productionplancontroller/getproductionplanchddatebymstid",method=RequestMethod.GET)
	@ResponseBody
	public List<ModelProductionPlanChd> getProductionPlanChdDateByMstId(Model model,
			ModelProductionPlanMst modelProductionPlanMst,
			ModelProductionPlanChd modelProductionPlanChd,
			@RequestParam("productionPlanMstId") Long productionPlanMstId){
		
//		 System.out.println("1. controller method name getProductionPlanChdDateByMstId \n :productionPlanMstId: " + productionPlanMstId);
//		 List<ModelProductionCustom> productionPlanChdList =  daoProductionPlan.getProductionPlanChdDateByMstId(productionPlanMstId);
//		 System.out.println("2. controller method name getProductionPlanChdDateByMstId \n :ProductionPlanChdList: "+productionPlanChdList) ;
//		 System.out.println("3. controller method name getProductionPlanChdDateByMstId \n :ProductionPlanChdList size: " + productionPlanChdList.size());
		 
		
		List<ModelProductionPlanChd> modelProductionPlanChdList =  daoProductionPlanChd.findProductionPlanChdListByMstId(productionPlanMstId);
		
		Date date =modelProductionPlanChdList.get(0).getProductionPlanDate();
		System.out.println("First Date : " +date);
		Date date1 =modelProductionPlanChdList.get(modelProductionPlanChdList.size()-1).getProductionPlanDate();
		System.out.println("Last Date : " + date1);
		
		
//		for(int i = 0; i < modelProductionPlanChdList.size(); i++) {
//	            
//             Date date =modelProductionPlanChdList.get(i).getProductionPlanDate();
//             System.out.println(date);
//		 } 
		 
		 return null;
	}
	
	
	@RequestMapping(path="/productionplancontroller/changescheduledateproductionplanchdbymstid",method=RequestMethod.GET)
	@ResponseBody
	public List<ModelProductionCustom> ChangeScheduleDateProductionPlanChdByMstId(Model model,
			ModelProductionPlanMst modelProductionPlanMst,
			@RequestParam("productionPlanMstId") Long productionPlanMstId,
			ModelProductionPlanChd modelProductionPlanChd,
			@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("shiftScheduleDate") Date shiftScheduleDate){
		
		 System.out.println("1. productionPlanMstId: " + productionPlanMstId);
		 System.out.println("2. shiftScheduleDate: " + shiftScheduleDate);
		 
		
		 List<ModelProductionPlanChd> modelProductionPlanChdList =  daoProductionPlanChd.findProductionPlanChdListByMstId(productionPlanMstId);
		 
		 for(int i = 0; i < modelProductionPlanChdList.size(); i++) {
	            
	             Date date =modelProductionPlanChdList.get(i).getProductionPlanDate();
	             System.out.println(date);
	             System.out.println("shiftScheduleDate  : "+shiftScheduleDate);
	            	
	            	modelProductionPlanChdList.get(i).setProductionPlanDate(shiftScheduleDate);
	            	modelProductionPlanChd=modelProductionPlanChdList.get(i);
	            	daoProductionPlanChd.saveProductionPlanChd(modelProductionPlanChd);
	          
	                shiftScheduleDate=date;
	                System.out.println("dasd : "+shiftScheduleDate);
	        }
		 
		 
// List<ModelProductionPlanChd> modelProductionPlanChdList1 =  daoProductionPlanChd.findProductionPlanChdListByMstId(productionPlanMstId);
//		 for(int i = 0; i < modelProductionPlanChdList1.size(); i++) {
//	           Date date =modelProductionPlanChdList1.get(i).getProductionPlanDate();
//	             System.out.println("Change Schedule Date : "+date);
//	        }
		 
		 
		 
		 
		 List<ModelProductionCustom> productionPlanChdList= new ArrayList<ModelProductionCustom>();
		 productionPlanChdList = daoProductionPlan.getProductionPlanChdByMstId(productionPlanMstId);
		 System.out.println(" controller : Model Production plan Chd  find by productionPlanMstId : " + productionPlanChdList);
		 System.out.println(" controller : Model Production plan Chd  list size : " + productionPlanChdList.size());
		
		return productionPlanChdList;
	}
	
	
	@RequestMapping(path="/productionplancontroller/getproductionplanmstplanned",method=RequestMethod.GET)
	@ResponseBody
	public List<ModelProductionCustom> getProductionPlanMstPlanned(Model model,ModelProductionPlanMst modelProductionPlanMst,
			@RequestParam("productionPlanMstId") Long productionPlanMstId){
		
		
		 System.out.println(" controller :productionPlanMstId: " + productionPlanMstId);
		 
		 List<ModelProductionCustom> productionPlanMstList= new ArrayList<ModelProductionCustom>();
		 productionPlanMstList = daoProductionPlan.getProductionPlanMstById(modelProductionPlanMst.getProductionPlanMstId());
		 System.out.println(" controller : Model Production plan Mst  find by Id : " + productionPlanMstList);
		 System.out.println(" controller : Model Production plan Mst  list size : " + productionPlanMstList.size());
		
		return productionPlanMstList;
	}
	
	
	@RequestMapping(path="/productionplancontroller/getProductionPlanMstInUsed",method=RequestMethod.GET)
	@ResponseBody
	public List<ModelProductionCustom> getProductionPlanMstInUsed(Model model,ModelProductionPlanMst modelProductionPlanMst,
			@RequestParam("wOMstId") Long wOMstId){
		
		
		 System.out.println(" controller :wOMstId: " + wOMstId);
		 
		 List<ModelProductionCustom> productionPlanMstList= new ArrayList<ModelProductionCustom>();
		 productionPlanMstList = daoProductionPlan.getInUsedProductionPlanMst(wOMstId);
//		 System.out.println(" controller : Model Production plan Mst  find by Id : " + productionPlanMstList);
//		 System.out.println(" controller : Model Production plan Mst  list size : " + productionPlanMstList.size());
		 
		 
		 
		
		return productionPlanMstList;
	}
	
	@RequestMapping(path="/productionplancontroller/getproductionplanchdplanned",method=RequestMethod.GET)
	@ResponseBody
	public List<ModelProductionCustom> getProductionPlanChdPlanned(Model model,ModelProductionPlanMst modelProductionPlanMst,
			@RequestParam("productionPlanMstId") Long productionPlanMstId){
		
		System.out.println(" controller :productionPlanMstIdt : " + productionPlanMstId);
		
		
		List<ModelProductionCustom> productionPlanChdList= new ArrayList<ModelProductionCustom>();
		 productionPlanChdList = daoProductionPlan.getProductionPlanChdByMstId(productionPlanMstId);
		 System.out.println(" controller : Model Production plan Chd  find by productionPlanMstId : " + productionPlanChdList);
		 System.out.println(" controller : Model Production plan Chd  list size : " + productionPlanChdList.size());
		
		return productionPlanChdList;
		
	}
	
	
	
	
	
	 		
	@RequestMapping(path="/production/plan/save",method=RequestMethod.POST)
	public String productionPlanSave(Model model,ModelProductionPlanMst modelProductionPlanMst) {
		System.out.println("Save Production Plan Mst ");
		
		try {
		if(modelProductionPlanMst.getProductionPlanMstId()==null )
		{
			
			
		    java.util.Date date = new java.util.Date();
		    java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		    
		    //productionPlan.setPlanDate(new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(productionPlan.getPlanDate())));		    
		    
		    System.out.println("plan Date : "+modelProductionPlanMst.getPlanDate());
		    modelProductionPlanMst.setEntryTimestamp(entryTime);
		    
		    Long logonUserId=systemUserId;
		    ModelUser modelUser = new ModelUser();
	        modelUser.setUserId(logonUserId);
		    
		    modelProductionPlanMst.setEnteredBy(modelUser);
		    
		    
		    daoProductionPlan.saveProductionPlan(modelProductionPlanMst);
		    
		}
		else
		{
			System.out.println("NUll");
			daoProductionPlan.saveProductionPlan(modelProductionPlanMst);
		}
	
	
		//return "redirect:/production/plan";
		model.addAttribute("productionPlan", modelProductionPlanMst);
		model.addAttribute("userList",daoProductionPlan.getUserList());
		
		//popup list
		List<ModelMachineType> machineTypeList= daoMachineTypeImp.getMachineName();
		model.addAttribute("machineTypeList",machineTypeList);
		
		System.out.println("tst");
		//return "production_plan";
		return "redirect:/pPlanCtrl/getProductionPlanMst/"+modelProductionPlanMst.getProductionPlanMstId();
		}
		
		catch(Exception e) {
			e.getMessage();
			return "production_plan";
		}
	
	}
	
	@RequestMapping(path="/pPlanCtrl/getProductionPlanMst/{id}", method=RequestMethod.GET)
	  public String getProductionPlanMst(Model model,@PathVariable("id") Long id, ModelProductionPlanMst modelProductionPlanMst) {
		try {
			
			model.addAttribute("productionPlanMst", modelProductionPlanMst);
//			List<ModelUser> userList= daoUserImp.getAllUSerName();
//			model.addAttribute("userList",userList);
//			System.out.println("tst1");
//			
//		    List<ModelOrderOwner> ownerList= daoOrderOwnerImp.getAllOwnerName();
//			model.addAttribute("ownerList",ownerList);
//			System.out.println("tst3");
//			
//			List<ModelItem> modelItemList= daoItem.getItemListActive();
//			model.addAttribute("modelItemList", modelItemList);
//			System.out.println("tst4");
//			
//			List<ModelDesign> modelDesignList= daoDesign.getDesignListActive();
//			model.addAttribute("modelDesignList", modelDesignList);
//			System.out.println("tst5");
			
			List<ModelUser> userList= daoUserImp.getAllUSerName();
			model.addAttribute("userList",userList);
			
		    List<ModelOrderOwnerType> ownerTypeList= ownerType.getTypeName();			
	        model.addAttribute("ownerTypeList",ownerTypeList);
			
			List<ModelOrderOwner> ownerList= daoOrderOwnerImp.getAllOwner();
			model.addAttribute("ownerList",ownerList);
			
			List<ModelItem> modelItemList= daoItem.getItemListActive();
			model.addAttribute("modelItemList", modelItemList);
			
			List<ModelDesign> modelDesignList= daoDesign.getDesignListActive();
			model.addAttribute("modelDesignList", modelDesignList);
			
//			List<ModelUserCluster> clusterList= userClusterRepository.findAll();						
//			model.addAttribute("clusterList",clusterList);
			System.out.println("tst6");
			
		   
			
			//popup list
			List<ModelMachineType> machineTypeList= daoMachineTypeImp.getMachineName();
			//System.out.println("machine id "+machineTypeList);
			model.addAttribute("machineTypeList",machineTypeList);
			System.out.println("tst7");
			model.addAttribute("shiftList",daoMachineShift.getShiftList());
			System.out.println("tst8");
		try {
			
			
			
			System.out.println("Production Plan Mst Id :" +id);
			
			List <ModelProductionPlanMst> modelProductionPlanMstList= new ArrayList<ModelProductionPlanMst>();
			Optional <ModelProductionPlanMst>  ProductionPlanMstDataByOpt=daoProductionPlan.findProductionplanMstById(id);
			System.out.println("ProductionPlanMstDataByOpt (optional type Object) : "+ProductionPlanMstDataByOpt);
			modelProductionPlanMst=ProductionPlanMstDataByOpt.get();
			
			System.out.println("Production Plan Mst Date : "+modelProductionPlanMst.getPlanDate());
			System.out.println("Production Plan Mst Active Status : "+modelProductionPlanMst.getActiveStatus());
			
			//Date parsedDate = new SimpleDateFormat("dd.MM.yyyy").parse(modelProductionPlanMst.getPlanDate());
			
			modelProductionPlanMstList.add(modelProductionPlanMst);
			
			
			System.out.println("modelProductionPlanMstList size " + modelProductionPlanMstList.size());
			
			System.out.println("Production Plan Mst: "+modelProductionPlanMstList);
			
			
			model.addAttribute("modelProductionPlanMstList",modelProductionPlanMstList);
			
		}
		catch(NoSuchElementException e){
			System.out.println("Data not found for Id : " + modelProductionPlanMst.getProductionPlanMstId());
			
		}
		
	}
	catch (Exception e) {
		 e.printStackTrace();
			 //System.out.println("Error at URL : //work_order//from" );
		 return "production_plan"; 
	}
		
		finally {
			System.out.println("final");
			return "production_plan";
			
			}
		//	return "production_plan";
	}
	

  /*
   * order Type : inquiry(Drop down selected option ) and checked : Not Planned
   * 
   * creator : sas
   * 
   * */
	@ResponseBody
	@RequestMapping(path="/production/plan/search/ajax")
	public List<ModelInquiryList> getOrderDataAjax(Model model,ModelProductionPlanMst modelProductionPlanMst,
			 // @RequestParam("orderTypeId") int orderTypeId,
			  @RequestParam("initialBuyerId") String initialBuyerId,
	          @RequestParam("ultimateBuyerName") String ultimateBuyerName,
	          @RequestParam("inqueryId") Long inqueryId,
	          //@RequestParam("planId") Long planId,
	          //@RequestParam("designBy") Long designBy,
	         // @RequestParam("designId") Long designId,
	         //@RequestParam("userId") Long userId,
	         // @RequestParam("userClusterId") Long userClusterId,
	          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("inqStartDate") Date inqStartDate,
	          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("inqEndDate") Date inqEndDate
	          //@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("designStartDate") Date designStartDate,
	          //@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("designEndDate") Date designEndDate,
              //@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("planStartDate") Date planStartDate,
	          //@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("planEndDate") Date planEndDate
	          ){
			     
		System.out.println("\n Select Inquiry and checked Not Planned \n");
		
		System.out.println("Ajax data sent " + "\n initial buyer :" + initialBuyerId+ "\n ultimate buyer name :" + ultimateBuyerName+  "\n Inquiry Id :" + inqueryId+  "\n Design By :" + "\n Inquiry Strat Date :" + inqStartDate );
		
		//List<ModelInquiryList> modelOrderList = daoProductionPlan.getOrderList(orderTypeId, initialBuyerId,ultimateBuyerName,inqueryId,planId,designBy,designId,userId,userClusterId,inqStartDate,inqEndDate,designStartDate,designEndDate,planStartDate,planStartDate);
		List<ModelInquiryList> modelOrderList = daoProductionPlan.getOrderList(initialBuyerId,ultimateBuyerName,inqueryId,inqStartDate,inqEndDate);
		//List<ModelInquiryList> modelOrderList = daoProductionPlan.getOrderList(orderTypeId, initialBuyerId,ultimateBuyerName,inqStartDate,inqEndDate);
		System.out.println("Order List : "+ modelOrderList.size());
		System.out.println(" Order List : "+ modelOrderList.toString());
	  	
				return modelOrderList;
	  			
	  }
	
	
	/*
	   * order Type : inquiry(Drop down selected option ) and Unchecked : Not Planned
	   * 
	   * creator : sas
	   * 
	   * */
	@ResponseBody
	@RequestMapping(path="/pPlanCtrl/getOrderDataPlanned")
	public List<ModelInquiryList> getOrderDataPlanned(Model model,ModelProductionPlanMst modelProductionPlanMst,
			 // @RequestParam("orderTypeId") int orderTypeId,
			  @RequestParam("initialBuyerId") String initialBuyerId,
	          @RequestParam("ultimateBuyerName") String ultimateBuyerName,
	          @RequestParam("inqueryId") Long inqueryId,
	         // @RequestParam("planId") Long planId,
	         // @RequestParam("designBy") Long designBy,
	        //  @RequestParam("designId") Long designId,
	        //  @RequestParam("userId") Long userId,
	         // @RequestParam("userClusterId") Long userClusterId,
	          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("inqStartDate") Date inqStartDate,
	          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("inqEndDate") Date inqEndDate
	        //  @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("designStartDate") Date designStartDate,
	        //  @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("designEndDate") Date designEndDate,
            //  @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("planStartDate") Date planStartDate,
	        //  @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("planEndDate") Date planEndDate
	          ){
			     
		System.out.println("\n Select Inquiry and Unchecked Not Planned \n");
		
		System.out.println("Ajax data sent : " + "\n initial buyer :" + initialBuyerId+ "\n ultimate buyer name :" + ultimateBuyerName+  "\n Inquiry Id :" + inqueryId+  "\n Inquiry Strat Date :" + inqStartDate);
		
		//List<ModelInquiryList> modelOrderList = daoProductionPlan.getOrderList(orderTypeId, initialBuyerId,ultimateBuyerName,inqueryId,planId,designBy,designId,userId,userClusterId,inqStartDate,inqEndDate,designStartDate,designEndDate,planStartDate,planStartDate);
		List<ModelInquiryList> modelOrderList = daoProductionPlan.getOrderListPlanned(initialBuyerId,ultimateBuyerName,inqueryId,inqStartDate,inqEndDate);
		//List<ModelInquiryList> modelOrderList = daoProductionPlan.getOrderList(orderTypeId, initialBuyerId,ultimateBuyerName,inqStartDate,inqEndDate);
		System.out.println("Order List : "+ modelOrderList.size());
		System.out.println(" Order List : "+ modelOrderList.toString());
	  	
				return modelOrderList;
	  			
	  }
	
	
	/*
	   * order Type : Work Order and Unchecked : Plan Done
	   * 
	   * creator : sas
	   * 
	   * */
	
	
	@ResponseBody
	@RequestMapping(path="/productionplancontroller/getwodatanotplaned")
	public List<ModelInquiryList> getWODataNotPlaned(Model model,
			  ModelProductionPlanMst modelProductionPlanMst,
			  @RequestParam("orderOwnerTypeId") Long orderOwnerTypeId,
			  @RequestParam("initialBuyerId") Long initialBuyerId,
	          @RequestParam("ultimateBuyerId") Long ultimateBuyerId,
	          @RequestParam("wOMstId") Long wOMstId,
	          @RequestParam("itemId") Long itemId,
	          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("wOStartDate") Date wOStartDate,
	          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("wOEndDate") Date wOEndDate){
			     
		System.out.println("Ajax request received");
		
		System.out.println("\n This Search based on Work Order and checked Not Planned \n");
		
		System.out.println("Ajax data sent " +
				"\n order Owner Type Id :" + orderOwnerTypeId+
		          "\n initial buyer :" + initialBuyerId+ 
				"\n ultimateBuyerId :" + ultimateBuyerId+  
				"\n wOMstId :" + wOMstId+ 
				"\n itemId :" + itemId+ 
				"\n wOStartDate :" + wOStartDate+ 
				"\n wOEndDate :" + wOEndDate);
		
		
		List<ModelInquiryList> modelWOList = daoProductionPlan.getWOList(orderOwnerTypeId,initialBuyerId,ultimateBuyerId,wOMstId,itemId,wOStartDate,wOEndDate);
		
		System.out.println("Order List : "+ modelWOList.size());
		
		System.out.println(" Order List : "+ modelWOList.toString());
	  	
	  	return modelWOList;
					
	  }
	
	
	
	
	
	/*
	   * order Type : Work Order and checked : Plan Done
	   * 
	   * creator : sas
	   * 
	   * */
	@ResponseBody
	@RequestMapping(path="/productionplancontroller/getwodataplanned")
	public List<ModelInquiryList> getWODataPlanned(Model model,
			  ModelProductionPlanMst modelProductionPlanMst,
			  
			  @RequestParam("orderOwnerTypeId") Long orderOwnerTypeId,
			  @RequestParam("initialBuyerId") Long initialBuyerId,
	          @RequestParam("ultimateBuyerId") Long ultimateBuyerId,
	          @RequestParam("wOMstId") Long wOMstId,
	          @RequestParam("itemId") Long itemId,
	          @RequestParam("userId") Long userId,
	          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("planStartDate") Date planStartDate,
	          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("planEndDate") Date planEndDate){
			     
		System.out.println("\n Select Work Order and checked plan done \n");
		
		System.out.println("Ajax data sent " +
				"\n order Owner Type Id :" + orderOwnerTypeId+
		                   "\n initial buyer :" + initialBuyerId+
		                   "\n ultimate buyer name :" + ultimateBuyerId+
		                   "\n Inquiry Id :" + wOMstId+
		                   "\n User :" + userId+
		                   "\n Plan Strat Date :" + planStartDate+
		                   "\n plan End Date :" + planEndDate);
		
		List<ModelInquiryList> modelWOList = daoProductionPlan.getWOListPlanned(orderOwnerTypeId,initialBuyerId,ultimateBuyerId,wOMstId,itemId,userId,planStartDate,planEndDate);
		
		System.out.println("Order List : "+ modelWOList.size());
		
		System.out.println(" Order List : "+ modelWOList.toString());
	  	
		return modelWOList;
	  			
	  }
	
	
	
	@ResponseBody
	@RequestMapping(path="/productionplancontroller/getmachinesheduledataavailable") 
	  public List<ModelMachineScheduleData> getMachineSheduleDataAvailable(Model model,ModelProductionPlanMst modelProductionPlan,
			  @RequestParam("machineTypeId") Long machineTypeId,
			  @RequestParam("machineId") Long machineId,
			  @RequestParam("machineShiftId") Long machineShiftId,
			  @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("startDate") Date startDate,
			  @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("endDate") Date endDate) {
		
		
		System.out.println("Ajax request received");
	
		System.out.println("Ajax data sent "+
		" machineTypeId: " + machineTypeId +
		" machineId: " + machineId + 
		" machineShiftId: " + machineShiftId +
		" startDate: " + startDate +
		" endDate: " + endDate);
		
		List<ModelMachineScheduleData> machineScheduleList=
		    	daoProductionPlan.getMachineSheduleList(machineTypeId, machineId, machineShiftId,startDate,endDate);
		
		System.out.println("No. of records of Machine Schedule: " + machineScheduleList.size());
		
	    return machineScheduleList;
	}
	
	
	@ResponseBody
	@RequestMapping(path="/productionplancontroller/getMachineSheduleNotAvailable") 
	  public List<ModelMachineScheduleData> getMachineSheduleNotAvailable(Model model,ModelProductionPlanMst modelProductionPlan,
			  @RequestParam("machineTypeId") Long machineTypeId,
			  @RequestParam("machineId") Long machineId,
			  @RequestParam("machineShiftId") Long machineShiftId,
			  @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("startDate") Date startDate,
			  @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("endDate") Date endDate) {
		
		
		System.out.println("Ajax request received");
	
		System.out.println("Ajax data sent "+" machineTypeId: " + machineTypeId + " machineId: " + machineId +  " machineShiftId: " + machineShiftId +
				 " startDate: " + startDate + " endDate: " + endDate);
		
		List<ModelMachineScheduleData> machineScheduleList=
		    	daoProductionPlan.getMachineSheduleListUnchecked(machineTypeId, machineId, machineShiftId,startDate,endDate);
		
		System.out.println("No. of records of Machine Schedule: " + machineScheduleList.size());
		
	    return machineScheduleList;
	}
	
	
/*	
	@ResponseBody
	@RequestMapping(path="/pPlanCtrl/getProductionPlanChdPopulate") 
	  public List<ModelMachineScheduleData> getProductionPlanChdPopulate(Model model,ModelProductionPlanMst modelProductionPlanMst) {
		     List<ModelMachineScheduleData> productionPlanChdList=daoProductionPlan.getProductionPlanChdList();
		
		     System.out.println("No. of records of Production Plan Chd List: " + productionPlanChdList.size());
		
		return productionPlanChdList;
	}
*/	
	
	
	
	
	// this method pick machine schedule list
	@RequestMapping(path="/productionPlan/machine/schedule/search") 
	  public String getMachineSheduleData(Model model,ModelProductionPlanMst modelProductionPlan,
			  @RequestParam("machineTypeId") Long machineTypeId,
			  @RequestParam("machineId") Long machineId,
			  @RequestParam("machineShiftId") Long machineShiftId,
			  @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("startDate") Date startDate,
			  @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("endDate") Date endDate) {
		
		
		
		// production plan other list
		List<ModelUser> userList= daoUserImp.getAllUSerName();
		model.addAttribute("userList",userList);
		
		List<ModelDesign> designList= daoDesignImp.getDesignList();
		model.addAttribute("designList",designList);
	
	
		model.addAttribute("productionPlan", modelProductionPlan);
		model.addAttribute("userList",daoProductionPlan.getUserList());
		
		//popup list
		List<ModelMachineType> machineTypeList= daoMachineTypeImp.getMachineName();
		model.addAttribute("machineTypeList",machineTypeList);
		//popup other list handle ajax  
		
		
		List<ModelMachineScheduleData> machineScheduleList=
		    	daoProductionPlan.getMachineSheduleList(machineTypeId,machineId, machineShiftId,startDate,endDate);
		
		System.out.println("schedule data size :" + machineScheduleList.size());
		model.addAttribute("machineScheduleList",machineScheduleList);
		
	    return"production_plan";
	}
	
	
	@ResponseBody
	@RequestMapping(path = "/productionplancontroller/productionplanchdmodaleditdata", method = RequestMethod.GET) 
	 public  List<ModelProductionCustom> productionPlanChdModalEditData(Model model,ModelProductionPlanChd modelProductionPlanChd,
			 @RequestParam("productionPlanChdId") Long productionPlanChdId){
		
		System.out.println("productionPlanChdId : "+ productionPlanChdId);
		
		List<ModelProductionCustom> productionPlanChdList= new ArrayList<ModelProductionCustom>();
		productionPlanChdList = daoProductionPlan.getProductionPlanChdByChdId(productionPlanChdId);
		System.out.println(" controller : Model Production plan Chd  find by productionPlanChdId : " + productionPlanChdList);
		System.out.println(" controller : Model Production plan Chd  list size : " + productionPlanChdList.size());
		
	
	
	return productionPlanChdList;
	}
	
	@ResponseBody
	@RequestMapping(path = "/productionplancontroller/editproductionplanchd", method = RequestMethod.POST) 
	 public  List<ModelProductionCustom> editProductionPlanChd(Model model,ModelProductionPlanChd modelProductionPlanChd,
			 ModelProductionPlanMst modelProductionPlanMst,
			 ModelMachineShift modelMachineShift,
			 @RequestParam("productionPlanChdId") Long productionPlanChdId,
			 @RequestParam("productionPlanMstId") Long productionPlanMstId,
			 //@RequestParam("machineShiftId") Long machineShiftId,
			 @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("productionPlanDate") Date productionPlanDate,
			 @RequestParam("productionPlanQty") Double productionPlanQty,
			 @RequestParam("remarks") String remarks,
			 @RequestParam("activeStatus") int activeStatus
			 )
	         {
		System.out.println("productionPlanMstId : "+ productionPlanMstId);
		System.out.println("productionPlanChdId : "+ productionPlanChdId);
		System.out.println("productionPlanDate : "+ productionPlanDate);
		//System.out.println("machineShiftId : "+ machineShiftId);
		System.out.println("productionPlanQty : "+ productionPlanQty);
		System.out.println("remarks : "+ remarks);
		System.out.println("activeStatus : "+ activeStatus);
		
		try {
			
			
//			List<ModelProductionCustom> productionPlanChdList= new ArrayList<ModelProductionCustom>();
//			productionPlanChdList=daoProductionPlan.getProductionPlanChdByChdId(productionPlanChdId);
//			System.out.println("ProductionPlanChdData  : " +productionPlanChdList);
			
			java.util.Date date = new java.util.Date();
			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
			
			Optional <ModelProductionPlanChd> ProductionPlanMstDataByOpt=productionPlanChdRepository.findById(productionPlanChdId);
			System.out.println("ProductionPlanMstData By Opt : " +ProductionPlanMstDataByOpt);
			modelProductionPlanChd=ProductionPlanMstDataByOpt.get();
			

			
			//modelMachineShift.setMachineShiftId(machineShiftId);
			//modelProductionPlanChd.setModelMachineShift(modelMachineShift);
			
			//modelProductionPlanMst.setProductionPlanMstId(productionPlanMstId);
			//modelProductionPlanChd.setModelProductionPlanMst(modelProductionPlanMst);
			
			modelProductionPlanChd.setProductionPlanDate(productionPlanDate);
            modelProductionPlanChd.setProductionPlanQty(productionPlanQty);
			modelProductionPlanChd.setRemarks(remarks);
			modelProductionPlanChd.setActiveStatus(activeStatus);
			
			Long logonUserId=systemUserId;
			 ModelUser modelUser1 = new ModelUser();
		        modelUser1.setUserId(logonUserId);
			
			
            modelProductionPlanChd.setUpdatedBy(modelUser1);
            modelProductionPlanChd.setUpdateTimestap(entryTime);
			
			
			daoProductionPlanChd.saveProductionPlanChd(modelProductionPlanChd);
			
			
			
			/*
			List<ModelProductionCustom> productionPlanChdList= new ArrayList<ModelProductionCustom>();
			productionPlanChdList = daoProductionPlan.getProductionPlanChdByChdId(modelProductionPlanChd.getProductionPlanChdId());
			System.out.println(" controller : Model Production plan Chd  find by productionPlanChdId : " + productionPlanChdList);
			System.out.println(" controller : Model Production plan Chd  list size : " + productionPlanChdList.size());
			
		
		
		return productionPlanChdList;
			*/
			
			
			List<ModelProductionCustom> productionPlanChdList= new ArrayList<ModelProductionCustom>();
			 productionPlanChdList = daoProductionPlan.getProductionPlanChdByMstId(productionPlanMstId);
			 System.out.println(" controller : Model Production plan Chd  find by productionPlanMstId : " + productionPlanChdList);
			 System.out.println(" controller : Model Production plan Chd  list size : " + productionPlanChdList.size());
			
			return productionPlanChdList;
			
       }catch(Exception e) {
			
			e.printStackTrace();
		    System.out.println("Problem occurred when Production Plan Chd Edit");
			return null;
		}
		
		
		
	
	         }
	
	
	@ResponseBody
	@RequestMapping(path = "/productionplancontroller/saveproductionplanchd", method = RequestMethod.POST) 
	 public  List<ModelProductionCustom> saveProductionPlanChd(Model model,ModelProductionPlanChd modelProductionPlanChd,
			 ModelProductionPlanMst modelProductionPlanMst,
			 ModelMachineShift modelMachineShift,
			 @RequestParam("productionPlanMstId") Long productionPlanMstId,
		     @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("productionPlanDate") Date productionPlanDate,
			 @RequestParam("machineShiftId") Long machineShiftId,
			 @RequestParam("productionPlanQty") Double productionPlanQty,
			 @RequestParam("remarks") String remarks)
	         {
		
		System.out.println("productionPlanMstId : "+ productionPlanMstId);
		System.out.println("productionPlanDate : "+ productionPlanDate);
		System.out.println("machineShiftId : "+ machineShiftId);
		System.out.println("productionPlanQty : "+ productionPlanQty);
		System.out.println("remarks : "+ remarks);
		
		try {
		
			java.util.Date date = new java.util.Date();
			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
			
			modelProductionPlanMst.setProductionPlanMstId(productionPlanMstId);
			modelProductionPlanChd.setModelProductionPlanMst(modelProductionPlanMst);
			
			modelMachineShift.setMachineShiftId(machineShiftId);
			modelProductionPlanChd.setModelMachineShift(modelMachineShift);
			
			System.out.println("production Plan Date : "+ productionPlanDate);
			modelProductionPlanChd.setProductionPlanDate(productionPlanDate);
			
			modelProductionPlanChd.setProductionPlanQty(productionPlanQty);
			
			modelProductionPlanChd.setRemarks(remarks);
			
			modelProductionPlanChd.setEntryTimestamp(entryTime);
			
			Long logonUserId=systemUserId;
			 ModelUser modelUser1 = new ModelUser();
		        modelUser1.setUserId(logonUserId);
			
			modelProductionPlanChd.setEnteredBy(modelUser1);
			
			modelProductionPlanChd.setActiveStatus(1);
			
			daoProductionPlanChd.saveProductionPlanChd(modelProductionPlanChd);
			
			System.out.println(" controller : Model Production plan Chd  find by productionPlanMstId : " + modelProductionPlanChd.getProductionPlanChdId());
			
			
			List<ModelProductionCustom> productionPlanChdList= new ArrayList<ModelProductionCustom>();
			productionPlanChdList = daoProductionPlan.getProductionPlanChdByChdId(modelProductionPlanChd.getProductionPlanChdId());
			System.out.println(" controller : Model Production plan Chd  find by productionPlanChdId : " + productionPlanChdList);
			System.out.println(" controller : Model Production plan Chd  list size : " + productionPlanChdList.size());
			
		
		
		return productionPlanChdList;
		
		}catch(Exception e) {
			
			e.printStackTrace();
		    System.out.println("Problem occurred when Production Plan Chd Save");
			return null;
		}
	         }
	
	
	
	
	
	
	@ResponseBody
	@RequestMapping(path = "/productionplancontroller/shiftscheduleproductionplanchd", method = RequestMethod.POST) 
	 public  List<ModelProductionCustom> shiftScheduleProductionPlanChd(Model model,ModelProductionPlanChd modelProductionPlanChd,
			 ModelMachineShift modelMachineShift,
			 @RequestParam("productionPlanChdId") Long productionPlanChdId,
			 @RequestParam("machineShiftId") Long machineShiftId,
			 @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("productionPlanDate") Date productionPlanDate)
	         {
		
		System.out.println("productionPlanChdId : "+ productionPlanChdId);
		System.out.println("productionPlanDate : "+ productionPlanDate);
		System.out.println("machineShiftId : "+ machineShiftId);
		
		
		try {
			
			java.util.Date date = new java.util.Date();
			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
			
			Optional <ModelProductionPlanChd> ProductionPlanMstDataByOpt=productionPlanChdRepository.findById(productionPlanChdId);
			System.out.println("ProductionPlanMstData By Opt : " +ProductionPlanMstDataByOpt);
			modelProductionPlanChd=ProductionPlanMstDataByOpt.get();
			

			
			modelMachineShift.setMachineShiftId(machineShiftId);
			modelProductionPlanChd.setModelMachineShift(modelMachineShift);
			
			modelProductionPlanChd.setProductionPlanDate(productionPlanDate);
			
			Long logonUserId=systemUserId;
			 ModelUser modelUser1 = new ModelUser();
		        modelUser1.setUserId(logonUserId);
			
            modelProductionPlanChd.setUpdatedBy(modelUser1);
            modelProductionPlanChd.setUpdateTimestap(entryTime);
			
			
			daoProductionPlanChd.saveProductionPlanChd(modelProductionPlanChd);
			
//		     List<ModelProductionCustom> productionPlanChdList= new ArrayList<ModelProductionCustom>();
//			 productionPlanChdList = daoProductionPlan.getProductionPlanChdByMstId(productionPlanMstId);
//			 System.out.println(" controller : Model Production plan Chd  find by productionPlanMstId : " + productionPlanChdList);
//			 System.out.println(" controller : Model Production plan Chd  list size : " + productionPlanChdList.size());
			
			System.out.println("After Change Shift Schedule potion : productionPlanChdId : "+ productionPlanChdId);
			
			List<ModelProductionCustom> productionPlanChdList= new ArrayList<ModelProductionCustom>();
			productionPlanChdList = daoProductionPlan.getProductionPlanChdByChdId(productionPlanChdId);
			System.out.println(" controller : Model Production plan Chd  find by productionPlanChdId : " + productionPlanChdList);
			System.out.println(" controller : Model Production plan Chd  list size : " + productionPlanChdList.size());
			
		
		
		return productionPlanChdList;
			
       }catch(Exception e) {
			
			e.printStackTrace();
		    System.out.println("Problem occurred when Production Plan Chd Shift Schedule");
			return null;
		}
		
		
		
	
 }
	
	
	
	
	
	
	
	
	
	
	
	/*
	@RequestMapping(path = "/pPlanCtrl/saveProductionPlanChd", method = RequestMethod.POST) 
	 public  String saveProductionPlanChd(Model model,ModelProductionPlanChd modelProductionPlanChd,
			 ModelProductionPlanMst modelProductionPlanMst,
			 ModelMachineShift modelMachineShift,
			 @RequestParam("productionPlanMstId") Long productionPlanMstId,
			 //@RequestParam("productionPlanChdId") Long productionPlanChdId,
			 //@RequestParam("productionPlanDate") Date productionPlanDate,
			 @RequestParam("machineShiftId") Long machineShiftId,
			 @RequestParam("productionPlanQty") Double productionPlanQty,
			 @RequestParam("remarks") String remarks)
	         {
		model.addAttribute("productionPlanMst", modelProductionPlanMst);
		
	
		try {
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		
		
		
		System.out.println("production Plan Mst Id : "+ productionPlanMstId);
		modelProductionPlanMst.setProductionPlanMstId(productionPlanMstId);
		System.out.println("model Production Plan Mst : "+ modelProductionPlanMst);
		modelProductionPlanChd.setModelProductionPlanMst(modelProductionPlanMst);
		
		System.out.println("machine Shift Id : "+ machineShiftId);
		modelMachineShift.setMachineShiftId(machineShiftId);
		
		System.out.println("Model Machine Shift : "+ modelMachineShift);
		modelProductionPlanChd.setModelMachineShift(modelMachineShift);
		
		//System.out.println("production Plan Date : "+ productionPlanDate);
		//modelProductionPlanChd.setProductionPlanDate(productionPlanDate);
		
		System.out.println("production Plan Qty : "+ productionPlanQty);
		modelProductionPlanChd.setProductionPlanQty(productionPlanQty);
		
		System.out.println("remarks : "+ remarks);
		modelProductionPlanChd.setRemarks(remarks);
		
		System.out.println("Entry Time stamp : "+ entryTime);
		modelProductionPlanChd.setEntryTimestamp(entryTime);
		System.out.println("Entered by 1");
		modelProductionPlanChd.setEnteredBy(1L);
		System.out.println("ActiveStatus : 1 "+ machineShiftId);
		modelProductionPlanChd.setActiveStatus(1);
		
		daoProductionPlanChd.saveProductionPlanChd(modelProductionPlanChd);
		
		

		
		return "redirect:/production/plan";
		
		}catch(Exception e) {
			e.printStackTrace();
		System.out.println("Problem occurred when Production Plan Chd Save");
			return "production_plan";
		}
		
			 }
	
	*/
	
	
	@ResponseBody
    @RequestMapping(path="/productionPlan/machine/type")
	public List<ModelMachine> findMachineNames(@RequestParam("machine")Long id){
   
        return daoMachineImp.getMachineByType(id);
	} 
	
	@RequestMapping(path="/productionPlan/machine/shift/type")
	@ResponseBody
	public List<ModelMachineShift> findMachineShiftName(@RequestParam("machineId")Long id){
    	System.out.println(id);
		return DaoMachineShift.getMachineShiftByName(id);
	}
	
	
	
	@RequestMapping(path="/pPlanCtrl/owner")
	@ResponseBody
	public List<ModelOrderOwner> findOwner(@RequestParam("ownerType")Long typeId){
		return orderOwner.getOwnerByTypeInOrder(typeId);
	}
	
	/*
	@RequestMapping(path="/production/plan/search_list")
	public String productionPlanWithSearch(Model model,ModelProductionPlan modelProductionPlan) {
		
		List<ModelUser> userList= user.getAllUSerName();
		model.addAttribute("userList",userList);
		
		List<ModelDesign> designList= design.getDesignList();
		model.addAttribute("designList",designList);
	
	
		model.addAttribute("productionPlan", modelProductionPlan);
		model.addAttribute("userList",daoProductionPlan.getUserList());
		
		model.addAttribute("searchList",daoProductionPlan.getOrderList());
		
		return"production_plan";
	}
	
	*/
	
	
//	@RequestMapping(path="/production/plan/search")
//  public String getAllOwner(Model model,@RequestParam("initial_Buyer") String initial_Buyer,@RequestParam("ultimate_Buyer") String ultimate_Buyer,@RequestParam("inquery_ID") Long inquery_ID,@RequestParam("plan_ID") Long plan_ID,@RequestParam("user")String user,@RequestParam("design")String design,@RequestParam("inquery_start_date")String inq_start,@RequestParam("inquery_end_date")String inq_end,@RequestParam("design_start_date")String design_start,@RequestParam("design_end_date")String design_end,@RequestParam("plan_start_date")String plan_start,@RequestParam("plan_end_date")String plan_end ) throws ParseException {
//  	if(inq_start!=null && inq_end!=null && design_start!=null && design_end!=null && plan_start!=null && plan_end!=null) {
//  		Date inq_st = new SimpleDateFormat("yyyy-MM-dd").parse(inq_start);
//	    	Date inq_ed=new SimpleDateFormat("yyyy-MM-dd").parse(inq_end);
//	    	Date design_st=new SimpleDateFormat("yyyy-MM-dd").parse(design_start);
//	    	Date design_ed=new SimpleDateFormat("yyyy-MM-dd").parse(design_end);
//	    	Date plan_st=new SimpleDateFormat("yyyy-MM-dd").parse(plan_start);
//	    	Date plan_ed=new SimpleDateFormat("yyyy-MM-dd").parse(plan_end);
//	    	daoProductionPlan.getAllOwner(initial_Buyer, ultimate_Buyer,inquery_ID ,plan_ID, user,design,inq_st,inq_ed,design_st,design_ed,plan_st,plan_ed);
//  	}
//  	
//  	return "redirect:/production/plan/search_list";
//  			
//  }
	
	
	/*
	 * replace by ajax
	@RequestMapping(path="/production/plan/search")
public String getOrderData(Model model,ModelProductionPlan modelProductionPlan,@RequestParam("order_type") int order_type,
		  @RequestParam("initial_Buyer") String initial_Buyer) {
		    
		    List<ModelUser> userList= daoUserImp.getAllUSerName();
			model.addAttribute("userList",userList);
			
			List<ModelDesign> designList= daoDesignImp.getDesignList();
			model.addAttribute("designList",designList);
			
			List<ModelOrderOwner> ownerList= daoOrderOwnerImp.getAllOwnerName();
			model.addAttribute("ownerList",ownerList);
			
			List<ModelUserCluster> clusterList= userClusterRepository.findAll();						
			model.addAttribute("clusterList",clusterList);
		
			model.addAttribute("productionPlan", modelProductionPlan);
			model.addAttribute("userList",daoProductionPlan.getUserList());
			
			
			System.out.println("order type" + order_type + " initial buyer" + initial_Buyer);
		    List<ModelInquiryList> modelOrderList=
	    	daoProductionPlan.getOrderList(order_type, initial_Buyer);
			model.addAttribute("searchList",modelOrderList);
			
			//popup list
			List<ModelMachineType> machineTypeList= daoMachineTypeImp.getMachineName();
			model.addAttribute("machineTypeList",machineTypeList);
	
			return"production_plan";
			
}
*/

     }
