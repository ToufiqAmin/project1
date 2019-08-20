package com.biziitech.mlfm.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.biziitech.mlfm.bg.daoimp.DaoUserImp;
import com.biziitech.mlfm.bg.model.ModelCurrency;
import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.custom.model.ModelPIChdOrder;
import com.biziitech.mlfm.custom.model.ModelPIInquiryList;
import com.biziitech.mlfm.custom.model.ModelPIMstCustom;
import com.biziitech.mlfm.custom.model.ModelUOMCustom;
import com.biziitech.mlfm.custom.model.ModelWOChdListCustom;
import com.biziitech.mlfm.dao.DaoPiMst;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.dao.DaoWorkOrder;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerTypeImp;
import com.biziitech.mlfm.daoimpl.DaoPIChdImp;
import com.biziitech.mlfm.daoimpl.DaoPiMstImp;
import com.biziitech.mlfm.daoimpl.DaoUserClusterImp;
import com.biziitech.mlfm.daoimpl.DaoWorkOrderChdImp;
import com.biziitech.mlfm.daoimpl.DaoWorkOrderImp;
import com.biziitech.mlfm.model.ModelOrder;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelOrderOwnerType;
import com.biziitech.mlfm.model.ModelPIChd;
import com.biziitech.mlfm.model.ModelPIMst;
import com.biziitech.mlfm.model.ModelPIType;
import com.biziitech.mlfm.model.ModelTermsCon;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.model.ModelUserCluster;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.model.ModelWOChd;
import com.biziitech.mlfm.repository.UserClusterRepository;
import com.biziitech.mlfm.repository.WorkOrderRepository;
import com.biziitech.mlfm.repository.CurrencyRepository;
import com.biziitech.mlfm.repository.PIChdRepository;
import com.biziitech.mlfm.repository.PITypeRepository;
import com.biziitech.mlfm.repository.PiMstRepository;
import com.biziitech.mlfm.repository.TermsConRepository;

@Controller
public class PIController {
	

	@Autowired
	private DaoOrderOwnerTypeImp daoownerType;
	
	@Autowired
	private DaoOrderOwnerImp orderOwner;
	
	@Autowired
	private DaoUserImp daoUserImp;
	
	@Autowired
	private UserClusterRepository userClusterRepository;
	
	@Autowired
	private DaoUserClusterImp userCluster;
	
	@Autowired
	private DaoWorkOrderImp daoWorkOrderImp;
	
	@Autowired
	private DaoPIChdImp daoPIChdImp;
	
	@Autowired
	private DaoWorkOrderChdImp daoWorkOrderChdImp;
	
	@Autowired
	private CurrencyRepository currencyRepository;
	
	@Autowired
	private DaoPiMstImp daoPiMstImp;
	
	@Autowired
	private DaoPiMst daoPiMst;
	
	@Autowired
	private PiMstRepository pimstRepository;
	
	@Autowired
	private PITypeRepository PITypeRepository;
	
	@Autowired
	private TermsConRepository termsConRepository;
	
	@Autowired
	private DaoWorkOrder daoWorkOrder;
	
	@Autowired
	private PIChdRepository pIChdRepository;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


    private Long systemUserId;
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	


	@RequestMapping(path = "/pimst/init/{userId}", method = RequestMethod.GET)
    public String getPimst(@PathVariable Long userId,Model model) {
		
		
		
		
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
		
		
		
		
		ModelPIMst PIMst= new ModelPIMst();
		PIMst.setActive(true);		
		model.addAttribute("PIMst",PIMst);
		
		List<ModelOrderOwnerType> ownerTypeList= daoownerType.getTypeName();			
        model.addAttribute("ownerTypeList",ownerTypeList);

        
		List<ModelOrderOwner> ownerList= orderOwner.getAllOwnerName();
		model.addAttribute("ownerList",ownerList);
		
		List<ModelUser> userList= daoUserImp.getAllUSerName();
		model.addAttribute("userList",userList);
		
		List<ModelUserCluster> clusterList= userClusterRepository.findAll();						
		model.addAttribute("clusterList",clusterList);
		//model.addAttribute("wolist", daoWorkOrderImp.allWorkOrderList());
		
		List<ModelPIType> piTypeList=PITypeRepository.findPITypes();
		model.addAttribute("piTypeList", piTypeList);
		
		List<ModelTermsCon> piTermsCon=termsConRepository.findPITermsCon();
		model.addAttribute("piTermsCon", piTermsCon);
		
	 
        return "pimst";
   }
	
	
	@RequestMapping(path="/inquery/owners")
	@ResponseBody
	public List<ModelOrderOwner> findOwners(@RequestParam("ownerType")Long typeId){
		
		System.out.println("" + typeId);
		
		return orderOwner.getOwnerByType(typeId);
	}
	
	
	@RequestMapping(path="/inquery/user/clusters")
	@ResponseBody
	public List <ModelUserCluster> findClusters(@RequestParam("user")Long userId){	
		
		System.out.println("" + userId);
		return userCluster.getClusterByUser(userId);
	}
	
