package com.biziitech.mlfm.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.custom.model.ModelMachineScheduleData;
import com.biziitech.mlfm.custom.model.ModelWashingCustom;
import com.biziitech.mlfm.dao.DaoMachineSchedule;
import com.biziitech.mlfm.model.ModelMachineShedule;
import com.biziitech.mlfm.model.ModelPIReceiveMst;
import com.biziitech.mlfm.repository.MachineScheduleRepository;



@Service
public class DaoMachineScheduleImp implements DaoMachineSchedule {
    
	
	@Autowired
	private MachineScheduleRepository machineScheduleRepository;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	private JdbcTemplate jdbc;
	

	@Autowired
    private DataSource dataSource;
	
	
	
	@Override
	public void saveSchedule(ModelMachineShedule modelSchedule) {
		
		machineScheduleRepository.save(modelSchedule);
		
	}



	@Override
	public List<ModelMachineScheduleData> getMachineScheduleByMachineId(Long id,Long machineTypeId,Long machineShiftId,Date from,Date to) {
	
		String qry="SELECT c.start_time,c.end_time, a.ACTIVE_STATUS,d.MACHINE_NAME,a.MACHINE_SCHEDULE_ID,a.MACHINE_SCHEDULE_NAME,c.SHIFT_NAME,d.MACHINE_NAME,a.SCHEDULE_DATE FROM mlfm_machine_schedule a INNER JOIN mlfm_machine_shift b on a.MACHINE_SHIFT_ID=b.MACHINE_SHIFT_ID INNER JOIN mlfm_shift c ON b.SHIFT_ID=c.SHIFT_ID INNER JOIN mlfm_machine d ON b.MACHINE_ID=d.MACHINE_ID INNER JOIN mlfm_machine_type e ON d.machine_type_id=e.MACHINE_TYPE_ID WHERE d.MACHINE_ID=coalesce(:id,d.MACHINE_ID)"
				+ "and e.MACHINE_TYPE_ID=coalesce(:typeId,e.MACHINE_TYPE_ID)"
				+ "and b.MACHINE_SHIFT_ID=coalesce(:shiftId,b.MACHINE_SHIFT_ID)"
				+ "and a.SCHEDULE_DATE BETWEEN coalesce(:start_date,a.SCHEDULE_DATE) and coalesce(:end_date,a.SCHEDULE_DATE)";
				
		
				
				RowMapper<ModelMachineScheduleData> serviceMapper=new RowMapper<ModelMachineScheduleData>() {
					@Override
					public ModelMachineScheduleData mapRow(ResultSet rs, int rownum) throws SQLException{
						ModelMachineScheduleData bn=new ModelMachineScheduleData();
				        
						bn.setMachineName(rs.getString("machine_name"));
						bn.setMachineShiftName(rs.getString("shift_name"));
						bn.setScheduleDate(rs.getDate("schedule_date"));
						bn.setActiveStatus(rs.getInt("active_status"));
						bn.setMachineSheduleId(rs.getLong("machine_schedule_id"));
						bn.setStartTime(rs.getString("start_time"));
						bn.setEndTime(rs.getString("end_time"));
						
						
					
					return bn;
					
					}	
				};
				

				MapSqlParameterSource parameters = new MapSqlParameterSource();
			      
			      parameters.addValue("id", id);
			      parameters.addValue("typeId", machineTypeId);
			      parameters.addValue("shiftId", machineShiftId);
			      parameters.addValue("start_date", from);
			      parameters.addValue("end_date", to);
			      
			      
			      
			    
			   
			      

				return  jdbcTemplate.query(qry,parameters,serviceMapper);
			}



	@Override
	public List<ModelMachineScheduleData> checkScheduleData(Long id, Long machineTypeId, Long machineShiftId,
			Date scheduleDate) {
		String qry="SELECT  a.ACTIVE_STATUS,d.MACHINE_NAME,a.MACHINE_SCHEDULE_ID,a.MACHINE_SCHEDULE_NAME,c.SHIFT_NAME,d.MACHINE_NAME,a.SCHEDULE_DATE FROM mlfm_machine_schedule a INNER JOIN mlfm_machine_shift b on a.MACHINE_SHIFT_ID=b.MACHINE_SHIFT_ID INNER JOIN mlfm_shift c ON b.SHIFT_ID=c.SHIFT_ID INNER JOIN mlfm_machine d ON b.MACHINE_ID=d.MACHINE_ID INNER JOIN mlfm_machine_type e ON d.machine_type_id=e.MACHINE_TYPE_ID WHERE d.MACHINE_ID=coalesce(:id,d.MACHINE_ID)"
				+ "and e.MACHINE_TYPE_ID=coalesce(:typeId,e.MACHINE_TYPE_ID)"
				+ "and b.MACHINE_SHIFT_ID=coalesce(:shiftId,b.MACHINE_SHIFT_ID)"
				+ "and a.SCHEDULE_DATE=coalesce(:start_date,a.SCHEDULE_DATE)";
				
		
				
				RowMapper<ModelMachineScheduleData> serviceMapper=new RowMapper<ModelMachineScheduleData>() {
					@Override
					public ModelMachineScheduleData mapRow(ResultSet rs, int rownum) throws SQLException{
						ModelMachineScheduleData bn=new ModelMachineScheduleData();
				        
						bn.setMachineName(rs.getString("machine_name"));
						bn.setMachineShiftName(rs.getString("shift_name"));
						bn.setScheduleDate(rs.getDate("schedule_date"));
						bn.setActiveStatus(rs.getInt("active_status"));
						
						
					
					return bn;
					
					}	
				};
				

				MapSqlParameterSource parameters = new MapSqlParameterSource();
			      
			      parameters.addValue("id", id);
			      parameters.addValue("typeId", machineTypeId);
			      parameters.addValue("shiftId", machineShiftId);
			      parameters.addValue("start_date", scheduleDate);
			    
			      
			      
			      
			    
			   
			      

				return  jdbcTemplate.query(qry,parameters,serviceMapper);
			}
	
	
	public void updateScheduleData(ModelMachineShedule mst){
        try {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("update MLFM_MACHINE_SCHEDULE set active_status=?,updated_by=?, update_timestamp=? where machine_schedule_id=?",mst.getActiveStatus(),mst.getUpdatedBy(),mst.getUpdateTimestamp(),mst.getMachineScheduleId());
        	
        
        System.out.println("update success");
       }
        
        
        catch(Exception e) {
        	e.printStackTrace();
}

	}



