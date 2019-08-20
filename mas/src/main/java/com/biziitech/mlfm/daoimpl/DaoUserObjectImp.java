package com.biziitech.mlfm.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.bg.model.ModelObjectGroup;
import com.biziitech.mlfm.custom.model.ModelProductionCustom;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.model.ModelUserObject;

import com.biziitech.mlfm.repository.UserObjectRepository;

@Service
public class DaoUserObjectImp implements DaoUserObject {
	
	
	@Autowired
	private UserObjectRepository userObjectRepository;
	
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
	
	
	
	
	@Override
	public List<ModelUserObject> getUserObject(Long userId) {
		// TODO Auto-generated method stub
		return userObjectRepository.findUserObjectByUserId(userId);
	}
	
	
	@Override
	public List<ModelUserObject> getUserObjectByObjectGroup(Long userId, String objectGroup) {
		// TODO Auto-generated method stub
		return userObjectRepository.findUserObjectByUserIdObjectGroup(userId,objectGroup);
	}
	
	
	

	@Override
	public void saveUserObject(ModelUserObject modelUserObject) {
		
		
		userObjectRepository.save(modelUserObject);
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ModelUserObject> getUserObjectById(Long userId) {
		// TODO Auto-generated method stub
		/*
		String qry="SELECT uo.USER_ID,bo.OBJECT_ID\r\n" + 
				"FROM bg_user_object uo \r\n" + 
				"INNER JOIN bg_object bo ON bo.OBJECT_ID=uo.OBJECT_ID\r\n" + 
				"INNER JOIN bg_object_group og ON og.OBJECT_GROUP_ID=bo.OBJECT_GROUP_ID\r\n" + 
				"and not exists (SELECT 1 FROM bg_user bu where bu.user_id=uo.USER_ID)\r\n" + 
				"WHERE bo.OBJECT_GROUP_ID=COALESCE(@objectGroupId,bo.OBJECT_GROUP_ID) AND\r\n" + 
				"uo.USER_ID=COALESCE(@userId,uo.USER_ID)";
		*/
		
		/*
		SELECT uo.USER_ID,u.user_name,bo.OBJECT_ID,bo.OBJECT_NAME,bo.FILE_PATH
		FROM bg_object bo 
		LEFT OUTER JOIN bg_user_object uo ON bo.OBJECT_ID=uo.OBJECT_ID
		LEFT OUTER JOIN bg_object_group og ON og.OBJECT_GROUP_ID=bo.OBJECT_GROUP_ID
		LEFT OUTER JOIN bg_user u ON uo.USER_ID=u.user_id
		WHERE bo.OBJECT_GROUP_ID=COALESCE(@objectGroupId,bo.OBJECT_GROUP_ID) AND
		uo.USER_ID=COALESCE(20190406657,uo.USER_ID)
		*/
		
		
	//	return userObjectRepository.findUserObjectByUserId(userId);
		
		return userObjectRepository.findUserObject(userId);
	}


	@Override
	public List<ModelObjectGroup> getObjectGroup() {
		// TODO Auto-generated method stub
		return userObjectRepository.findObjectGroup();
	}


	@Override
	public List<ModelUserObject> getUserObjectByObjectGroupId(Long objectTypeId) {
		
		/*
		String  qry="SELECT * \r\n" + 
				"FROM bg_object o\r\n" + 
				"INNER JOIN bg_object_type ot ON ot.OBJECT_TYPE_ID=o.OBJECT_TYPE_ID\r\n" + 
				"WHERE ot.OBJECT_TYPE_ID=COALESCE(:objectTypeId,ot.OBJECT_TYPE_ID)";
	
		
		
		RowMapper<ModelUserObject> serviceMapper=new RowMapper<ModelUserObject>() {
			@Override
			public ModelUserObject mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelUserObject bn=new ModelUserObject();
				
			
				// bn.setProductionPlanMstId(rs.getLong("PRODUCTION_PLAN_MST_ID"));
		    return bn;
			
			}	
		};
		
		  MapSqlParameterSource parameters = new MapSqlParameterSource();
		  parameters.addValue("objectTypeId", objectTypeId);
		  System.out.println("query : " + qry);
	      
		
		return  namedJdbcTemplate.query(qry,parameters,serviceMapper);
		*/
		
		
		// TODO Auto-generated method stub
		
		
		 return userObjectRepository.findUserObjectByObjectGroupId(objectTypeId);
	}




	@Override
	public List<ModelUserObject> checkUserObject(Long userId, Long objectId) {
		
		String  qry="SELECT * FROM bg_user_object uo \r\n" + 
				"INNER JOIN bg_user u on u.user_id=uo.USER_ID\r\n" + 
				"INNER JOIN bg_object o ON o.OBJECT_ID=uo.OBJECT_ID\r\n" + 
				"WHERE u.user_id=COALESCE(:userId,u.user_id)\r\n" + 
				"AND o.OBJECT_ID=COALESCE(:objectId,o.OBJECT_ID)";
		
		/*
		 * 
		 * SELECT * FROM bg_user_object uo 
INNER JOIN bg_user u on u.user_id=uo.USER_ID
INNER JOIN bg_object o ON o.OBJECT_ID=uo.OBJECT_ID
WHERE u.user_id=COALESCE(@userId,u.user_id)
AND o.OBJECT_ID=COALESCE(@objectId,o.OBJECT_ID)
		 * 
		 * 
		 * */
	
		
		
		RowMapper<ModelUserObject> serviceMapper=new RowMapper<ModelUserObject>() {
			@Override
			public ModelUserObject mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelUserObject bn=new ModelUserObject();
				
			
				// bn.setProductionPlanMstId(rs.getLong("PRODUCTION_PLAN_MST_ID"));
		    return bn;
			
			}	
		};
		
		  MapSqlParameterSource parameters = new MapSqlParameterSource();
		  parameters.addValue("userId", userId);
		  parameters.addValue("objectId", objectId);
		  System.out.println("query : " + qry);
	      
		
		return  namedJdbcTemplate.query(qry,parameters,serviceMapper);
		
		// TODO Auto-generated method stub

	}



}