	//This seach for workordermst which has no pi
	
	@ResponseBody
	@RequestMapping(value="/workorder/mst/search", method = RequestMethod.GET)
	     public List<ModelPIInquiryList>  getAllWorkOrderWithoutPI(Model model,@RequestParam("ownerType")Long ownerType,@RequestParam("owner")String owner,
		       @RequestParam("ultimateOwner")String ultimateOwner,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam(value="work_start_date")Date startdate,
		       @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam(value="work_end_date")Date enddate,
		       @RequestParam("user")Long user,@RequestParam("cluster")Long cluster
		       ) 
		    		  // throws ParseException 
	     {

			  	               
		            	  
		            	   // Date startdate = new SimpleDateFormat("yyyy-MM-dd").parse(work_start_date);
					    	//Date enddate=new SimpleDateFormat("yyyy-MM-dd").parse(work_end_date);
					    
					    	List<ModelPIInquiryList> woDataList=new ArrayList<ModelPIInquiryList>();
				      		
				      		woDataList=	daoWorkOrder.getWoDataList(ownerType, owner, ultimateOwner, user, cluster, startdate, enddate);
				      				      				      		      		
				   
				      		
				      		return woDataList;	
		               	                     
			    	
			    }
	
	// This search for workordermst which has pi done
	
	
	@ResponseBody
	@RequestMapping(value="/workorder/mst/search/withpi", method = RequestMethod.GET)
	     public List<ModelPIInquiryList>  getAllWorkOrderWithPI(Model model,@RequestParam("ownerType")Long ownerType,@RequestParam("owner")String owner,
			       @RequestParam("ultimateOwner")String ultimateOwner,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam(value="work_start_date")Date startdate,
			       @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam(value="work_end_date")Date enddate,
			       @RequestParam("user")Long user,@RequestParam("cluster")Long cluster
			       ) 
		      // ) throws ParseException {

	     {      
		            	  
		            	  // Date startdate = new SimpleDateFormat("yyyy-MM-dd").parse(work_start_date);
					    	//Date enddate=new SimpleDateFormat("yyyy-MM-dd").parse(work_end_date);
					    
					    	List<ModelPIInquiryList> woDataList=new ArrayList<ModelPIInquiryList>();
				      		
				      		woDataList=	daoWorkOrder.getWoDataListWithPI(ownerType, owner, ultimateOwner, user, cluster, startdate, enddate);
				      				      				      		      		
				   
				      		
				      		return woDataList;	
		               	                     
			    	
			    }
			    
	    
	        @ResponseBody
	        @RequestMapping(path="/workmst/workchd/search/ajax", method=RequestMethod.GET)
		    public List<ModelPIChdOrder> getWOChdByWOMstAjax(Model model,@RequestParam("id") Long workOrderid) {
	        	System.out.println("id :" + workOrderid);
	        	//List<ModelWOChd> modelWOChdList=daoWorkOrderChdImp.getWOChdByWOMst(workOrderid);
	        	
	        	List<ModelPIChdOrder> modelWOChdList=daoWorkOrderChdImp.getWOChdDataList(workOrderid);
	        	      				
				return modelWOChdList;
		    }
	        
