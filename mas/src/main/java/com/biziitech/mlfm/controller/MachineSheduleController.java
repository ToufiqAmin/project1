package com.biziitech.mlfm.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.exolab.castor.types.DateTime;
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
import com.biziitech.mlfm.custom.model.ModelMachineScheduleData;
import com.biziitech.mlfm.custom.model.ModelPIChdOrder;
import com.biziitech.mlfm.custom.model.ModelPIReceiveCustom;
import com.biziitech.mlfm.dao.DaoMachineShift;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.daoimpl.DaoMachineImp;
import com.biziitech.mlfm.daoimpl.DaoMachineScheduleImp;
import com.biziitech.mlfm.daoimpl.DaoMachineShiftImp;
import com.biziitech.mlfm.daoimpl.DaoMachineTypeImp;
import com.biziitech.mlfm.model.ModelMachine;
import com.biziitech.mlfm.model.ModelMachineShedule;
import com.biziitech.mlfm.model.ModelMachineShift;
import com.biziitech.mlfm.model.ModelMachineType;
import com.biziitech.mlfm.model.ModelPIReceiveMst;
import com.biziitech.mlfm.model.ModelShift;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.repository.MachineRepository;
import com.ibm.icu.impl.duration.Period;

@Controller
public class MachineSheduleController {
	
	@Autowired
	private DaoMachineTypeImp machineTypeImp;
	
	@Autowired
	private DaoMachineImp daoMachineImp;
	
	@Autowired
	private DaoMachineShiftImp machineShiftImp;
	
	@Autowired
	private DaoMachineShift daoMachineShift;
	
	
	@Autowired
	private DaoMachineScheduleImp machineScheduleImp;
	
	@Autowired
	private MachineRepository machineRepository;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


private Long systemUserId;
	
	
	@RequestMapping(path = "/machineshedule/init/{userId}", method = RequestMethod.GET)
    public String createmachineshedule(@PathVariable Long userId,Model model) {
		
		
		
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
	
		
		ModelMachine newmachine=new ModelMachine();
		model.addAttribute("newmachine", newmachine);
		
		List<ModelMachineType> machineName= machineTypeImp.getMachineName();
		model.addAttribute("machineTypeList",machineName);
		
		List<ModelShift> modelShift=daoMachineShift.getShiftList();
		model.addAttribute("shiftList",modelShift);
		
		model.addAttribute("machineList",machineRepository.findMachine());
		
	/*	HashMap<String, Integer> map = new HashMap<>(); 
		map.put("Sat", 0);
		map.put("Sun", 1);
		map.put("Mon", 2);
		map.put("Tue", 3);
		map.put("Wed", 4);
		map.put("Thu", 5);
		map.put("Fri", 6);
		model.addAttribute("dayList",map);
		
		*/
		  
        return "machine_shedule";
   }
	 
