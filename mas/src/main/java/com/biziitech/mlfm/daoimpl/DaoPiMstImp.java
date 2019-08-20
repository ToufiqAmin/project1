package com.biziitech.mlfm.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.custom.model.ModelPIInquiryList;
import com.biziitech.mlfm.custom.model.ModelPIMstCustom;
import com.biziitech.mlfm.dao.DaoPiMst;
import com.biziitech.mlfm.model.ModelPIMst;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.repository.PiMstRepository;

@Service
public class DaoPiMstImp implements DaoPiMst  {
	
	@Autowired
	private PiMstRepository pIMstRepository;
	
	@Autowired
    private DataSource dataSource;
    //private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
    
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate=jdbcTemplate;
          }

	@Override
	public void savePIMst(ModelPIMst modelPIMst) {
		if(modelPIMst.isActive()) 
			modelPIMst.setActiveStatus(1);
		
		else 
			modelPIMst.setActiveStatus(0);
		
		pIMstRepository.save(modelPIMst);
		
	}
 
	@Override
	public List<ModelPIMstCustom> getPIMstDetails() {
		
		  
		String qry="SELECT a.PI_MST_ID, a.PI_DATE, a.USER_PI_NO, a.LC_NO, a.TERMSCON_DESC, a.pi_type, c.type_name, a.ORDER_OWNER_ID, g.owner_name FROM mlfm_pi_mst a INNER JOIN mlfm_pi_type c ON a.PI_TYPE=c.PI_TYPE_ID INNER JOIN mlfm_order_owner g ON a.ORDER_OWNER_ID=g.order_owner_id   WHERE a.ACTIVE_STATUS=1 AND NOT EXISTS (SELECT 1 FROM mlfm_pi_chd b WHERE b.PI_MST_ID=a.PI_MST_ID)";
		//String qry="SELECT a.PI_MST_ID, a.PI_DATE, a.USER_PI_NO, a.LC_NO, a.TERMSCON_DESC, a.pi_type, c.type_name FROM mlfm_pi_mst a INNER JOIN mlfm_pi_type c ON a.PI_TYPE=c.PI_TYPE_ID   WHERE a.ACTIVE_STATUS=1 AND NOT EXISTS (SELECT 1 FROM mlfm_pi_chd b WHERE b.PI_MST_ID=a.PI_MST_ID)";
		RowMapper<ModelPIMstCustom> serviceMapper=new RowMapper<ModelPIMstCustom>() {
			public ModelPIMstCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelPIMstCustom bn=new ModelPIMstCustom();
		
			   bn.setpIMstId(rs.getLong("pi_mst_id"));
			   System.out.println(rs.getLong("PI_MST_ID"));
			   bn.setpIDate(rs.getDate("PI_DATE"));
			   System.out.println(rs.getDate("PI_DATE"));
			   bn.setpIType(rs.getInt("pi_type"));
			   bn.setpITypeName(rs.getString("type_name"));
			   //bn.setpITypeName(rs.getString("type_name"));		   
			   bn.setTermsconDesc(rs.getString("TERMSCON_DESC"));
			   //System.out.println(rs.getString("TERMSCON_DESC"));
			   bn.setOrderOwnerId(rs.getLong("order_Owner_Id"));
			   bn.setOwnerName(rs.getString("owner_name"));
			    
			   return bn;
			    
			
			}
				
		};
		
		return jdbcTemplate.query(qry,serviceMapper);
	}
	
	
	@Override
	public List<ModelPIMstCustom> getPIMstById(Long id) {
		
		  
		String qry="select * from MLFM_PI_MST a where a.pi_mst_id=COALESCE(:id,a.pi_mst_id)";
		
		RowMapper<ModelPIMstCustom> serviceMapper=new RowMapper<ModelPIMstCustom>() {
			public ModelPIMstCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelPIMstCustom bn=new ModelPIMstCustom();
		
			   bn.setpIMstId(rs.getLong("pi_mst_id"));
			   System.out.println(rs.getLong("PI_MST_ID"));
			   bn.setUserPINo(rs.getString("user_pi_no"));
			   bn.setLcNo(rs.getString("lc_no"));
			   bn.setpIDate(rs.getDate("PI_DATE"));
			   System.out.println(rs.getDate("PI_DATE"));
			   bn.setRemarks(rs.getString("remarks"));
			   bn.setpIType(rs.getInt("pi_type"));
			   bn.setTermsconDesc(rs.getString("termscon_desc"));
			   bn.setOrderOwnerId(rs.getLong("order_owner_id"));
			   System.out.println(" id :" +rs.getString("order_owner_id"));
			    
			   return bn;
			    
			
			}
				
		};
		
		  MapSqlParameterSource parameters = new MapSqlParameterSource();
	      parameters.addValue("id", id);
	      
	     
		  System.out.println("id : " + id );
		
		return jdbcTemplate.query(qry,parameters,serviceMapper);
	}

	//@Override
	//public List<ModelPIMst> getPIMstById(Long id) {
		//
		//List<ModelPIMst> pIMSt=pIMstRepository.findPIMstById(id);
		
		//return pIMSt;
	//}
	
	
	
	/*@Override
	public List<ModelPIMst> getPIMstDetails() {
		
		  
		String qry="SELECT a.PI_MST_ID, a.PI_DATE, a.USER_PI_NO, a.LC_NO, a.pi_type, a.TERMSCON_DESC, a.pi_type FROM mlfm_pi_mst a WHERE a.ACTIVE_STATUS=1 AND NOT EXISTS (SELECT 1 FROM mlfm_pi_chd b WHERE b.PI_MST_ID=a.PI_MST_ID)";
		//String qry="SELECT a.PI_MST_ID, a.PI_DATE, a.USER_PI_NO, a.LC_NO, a.TERMSCON_DESC, a.pi_type, c.type_name FROM mlfm_pi_mst a INNER JOIN mlfm_pi_type c ON a.PI_TYPE=c.PI_TYPE_ID   WHERE a.ACTIVE_STATUS=1 AND NOT EXISTS (SELECT 1 FROM mlfm_pi_chd b WHERE b.PI_MST_ID=a.PI_MST_ID)";
		RowMapper<ModelPIMst> serviceMapper=new RowMapper<ModelPIMst>() {
			public ModelPIMst mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelPIMst bn=new ModelPIMst();
		
			   bn.setPIMstId(rs.getLong("PI_MST_ID"));
			   System.out.println(rs.getLong("PI_MST_ID"));
			   bn.setPiDate(rs.getDate("PI_DATE"));
			  
			  // System.out.println(rs.getDate("PI_DATE"));
			  // bn.setPIType(rs.getInt("PI_TYPE"));
			  // bn.setPITypeName(rs.getString("TYPE_NAME"));
			  // bn.setTermsconDesc(rs.getString("TERMSCON_DESC"));
			    
			   return bn;
			    
			
			}
				
		};
		
		return jdbcTemplate.query(qry,serviceMapper);
	}*/
			
}