	        @ResponseBody
	        @RequestMapping(path="/pimst/search", method=RequestMethod.GET)
		    public List<ModelPIMstCustom> getPIMStDetails() {
	        	
	        	List<ModelPIMstCustom> PIMstList=daoPiMstImp.getPIMstDetails();
	        	
	        	System.out.println("PIMst data size :" + PIMstList.size());
	        	
  				
				return PIMstList;
	           
		    }
	        
   
	        @RequestMapping(path = "/PIMst/save", method = RequestMethod.POST) 
	    	public  String savePIMst(ModelPIMst pimst, Model model) {
	    	
	    	if(pimst.getpIMstId()==null )
	    			{
	    			java.util.Date date = new java.util.Date();
	    			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
	    				pimst.setEntryTimestamp(entryTime);
	    				pimst.setEnteredBy(1);
	    				daoPiMstImp.savePIMst(pimst);
	    				
	    				
	    				
	    			}
	    			else
	    			{
	    				Optional<ModelPIMst> existspimst=pimstRepository.findById(pimst.getpIMstId());
	    				pimst.setEnteredBy(existspimst.get().getEnteredBy());
	    				pimst.setEntryTimestamp(existspimst.get().getEntryTimestamp());
	    				
	    				daoPiMstImp.savePIMst(pimst);
	    				
	    			}
	    	
	    	 return "redirect:/pimst/init";
	    	
	    }
	        
	        
	        
	        @ResponseBody
	        @RequestMapping(path="/pichd/search", method=RequestMethod.GET)
		    public List<ModelPIChdOrder> getPIChdByWOChd(Model model,@RequestParam("chdId") Long workOrderChdId) {
	        	System.out.println("chdId :" + workOrderChdId);
	        	
	        	
	        	List<ModelPIChdOrder> resultList=daoPIChdImp.getPIChdDataList(workOrderChdId);
	        	List<ModelPIChdOrder> modelPIChdList=new ArrayList<ModelPIChdOrder>();
	        	
	        	System.out.println("PICHd data size :" + modelPIChdList.size()); 
	        	
	        	List<ModelCurrency> currencyList=new ArrayList<ModelCurrency>(); 
	    		currencyList=currencyRepository.findCurrency();
	        	for(ModelPIChdOrder m:resultList ) {
	    		m.setModelCurrencyList(currencyList);
	    		modelPIChdList.add(m);
	    		
	        	}
	        	
	        	
	        	
				return modelPIChdList;
		    }
	        
