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

import com.biziitech.mlfm.bg.dao.DaoUser;
import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.dao.DaoCluster;
import com.biziitech.mlfm.dao.DaoUserCluster;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.model.ModelCluster;
import com.biziitech.mlfm.model.ModelUserCluster;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.repository.UserClusterRepository;

@Controller
public class UserClusterController {
	
	
	@Autowired
	private DaoUserCluster daoUserCluster;
	
	@Autowired
	private UserClusterRepository userClusterRepository;
	
	@Autowired
	private DaoUser daoUser;
	
	@Autowired
	private DaoCluster daoCluster;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


private Long systemUserId;
	
	 @RequestMapping(path = "/userclustercontroller/init/{userId}", method = RequestMethod.GET)
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
		 
		 
		 ModelUserCluster userCluster = new ModelUserCluster();
		 model.addAttribute("userCluster",userCluster);
		 
		 List<ModelUser> modelUser1 = daoUser.getAllUSerNameInOrder();
		 model.addAttribute("modelUser",modelUser1);
		 
		 List<ModelCluster> modelCluster = daoCluster.getClusterName();
		 model.addAttribute("modelCluster",modelCluster);
		 
		 String msg=" ";
	     model.addAttribute("message", msg);
	        
	     return "userCluster";

	}
	 
	 @RequestMapping(path = "/userclustercontroller/saveusercluster", method = RequestMethod.POST) 
	 	public  String saveUserCluster(ModelUserCluster modelUserCluster,ModelUser modelUser2, Model model) {
	 	
		 
		     Long userId=systemUserId;
		 
			java.util.Date date1 = new java.util.Date();
			java.sql.Timestamp updateTime = new java.sql.Timestamp(date1.getTime());
		 
	 	
			 if(modelUserCluster.getUserClusterId()==null )
				{
				 
				 Long user=modelUserCluster.getModelUser().getUserId();
				 System.out.println("userId:"+user);
				 Long cluster=modelUserCluster.getModelCluster().getClusterId();
				 System.out.println("clusterId:"+cluster);
				 List<ModelUserCluster> userCluster=daoUserCluster.findUserClusterByClusterId(cluster);
				 System.out.println("Size: "+userCluster.size());
				 if(userCluster.size()!=0) 
				 {
					 String msg="Data already exists in this Cluster";
				     model.addAttribute("message", msg);
					 
				 }
				 else {
			 
	 	java.util.Date date = new java.util.Date();
			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());

	 			
			modelUserCluster.setEntryTimestamp(entryTime);
			//modelUser2.setUserId(1L);
			
			 ModelUser user1=new ModelUser();
			 user1.setUserId(userId);
			modelUserCluster.setEnteredBy(user1);
			
			daoUserCluster.saveUserCluster(modelUserCluster);
			
			String msg="Successfully Saved";
        	model.addAttribute("message",msg );
				 }
			
				}
				else
				{
					
					Optional<ModelUserCluster> existsUserCluster=userClusterRepository.findById(modelUserCluster.getUserClusterId());
					modelUserCluster.setEnteredBy(existsUserCluster.get().getEnteredBy());
					modelUserCluster.setEntryTimestamp(existsUserCluster.get().getEntryTimestamp());
					
					
					ModelUser user1=new ModelUser();
					 user1.setUserId(userId);
					 
					modelUserCluster.setUpdatedBy(user1);
					modelUserCluster.setUpdateTimestap(updateTime);
					
					daoUserCluster.saveUserCluster(modelUserCluster);
					String msg="Successfully Saved";
		        	model.addAttribute("message",msg );
				}
			 
			 ModelUserCluster userCluster = new ModelUserCluster();
			 model.addAttribute("userCluster",userCluster);
			 
			 List<ModelUser> modelUser = daoUser.getAllUSerNameInOrder();
			 model.addAttribute("modelUser",modelUser);
			 
			 List<ModelCluster> modelCluster = daoCluster.getClusterName();
			 model.addAttribute("modelCluster",modelCluster);
			 
			 
			 
			 
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
						
						
						
						
						 Optional<ModelUserCluster> userClusterById=userClusterRepository.findById(modelUserCluster.getUserClusterId());
				    	 if( userClusterById.get().getActiveStatus()==1)
				    		 userClusterById.get().setActive(true); 
				    		else
				    			userClusterById.get().setActive(false);	
				            model.addAttribute("userCluster", userClusterById);
				            if(modelUserCluster.getUserClusterId()!=0) {
				            	String msg1="Successfully Saved ";
				            	 model.addAttribute("message",msg1 );
				            }
				            else
				            {
				            	String msg1="Some Error Occured";
				            	 model.addAttribute("message",msg1);
				            }
			 
			 
			 
			 
			 
	 
	 	 return "userCluster";
	 	
	 }
	 
	 @RequestMapping(path = "/userclustercontroller/update/{id}", method = RequestMethod.GET)
	    public String updateUserCluster(@PathVariable Long id, Model model) {
		 
		 
		 
		 
		 
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
		 
		 
		 
		 
	    	
	    	 Optional<ModelUserCluster> userClusterById=userClusterRepository.findById(id);
	    	 if( userClusterById.get().getActiveStatus()==1)
	    		 userClusterById.get().setActive(true); 
	    		else
	    			userClusterById.get().setActive(false);	
	            model.addAttribute("userCluster", userClusterById);
	            if(id!=0) {
	            	String msg=" ";
	            	 model.addAttribute("message",msg );
	            }
	            else
	            {
	            	String msg="Some Error Occured";
	            	 model.addAttribute("message",msg);
	            }
	    	
	         List<ModelUser> modelUser1 = daoUser.getAllUSerNameInOrder();
	   		 model.addAttribute("modelUser",modelUser1);
	   		 
	   		 List<ModelCluster> modelCluster = daoCluster.getClusterName();
	   		 model.addAttribute("modelCluster",modelCluster);
	            
	        return "userCluster";

	    }

}
