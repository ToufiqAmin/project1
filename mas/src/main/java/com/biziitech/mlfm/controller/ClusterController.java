package com.biziitech.mlfm.controller;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.dao.DaoCluster;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.model.ModelCluster;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.repository.ClusterRepository;



@Controller
public class ClusterController {
	
	@Autowired
	private DaoCluster daoCluster;
	
	@Autowired
	private ClusterRepository clusterRepository;
	
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	
	@Autowired
	private DaoLogonImp daoLogonImp;


private Long systemUserId;
	
	 @RequestMapping(path = "/clustercontroller/init/{userId}", method = RequestMethod.GET)
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
		// model.addAttribute("userId",userId );	
			
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
		 
		 ModelCluster cluster = new ModelCluster();
		 model.addAttribute("cluster",cluster);
		 
		 String msg=" ";
	     model.addAttribute("message", msg);
	        
	     return "cluster";

	}
	 
	 
	 @RequestMapping(path = "/clustercontroller/savecluster", method = RequestMethod.POST)
	 //@RequestMapping(path = "/clustercontroller/savecluster", method = RequestMethod.POST)
 	public  String saveCluster(ModelCluster modelCluster, ModelUser modelUser, Model model) {
 	
		 Long userId=systemUserId;
		 
			java.util.Date date1 = new java.util.Date();
			java.sql.Timestamp updateTime = new java.sql.Timestamp(date1.getTime());
 	
		 if(modelCluster.getClusterId()==null )
			{
		 
 	java.util.Date date = new java.util.Date();
		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());

 			
		modelCluster.setEntryTimestamp(entryTime);
		
		 ModelUser user=new ModelUser();
	     user.setUserId(userId);
		  modelCluster.setEnteredBy(user);
		  
		daoCluster.saveCluster(modelCluster);
			}
			else
			{
				java.util.Date date = new java.util.Date();
				java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
				Optional<ModelCluster> existsCluster=clusterRepository.findById(modelCluster.getClusterId());
				modelCluster.setEnteredBy(existsCluster.get().getEnteredBy());
				modelCluster.setEntryTimestamp(existsCluster.get().getEntryTimestamp());
				
				 ModelUser user=new ModelUser();
			     user.setUserId(userId);
				modelCluster.setUpdatedBy(user);
				modelCluster.setUpdateTimestap(updateTime);
				
				daoCluster.saveCluster(modelCluster);
			}
		 
		 
		 
		 
		 //   System.out.println("system user :" + systemUserId);
		 //   Long userId=systemUserId;
		    
		     ModelUser logonUser=daoLogonImp.getLogonUserName(userId);
			 String userName=logonUser.getUserName();
			 System.out.println("logon user name is :" + userName);
			 String name=userName;
			 model.addAttribute("name",name );
			 model.addAttribute("userId",userId);
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
		
	
		String msg="Successfully Saved";
     	model.addAttribute("message",msg );
     	
     	ModelCluster cluster = new ModelCluster();
		 model.addAttribute("cluster",cluster);
		 
		 
		 
		 Optional<ModelCluster> clusterById=clusterRepository.findById(modelCluster.getClusterId());
    	 if( clusterById.get().getActiveStatus()==1)
    		 clusterById.get().setActive(true); 
    		else
    			clusterById.get().setActive(false);	
            model.addAttribute("cluster", clusterById);
            
            if(modelCluster.getClusterId()!=0) {
            	String msg1="Successfully Saved";
            	 model.addAttribute("message",msg1 );
            }
            else
            {
            	String msg1="Some Error Occured";
            	 model.addAttribute("message",msg1);
            }
		 
		 
		 
		 
 
 	 return "cluster";
 	
 }
	 
	 
	 @RequestMapping(path = "/clustercontroller/update/{id}", method = RequestMethod.GET)
	    public String updateCluster(@PathVariable Long id, Model model){
		 
		 System.out.println("system user :" + systemUserId);
		    Long userId=systemUserId;
		    
		     ModelUser logonUser=daoLogonImp.getLogonUserName(userId);
			 String userName=logonUser.getUserName();
			 System.out.println("logon user name is :" + userName);
			 String name=userName;
			 model.addAttribute("name",name );
			 model.addAttribute("userId",userId);
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
	    	
	    	 Optional<ModelCluster> clusterById=clusterRepository.findById(id);
	    	 if( clusterById.get().getActiveStatus()==1)
	    		 clusterById.get().setActive(true); 
	    		else
	    			clusterById.get().setActive(false);	
	            model.addAttribute("cluster", clusterById);
	            if(id!=0) {
	            	String msg=" ";
	            	 model.addAttribute("message",msg );
	            }
	            else
	            {
	            	String msg="Some Error Occured";
	            	 model.addAttribute("message",msg);
	            }
	    	
	        return "cluster";

	    }

}
