package com.biziitech.mlfm.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biziitech.mlfm.bg.daoimp.DaoUserImp;
import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.custom.model.ModelGatePassCustom;
import com.biziitech.mlfm.custom.model.ModelPIChdOrder;
import com.biziitech.mlfm.custom.model.ModelPIMstCustom;
import com.biziitech.mlfm.custom.model.ModelWashingCustom;
import com.biziitech.mlfm.daoimpl.DaoGatePassChdImp;
import com.biziitech.mlfm.daoimpl.DaoGatePassImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerTypeImp;
import com.biziitech.mlfm.model.ModelGatePassChd;
import com.biziitech.mlfm.model.ModelGatePassMst;
import com.biziitech.mlfm.model.ModelOrderItemQty;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelOrderOwnerFeedback;
import com.biziitech.mlfm.model.ModelOrderOwnerType;
import com.biziitech.mlfm.model.ModelPIMst;
import com.biziitech.mlfm.model.ModelProduction;
import com.biziitech.mlfm.repository.GatePassRepository;


@Controller
public class GatePassController {
	 
	@Autowired
	private DaoOrderOwnerTypeImp daoownerType;
	
	@Autowired
	private DaoGatePassImp daoGatePassImp;
	
	@Autowired
	private DaoGatePassChdImp daoGatePassChdImp;
	
	@Autowired
	private DaoOrderOwnerImp orderOwner;
	
	@Autowired
	private DaoUserImp user;
	
	@Autowired
	private GatePassRepository gatePassRepository;

	@RequestMapping(path="/gatePass/init", method = RequestMethod.GET)
	public String gatePassInit(Model model) {
	
		ModelGatePassMst modelGatePass=new ModelGatePassMst();
		
		model.addAttribute("gatePass", modelGatePass);
		
		List<ModelOrderOwnerType> ownerTypeList= daoownerType.getTypeName();			
         model.addAttribute("ownerTypeList",ownerTypeList);
        
        List<ModelUser> userList= user.getAllUSerName();
		model.addAttribute("userList",userList);
		
		return "gate_pass";
		

     }
	
	@RequestMapping(path="gatePass/inquery/owner")
	@ResponseBody
	public List<ModelOrderOwner> findOwnersByType(@RequestParam("ownerType")Long typeId){
		
		System.out.println("" + typeId);
		
		return orderOwner.getOwnerByType(typeId);
	}
	
	    @ResponseBody
	    @RequestMapping(value = "/gatePass/inquiry/search", method=RequestMethod.GET)
	    public List<ModelWashingCustom> getGatePassSearchData(@RequestParam("id") Long ownerType,@RequestParam("owner") Long owner,@RequestParam("orderId") Long orderId,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("startDate") Date startDate,
		          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("endDate") Date endDate) {
		   
     	
	    	System.out.println("order id :"+ orderId);
     	
     	List<ModelWashingCustom> inquiryData=daoGatePassImp.getInquirySearchData(ownerType,owner,orderId,startDate,endDate);
		   
	        return inquiryData;
		}
	    
	    
	    @ResponseBody
	    @RequestMapping(value = "/gatePass/workOrder/search", method=RequestMethod.GET)
	    public List<ModelWashingCustom> getGatePassWorkOrderhData(@RequestParam("id") Long ownerType,@RequestParam("owner") Long owner,@RequestParam("orderId") Long orderId,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("startDate") Date startDate,
		          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("endDate") Date endDate) {
		   
     	System.out.println("ownerType :" + ownerType);
     	
     	List<ModelWashingCustom> workOrderData=daoGatePassImp.getWorkOrderSearchData(ownerType, owner, orderId, startDate, endDate);
		   
	        return workOrderData;
		}
	    
