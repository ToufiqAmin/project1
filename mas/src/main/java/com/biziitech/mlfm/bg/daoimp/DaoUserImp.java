package com.biziitech.mlfm.bg.daoimp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.bg.dao.DaoUser;
import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.custom.model.ModelMachineScheduleData;
import com.biziitech.mlfm.repository.UserRepository;

@Service
public class DaoUserImp implements DaoUser{
	
	
	@Autowired
    private DataSource dataSource;
	
	
        private JdbcTemplate jdbcTemplate;
        public void setDataSource(DataSource dataSource) {
            this.dataSource = dataSource;
            this.jdbcTemplate = new JdbcTemplate(dataSource);
          }
        
        
       
      
    	@Autowired
    	private NamedParameterJdbcTemplate namedJdbcTemplate;
        public void setNamedDataSource(DataSource dataSource) {
            this.dataSource = dataSource;
            this.namedJdbcTemplate=namedJdbcTemplate;
              }
	
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<ModelUser> getAllUSerName() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public List<ModelUser> getAllUSerNameInOrder() {
		// TODO Auto-generated method stub
		return userRepository.getUserNameInOrder();
	}

	@Override
	public void save(ModelUser modelUser) {
		// TODO Auto-generated method stub
		if(modelUser.isActive())
		{
			modelUser.setActiveStatus(1);
			
		}
		else
		{
			modelUser.setActiveStatus(0);
			
		}
		if(modelUser.isAllInquiryFlag()) 
		{
			modelUser.setAllInquiryFlagStatus(1);
		}
		else 
		{
			modelUser.setAllInquiryFlagStatus(0);
		}
		
		
		userRepository.save(modelUser);
	}

	@Override
	public List<ModelUser> getUserListByCraiteria(String userName, String titleName, String passportNo,
			int status) {
		// TODO Auto-generated method stub
		List<ModelUser> resultList= userRepository.findUserDetails(userName, titleName, passportNo, status);
		
		List<ModelUser> userList=new ArrayList<>();
		
		for(ModelUser user: resultList) {
				if(user.getActiveStatus()==1)
				 { 
					user.setsActive("Yes");
					user.setActive(true);
				 }
				 
				 else
				 {
					 user.setsActive("NO");
					 user.setActive(false);
				     
				 }
				
				if(user.getGenderStatus()==1) 
				{
					user.setsGender("Male");
					
				}
				else if(user.getGenderStatus()==2) 
				{
					user.setsGender("Female");
					
				}
				else if(user.getGenderStatus()==3) 
				{
					user.setsGender("Others");
				}
				 
				userList.add(user);
		}
		
		
		
		return userList;
	}
	
	
	@Override
	//public Long getUserIdByName(String pUserName) {
	public List<ModelUser> getUserIdByName(String pUserName) {
		
		
		String  qry="SELECT bu.USER_ID,bu.user_name\r\n" + 
				" FROM bg_user bu\r\n" + 
				" WHERE bu.user_name LIKE CONCAT('%',:userName, '%')";
			/*
			 * sql query
			 * 
			 * 
			 * SELECT bu.USER_ID,bu.user_name
FROM bg_user bu
WHERE bu.user_name = CONCAT(''',:userName,''')
			 * 
			 * 
			 * */
			
			RowMapper<ModelUser> serviceMapper=new RowMapper<ModelUser>() {
				@Override
				public ModelUser mapRow(ResultSet rs, int rownum) throws SQLException{
					ModelUser bn=new ModelUser();
					
				
					//bn.setShiftName(rs.getString("SHIFT_NAME"));
					
				    bn.setUserId(rs.getLong("USER_ID"));
				    bn.setUserName(rs.getString("user_name"));
	
			    return bn;
				
				}	
			};
			
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			
			  parameters.addValue("userName", pUserName);
		
		      
		      
		      System.out.println("query for  : " + qry);
		      
			// TODO Auto-generated method stub
			return  namedJdbcTemplate.query(qry,parameters,serviceMapper);
		
		//ModelUser userId=userRepository.getUserIdByName(pUserName);
		//Long userId=userRepository.getUserIdByName(pUserName);
		//return userId;
	}

	@Override
	public List<ModelUser> getUser(Long userId) {
			  
		return userRepository.getUser(userId);
		//return null;
	}

	@Override
	public List<ModelUser> checkFlag(Long userId) {
		
		String  qry="SELECT a.ALL_INQUIRY_FLAG FROM bg_user a WHERE a.user_id=coalesce(:userId,a.user_id)";
			
			
			RowMapper<ModelUser> serviceMapper=new RowMapper<ModelUser>() {
				@Override
				public ModelUser mapRow(ResultSet rs, int rownum) throws SQLException{
					ModelUser bn=new ModelUser();
					
				    bn.setAllInquiryFlagStatus(rs.getInt("ALL_INQUIRY_FLAG"));
		
	
			    return bn;
				
				}	
			};
			
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			
			  parameters.addValue("userId", userId);
		
		      
		      
		      System.out.println("query for  : " + qry);
		      
			
			return  namedJdbcTemplate.query(qry,parameters,serviceMapper);
		
	}

}