	@Override
	public List<ModelMachineScheduleData> activeInactiveData(Long id) {
		String qry="SELECT c.start_time,c.end_time, a.ACTIVE_STATUS,d.MACHINE_NAME,a.MACHINE_SCHEDULE_ID,a.MACHINE_SCHEDULE_NAME,c.SHIFT_NAME,d.MACHINE_NAME,a.SCHEDULE_DATE FROM mlfm_machine_schedule a INNER JOIN mlfm_machine_shift b on a.MACHINE_SHIFT_ID=b.MACHINE_SHIFT_ID INNER JOIN mlfm_shift c ON b.SHIFT_ID=c.SHIFT_ID INNER JOIN mlfm_machine d ON b.MACHINE_ID=d.MACHINE_ID INNER JOIN mlfm_machine_type e ON d.machine_type_id=e.MACHINE_TYPE_ID WHERE a.MACHINE_SCHEDULE_ID=coalesce(:id,a.MACHINE_SCHEDULE_ID)";
				
				
				RowMapper<ModelMachineScheduleData> serviceMapper=new RowMapper<ModelMachineScheduleData>() {
					@Override
					public ModelMachineScheduleData mapRow(ResultSet rs, int rownum) throws SQLException{
						ModelMachineScheduleData bn=new ModelMachineScheduleData();
				        
						bn.setMachineName(rs.getString("machine_name"));
						bn.setMachineShiftName(rs.getString("shift_name"));
						bn.setScheduleDate(rs.getDate("schedule_date"));
						bn.setActiveStatus(rs.getInt("active_status"));
						bn.setMachineSheduleId(rs.getLong("machine_schedule_id"));
						bn.setStartTime(rs.getString("start_time"));
						bn.setEndTime(rs.getString("end_time"));
						
						
					
					return bn;
					
					}	
				};
				

				MapSqlParameterSource parameters = new MapSqlParameterSource();
			      
			      parameters.addValue("id", id);
			     
			      
	      

				return  jdbcTemplate.query(qry,parameters,serviceMapper);
			}



	@Override
	public List<ModelMachineScheduleData> getScheduleAllData(Long shiftId,Long machineId,Date from,Date to
			,int status) {
		String qry="SELECT c.start_time,c.end_time, a.ACTIVE_STATUS,d.MACHINE_NAME,a.MACHINE_SCHEDULE_ID,a.MACHINE_SCHEDULE_NAME,c.SHIFT_NAME,d.MACHINE_NAME,a.SCHEDULE_DATE FROM mlfm_machine_schedule a INNER JOIN mlfm_machine_shift b on a.MACHINE_SHIFT_ID=b.MACHINE_SHIFT_ID INNER JOIN mlfm_shift c ON b.SHIFT_ID=c.SHIFT_ID INNER JOIN mlfm_machine d ON b.MACHINE_ID=d.MACHINE_ID INNER JOIN mlfm_machine_type e ON d.machine_type_id=e.MACHINE_TYPE_ID WHERE c.shift_id=coalesce(:shiftId,c.shift_id)"
				+ "and d.MACHINE_ID=coalesce(:machineId,d.MACHINE_ID)"
				+ "and a.SCHEDULE_DATE BETWEEN coalesce(:start_date,a.SCHEDULE_DATE) and coalesce(:end_date,a.SCHEDULE_DATE)"
				+ "and a.active_status=coalesce(:active, a.active_status)";
				
				//+ "and a.SCHEDULE_DATE BETWEEN coalesce(:start_date,a.SCHEDULE_DATE) and coalesce(:end_date,a.SCHEDULE_DATE)";
				
				
		
				
				RowMapper<ModelMachineScheduleData> serviceMapper=new RowMapper<ModelMachineScheduleData>() {
					@Override
					public ModelMachineScheduleData mapRow(ResultSet rs, int rownum) throws SQLException{
						ModelMachineScheduleData bn=new ModelMachineScheduleData();
				        
						bn.setMachineName(rs.getString("machine_name"));
						bn.setMachineShiftName(rs.getString("shift_name"));
						bn.setScheduleDate(rs.getDate("schedule_date"));
						bn.setActiveStatus(rs.getInt("active_status"));
						bn.setMachineSheduleId(rs.getLong("machine_schedule_id"));
						
						bn.setStartTime(rs.getString("start_time"));
						bn.setEndTime(rs.getString("end_time"));
						
						
					
					return bn;
					
					}	
				};
				

				MapSqlParameterSource parameters = new MapSqlParameterSource();
			   
			      parameters.addValue("shiftId", shiftId);
			      parameters.addValue("machineId", machineId);
			      parameters.addValue("start_date", from);
			      parameters.addValue("end_date", to);
			      parameters.addValue("active", status);
				return  jdbcTemplate.query(qry,parameters,serviceMapper);
			}

	
     
}
