package com.biziitech.mlfm.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.custom.model.ModelDeliverChallanCustom;
import com.biziitech.mlfm.custom.model.ModelGatePassCustom;
import com.biziitech.mlfm.custom.model.ModelMachineScheduleData;
import com.biziitech.mlfm.custom.model.ModelPIChdOrder;
import com.biziitech.mlfm.custom.model.ModelPIMstCustom;
import com.biziitech.mlfm.custom.model.ModelPIReceiveCustom;
import com.biziitech.mlfm.custom.model.ModelWashingCustom;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerTypeImp;
import com.biziitech.mlfm.daoimpl.DaoPIReceiveChdImp;
import com.biziitech.mlfm.daoimpl.DaoPIReceiveImp;
import com.biziitech.mlfm.daoimpl.DaoPIReceiveMstImp;
import com.biziitech.mlfm.model.ModelDeliveryChallan;
import com.biziitech.mlfm.model.ModelGatePassMst;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelOrderOwnerType;
import com.biziitech.mlfm.model.ModelPIChd;
import com.biziitech.mlfm.model.ModelPIMst;
import com.biziitech.mlfm.model.ModelPIReceiveChd;
import com.biziitech.mlfm.model.ModelPIReceiveMst;
import com.biziitech.mlfm.model.ModelPIType;
import com.biziitech.mlfm.repository.PIReceiveMstRepository;
import com.biziitech.mlfm.repository.PITypeRepository;

@Controller
public class PIReceiveController {
	
	
	@Autowired
	private DaoOrderOwnerImp orderOwner;
	
	@Autowired
	private DaoPIReceiveImp daoPIReceiveImp;
	
	@Autowired
	private DaoPIReceiveMstImp daoPIReceiveMstImp;
	
	@Autowired
	private DaoPIReceiveChdImp daoPIReceiveChdImp;
	
	@Autowired
	private DaoOrderOwnerTypeImp daoownerType;
	
	@Autowired
	private PITypeRepository PITypeRepository;
	
	@Autowired
	private PIReceiveMstRepository pIReceiveMstRepository;
	
	@RequestMapping(path="pIReceiveController/init")
	public String init(Model model) {
		
		List<ModelOrderOwnerType> ownerTypeList= daoownerType.getTypeName();			
        model.addAttribute("ownerTypeList",ownerTypeList);
        
        List<ModelPIType> piTypeList=PITypeRepository.findPITypes();
		model.addAttribute("piTypeList", piTypeList);
		
		return "pi_receive";
		
		
		
	}
	
	
	@RequestMapping(path="pIReceive/inquery/owner")
	@ResponseBody
	public List<ModelOrderOwner> findOwnersByType(@RequestParam("ownerType")Long typeId){
		
		System.out.println("" + typeId);
		
		return orderOwner.getOwnerByType(typeId);
	}
	
	    @ResponseBody
	    @RequestMapping(value = "/pIreceive/pI/search", method=RequestMethod.GET)
	    public List<ModelPIMstCustom> getPISearchData(@RequestParam("id") Long ownerType,@RequestParam("owner") Long owner,@RequestParam("wOMstId") Long wOMstId,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("startDate") Date startDate,
		          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("endDate") Date endDate,@RequestParam("pITypeId") Long pITypeId) {
		   
  	
  	
  	    List<ModelPIMstCustom> pIData=daoPIReceiveImp.getPISearchData(ownerType, owner, wOMstId, startDate, endDate, pITypeId);
		   
	        return pIData;
		}
	    
	    
	    
	    @ResponseBody
	    @RequestMapping(path = "/pireceivecontroller/pireceivemst/save", method = RequestMethod.POST) 
    	public  List<ModelPIReceiveCustom> savePIReceiveMst(ModelPIReceiveMst receiveMst,ModelPIMst modelPIMst,@RequestParam("pIMstId") Long pIMstId,@RequestParam("receiveAmount") Double receiveAmount)
    			 {
    	   
        	java.util.Date date = new java.util.Date();
			java.sql.Timestamp entryTimestamp = new java.sql.Timestamp(date.getTime());
			
			
			modelPIMst.setpIMstId(pIMstId);
			
			receiveMst.setModelPIMst(modelPIMst);
			receiveMst.setReceiveMstDate(date);
			receiveMst.setReceiveMstAmt(receiveAmount);
			receiveMst.setActiveStatus(1);
			receiveMst.setEnteredBy(1);
			receiveMst.setEntryTimestamp(entryTimestamp);
			
			
			daoPIReceiveMstImp.savePIReceiveMst(receiveMst);
			
			Long id=receiveMst.getpIReceiveMstId();
				
			
			List<ModelPIReceiveCustom> dataList=daoPIReceiveMstImp.getPIReceiveMstById(id);
			

    	    return dataList;
			
			
    	
    }
	    
	    
	    @RequestMapping(path="pichdsearch/pimst/id")
		@ResponseBody
		public List<ModelPIReceiveCustom> findPIChd(@RequestParam("id")Long id){
			
			System.out.println("id" + id);
			
			List<ModelPIReceiveCustom> dataListChd=daoPIReceiveMstImp.getPIChdById(id);
			
			
			System.out.println("size data" + dataListChd.size());
			
			return dataListChd;
		}
	    
	    
	    