	        @ResponseBody
	        @RequestMapping(path = "/pichd/save", method = RequestMethod.POST) 
	    	public List<ModelPIChdOrder> savePIChd(ModelPIChd pIChd,ModelPIMst modelPIMst,ModelUOM modelUOM,ModelCurrency modelCurrency,@RequestParam("pIMstId") Long pIMstId,@RequestParam("wOChId") Long wOChdId,@RequestParam("uOMId") Long uOMId,@RequestParam("pIQty") double pIQty,@RequestParam("itemRate") double itemRate,@RequestParam("currency") Long currency) {
	    	   	
	        	try {
	        	
	        	
	        	java.util.Date date = new java.util.Date();
    			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
	        	
	        	System.out.println("pIMstId "+ pIMstId);
	        	ModelWOChd modelWOChd=new ModelWOChd();
	        	modelWOChd.setWoChdId(wOChdId);
	        	modelPIMst.setpIMstId(pIMstId);
	        	modelUOM.setUomId(uOMId);
	        	modelCurrency.setCurrencyId(currency);
	        	
	        	pIChd.setModelPIMst(modelPIMst);
	        	pIChd.setModelWOChd(modelWOChd);
	        	pIChd.setActiveStatus(1);
	        	pIChd.setItemQty(pIQty);
	        	pIChd.setModelUOM(modelUOM);
	        	pIChd.setItemRate(itemRate);
	        	pIChd.setModelCurrency(modelCurrency);
	        	pIChd.setEnteredBy(1);
	        	pIChd.setEntryTimestamp(entryTime);
	        	
	        	   	
	        	daoPIChdImp.savePIChd(pIChd);
	        	
	        	//edited by KTA
	        	
	        	Long pIChdId=pIChd.getpIChdId();
	        	System.out.println("pIChdId: "+pIChdId);
	        	
	        	
	        	List<ModelPIChdOrder> pichd=daoPIChdImp.getPIChdDataById(pIChdId);
	        	System.out.println("pichd size: "+pichd.size());
	    	
	    	 return pichd;
	    	 // end edited by KTA
	        	}		
	    		catch(Exception e) {			
	    		 e.printStackTrace();
	    		 System.out.println("error");
	    			return null;
	    		}
	    	
	    }
	    
	     
	        @RequestMapping(path="/inquery/buyers")
	    	@ResponseBody
	    	public List<ModelOrderOwner> findBuyers(@RequestParam("buyerType")Long typeId){
	    		
	    		System.out.println("" + typeId);
	    		
	    		return orderOwner.getOwnerByType(typeId);
	    	}
	        
	        
	        @ResponseBody
		    @RequestMapping(value = "/pimst/edit", method=RequestMethod.GET)
		    public List<ModelPIMstCustom> GetPIMstData(@RequestParam("id") Long id) {
			   
	        	System.out.println("pIMStId :" + id);
	        	
	        	List<ModelPIMstCustom> pIMStData=daoPiMstImp.getPIMstById(id);
			   
		        return pIMStData;
			}
	        
	        
	        @ResponseBody
		    @RequestMapping(value = "/picontroller/pichd/edit/data", method=RequestMethod.GET)
		    public List<ModelPIChdOrder> GetPIChdDataForEdit(@RequestParam("id") Long id) {
			   
	        	System.out.println("woChdId :" + id);
	        	
	        	
	        	List<ModelPIChdOrder> resultList=daoPIChdImp.getPIChdDataByWOChId(id);
	        	List<ModelPIChdOrder> modelPIChdList=new ArrayList<ModelPIChdOrder>();
	        	
	        	System.out.println("PICHd data size :" + modelPIChdList.size()); 
	        	
	        	List<ModelCurrency> currencyList=new ArrayList<ModelCurrency>(); 
	    		currencyList=currencyRepository.findCurrency();
	        	for(ModelPIChdOrder m:resultList ) {
	    		m.setModelCurrencyList(currencyList);
	    		modelPIChdList.add(m);
	    		
	        	}
	        	
	        	
	        	
				return modelPIChdList;
	    
			}
	        
	        
	        @ResponseBody
	        @RequestMapping(path = "/picontroller/pichd/edit/data/save", method = RequestMethod.POST) 
	    	public List<ModelPIChdOrder> savePIChdEditData(ModelPIChd pIChd,ModelCurrency modelCurrency,@RequestParam("pIQty") double pIQty,@RequestParam("currency") Long currency,@RequestParam("pIChdId") Long pIChdId) {
	    	   	
	        	try {
	        	
	        	
	        	java.util.Date date = new java.util.Date();
    			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
	        	
	        	modelCurrency.setCurrencyId(currency);	       
	        	pIChd.setItemQty(pIQty);
	        	pIChd.setModelCurrency(modelCurrency);
	        	pIChd.setUpdatedBy(1L);
	        	pIChd.setUpdateTimestamp(entryTime);
	        	pIChd.setpIChdId(pIChdId);
	        	
	        	
	        	daoPIChdImp.updatePIChdData(pIChd);
	        	
	        	
	     	        	
	        	System.out.println("pIChdId: "+pIChdId);
	        	
	        	
	        	List<ModelPIChdOrder> pichd=daoPIChdImp.getPIChdDataById(pIChdId);
	        	System.out.println("pichd size: "+pichd.size());
	    	
	    	 return pichd;
	    	
	        	}		
	    		catch(Exception e) {			
	    		 e.printStackTrace();
	    		 System.out.println("error");
	    			return null;
	    		}
	    	
	    }
	        
	        
	        @ResponseBody
	        @RequestMapping(path="/picontroller/workchd/search", method=RequestMethod.GET)
		    public List<ModelPIChdOrder> getDoneWOChdByWOMst(Model model,@RequestParam("id") Long id) {
	        	System.out.println("id :" + id);
	        	
	        	List<ModelPIChdOrder> modelWOChdList=daoWorkOrderChdImp.getDoneWOChdDataList(id);
	        	      				
				return modelWOChdList;
		    }
	
	
}