	    @RequestMapping(path="/machine/type")
		@ResponseBody
		public List<ModelMachine> findMachineName(@RequestParam("machine")Long id){
			
	    	
	    	System.out.println("MahchineType Id : " + id);
	    	
		return daoMachineImp.getMachineByType(id);
	    	
	}
	    
	    
	    @RequestMapping(path="/machine/shift")
		@ResponseBody
		public List<ModelMachineShift> findMachineShiftName(@RequestParam("machineId")Long id){
	    	
	    	System.out.println("Machine id :" +id);
				
		 return machineShiftImp.getMachineShiftByName(id);
		}
	    
	    
	     @ResponseBody
		 @RequestMapping(path = "/machineshedulecontroller/shedulecreate/save") 
		 public  List<ModelMachineScheduleData> saveMachineSheduleData(@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("startDate")LocalDate startDate,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("endDate")LocalDate endDate
				 ,@RequestParam("machineType") Long machineTypeId,@RequestParam("machine") Long machineId,@RequestParam("machineShift") Long shiftId,@RequestParam("scheduleName") String scheduleName) throws Exception {
		
			 System.out.println("Start Date: " + startDate);
			 System.out.println("End Date: " + endDate);
			 System.out.println("Machine TypeId: " + machineTypeId);
			 System.out.println("Machine Id: " + machineId);
			 System.out.println("Shift Id: " + shiftId);
			 System.out.println("Schedule Name :" + scheduleName);
			
			
			 
			 java.util.Date date = new java.util.Date();
		     java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		     
		   
			 SimpleDateFormat dt12 = new SimpleDateFormat("yyyy-MM-dd");
			
			
			 LocalDate sdate= startDate;
			 LocalDate edate= endDate;
			 Date serachStartDate=dt12.parse(sdate.toString());
			 Date serachEndDate=dt12.parse(edate.toString());
			      			 
			 
			 /*
			 
			 Date scheduleDate=newDate;
			 
			 long start=scheduleDate.getTime();
			 long end=newEndDate.getTime();
			 
			 long diff=(end-start)/(1000*60*60*24);
			 
			 System.out.println("diff " + diff);
			
			 
			 for (int i=1; i<=diff+1; i++) {
				 
				 System.out.println("day " + i);
				 
				 System.out.println("Today is :" + scheduleDate);
				 
	
				ModelMachineShedule machineSchedule= new ModelMachineShedule();
				ModelMachineShift modelShift=new ModelMachineShift(); 
				modelShift.setMachineShiftId(shiftId);
				 
				machineSchedule.setActiveStatus(1);
				machineSchedule.setEnteredBy(1L);
				machineSchedule.setEntryTimestamp(entryTime);
				machineSchedule.setMachineScheduleName(scheduleName);
				machineSchedule.setModelMachineShfit(modelShift);
				machineSchedule.setScheduleDate(scheduleDate);
				
				machineScheduleImp.saveSchedule(machineSchedule);
				 
				 
				 			 
				 
				 
			 }
		/*	 
		
			 String days[] = { "", "Monday", "Tuesday", "Wednesday", "Thursday",
			            "Friday", "Saturday", "Sunday" };
			   // boolean finish = false; 
			 
			 Date d1 = startDate;
			 Date d2 = endDate;
			 Calendar c1 = Calendar.getInstance();
		     c1.setTime(d1);
		     Calendar c2 = Calendar.getInstance();
		        c2.setTime(d2);

		        int sundays = 0;
		        int saturday = 0;
		        int totalDays;
		        

		        while (! c1.after(c2)) {
		            if (c1.get(Calendar.DAY_OF_MONTH) == Calendar.ALL_STYLES ){
		               saturday++; 
		            }
		            if(c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
		               sundays++;
		            }
		            
		            

		            c1.add(Calendar.DATE, 1);
		        }

		        System.out.println("Saturday Count = "+saturday);
		        System.out.println("Sunday Count = "+sundays);
		        
		      */ 
		     
		        LocalDate newEndDate= endDate;
		        
		        LocalDate specificEndDate=newEndDate.plusDays(1);
				
				
				for(LocalDate date1= startDate; date1.isBefore(specificEndDate); date1 = date1.plusDays(1))
				{
					SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
					
					Date checkDate=dt1.parse(date1.toString());
					
					List<ModelMachineScheduleData> data=machineScheduleImp.checkScheduleData(machineId, machineTypeId, shiftId, checkDate);
					
					if(data.size()==0) {
						
						 ModelMachineShedule machineSchedule= new ModelMachineShedule();
						   
						    ModelMachineShift modelShift=new ModelMachineShift(); 
							modelShift.setMachineShiftId(shiftId);
							 
							machineSchedule.setActiveStatus(1);
							machineSchedule.setEnteredBy(1L);
							machineSchedule.setEntryTimestamp(entryTime);
							machineSchedule.setMachineScheduleName(scheduleName);
							machineSchedule.setModelMachineShfit(modelShift);
							
							
							
							Date newDate=dt1.parse(date1.toString());
										
							machineSchedule.setScheduleDate(newDate);
							
							System.out.println("Today is :" + newDate);
							
						
							machineScheduleImp.saveSchedule(machineSchedule);
													
						
					}
					
					
					else {
						
						 System.out.println("Exits date is :" + checkDate); 
					}
				   
				   
										
					
				}
				
				
				List<ModelMachineScheduleData> data=machineScheduleImp.getMachineScheduleByMachineId(machineId, machineTypeId,shiftId, serachStartDate, serachEndDate);
			 
			    
		
		  return data;
		
	    }
	     
	     
	     
	     
	     @ResponseBody
		 @RequestMapping(path = "/machineshedulecontroller/get/data") 
		 public  List<ModelMachineScheduleData> getCreatedData(@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("startDate")Date startDate,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("endDate")Date endDate
				 ,@RequestParam("machineType") Long machineTypeId,@RequestParam("machine") Long machineId,@RequestParam("machineShift") Long shiftId,@RequestParam("scheduleName") String scheduleName) throws Exception {
		
			
			
				
			 List<ModelMachineScheduleData> data=machineScheduleImp.getMachineScheduleByMachineId(machineId, machineTypeId,shiftId, startDate, endDate);
			 
			    
		
		  return data;
	     }
	     
	     
	        @ResponseBody
		    @RequestMapping(path = "/machineschedulecontroller/inactive/active/save", method = RequestMethod.POST) 
		    public  List<ModelMachineScheduleData> activeInactiveData(@RequestParam("id")Long id,@RequestParam("verifyStatus")int activeStatus){
			    
	        	
	        	List<ModelMachineScheduleData> modelList=new ArrayList<ModelMachineScheduleData>();
			
	        	java.util.Date dates = new java.util.Date();
				java.sql.Timestamp entryTimestamp = new java.sql.Timestamp(dates.getTime());
				
				System.out.println("Status"  +activeStatus);
				
				if(activeStatus==1) {
					
				ModelMachineShedule schedule= new ModelMachineShedule();
				
				schedule.setUpdatedBy(1L);
				schedule.setUpdateTimestamp(entryTimestamp);
				schedule.setActiveStatus(1);
				schedule.setMachineScheduleId(id);
				
				machineScheduleImp.updateScheduleData(schedule);
				
				
				List<ModelMachineScheduleData> dataListMst=machineScheduleImp.activeInactiveData(id);
				
				modelList.addAll(dataListMst);
				
				}
				
				if(activeStatus==0) {
					
                ModelMachineShedule schedule1= new ModelMachineShedule();
				
				schedule1.setUpdatedBy(1L);
				schedule1.setUpdateTimestamp(entryTimestamp);
				schedule1.setActiveStatus(0);
				schedule1.setMachineScheduleId(id);
				
				machineScheduleImp.updateScheduleData(schedule1);
				
				
				List<ModelMachineScheduleData> dataList=machineScheduleImp.activeInactiveData(id);
				
				modelList.addAll(dataList);
					
				}
				
				
				return modelList;
	        }
	        
	      /*  
	         @ResponseBody
			 @RequestMapping(path = "/machineshedulecontroller/search/data") 
			 public  List<ModelMachineScheduleData> getSearchData(@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("startDate")Date startDate,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("endDate")Date endDate
					 ,@RequestParam("machineId") Long machineId,@RequestParam("shiftId") Long shiftId,@RequestParam("status") int status) throws Exception {
			
				
	        	 System.out.println("shift id: "+ shiftId);
	        	 System.out.println("satart date :" + startDate);
	        	 System.out.println("end date :" + endDate);
	        	 System.out.println("Status :" + status);
	        	 System.out.println("Machine Id :" + machineId);
				
					
				List<ModelMachineScheduleData> data=machineScheduleImp.getScheduleAllData(shiftId,machineId,startDate,endDate,status);
				 
			    
			
			  return data;
		     }
		     
		     */
		 
}