	    @RequestMapping(path = "/pireceivecontroller/pireceivechd/save", method = RequestMethod.POST) 
    	public  String savePIReceiveChd(ModelPIChd pIChd,ModelPIReceiveChd receiveChd,ModelPIReceiveMst receiveMst,ModelPIMst modelPIMst,@RequestParam("pIChdId") Long pIChdId,@RequestParam("receiveMstId") Long receiveMstId)
    			 {
    	   
        	java.util.Date date = new java.util.Date();
			java.sql.Timestamp entryTimestamp = new java.sql.Timestamp(date.getTime());
			
			receiveMst.setpIReceiveMstId(receiveMstId);
			pIChd.setpIChdId(pIChdId);
			
			receiveChd.setModelPIReceiveMst(receiveMst);
			receiveChd.setModelPIChd(pIChd);
			receiveChd.setActiveStatus(1);
			receiveChd.setEnteredBy(1);
			receiveChd.setEntryTimestamp(entryTimestamp);
			
			daoPIReceiveChdImp.savePIReceiveChd(receiveChd);
			
			
			
			
		return "pi_receive";
			
			
    	
    }
	    
	    
	    @ResponseBody
	    @RequestMapping(value = "/pireceivecontroller/receivedone/search", method=RequestMethod.GET)
	    public List<ModelPIMstCustom> getPIReceiveDoneData(@RequestParam("id") Long ownerType,@RequestParam("owner") Long owner,@RequestParam("wOMstId") Long wOMstId,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("startDate") Date startDate,
		          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("endDate") Date endDate,@RequestParam("pITypeId") Long pITypeId,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("doneStartDate") Date doneStartDate,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("doneEndDate") Date doneEndDate) {
		   
  	
  	
  	    List<ModelPIMstCustom> pIData=daoPIReceiveImp.getPIReceiveDoneData(ownerType, owner, wOMstId, startDate, endDate, pITypeId, doneStartDate, doneEndDate);
		   
	        return pIData;
		}
	    
	    
	    
	    @RequestMapping(path="/pireceivecontroller/pireceivemst/search")
		@ResponseBody
		public List<ModelPIReceiveCustom> findPIReceiveMst(@RequestParam("id")Long id){
			
			System.out.println("id" + id);
			
			List<ModelPIReceiveCustom> dataListMst=daoPIReceiveMstImp.getPIReceiveMstByPIMstId(id);
			
			
			System.out.println("data size : " + dataListMst.size());
			
			return dataListMst;
		}
	    
	    @RequestMapping(path="/pireceivecontroller/pireceivechd/search")
		@ResponseBody
		public List<ModelPIReceiveCustom> findPIReceiveChd(@RequestParam("id")Long id){
			
			System.out.println("id" + id);
			
			List<ModelPIReceiveCustom> dataListMst=daoPIReceiveChdImp.getPIReceiveChdByPIMstId(id);
			
			
			System.out.println("data size : " + dataListMst.size());
			
			return dataListMst;
		}
	    
	    
	    
	    @ResponseBody
	    @RequestMapping(path = "/deliverycontroller/inactive/active/save", method = RequestMethod.POST) 
	    public  List<ModelPIReceiveCustom> saveEditReceiveData(ModelPIReceiveMst receiveMst,@RequestParam("id")Long id,@RequestParam("verifyStatus")int activeStatus,@RequestParam("pIMstId")Long pIMstId){
		
		
	    	List<ModelPIReceiveCustom> modelList=new ArrayList<ModelPIReceiveCustom>();
	    	
        	java.util.Date dates = new java.util.Date();
			java.sql.Timestamp entryTimestamp = new java.sql.Timestamp(dates.getTime());
			
			System.out.println("Status"  +activeStatus);
			
			if(activeStatus==1) {
				
			
			
			receiveMst.setUpdatedBy(1L);
			receiveMst.setUpdateTimestamp(entryTimestamp);
			receiveMst.setActiveStatus(1);
			receiveMst.setpIReceiveMstId(id);
			
			daoPIReceiveMstImp.updateReceiveMst(receiveMst);
			
			
			List<ModelPIReceiveCustom> dataListMst=daoPIReceiveMstImp.getPIReceiveMstByPIMstId(pIMstId);
			
			
			modelList.addAll(dataListMst);
			
			}
			
			if(activeStatus==0) {
				
				receiveMst.setUpdatedBy(1L);
				receiveMst.setUpdateTimestamp(entryTimestamp);
				receiveMst.setActiveStatus(0);
				receiveMst.setpIReceiveMstId(id);
				
				daoPIReceiveMstImp.updateReceiveMst(receiveMst);
				
				List<ModelPIReceiveCustom> dataList=daoPIReceiveMstImp.getPIReceiveMstByPIMstId(pIMstId);
				
				modelList.addAll(dataList);
				
			}
				
				return modelList;
				
			}
	    
	    
	    
			
	
}