	    @ResponseBody
	    @RequestMapping(path = "/gatePassMst/save", method = RequestMethod.POST) 
    	public  List<ModelGatePassCustom> saveGatePassMst(ModelGatePassMst gatePass,ModelOrderOwner modelOrderOwner,ModelUser modelUser,@RequestParam("owner") Long owner, @RequestParam("issuer") Long issuer,@RequestParam("type") int type,@RequestParam("purpose") String purpose,@RequestParam("vehicleName") String vehicleName,@RequestParam("active") int active,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam(value="date")Date date
    			
    			,@RequestParam("vehicleNo") String vehicleNo,@RequestParam("driverName") String driverName,@RequestParam("contactNumber") String contactNumber,Model model,@RequestParam("remarks") String remarks) {
    	   
        	java.util.Date dates = new java.util.Date();
			java.sql.Timestamp entryTimestamp = new java.sql.Timestamp(dates.getTime());
			
			modelOrderOwner.setOrderOwnerId(owner);
			modelUser.setUserId(issuer);
			
			gatePass.setModelOrderOwner(modelOrderOwner);
			gatePass.setModelUser(modelUser);
			gatePass.setGatePassDate(date);
			gatePass.setGatePassType(type);
			gatePass.setVehicleTypeName(vehicleName);
			gatePass.setVehicleNumber(vehicleNo);
			gatePass.setVehicleDriverName(driverName);
			gatePass.setVehicleDriverContactNo(contactNumber);
			gatePass.setActiveStatus(active);
			gatePass.setEntryTimestamp(entryTimestamp);
			gatePass.setEnteredBy(1);
			gatePass.setRemarks(remarks);
			
			daoGatePassImp.saveGatePassMst(gatePass);
			
			
			
			Long id=gatePass.getGatePassMstId();
			
			System.out.println(id);
			
			List<ModelGatePassCustom> gatePassDataList= daoGatePassImp.getIGatePassMstData(id);
			
			      	

    	 return gatePassDataList;
    	
    }
	    
	    @ResponseBody
	    @RequestMapping(value = "/gatePassMst/edit", method=RequestMethod.GET)
	    public List<ModelGatePassCustom> editData(@RequestParam("id") Long id) {
		   
     	System.out.println("ownerType :" + id);
     	
     	List<ModelGatePassCustom> dataForEdit=daoGatePassImp.getIGatePassMstData(id);
		   
	        return dataForEdit;
		}

	    @ResponseBody
	    @RequestMapping(path = "/gatePassMst/edit/save", method = RequestMethod.POST) 
    	public  List<ModelGatePassCustom> saveEditGatePassMst(ModelGatePassMst gatePass,ModelUser modelUser,@RequestParam("issuer") Long issuer,@RequestParam("type") int type,@RequestParam("purpose") String purpose,@RequestParam("vehicleName") String vehicleName,@RequestParam("active") int active,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam(value="date")Date date
    			
    			,@RequestParam("vehicleNo") String vehicleNo,@RequestParam("driverName") String driverName,@RequestParam("contactNumber") String contactNumber,Model model,@RequestParam("editId") Long editId,@RequestParam("remarks") String remarks) {
    	   
        	java.util.Date dates = new java.util.Date();
			java.sql.Timestamp entryTimestamp = new java.sql.Timestamp(dates.getTime());
			
			
			modelUser.setUserId(issuer);			
			gatePass.setModelUser(modelUser);
			gatePass.setGatePassDate(date);
			gatePass.setGatePassType(type);
			gatePass.setVehicleTypeName(vehicleName);
			gatePass.setVehicleNumber(vehicleNo);
			gatePass.setVehicleDriverName(driverName);
			gatePass.setVehicleDriverContactNo(contactNumber);
			gatePass.setActiveStatus(active);
			gatePass.setUpdateTimestamp(entryTimestamp);
			//gatePass.setEnteredBy(1);
			gatePass.setUpdatedBy(1L);
			gatePass.setGatePassMstId(editId);
			gatePass.setRemarks(remarks);
			
			daoGatePassImp.updateGatePassMst(gatePass);
			
			Long id=editId;
			
			System.out.println(editId);
			
			List<ModelGatePassCustom> gatePassDataList= daoGatePassImp.getIGatePassMstData(id);
			
			      	

    	 return gatePassDataList;
    	
    }
	    
	    
	    @ResponseBody
	    @RequestMapping(value = "/gatePass/chd/search", method=RequestMethod.GET)
	    public List<ModelWashingCustom> getDataForChd(@RequestParam("productionId") Long id) {
		   
     	System.out.println("ownerId For chd:" + id);
     	
     	List<ModelWashingCustom> searchList=daoGatePassImp.getInquiryDataById(id);
		   
	        return searchList;
		}
	    
	    
	    @ResponseBody
	    @RequestMapping(value = "/gatePassmst/search", method=RequestMethod.GET)
	    public List<ModelGatePassCustom> getAllData() {
		   
     	List<ModelGatePassCustom> allData=daoGatePassImp.getAllData();
		   
	        return allData;
		}
	    
	    
	    @ResponseBody
	    @RequestMapping(value = "/gatePassChd/save", method=RequestMethod.POST)
	    public List<ModelWashingCustom> saveChdData(Model model,ModelGatePassChd gatePassChd,@RequestParam("productionId") Long productionId,@RequestParam("gatePassMstId") Long gatePassMstId,@RequestParam("Qty") Double qty
	    		
	    		,ModelGatePassMst gatePassMst,ModelProduction production) {
		   
	    	
	    	java.util.Date dates = new java.util.Date();
			java.sql.Timestamp entryTimestamp = new java.sql.Timestamp(dates.getTime());
	    	
              gatePassMst.setGatePassMstId(gatePassMstId);
              production.setProductionId(productionId);
              
              gatePassChd.setModelGatePassMst(gatePassMst);
              gatePassChd.setModelProduction(production);
              gatePassChd.setItemQty(qty);
              gatePassChd.setEnteredBy(1);
              gatePassChd.setActiveStatus(1);
              
              gatePassChd.setEntryTimestamp(entryTimestamp);
              
              daoGatePassChdImp.saveGatePassChd(gatePassChd);
              
              
              Long gatePassChdId=gatePassChd.getGatePassChdId();
              
              List<ModelWashingCustom> data=daoGatePassChdImp.getDataByChdId(gatePassChdId);
              
              System.out.println("Chd Id :" + gatePassChdId);
              
              
		   
	        return data;
		}
	    
	    
	    @ResponseBody
	    @RequestMapping(value = "/gatePass/done/inquiry/search", method=RequestMethod.GET)
	    public List<ModelWashingCustom> getGatePassDoneSearchData(@RequestParam("id") Long ownerType,@RequestParam("owner") Long owner,@RequestParam("orderId") Long orderId
		          ) {
		       	
     	
     	List<ModelWashingCustom> inquiryData=daoGatePassImp.getGetPassDoneSearchData(ownerType, owner, orderId);
		   
	        return inquiryData;
		}
	    
	    
	    @ResponseBody
	    @RequestMapping(value = "/gatepasscontroller/chd/data", method=RequestMethod.GET)
	    public List<ModelWashingCustom> getGatePassChdDataById(@RequestParam("productionId") Long productionId) {
		          
		       	
     	
     	List<ModelWashingCustom> chdData=daoGatePassChdImp.getDataByChdByProductionId(productionId);
		   
	        return chdData;
		}
	    
	    
	    @ResponseBody
	    @RequestMapping(path = "/gatePassChd/edit/save", method = RequestMethod.POST) 
    	public  List<ModelWashingCustom> saveEditGatePassChd(ModelGatePassChd gatePass,@RequestParam("id") Long id ,@RequestParam("itemQty") Double itemQty ) {
    			
    		System.out.println("ehllo");	
    	   
        	java.util.Date dates = new java.util.Date();
			java.sql.Timestamp entryTimestamp = new java.sql.Timestamp(dates.getTime());
			
			gatePass.setItemQty(itemQty);
		
			gatePass.setUpdateTimestamp(entryTimestamp);
			gatePass.setUpdatedBy(1L);
			gatePass.setGatePassChdId(id);
			
			daoGatePassChdImp.updateGatePassChd(gatePass);
			
			List<ModelWashingCustom> gatePassChdDataList=daoGatePassChdImp.getDataByChdId(id);
			
			      	

    	 return gatePassChdDataList;
    	
    }
	    
	    
}
